package com.scienjus.smartqq.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.ArrayList;
import java.util.List;

/**
 * 讨论组资料
 * @author ScienJus
 * @date 2015/12/24.
 */
public class DiscussInfo {

    @JSONField(name = "did")
    private long id;

    @JSONField(name = "discu_name")
    private String name;

    private List<DiscussUser> users = new ArrayList<>();

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

    public List<DiscussUser> getUsers() {
        return users;
    }

    public void setUsers(List<DiscussUser> users) {
        this.users = users;
    }

    public void addUser(DiscussUser user) {
        this.users.add(user);
    }

    @Override
    public String toString() {
        return "DiscussInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", users=" + users +
                '}';
    }
}
