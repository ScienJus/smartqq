package com.scienjus.smartqq.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.scienjus.smartqq.json.GsonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 讨论组消息
 * @author ScienJus
 * @author <a href="mailto:xianguang.zhou@outlook.com">Xianguang Zhou</a>
 * @since 15/12/19.
 */
public class DiscussMessage implements WithUserId {

    private long discussId;

    private long time;

    private List<MessageContentElement> contentElements;

    private long userId;

    private Font font;

    public DiscussMessage(JsonObject jsonObject) {
        JsonArray contentJsonArray = jsonObject.getAsJsonArray("content");

        this.font = GsonUtil.gson.fromJson(contentJsonArray.get(0).getAsJsonArray().get(1), Font.class);

	    final int contentJsonArraySize = contentJsonArray.size();
	    contentElements = new ArrayList<>(contentJsonArraySize - 1);
	    for (int i = 1; i < contentJsonArraySize; i++) {
		    contentElements.add(MessageContentElement.fromJson(contentJsonArray.get(i)));
	    }

	    this.time = jsonObject.get("time").getAsLong();
        this.discussId = jsonObject.get("did").getAsLong();
        this.userId = jsonObject.get("send_uin").getAsLong();
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

	public List<MessageContentElement> getContentElements() {
		return contentElements;
	}

	public void setContentElements(List<MessageContentElement> contentElements) {
		this.contentElements = contentElements;
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
                Objects.equals(contentElements, that.contentElements) &&
                Objects.equals(font, that.font);
    }

    @Override
    public int hashCode() {
        return Objects.hash(discussId, time, contentElements, userId, font);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DiscussMessage{");
        sb.append("discussId=").append(discussId);
        sb.append(", time=").append(time);
	    sb.append(", contentElements=").append(contentElements);
        sb.append(", userId=").append(userId);
        sb.append(", font=").append(font);
        sb.append('}');
        return sb.toString();
    }

}
