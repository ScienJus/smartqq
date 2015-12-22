package com.scienjus.smartqq.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.scienjus.smartqq.callback.MessageCallback;
import com.scienjus.smartqq.constant.ApiUrl;
import com.scienjus.smartqq.model.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.*;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Api客户端
 * @author ScienJus
 * @date 2015/12/18.
 */
public class SmartQQClient {

    //日志
    private static final Logger LOGGER = Logger.getLogger(SmartQQClient.class);

    //执行拉取消息的线程池
    private static final ScheduledThreadPoolExecutor POOL = new ScheduledThreadPoolExecutor(20);

    //消息id，这个好像可以随便设置，所以设成全局的
    private static long MESSAGE_ID = 43690001;

    //保存cookie信息
    private HttpClientContext context = HttpClientContext.create();

    //鉴权参数
    private String ptwebqq;

    private String vfwebqq;

    private long uin;

    private String psessionid;

    //拉取消息的线程
    private ScheduledFuture pollMessageFuture;

    /**
     * 登录
     */
    public void login() {
        getQRCode();
        String url = verifyQRCode();
        getPtwebqq(url);
        getVfwebqq();
        getUinAndPsessionid();
    }

    //登录流程1：获取二维码
    private void getQRCode() {
        LOGGER.info("开始获取二维码");
        //发送请求的客户端
        CloseableHttpClient client = HttpClients.createDefault();
        String filePath = getClass().getResource("/").getPath().concat("qrcode.png");
        HttpGet get = defaultHttpGet(ApiUrl.GET_QE_CODE);
        try (CloseableHttpResponse response = client.execute(get, context);
            FileOutputStream out = new FileOutputStream(filePath)) {
            int len;
            byte[] buffer = new byte[1024];
            while ((len = response.getEntity().getContent().read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            out.close();
            LOGGER.info("二维码已保存在 " + filePath + " 文件中，请打开手机QQ并扫描二维码");
        } catch (IOException e) {
            LOGGER.error("获取二维码失败");
        }
    }

    //登录流程2：校验二维码
    private String verifyQRCode() {
        LOGGER.info("等待扫描二维码");
        HttpGet get = defaultHttpGet(ApiUrl.VERIFY_QR_CODE);
        //发送请求的客户端
        CloseableHttpClient client = HttpClients.createDefault();

        //阻塞直到确认二维码认证成功
        while (true) {
            sleep(1);
            try (CloseableHttpResponse response = client.execute(get, context)) {
                String responseText = getResponseText(response);
                if (responseText.contains("成功")) {
                    for (String content : responseText.split("','")) {
                        if (content.startsWith("http")) {
                            return content;
                        }
                    }
                } else if (responseText.contains("已失效")) {
                    LOGGER.info("二维码已失效，尝试重新获取二维码");
                    getQRCode();
                }
            } catch (IOException e) {
                LOGGER.error("校验二维码失败");
            }
        }

    }

    //登录流程3：获取ptwebqq
    private void getPtwebqq(String url) {
        LOGGER.info("开始获取ptwebqq");
        //发送请求的客户端
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = defaultHttpGet(ApiUrl.GET_PTWEBQQ, url);
        try {
            client.execute(get, context);
            for (Cookie cookie : context.getCookieStore().getCookies()) {
                if (cookie.getName().equals("ptwebqq")) {
                    this.ptwebqq = cookie.getValue();
                }
            }
        } catch (IOException e) {
            LOGGER.error("获取ptwebqq失败");
        }
    }

    //登录流程4：获取vfwebqq
    private void getVfwebqq() {
        LOGGER.info("开始获取vfwebqq");
        //发送请求的客户端
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = defaultHttpGet(ApiUrl.GET_VFWEBQQ, ptwebqq);
        try (CloseableHttpResponse response = client.execute(get, context)) {
            JSONObject responseJson = JSON.parseObject(getResponseText(response));
            this.vfwebqq = responseJson.getJSONObject("result").getString("vfwebqq");
        } catch (IOException e) {
            LOGGER.error("获取vfwebqq失败");
        }
    }

    //登录流程5：获取uin和psessionid
    private void getUinAndPsessionid() {
        LOGGER.info("开始获取uin和psessionid");
        JSONObject r = new JSONObject();
        r.put("ptwebqq", ptwebqq);
        r.put("clientid", 53999199);
        r.put("psessionid", "");
        r.put("status", "online");
        try {
            HttpPost post = defaultHttpPost(ApiUrl.GET_UIN_AND_PSESSIONID, new BasicNameValuePair("r", r.toJSONString()));
            try (CloseableHttpClient client = HttpClients.createDefault();
                 CloseableHttpResponse response = client.execute(post, context)) {
                JSONObject responseJson = JSON.parseObject(getResponseText(response));
                this.psessionid = responseJson.getJSONObject("result").getString("psessionid");
                this.uin = responseJson.getJSONObject("result").getLongValue("uin");
            } catch (IOException e) {
                LOGGER.error("获取uin和psessionid失败");
            }
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("获取uin和psessionid失败");
        }

    }

    /**
     * 获取群列表
     * @return
     */
    public List<Group> getGroupList() {
        LOGGER.info("开始获取群列表");
        JSONObject r = new JSONObject();
        r.put("vfwebqq", vfwebqq);
        r.put("hash", hash());
        try {
            //发送请求的客户端
            HttpPost post = defaultHttpPost(ApiUrl.GET_GROUP_LIST, new BasicNameValuePair("r", r.toJSONString()));
            post.setEntity(new UrlEncodedFormEntity(Arrays.asList(new BasicNameValuePair("r", r.toJSONString()))));
            try (CloseableHttpClient client = HttpClients.createDefault();
                 CloseableHttpResponse response = client.execute(post, context)) {
                JSONObject responseJson = JSON.parseObject(getResponseText(response));
                return JSON.parseArray(responseJson.getJSONObject("result").getJSONArray("gnamelist").toJSONString(), Group.class);
            } catch (IOException e) {
                LOGGER.error("获取群列表失败");
            }
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("获取群列表失败");
        }
        return Collections.emptyList();
    }

    /**
     * 拉取消息
     * @param callback  获取消息后的回调
     */
    public void pollMessage(MessageCallback callback) {
        LOGGER.info("开始接受消息");
        JSONObject r = new JSONObject();
        r.put("ptwebqq", ptwebqq);
        r.put("clientid", 53999199);
        r.put("psessionid", psessionid);
        r.put("key", "");
        try {
            HttpPost post = defaultHttpPost(ApiUrl.POLL_MESSAGE, new BasicNameValuePair("r", r.toJSONString()));
            if (pollMessageFuture != null) {
                pollMessageFuture.cancel(false);
            }
            pollMessageFuture = POOL.scheduleWithFixedDelay(new PollMessageTask(post, callback), 1, 1, TimeUnit.MILLISECONDS);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("获取接受消息失败");
        }
    }

    /**
     * 停止拉取消息
     */
    public void stopPoll() {
        if (pollMessageFuture != null) {
            pollMessageFuture.cancel(false);
        }
        pollMessageFuture = null;
    }

    /**
     * 发送群消息
     * @param groupId
     * @param msg
     */
    public void sendMessageToGroup(long groupId, String msg) {
        LOGGER.info("开始发送群消息");
        JSONObject r = new JSONObject();
        r.put("group_uin", groupId);
        r.put("content", JSON.toJSONString(Arrays.asList(msg, Arrays.asList("font", Font.DEFAULT_FONT))));  //注意这里虽然格式是Json，但是实际是String
        r.put("face", 573);
        r.put("clientid", 53999199);
        r.put("msg_id", MESSAGE_ID++);
        r.put("psessionid", psessionid);
        try {
            HttpPost post = defaultHttpPost(ApiUrl.SEND_MESSAGE_TO_GROUP, new BasicNameValuePair("r", r.toJSONString()));
            try (CloseableHttpClient client = HttpClients.createDefault();
                 CloseableHttpResponse response = client.execute(post, context)) {
                JSONObject responseJson = JSON.parseObject(getResponseText(response));
                if (0 == responseJson.getIntValue("errCode")) {
                    LOGGER.error("发送群消息成功");
                } else {
                    LOGGER.error("发送群消息失败");
                }
            } catch (IOException e) {
                LOGGER.error("发送群消息失败");
            }
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("发送群消息失败");
        }
    }

    /**
     * 发送消息
     * @param friendId
     * @param msg
     */
    public void sendMessageToFriend(long friendId, String msg) {
        LOGGER.info("开始发送消息");
        JSONObject r = new JSONObject();
        r.put("to", friendId);
        r.put("content", JSON.toJSONString(Arrays.asList(msg, Arrays.asList("font", Font.DEFAULT_FONT))));  //注意这里虽然格式是Json，但是实际是String
        r.put("face", 573);
        r.put("clientid", 53999199);
        r.put("msg_id", MESSAGE_ID++);
        r.put("psessionid", psessionid);
        try {
            //发送请求的客户端
            HttpPost post = defaultHttpPost(ApiUrl.SEND_MESSAGE_TO_FRIEND, new BasicNameValuePair("r", r.toJSONString()));
            try (CloseableHttpClient client = HttpClients.createDefault();
                 CloseableHttpResponse response = client.execute(post, context)) {
                JSONObject responseJson = JSON.parseObject(getResponseText(response));
                if (0 == responseJson.getIntValue("errCode")) {
                    LOGGER.error("发送消息成功");
                } else {
                    LOGGER.error("发送消息失败");
                }
            } catch (IOException e) {
                LOGGER.error("发送消息失败");
            }
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("发送消息失败");
        }
    }

    /**
     * 获取好友列表
     * @return
     */
    public List<Category> getFriendList() {
        LOGGER.info("开始获取好友列表");
        JSONObject r = new JSONObject();
        r.put("vfwebqq", vfwebqq);
        r.put("hash", hash());
        try {
            HttpPost post = defaultHttpPost(ApiUrl.GET_FRIEND_LIST, new BasicNameValuePair("r", r.toJSONString()));
            try (CloseableHttpClient client = HttpClients.createDefault();
                 CloseableHttpResponse response = client.execute(post, context)) {
                JSONObject responseJson = JSON.parseObject(getResponseText(response));
                if (0 == responseJson.getIntValue("retcode")) {
                    JSONObject result = responseJson.getJSONObject("result");
                    //获得分组
                    JSONArray categories = result.getJSONArray("categories");
                    Map<Integer, Category> categoryMap = new HashMap<>();
                    categoryMap.put(0, Category.defaultCategory());
                    for (int i = 0; categories != null && i < categories.size(); i++) {
                        Category category = categories.getObject(i, Category.class);
                        categoryMap.put(category.getIndex(), category);
                    }
                    //获得好友信息
                    Map<Long, Friend> friendMap = new HashMap<>();
                    JSONArray friends = result.getJSONArray("friends");
                    for (int i = 0; friends != null && i < friends.size(); i++) {
                        JSONObject item = friends.getJSONObject(i);
                        Friend friend = new Friend();
                        friend.setUserId(item.getLongValue("uin"));
                        friendMap.put(friend.getUserId(), friend);
                        categoryMap.get(item.getIntValue("categories")).addFriend(friend);
                    }
                    JSONArray marknames = result.getJSONArray("marknames");
                    for (int i = 0; marknames != null && i < marknames.size(); i++) {
                        JSONObject item = marknames.getJSONObject(i);
                        friendMap.get(item.getLongValue("uin")).setMarkname(item.getString("markname"));
                    }
                    JSONArray info = result.getJSONArray("info");
                    for (int i = 0; info != null && i < info.size(); i++) {
                        JSONObject item = info.getJSONObject(i);
                        friendMap.get(item.getLongValue("uin")).setNickname(item.getString("nick"));
                    }
                    JSONArray vipinfo = result.getJSONArray("vipinfo");
                    for (int i = 0; vipinfo != null && i < vipinfo.size(); i++) {
                        JSONObject item = vipinfo.getJSONObject(i);
                        Friend friend = friendMap.get(item.getLongValue("u"));
                        friend.setVip(item.getIntValue("is_vip") == 1);
                        friend.setVipLevel(item.getIntValue("vip_level"));
                    }
                    return new ArrayList<>(categoryMap.values());
                } else {
                    LOGGER.error("获取好友列表失败");
                }
            } catch (IOException e) {
                LOGGER.error("获取好友列表失败");
            }
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("获取好友列表失败");
        }
        return Collections.EMPTY_LIST;
    }

    //hash加密方法
    private String hash() {
        return hash(uin, ptwebqq);
    }

    //线程暂停
    private static void sleep(long seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {}
    }

    //hash加密方法
    private static String hash(long x, String K) {
        int[] N = new int[4];
        for (int T = 0; T < K.length(); T++) {
            N[T % 4] ^= K.charAt(T);
        }
        String[] U = {"EC", "OK"};
        long[] V = new long[4];
        V[0] = x >> 24 & 255 ^ U[0].charAt(0);
        V[1] = x >> 16 & 255 ^ U[0].charAt(1);
        V[2] = x >> 8 & 255 ^ U[1].charAt(0);
        V[3] = x & 255 ^ U[1].charAt(1);

        long[] U1 = new long[8];

        for (int T = 0; T < 8; T++) {
            U1[T] = T % 2 == 0 ? N[T >> 1] : V[T >> 1];
        }

        String[] N1 = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
        String V1 = "";
        for (long aU1 : U1) {
            V1 += N1[(int) ((aU1 >> 4) & 15)];
            V1 += N1[(int) (aU1 & 15)];
        }
        return V1;
    }

    //得到返回的数据
    private static String getResponseText(CloseableHttpResponse response) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))) {
            StringBuilder buffer = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            return buffer.toString();
        } catch (IOException e) {
            LOGGER.error("获取返回数据失败");
        }
        return "";
    }

    //默认的http get
    private static HttpGet defaultHttpGet(ApiUrl apiUrl, Object... params) {
        String url = apiUrl.buildUrl(params);
        HttpGet get = new HttpGet(url);
        if (apiUrl.getReferer() != null) {
            get.setHeader("Referer", apiUrl.getReferer());
        }
        get.setHeader("User-Agent", ApiUrl.getUserAgent());
        return get;
    }

    //默认的http post
    private static HttpPost defaultHttpPost(ApiUrl apiUrl, BasicNameValuePair... params) throws UnsupportedEncodingException {
        HttpPost post = new HttpPost(apiUrl.getUrl());
        if (apiUrl.getReferer() != null) {
            post.setHeader("Referer", apiUrl.getReferer());
        }
        post.setEntity(new UrlEncodedFormEntity(Arrays.asList(params), "UTF-8"));
        post.setHeader("Origin", apiUrl.getOrigin());
        post.setHeader("User-Agent", ApiUrl.getUserAgent());
        return post;
    }

    //拉取消息的线程
    private class PollMessageTask implements Runnable {

        //请求的post方法
        private HttpPost post;

        //拉取到消息的回调
        private MessageCallback callback;

        public PollMessageTask(HttpPost post, MessageCallback callback) {
            this.post = post;
            this.callback = callback;
        }

        @Override
        public void run() {
            try (CloseableHttpClient client = HttpClients.createDefault();
                 CloseableHttpResponse response = client.execute(post, context)) {
                JSONObject responseJson = JSON.parseObject(getResponseText(response));

                if (responseJson.getIntValue("retcode") == 0) {
                    JSONArray array = responseJson.getJSONArray("result");
                    for (int i = 0; array != null && i < array.size(); i++) {
                        JSONObject message = array.getJSONObject(i);
                        String type = message.getString("poll_type");
                        if ("message".equals(type)) {
                            callback.onMessage(new Message(message.getJSONObject("value")));
                        } else if ("group_message".equals(type)) {
                            callback.onGroupMessage(new GroupMessage(message.getJSONObject("value")));
                        }
                    }
                } else {
                    LOGGER.error("接受消息失败 retcode: " + responseJson.getIntValue("retcode"));
                }
            } catch (IOException e) {
                LOGGER.error("暂时没有消息");
            }
        }
    }
}
