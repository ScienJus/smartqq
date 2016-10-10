package com.scienjus.smartqq.model;

import java.util.Objects;

/**
 * 好友
 * @author ScienJus
 * @author Xianguang Zhou <xianguang.zhou@outlook.com>
 * @date 2015/12/18.
 */
public class Friend implements WithUserId {

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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getMarkname() {
        return markname;
    }

    public void setMarkname(String markname) {
        this.markname = markname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public boolean isVip() {
        return vip;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
    }

    public int getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(int vipLevel) {
        this.vipLevel = vipLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Friend friend = (Friend) o;
        return userId == friend.userId &&
                vip == friend.vip &&
                vipLevel == friend.vipLevel &&
                Objects.equals(markname, friend.markname) &&
                Objects.equals(nickname, friend.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, markname, nickname, vip, vipLevel);
    }

}
