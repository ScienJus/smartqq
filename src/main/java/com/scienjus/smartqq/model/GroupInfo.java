package com.scienjus.smartqq.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 群资料
 * @author ScienJus
 * @date 2015/12/24.
 */
@Data
public class GroupInfo {

    private long gid;

    private long createtime;

    private String memo;

    private String name;

    private long owner;

    private String markname;

    private List<GroupUser> users = new ArrayList<>();

    public void addUser(GroupUser user) {
        this.users.add(user);
    }

}
