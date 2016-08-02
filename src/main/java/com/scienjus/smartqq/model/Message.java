package com.scienjus.smartqq.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.scienjus.smartqq.json.GsonUtil;

import java.util.Objects;

/**
 * 消息.
 *
 * @author ScienJus
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @author Xianguang Zhou <xianguang.zhou@outlook.com>
 * @date 15/12/19.
 */
public class Message {

    private long time;

    private String content;

    private long userId;

    private Font font;

    public Message(JsonObject json) {
        JsonArray cont = json.getAsJsonArray("content");
        this.font = GsonUtil.gson.fromJson(cont.get(0).getAsJsonArray().get(1), Font.class);

        final int size = cont.size();
        final StringBuilder contentBuilder = new StringBuilder();
        for (int i = 1; i < size; i++) {
            contentBuilder.append(cont.get(i).getAsString());
        }
        this.content = contentBuilder.toString();

        this.time = json.get("time").getAsLong();
        this.userId = json.get("from_uin").getAsLong();
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
        Message message = (Message) o;
        return time == message.time &&
                userId == message.userId &&
                Objects.equals(content, message.content) &&
                Objects.equals(font, message.font);
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, content, userId, font);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Message{");
        sb.append("time=").append(time);
        sb.append(", content='").append(content).append('\'');
        sb.append(", userId=").append(userId);
        sb.append(", font=").append(font);
        sb.append('}');
        return sb.toString();
    }
}
