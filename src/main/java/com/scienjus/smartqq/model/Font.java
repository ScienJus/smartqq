package com.scienjus.smartqq.model;

import lombok.Data;

/**
 * 字体
 * @author ScienJus
 * @date 15/12/19.
 */
@Data
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

}
