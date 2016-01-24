package com.scienjus.smartqq.model;

import lombok.Data;

/**
 * 好友
 * @author ScienJus
 * @date 2015/12/18.
 */
@Data
public class Friend {

    private long userId;

    private String markname = "";

    private String nickname;

    private boolean vip;

    private int vipLevel;

    @Override
    public String toString() {
        return "Friend{" +
                "userId=" + userId +
                ", markname='" + markname + '\'' +
                ", nickname='" + nickname + '\'' +
                ", vip=" + vip +
                ", vipLevel=" + vipLevel +
                '}';
    }
}
