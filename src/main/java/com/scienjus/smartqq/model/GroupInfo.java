package com.scienjus.smartqq.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 群资料
 * @author ScienJus
 * @author Xianguang Zhou <xianguang.zhou@outlook.com>
 * @date 2015/12/24.
 */
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

    public long getGid() {
        return gid;
    }

    public void setGid(long gid) {
        this.gid = gid;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getOwner() {
        return owner;
    }

    public void setOwner(long owner) {
        this.owner = owner;
    }

    public String getMarkname() {
        return markname;
    }

    public void setMarkname(String markname) {
        this.markname = markname;
    }

    public List<GroupUser> getUsers() {
        return users;
    }

    public void setUsers(List<GroupUser> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupInfo groupInfo = (GroupInfo) o;
        return gid == groupInfo.gid &&
                createtime == groupInfo.createtime &&
                owner == groupInfo.owner &&
                Objects.equals(memo, groupInfo.memo) &&
                Objects.equals(name, groupInfo.name) &&
                Objects.equals(markname, groupInfo.markname) &&
                Objects.equals(users, groupInfo.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gid, createtime, memo, name, owner, markname, users);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GroupInfo{");
        sb.append("gid=").append(gid);
        sb.append(", createtime=").append(createtime);
        sb.append(", memo='").append(memo).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", owner=").append(owner);
        sb.append(", markname='").append(markname).append('\'');
        sb.append(", users=").append(users);
        sb.append('}');
        return sb.toString();
    }
}
