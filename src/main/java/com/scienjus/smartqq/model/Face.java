package com.scienjus.smartqq.model;

import com.google.gson.JsonArray;

import java.util.Objects;

/**
 * face
 *
 * @author <a href="mailto:xianguang.zhou@outlook.com">Xianguang Zhou</a>
 * @since 2016/08/21.
 */
public class Face implements MessageContentElement {
	private int code;

	public Face() {
	}

	public Face(int code) {
		this.code = code;
	}

	public Face(JsonArray jsonArray) {
		this.code = jsonArray.get(1).getAsInt();
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Face face = (Face) o;
		return code == face.code;
	}

	@Override
	public int hashCode() {
		return Objects.hash(code);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Face{");
		sb.append("code=").append(code);
		sb.append('}');
		return sb.toString();
	}
}
