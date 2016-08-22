package com.scienjus.smartqq.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.scienjus.smartqq.exception.SmartqqException;

/**
 * message content element
 *
 * @author Xianguang Zhou <xianguang.zhou@outlook.com>
 * @date 2016/08/21.
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

}
