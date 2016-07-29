package com.scienjus.smartqq.model;

import java.util.Objects;

/**
 * 最近会话
 * @author ScienJus
 * @date 2015/12/24.
 */
public class Recent {

    private long uin;

    //0:好友、1:群、2:讨论组
    private int type;

    public long getUin() {
        return uin;
    }

    public void setUin(long uin) {
        this.uin = uin;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recent recent = (Recent) o;
        return uin == recent.uin &&
                type == recent.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uin, type);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Recent{");
        sb.append("uin=").append(uin);
        sb.append(", type=").append(type);
        sb.append('}');
        return sb.toString();
    }
}
