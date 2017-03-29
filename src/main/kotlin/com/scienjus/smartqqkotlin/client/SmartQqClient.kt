package com.scienjus.smartqqkotlin.client

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import com.scienjus.smartqqkotlin.constant.ApiUrl
import com.scienjus.smartqqkotlin.model.*
import com.scienjus.smartqqkotlin.util.Cache
import com.scienjus.smartqqkotlin.util.Event
import net.dongliu.requests.Client
import net.dongliu.requests.Response
import net.dongliu.requests.Session
import net.dongliu.requests.exception.RequestException
import org.apache.http.client.protocol.HttpClientContext
import org.apache.http.cookie.Cookie
import org.apache.log4j.Logger
import java.io.Closeable
import java.net.SocketTimeoutException
import java.time.Duration
import java.util.*
import java.util.function.Consumer

/**
 * Api客户端.
 * @author ScienJus
 * @author [Liang Ding](http://88250.b3log.org)
 * @date 2015/12/18.
 */

class SmartQqClient constructor(var retryTimes: Long, _cacheTimeout: Duration) : Closeable {
    constructor() : this(5, Duration.ofHours(1))

    // 缓存超时
    var cacheTimeout: Duration = _cacheTimeout
        get
        set(value) {
            field = value
            _friends.timeout = value
            _friendCategories.timeout = value
            _groups.timeout = value
            _discussions.timeout = value
            _chatHistories.timeout = value
            _myInfo.timeout = value
        }

    // 状态
    @Volatile var status: ClientStatus = ClientStatus.IDLE
        get
        private set
    // 日志
    internal val LOGGER = Logger.getLogger(SmartQqClient::class.java)

    // 客户端id，固定的
    private val CLIENT_ID: Long = 53999199

    // HTTP客户端
    private val client: Client = Client.pooled().maxPerRoute(5).maxTotal(10).build()
    internal lateinit var session: Session

    // 消息id
    private var messageId: Long = 43690001

    // 二维码令牌
    private lateinit var qrsig: String
    private var hash33: Int = 0

    // 鉴权参数
    private lateinit var ptwebqq: String
    private lateinit var vfwebqq: String
    private var uin: Long = 0
    private lateinit var psessionid: String
    private lateinit var hash: String

    // 线程开关
    private @Volatile var pollStarted = false

    // 事件
    val friendMessageReceived = Event<FriendMessage>()
    val groupMessageReceived = Event<GroupMessage>()
    val discussionMessageReceived = Event<DiscussionMessage>()
    val messageSent = Event<MessageSentEventArgs>()
    val connectionClosed = Event<Unit>()

    private val _friends = Cache<List<Friend>>(cacheTimeout)
    private val _friendCategories = Cache<List<FriendCategory>>(cacheTimeout)
    private val _groups = Cache<List<Group>>(cacheTimeout)
    private val _discussions = Cache<List<Discussion>>(cacheTimeout)
    private val _chatHistories = Cache<List<ChatHistory>>(cacheTimeout)
    private val _myInfo = Cache<FriendInfo>(cacheTimeout)

    val friends: List<Friend>
        get() = _friends.getValue {
            requireLoggedIn()
            LOGGER.debug("开始获取好友列表")

            val result = ApiUrl.GET_FRIEND_LIST.post(this, JSONObject(mapOf(
                    "vfwebqq" to vfwebqq,
                    "hash" to hash
            ))).getJson().getJSONObject("result")!!

            val statusArray = ApiUrl.GET_FRIEND_STATUS.get(this, vfwebqq, psessionid)
                    .getJson().getJSONArray("result")
            val idToStatus = statusArray.indices.map { statusArray.getJSONObject(it) }.map {
                it.getLong("uin") to Pair(it.getString("status"), it.getInteger("client_type"))
            }.toMap()

            val categoryArray = result.getJSONArray("friends")
            val idToCategoryIndex = categoryArray.indices.map { categoryArray.getJSONObject(it) }.map {
                it.getLong("uin") to it.getInteger("categories")
            }.toMap()

            val aliasArray = result.getJSONArray("marknames")
            val idToAlias = aliasArray.indices.map { aliasArray.getJSONObject(it) }.map {
                it.getLong("uin") to it.getString("markname")
            }.toMap()

            val vipArray = result.getJSONArray("vipinfo")
            val idToVip = vipArray.indices.map { vipArray.getJSONObject(it) }.map {
                it.getLong("uin") to Pair(it.getInteger("is_vip") == 1, it.getInteger("vip_level"))
            }.toMap()

            val infoArray = result.getJSONArray("info")
            infoArray.indices.map { infoArray.getJSONObject(it) }.map {
                Friend(
                        this,
                        it.getLong("uin"),
                        idToCategoryIndex[it.getLong("uin")]!!,
                        it.getString("nick"),
                        idToAlias[it.getLong("uin")],
                        idToStatus[it.getLong("uin")]?.first,
                        idToStatus[it.getLong("uin")]?.second,
                        idToVip[it.getLong("uin")]?.first ?: false,
                        idToVip[it.getLong("uin")]?.second ?: 0)
            }
        }!!
    val friendCategories: List<FriendCategory>
        get() = _friendCategories.getValue {
            requireLoggedIn()
            LOGGER.debug("开始获取好友分组列表")
            val result = ApiUrl.GET_FRIEND_LIST.post(this, JSONObject(mapOf(
                    "vfwebqq" to vfwebqq,
                    "hash" to hash
            ))).getJson().getJSONObject("result").getJSONArray("categories")

            result.indices.map {
                FriendCategory(
                        this,
                        it,
                        if (it == 0) "我的好友" else result.getJSONObject(it).getString("name"))
            }
        }!!
    val groups: List<Group>
        get() = _groups.getValue {
            requireLoggedIn()
            LOGGER.debug("开始获取群列表")

            val result = ApiUrl.GET_GROUP_LIST.post(this, JSONObject(mapOf(
                    "vfwebqq" to vfwebqq,
                    "hash" to hash
            ))).getJson().getJSONObject("result")
            result.getJSONArray("gnamelist").indices.map {
                val obj = result.getJSONArray("gnamelist").getJSONObject(it)
                Group(this, obj.getLong("gid"), obj.getLong("code"), obj.getString("name"))
            }
        }!!
    val discussions: List<Discussion>
        get() = _discussions.getValue {
            requireLoggedIn()
            LOGGER.debug("开始获取讨论组列表")

            val result = ApiUrl.GET_DISCUSS_LIST.get(this, psessionid, vfwebqq).getJson().getJSONObject("result").getJSONArray("dnamelist")
            result.indices.map {
                val obj = result.getJSONObject(it)
                Discussion(
                        this,
                        obj.getLong("did"),
                        obj.getString("name"))
            }
        }!!
    val chatHistories: List<ChatHistory>
        get() = _chatHistories.getValue {
            requireLoggedIn()
            LOGGER.debug("开始获取最近会话列表")
            val result = ApiUrl.GET_RECENT_LIST.post(this, JSONObject(mapOf(
                    "vfwebqq" to vfwebqq,
                    "clientid" to CLIENT_ID,
                    "psessionid" to ""
            ))).getJson().getJSONArray("result")
            result.indices.map { result.getJSONObject(it) }.map {
                ChatHistory(
                        this,
                        it.getLong("uin"),
                        ChatHistory.HistoryType.fromInt(it.getInteger("type")))
            }
        }!!
    private val myInfo: FriendInfo
        get() = _myInfo.getValue {
            LOGGER.debug("开始获取登录用户信息")
            FriendInfo(ApiUrl.GET_ACCOUNT_INFO.get(this).getJson().getJSONObject("result"))
        }!!

    val id: Long
        get() = myInfo.id
    val nickname: String?
        get() = myInfo.nickname
    val bio: String?
        get() = myInfo.bio
    val gender: String?
        get() = myInfo.gender
    val phone: String?
        get() = myInfo.phone
    val cellphone: String?
        get() = myInfo.cellphone
    val email: String?
        get() = myInfo.email
    val homepage: String?
        get() = myInfo.homepage
    val birthday: Date
        get() = myInfo.birthday
    val school: String?
        get() = myInfo.school
    val job: String?
        get() = myInfo.job
    val bloodType: Int
        get() = myInfo.bloodType
    val country: String?
        get() = myInfo.country
    val province: String?
        get() = myInfo.province
    val city: String?
        get() = myInfo.city
    val personal: String?
        get() = myInfo.personal
    val shengxiao: Int
        get() = myInfo.shengxiao
    val account: String?
        get() = myInfo.account
    val vipInfo: Int
        get() = myInfo.vipInfo
    val qqNumber: Long
        get() = getQqNumber(id)

    private fun requireLoggedIn() {
        if (status != ClientStatus.ACTIVE) throw IllegalStateException("尚未登录，无法进行该操作")
    }

    /**
     * 使用二维码登录SmartQQ。
     * @param qrCodeDownloadedCallback 二维码获取回调
     */
    fun start(qrCodeDownloadedCallback: Consumer<ByteArray>): LoginResult {
        startPreExecute()
        try {
            login(qrCodeDownloadedCallback)
        } catch (ex: QrCodeExpiredException) {
            status = ClientStatus.IDLE
            return LoginResult.QR_CODE_EXPIRED
        } catch (ex: Exception) {
            LOGGER.error(ex)
            status = ClientStatus.IDLE
            return LoginResult.FAILED
        }
        return startPostExecute()
    }

    /**
     * 使用导出的cookie登录SmartQQ。
     */
    fun start(cookies: List<Cookie>): LoginResult {
        startPreExecute()
        try {
            val field = session::class.java.getDeclaredField("context")
            field.isAccessible = true
            val store = (field.get(session) as HttpClientContext).cookieStore
            cookies.forEach { cookie -> store.addCookie(cookie) }
        } catch (ex: Exception) {
            LOGGER.error(ex)
            status = ClientStatus.IDLE
            return LoginResult.FAILED
        }
        return startPostExecute()
    }

    private fun startPreExecute() {
        if (status != ClientStatus.IDLE) {
            throw IllegalStateException("已登录或正在登录，请勿重复登录")
        }
        status = ClientStatus.LOGGING_IN
        session = client.session()
    }

    private fun startPostExecute(): LoginResult {
        if (!testLogin()) return LoginResult.FAILED
        status = ClientStatus.ACTIVE
        startMessageLoop()
        return LoginResult.SUCCEEDED
    }

    /**
     * 手动退出客户端。
     */
    override fun close() {
        if (status == ClientStatus.IDLE) {
            throw IllegalStateException("尚未登录，无法退出")
        }
        status = ClientStatus.IDLE
        pollStarted = false
        invalidateCache()
    }

    /**
     * 手动清除缓存。
     */
    fun invalidateCache() {
        _friends.invalidate()
        _friendCategories.invalidate()
        _groups.invalidate()
        _discussions.invalidate()
        _chatHistories.invalidate()
        _myInfo.invalidate()
    }

    /**
     * 导出cookie。
     */
    fun dumpCookies(): List<Cookie> {
        requireLoggedIn()
        val field = session::class.java.getDeclaredField("context")
        field.isAccessible = true
        return (field.get(session) as HttpClientContext).cookieStore.cookies
    }

    // 开始消息循环
    private fun startMessageLoop() {
        pollStarted = true
        Thread(Runnable {
            while (pollStarted) {
                try {
                    pollMessage()
                } catch (e: RequestException) {
                    // 忽略SocketTimeoutException
                    when (e.cause) {
                        is SocketTimeoutException -> LOGGER.debug(e.message)
                        else -> {
                            close()
                            connectionClosed.invoke(Unit)
                        }
                    }
                } catch (e: Exception) {
                    LOGGER.error(e.message)
                }
            }
        }).start()
    }

    // 登录
    private fun login(qrCodeDownloadedCallback: Consumer<ByteArray>) {
        getQrCode(qrCodeDownloadedCallback)
        val url = verifyQrCode()
        getPtwebqq(url)
        getVfwebqq()
        getUinAndPsessionid()
        if (!testLogin()) {
            throw RequestException("发生未知错误：获取在线好友列表的请求失败")
        }
    }

    // 登录流程1：获取二维码
    private fun getQrCode(qrCodeDownloadedCallback: Consumer<ByteArray>) {
        LOGGER.debug("开始获取二维码")
        val response = session.get(ApiUrl.GET_QR_CODE.url)
                .addHeader("User-Agent", ApiUrl.USER_AGENT)
                .bytes()
        qrsig = response.cookies.find { it.name == "qrsig" }!!.value
        hash33 = calculateHash33()
        qrCodeDownloadedCallback.accept(response.body)
        LOGGER.info("二维码已获取")
    }

    // 登录流程2：校验二维码
    private fun verifyQrCode(): String {
        LOGGER.debug("等待扫描二维码")

        // 阻塞直到确认二维码认证成功
        while (true) {
            Thread.sleep(1000)
            val response = ApiUrl.VERIFY_QR_CODE.get(this, hash33)
            val result = response.body
            if (result.contains("成功")) {
                LOGGER.info("二维码已扫描")
                ptwebqq = response.cookies.find { it.name == "ptwebqq" }!!.value
                return result.split(("','").toRegex()).dropLastWhile(String::isEmpty).toTypedArray().find { item -> item.startsWith("http") }!!
            } else if (result.contains("已失效")) {
                LOGGER.warn("二维码已失效")
                throw QrCodeExpiredException()
            }
        }

    }

    // 登录流程3：获取ptwebqq
    private fun getPtwebqq(url: String) {
        LOGGER.debug("开始获取ptwebqq")
        ApiUrl.GET_PTWEBQQ.get(this, url)
    }

    // 登录流程4：获取vfwebqq
    private fun getVfwebqq() {
        LOGGER.debug("开始获取vfwebqq")
        vfwebqq = ApiUrl.GET_VFWEBQQ.get(this, ptwebqq).getJson().getJSONObject("result").getString("vfwebqq")
    }

    // 登录流程5：获取uin和psessionid
    private fun getUinAndPsessionid() {
        LOGGER.debug("开始获取uin和psessionid")
        val result = ApiUrl.GET_UIN_AND_PSESSIONID.post(this, JSONObject(mapOf(
                "ptwebqq" to ptwebqq,
                "clientid" to CLIENT_ID,
                "psessionid" to "",
                "status" to "online"))).getJson().getJSONObject("result")
        psessionid = result.getString("psessionid")
        uin = result.getLongValue("uin")
        hash = calculateHash()
    }

    // 计算hash值
    private fun calculateHash(): String {
        val N = IntArray(4)
        for (T in 0..ptwebqq.length - 1) {
            N[T % 4] = N[T % 4] xor ptwebqq[T].toInt()
        }
        val U = arrayOf("EC", "OK")
        val V = LongArray(4)
        V[0] = (uin shr 24) and 255 xor U[0][0].toLong()
        V[1] = (uin shr 16) and 255 xor U[0][1].toLong()
        V[2] = (uin shr 8) and 255 xor U[1][0].toLong()
        V[3] = uin and 255 xor U[1][1].toLong()

        val U1 = LongArray(8)

        for (T in 0..7) {
            U1[T] = if (T % 2 == 0) N[T shr 1].toLong() else V[T shr 1]
        }

        val N1 = arrayOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F")
        var V1 = ""
        for (aU1 in U1) {
            V1 += N1[((aU1 shr 4) and 15).toInt()]
            V1 += N1[(aU1 and 15).toInt()]
        }
        return V1
    }

    // 用于生成ptqrtoken的哈希函数
    private fun calculateHash33(): Int {
        var e = 0
        val n = qrsig.length
        var i = 0
        while (n > i) {
            e += (e shl 5) + qrsig[i].toInt()
            ++i
        }
        return 2147483647 and e
    }

    // 测试登录状态（顺便解决103问题）
    private fun testLogin(): Boolean {
        val response = ApiUrl.TEST_LOGIN.get(this, vfwebqq, CLIENT_ID, psessionid, Math.random())
        return response.statusCode == 200 && JSON.parseObject(response.body).getIntValue("retcode") == 0
    }

    // 拉取消息
    private fun pollMessage() {
        LOGGER.debug("开始接收消息")
        val response = ApiUrl.POLL_MESSAGE.post(this, JSONObject(mapOf(
                "ptwebqq" to ptwebqq,
                "clientid" to CLIENT_ID,
                "psessionid" to psessionid,
                "key" to "")))
        val array = response.getJson().getJSONArray("result") ?: return
        for (i in array.indices) {
            val message = array.getJSONObject(i)
            val type = message.getString("poll_type")
            when (type) {
                "message" -> friendMessageReceived.invoke(FriendMessage(this, message.getJSONObject("value")))
                "group_message" -> groupMessageReceived.invoke(GroupMessage(this, message.getJSONObject("value")))
                "discu_message" -> discussionMessageReceived.invoke(DiscussionMessage(this, message.getJSONObject("value")))
            }
        }
    }

    /**
     * 获得QQ号。
     * @param id 用户ID
     * @return QQ号
     */
    fun getQqNumber(id: Long): Long {
        LOGGER.debug("开始获取QQ号")

        val response = ApiUrl.GET_QQ_BY_ID.get(this, id, vfwebqq)
        return response.getJson().getJSONObject("result").getLongValue("account")
    }

    /**
     * 发送消息。
     * @param type 消息对象类型
     * @param id 对象ID
     * @param content 消息内容
     */
    fun message(type: TargetType, id: Long, content: String) {
        requireLoggedIn()
        LOGGER.debug("开始发送消息，对象类型：$type")
        val response = (when (type) {
            TargetType.FRIEND -> ApiUrl.SEND_MESSAGE_TO_FRIEND
            TargetType.GROUP -> ApiUrl.SEND_MESSAGE_TO_GROUP
            TargetType.DISCUSSION -> ApiUrl.SEND_MESSAGE_TO_DISCUSS
        }).postWithRetry(this, JSONObject(mapOf(
                when (type) {
                    TargetType.FRIEND -> "to"
                    TargetType.GROUP -> "group_uin"
                    TargetType.DISCUSSION -> "did"
                } to id,
                "content" to JSONArray(listOf(
                        content,
                        JSONArray(listOf("font", Font.DEFAULT_FONT))
                )).toString(),
                "face" to "573",
                "clientid" to CLIENT_ID,
                "msg_id" to messageId++,
                "psessionid" to psessionid
        )))

        if (response.statusCode != 200) {
            LOGGER.error("消息发送失败，HTTP返回码${response.statusCode}")
        }
        val json = response.getJson().getJSONObject("result")
        val errCode = json.getInteger("errCode")
        when (errCode) {
            0 -> {
                LOGGER.debug("消息发送成功")
                messageSent.invoke(MessageSentEventArgs(when (type) {
                    TargetType.FRIEND -> friends
                    TargetType.GROUP -> groups
                    TargetType.DISCUSSION -> discussions
                }.find({ n -> n.id == id })!!, content))
            }
            100100 -> LOGGER.debug("消息已发送，返回码100100")
            null -> LOGGER.warn("消息已发送，API返回异常")
            else -> LOGGER.error("发送失败，API返回码${json.getInteger("retcode")}")
        }
    }

    /**
     * 获得好友的详细信息
     */
    internal fun getFriendInfo(id: Long): FriendInfo {
        LOGGER.debug("开始获取好友资料")
        return FriendInfo(ApiUrl.GET_FRIEND_INFO.get(this, id, vfwebqq, psessionid).getJson().getJSONObject("result"))
    }

    /**
     * 获得群的详细信息
     */
    internal fun getGroupInfo(code: Long): GroupInfo {
        LOGGER.debug("开始获取群资料")

        val result = ApiUrl.GET_GROUP_INFO.get(this, code, vfwebqq).getJson().getJSONObject("result")
        // 获得群成员信息
        val statusArray = result.getJSONArray("stats")
        val idToStatus = statusArray.indices.map { statusArray.getJSONObject(it) }.map {
            it.getLongValue("uin") to Pair(it.getIntValue("client_type"), it.getIntValue("stat"))
        }.toMap()

        val aliasArray = result.getJSONArray("cards")
        val idToAlias = aliasArray.indices.map { aliasArray.getJSONObject(it) }.map {
            it.getLongValue("muin") to it.getString("card")
        }.toMap()

        val vipArray = result.getJSONArray("vipinfo")
        val idToVip = vipArray.indices.map { vipArray.getJSONObject(it) }.map {
            it.getLongValue("u") to Pair(it.getIntValue("is_vip") == 1, it.getIntValue("vip_level"))
        }.toMap()

        val memberArray = result.getJSONArray("minfo")
        val members = memberArray.indices.map { memberArray.getJSONObject(it) }.map {
            GroupMember(
                    this,
                    it.getLong("uin"),
                    it.getString("nick"),
                    idToAlias[it.getLong("uin")],
                    it.getString("gender"),
                    it.getString("country"),
                    it.getString("province"),
                    it.getString("city"),
                    idToStatus[it.getLong("uin")]?.first ?: 0,
                    idToStatus[it.getLong("uin")]?.second ?: 0,
                    idToVip[it.getLong("uin")]?.first ?: false,
                    idToVip[it.getLong("uin")]?.second ?: 0)
        }

        val info = result.getJSONObject("ginfo")
        return GroupInfo(info.getLong("gid"), info.getString("name"), info.getLong("owner"), info.getLongValue("createTime"), info.getString("memo"), members)
    }

    /**
     * 获得讨论组的详细信息
     */
    internal fun getDiscussInfo(discussId: Long): DiscussionInfo {
        LOGGER.debug("开始获取讨论组资料")

        val result = ApiUrl.GET_DISCUSS_INFO.get(this, discussId, vfwebqq, psessionid).getJson().getJSONObject("result")

        val statusArray = result.getJSONArray("mem_status")
        val idToStatus = statusArray.indices.map { statusArray.getJSONObject(it) }.map {
            it.getLong("uin") to Pair(it.getIntValue("client_type"), it.getString("status"))
        }.toMap()

        val memberArray = result.getJSONArray("mem_info")
        val members = memberArray.indices.map { memberArray.getJSONObject(it) }.map {
            DiscussionMember(this, it.getLong("uin"), it.getString("nick"), idToStatus[it.getLong("uin")]?.first ?: 0, idToStatus[it.getLong("uin")]?.second)
        }

        val info = result.getJSONObject("info")
        return DiscussionInfo(info.getLong("did"), info.getString("discu_name"), members)
    }

    // 检验Json返回结果
    private fun Response<String>.getJson(): JSONObject {
        if (this.statusCode != 200) {
            throw RequestException("请求失败，HTTP返回码${this.statusCode}")
        }
        val json = JSON.parseObject(this.body)
        val code: Int? = json.getInteger("retcode")
        when (code) {
            0 -> LOGGER.debug("请求已完成")
            null -> throw RequestException("请求失败，API返回异常", SmartQqClient.ApiException())
            103 -> LOGGER.error("请求失败，API返回码103。你需要进入http://w.qq.com，检查是否能正常接收消息。如果可以的话点击[设置]->[退出登录]后查看是否恢复正常")
            100100 -> LOGGER.debug("请求已完成，API返回码100100")
            else -> throw RequestException("请求失败，API返回码$code", SmartQqClient.ApiException())
        }
        return json
    }

    /**
     * 登录结果。
     */
    enum class LoginResult {
        SUCCEEDED, QR_CODE_EXPIRED, FAILED
    }

    /**
     * 客户端状态。
     */
    enum class ClientStatus {
        IDLE, LOGGING_IN, ACTIVE
    }

    /**
     * 消息目标类型。
     */
    enum class TargetType {
        FRIEND, GROUP, DISCUSSION
    }

    /**
     * 此异常指示登录流程由于二维码失效而被迫中止。
     */
    internal class QrCodeExpiredException : Exception()

    /**
     * 此异常指示因为客户端拒绝了请求而引起了错误。
     */
    internal class ApiException : Exception()

    /**
     * 「消息已发送」事件的回调参数结构。
     */
    data class MessageSentEventArgs(val target: MessageTarget, val content: String)
}