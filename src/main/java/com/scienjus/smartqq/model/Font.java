package com.scienjus.smartqq.model;

import java.util.Arrays;
import java.util.Objects;

/**
 * 字体
 * @author ScienJus
 * @author Xianguang Zhou <xianguang.zhou@outlook.com>
 * @date 15/12/19.
 */
public class Font {

    public static final Font DEFAULT_FONT = defaultFont();

    private static Font defaultFont() {
        Font font = new Font();
        font.setColor("000000");
        font.setStyle(new int[]{0, 0, 0});
        font.setName("宋体");
        font.setSize(10);
        return font;
    }

    private int[] style;

    private String color;

    private String name;

    private int size;

    public int[] getStyle() {
        return style;
    }

    public void setStyle(int[] style) {
        this.style = style;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Font font = (Font) o;
        return size == font.size &&
                Arrays.equals(style, font.style) &&
                Objects.equals(color, font.color) &&
                Objects.equals(name, font.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(style, color, name, size);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Font{");
        sb.append("style=").append(Arrays.toString(style));
        sb.append(", color='").append(color).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", size=").append(size);
        sb.append('}');
        return sb.toString();
    }
}
