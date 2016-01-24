package com.scienjus.smartqq.model;

import lombok.Data;

/**
 * 讨论组成员
 * @author ScienJus
 * @date 2015/12/24.
 */
@Data
public class DiscussUser {

    private long uin;

    private String nick;

    private int clientType;

    private String status;

    @Override
    public String toString() {
        return "DiscussUser{" +
                "uin=" + uin +
                ", nick='" + nick + '\'' +
                ", clientType='" + clientType + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
