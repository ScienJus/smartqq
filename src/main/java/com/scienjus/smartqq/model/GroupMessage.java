package com.scienjus.smartqq.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.scienjus.smartqq.json.GsonUtil;

import java.util.Objects;

/**
 * 群消息.
 *
 * @author ScienJus
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @author Xianguang Zhou <xianguang.zhou@outlook.com>
 * @date 15/12/19.
 */
public class GroupMessage {

    private long groupId;

    private long time;

    private String content;

    private long userId;

    private Font font;

    public GroupMessage(JsonObject json) {
        JsonArray cont = json.getAsJsonArray("content");
        this.font = GsonUtil.gson.fromJson(cont.get(0).getAsJsonArray().get(1),Font.class);

        final int size = cont.size();
        final StringBuilder contentBuilder = new StringBuilder();
        for (int i = 1; i < size; i++) {
            contentBuilder.append(cont.get(i).getAsString());
        }
        this.content = contentBuilder.toString();

        this.time = json.get("time").getAsLong();
        this.groupId = json.get("group_code").getAsLong();
        this.userId = json.get("send_uin").getAsLong();
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
