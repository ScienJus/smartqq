package com.scienjus.smartqq.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.scienjus.smartqq.json.GsonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 群消息.
 *
 * @author ScienJus
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @author <a href="mailto:xianguang.zhou@outlook.com">Xianguang Zhou</a>
 * @since 15/12/19.
 */
public class GroupMessage implements WithUserId {

    private long groupId;

    private long time;

	private List<MessageContentElement> contentElements;

    private long userId;

    private Font font;

    public GroupMessage(JsonObject jsonObject) {
        JsonArray contentJsonArray = jsonObject.getAsJsonArray("content");

        this.font = GsonUtil.gson.fromJson(contentJsonArray.get(0).getAsJsonArray().get(1), Font.class);

	    final int contentJsonArraySize = contentJsonArray.size();
	    contentElements = new ArrayList<>(contentJsonArraySize - 1);
	    for (int i = 1; i < contentJsonArraySize; i++) {
		    contentElements.add(MessageContentElementUtil.fromJson(contentJsonArray.get(i)));
	    }

        this.time = jsonObject.get("time").getAsLong();
        this.groupId = jsonObject.get("group_code").getAsLong();
        this.userId = jsonObject.get("send_uin").getAsLong();
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
        GroupMessage that = (GroupMessage) o;
        return groupId == that.groupId &&
                time == that.time &&
                userId == that.userId &&
                Objects.equals(contentElements, that.contentElements) &&
                Objects.equals(font, that.font);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, time, contentElements, userId, font);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GroupMessage{");
        sb.append("groupId=").append(groupId);
        sb.append(", time=").append(time);
	    sb.append(", contentElements=").append(contentElements);
        sb.append(", userId=").append(userId);
        sb.append(", font=").append(font);
        sb.append('}');
        return sb.toString();
    }

}
