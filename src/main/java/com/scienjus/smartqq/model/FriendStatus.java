package com.scienjus.smartqq.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Objects;

/**
 * 好友状态
 * @author ScienJus
 * @date 2015/12/24.
 */
public class FriendStatus {

    private long uin;

    private String status;

    @JSONField(name = "client_type")
    private int clientType;

    public long getUin() {
        return uin;
    }

    public void setUin(long uin) {
        this.uin = uin;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getClientType() {
        return clientType;
    }

    public void setClientType(int clientType) {
        this.clientType = clientType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FriendStatus that = (FriendStatus) o;
        return uin == that.uin &&
                clientType == that.clientType &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uin, status, clientType);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FriendStatus{");
        sb.append("uin=").append(uin);
        sb.append(", status='").append(status).append('\'');
        sb.append(", clientType=").append(clientType);
        sb.append('}');
        return sb.toString();
    }
}
