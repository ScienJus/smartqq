package com.scienjus.smartqq.model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

/**
 * 讨论组
 * @author ScienJus
 * @author Xianguang Zhou <xianguang.zhou@outlook.com>
 * @date 2015/12/23.
 */
public class Discuss {

    @SerializedName("did")
    private long id;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Discuss discuss = (Discuss) o;
        return id == discuss.id &&
                Objects.equals(name, discuss.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Discuss{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
