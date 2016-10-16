package com.scienjus.smartqq.model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

/**
 * 群
 * @author ScienJus
 * @author <a href="mailto:xianguang.zhou@outlook.com">Xianguang Zhou</a>
 * @since 2015/12/18.
 */
public class Group {

    @SerializedName("gid")
    private long id;

    private String name;

    private long flag;

    private long code;

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

    public long getFlag() {
        return flag;
    }

    public void setFlag(long flag) {
        this.flag = flag;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return id == group.id &&
                flag == group.flag &&
                code == group.code &&
                Objects.equals(name, group.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, flag, code);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Group{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", flag=").append(flag);
        sb.append(", code=").append(code);
        sb.append('}');
        return sb.toString();
    }
}
