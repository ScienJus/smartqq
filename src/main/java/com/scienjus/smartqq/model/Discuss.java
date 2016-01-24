package com.scienjus.smartqq.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 讨论组
 * @author ScienJus
 * @date 2015/12/23.
 */
@Data
public class Discuss {

    @JSONField(name = "did")
    private long id;

    private String name;
}
