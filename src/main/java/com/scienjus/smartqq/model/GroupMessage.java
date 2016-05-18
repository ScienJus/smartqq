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

        final int size = cont.size();
        final StringBuilder contentBuilder = new StringBuilder();
        for (int i = 1; i < size; i++) {
            contentBuilder.append(cont.getString(i));
        }
        this.content = contentBuilder.toString();

        this.time = json.getLongValue("time");
        this.groupId = json.getLongValue("group_code");
        this.userId = json.getLongValue("send_uin");
    }

}
