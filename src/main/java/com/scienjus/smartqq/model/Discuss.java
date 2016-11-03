package com.scienjus.smartqq.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 讨论组.
 *
 * @author ScienJus
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @date 2015/12/23.
 */
public class Discuss {

    @JSONField(name = "did")
    private long id;

    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
