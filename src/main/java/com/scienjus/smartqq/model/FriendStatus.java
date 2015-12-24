package com.scienjus.smartqq.model;

import com.alibaba.fastjson.annotation.JSONField;

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
    public String toString() {
        return "FriendStatus{" +
                "uin=" + uin +
                ", status='" + status + '\'' +
                ", clientType=" + clientType +
                '}';
    }
}
