package com.scienjus.smartqq.kotlin.constant

import com.alibaba.fastjson.JSONObject
import com.scienjus.smartqq.kotlin.client.SmartQqClient
import net.dongliu.requests.Response

/**
 * Api的请求地址和Referer
 * @author ScienJus
 * @date 2015/12/19
 */
internal enum class ApiUrl constructor(val url: String, val referer: String?) {
    GET_QR_CODE(
            "https://ssl.ptlogin2.qq.com/ptqrshow?appid=501004106&e=0&l=M&s=5&d=72&v=4&t=0.1",
            ""
    ),
    VERIFY_QR_CODE(
            "https://ssl.ptlogin2.qq.com/ptqrlogin?" +
                    "ptqrtoken={1}&webqq_type=10&remember_uin=1&login2qq=1&aid=501004106&" +
                    "u1=http%3A%2F%2Fw.qq.com%2Fproxy.html%3Flogin2qq%3D1%26webqq_type%3D10&" +
                    "ptredirect=0&ptlang=2052&daid=164&from_ui=1&pttype=1&dumy=&fp=loginerroralert&0-0-157510&" +
                    "mibao_css=m_webqq&t=undefined&g=1&js_type=0&js_ver=10184&login_sig=&pt_randsalt=3",
            "https://ui.ptlogin2.qq.com/cgi-bin/login?" +
                    "daid=164&target=self&style=16&mibao_css=m_webqq&appid=501004106&enable_qlogin=0&no_verifyimg=1&" +
                    "s_url=http%3A%2F%2Fw.qq.com%2Fproxy.html&f_url=loginerroralert&strong_login=1&login_state=10&t=20131024001"
    ),
    GET_PTWEBQQ(
            "{1}",
            null
    ),
    GET_VFWEBQQ(
            "http://s.web2.qq.com/api/getvfwebqq?ptwebqq={1}&clientid=53999199&psessionid=&t=0.1",
            "http://s.web2.qq.com/proxy.html?v=20130916001&callback=1&id=1"
    ),
    GET_UIN_AND_PSESSIONID(
            "http://d1.web2.qq.com/channel/login2",
            "http://d1.web2.qq.com/proxy.html?v=20151105001&callback=1&id=2"
    ),
    TEST_LOGIN(
            "http://d1.web2.qq.com/channel/get_online_buddies2?vfwebqq={1}&clientid=53999199&psessionid={2}&t=0.1",
            "http://d1.web2.qq.com/proxy.html?v=20151105001&callback=1&id=2"
    ),
    GET_GROUP_LIST(
            "http://s.web2.qq.com/api/get_group_name_list_mask2",
            "http://d1.web2.qq.com/proxy.html?v=20151105001&callback=1&id=2"
    ),
    POLL_MESSAGE(
            "http://d1.web2.qq.com/channel/poll2",
            "http://d1.web2.qq.com/proxy.html?v=20151105001&callback=1&id=2"
    ),
    SEND_MESSAGE_TO_GROUP(
            "http://d1.web2.qq.com/channel/send_qun_msg2",
            "http://d1.web2.qq.com/proxy.html?v=20151105001&callback=1&id=2"
    ),
    GET_FRIEND_LIST(
            "http://s.web2.qq.com/api/get_user_friends2",
            "http://s.web2.qq.com/proxy.html?v=20130916001&callback=1&id=1"
    ),
    SEND_MESSAGE_TO_FRIEND(
            "http://d1.web2.qq.com/channel/send_buddy_msg2",
            "http://d1.web2.qq.com/proxy.html?v=20151105001&callback=1&id=2"
    ),
    GET_DISCUSS_LIST(
            "http://s.web2.qq.com/api/get_discus_list?clientid=53999199&psessionid={1}&vfwebqq={2}&t=0.1",
            "http://s.web2.qq.com/proxy.html?v=20130916001&callback=1&id=1"
    ),
    SEND_MESSAGE_TO_DISCUSS(
            "http://d1.web2.qq.com/channel/send_discu_msg2",
            "http://d1.web2.qq.com/proxy.html?v=20151105001&callback=1&id=2"
    ),
    GET_ACCOUNT_INFO(
            "http://s.web2.qq.com/api/get_self_info2?t=0.1",
            "http://s.web2.qq.com/proxy.html?v=20130916001&callback=1&id=1"
    ),
    GET_RECENT_LIST(
            "http://d1.web2.qq.com/channel/get_recent_list2",
            "http://d1.web2.qq.com/proxy.html?v=20151105001&callback=1&id=2"
    ),
    GET_FRIEND_STATUS(
            "http://d1.web2.qq.com/channel/get_online_buddies2?vfwebqq={1}&clientid=53999199&psessionid={2}&t=0.1",
            "http://d1.web2.qq.com/proxy.html?v=20151105001&callback=1&id=2"
    ),
    GET_GROUP_INFO(
            "http://s.web2.qq.com/api/get_group_info_ext2?gcode={1}&vfwebqq={2}&t=0.1",
            "http://s.web2.qq.com/proxy.html?v=20130916001&callback=1&id=1"
    ),
    GET_QQ_BY_ID(
            "http://s.web2.qq.com/api/get_friend_uin2?tuin={1}&type=1&vfwebqq={2}&t=0.1",
            "http://s.web2.qq.com/proxy.html?v=20130916001&callback=1&id=1"
    ),
    GET_DISCUSS_INFO(
            "http://d1.web2.qq.com/channel/get_discu_info?did={1}&vfwebqq={2}&clientid=53999199&psessionid={3}&t=0.1",
            "http://d1.web2.qq.com/proxy.html?v=20151105001&callback=1&id=2"
    ),
    GET_FRIEND_INFO(
            "http://s.web2.qq.com/api/get_friend_info2?tuin={1}&vfwebqq={2}&clientid=53999199&psessionid={3}&t=0.1",
            "http://s.web2.qq.com/proxy.html?v=20130916001&callback=1&id=1"
    );

    fun buildUrl(vararg params: Any): String {
        var i = 1
        var url = this.url
        for (param in params) {
            url = url.replace("{" + i++ + "}", param.toString())
        }
        return url
    }

    fun get(client: SmartQqClient, vararg params: Any): Response<String> {
        val request = client.session.get(buildUrl(*params)).addHeader("User-Agent", USER_AGENT)
        if (referer != null) request.addHeader("Referer", referer)
        return request.text()
    }

    fun post(client: SmartQqClient, r: JSONObject): Response<String>
            = client.session.post(url)
            .addHeader("User-Agent", USER_AGENT)
            .addHeader("Referer", referer)
            .addHeader("Origin", origin)
            .addForm("r", r.toJSONString())
            .text()

    fun postWithRetry(client: SmartQqClient, r: JSONObject): Response<String> {
        var times = 0
        var response: Response<String>
        do {
            response = post(client, r)
            times++
        } while (times < client.retryTimes && response.statusCode != 200)
        return response
    }

    val origin: String
        get() = this.url.substring(0, url.lastIndexOf("/"))

    companion object {
        val USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.33 Safari/537.36"
    }
}
