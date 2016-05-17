package com.scienjus.smartqq.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * 群消息.
 *
 * @author ScienJus
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @date 15/12/19.
 */
@Data
public class GroupMessage {

    private long groupId;

    private long time;

    private String content;

    private long userId;

    private Font font;

    public GroupMessage(JSONObject json) {
        JSONArray cont = json.getJSONArray("content");
        this.font = cont.getJSONArray(0).getObject(1, Font.class);

        if (2 == cont.size()) { // 接收群内普通消息
            this.content = cont.getString(1);
        } else if (4 == cont.size()) { // 接收群内 @用户 的消息
            this.content = cont.getString(3);
        }

        this.time = json.getLongValue("time");
        this.groupId = json.getLongValue("group_code");
        this.userId = json.getLongValue("send_uin");
    }

}
