package com.scienjus.smartqq.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

/**
 * Gson 工具
 *
 * @author Xianguang Zhou <xianguang.zhou@outlook.com>
 * @date 2016/07/31.
 */
public class GsonUtil {
    public static final Gson gson = new GsonBuilder().create();
    public static final JsonParser jsonParser = new JsonParser();
}
