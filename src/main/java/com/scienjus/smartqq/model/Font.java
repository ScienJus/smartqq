package com.scienjus.smartqq.model;

import java.util.Arrays;

/**
 * 字体模型
 * @author ScienJus
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
    public String toString() {
        return "Font{" +
                "style=" + Arrays.toString(style) +
                ", color='" + color + '\'' +
                ", name='" + name + '\'' +
                ", size=" + size +
                '}';
    }
}
