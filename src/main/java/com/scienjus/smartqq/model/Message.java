package com.scienjus.smartqq.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * 消息
 * @author ScienJus
 * @date 15/12/19.
 */
@Data
public class Message {

    private long time;

    private String content;

    private long userId;

    private Font font;

    public Message(JSONObject json) {
        JSONArray content = json.getJSONArray("content");
        this.font = content.getJSONArray(0).getObject(1, Font.class);
        this.content = content.getString(1);
        this.time = json.getLongValue("time");
        this.userId = json.getLongValue("from_uin");
    }

}
