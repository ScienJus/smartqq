package com.scienjus.smartqq.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.scienjus.smartqq.json.GsonUtil;

import java.util.Objects;

/**
 * 讨论组消息
 * @author ScienJus
 * @author Xianguang Zhou <xianguang.zhou@outlook.com>
 * @date 15/12/19.
 */
public class DiscussMessage {

    private long discussId;

    private long time;

    private String content;

    private long userId;

    private Font font;

    public DiscussMessage(JsonObject json) {
        JsonArray content = json.getAsJsonArray("content");
        this.font = GsonUtil.gson.fromJson(content.get(0).getAsJsonArray().get(1), Font.class);
        this.content = content.get(1).getAsString();
        this.time = json.get("time").getAsLong();
        this.discussId = json.get("did").getAsLong();
        this.userId = json.get("send_uin").getAsLong();
    }

    public long getDiscussId() {
        return discussId;
    }

    public void setDiscussId(long discussId) {
        this.discussId = discussId;
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
        DiscussMessage that = (DiscussMessage) o;
        return discussId == that.discussId &&
                time == that.time &&
                userId == that.userId &&
                Objects.equals(content, that.content) &&
                Objects.equals(font, that.font);
    }

    @Override
    public int hashCode() {
        return Objects.hash(discussId, time, content, userId, font);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DiscussMessage{");
        sb.append("discussId=").append(discussId);
        sb.append(", time=").append(time);
        sb.append(", content='").append(content).append('\'');
        sb.append(", userId=").append(userId);
        sb.append(", font=").append(font);
        sb.append('}');
        return sb.toString();
    }
}
