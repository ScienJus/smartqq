package com.scienjus.smartqq.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 群
 * @author ScienJus
 * @date 2015/12/18.
 */
@Data
public class Group {

    @JSONField(name = "gid")
    private long id;

    private String name;

    private long flag;

    private long code;

}
