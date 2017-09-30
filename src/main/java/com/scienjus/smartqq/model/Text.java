package com.scienjus.smartqq.model;

import java.util.Objects;

/**
 * text
 *
 * @author <a href="mailto:xianguang.zhou@outlook.com">Xianguang Zhou</a>
 * @since 2016/08/21.
 */
public class Text implements MessageContentElement {
	private String string;

	public Text() {
	}

	public Text(String string) {
		this.string = string;
	}

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Text text = (Text) o;
		return Objects.equals(string, text.string);
	}

	@Override
	public int hashCode() {
		return Objects.hash(string);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Text{");
		sb.append("string='").append(string).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
