package com.scienjus.smartqq.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Objects;

/**
 * 群消息.
 *
 * @author ScienJus
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @date 15/12/19.
 */
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

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupMessage that = (GroupMessage) o;
        return groupId == that.groupId &&
                time == that.time &&
                userId == that.userId &&
                Objects.equals(content, that.content) &&
                Objects.equals(font, that.font);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, time, content, userId, font);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GroupMessage{");
        sb.append("groupId=").append(groupId);
        sb.append(", time=").append(time);
        sb.append(", content='").append(content).append('\'');
        sb.append(", userId=").append(userId);
        sb.append(", font=").append(font);
        sb.append('}');
        return sb.toString();
    }
}
