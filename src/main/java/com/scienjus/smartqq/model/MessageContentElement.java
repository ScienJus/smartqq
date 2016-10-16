package com.scienjus.smartqq.model;

import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.scienjus.smartqq.exception.SmartqqException;
import com.scienjus.smartqq.json.GsonUtil;

/**
 * message content element
 *
 * @author <a href="mailto:xianguang.zhou@outlook.com">Xianguang Zhou</a>
 * @since 2016/08/21.
 */
public interface MessageContentElement {

	public static MessageContentElement fromJson(JsonElement jsonElement) {
		if (jsonElement.isJsonArray()) {
			JsonArray jsonArray = jsonElement.getAsJsonArray();
			String type = jsonArray.get(0).getAsString();
			if ("face".equals(type)) {
				return new Face(jsonArray);
			} else {
				throw new SmartqqException(String.format("未知的消息内容元素类型“%s”", type));
			}
		} else {
			return new Text(jsonElement.getAsString());
		}
	}

	public static String toContentJson(List<MessageContentElement> messageContentElements, Font font) {
		JsonArray jsonArray = new JsonArray();
		for (MessageContentElement element : messageContentElements) {
			if (element instanceof Text) {
				jsonArray.add(((Text) element).getString());
			} else if (element instanceof Face) {
				JsonArray faceJson = new JsonArray();
				faceJson.add("face");
				faceJson.add(((Face) element).getCode());
				jsonArray.add(faceJson);
			} else {
				throw new SmartqqException(String.format("未知的消息内容元素类型“%s”", element.getClass()));
			}
		}
		if (null == font) {
			font = Font.DEFAULT_FONT;
		}
		JsonArray fontJson = new JsonArray();
		fontJson.add("font");
		fontJson.add(GsonUtil.gson.toJsonTree(font, Font.class));
		jsonArray.add(fontJson);
		return GsonUtil.gson.toJson(jsonArray);
	}
}
