package com.scienjus.smartqq.model;

import java.util.Objects;

/**
 * 讨论组成员
 * @author ScienJus
 * @date 2015/12/24.
 */
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

    public long getUin() {
        return uin;
    }

    public void setUin(long uin) {
        this.uin = uin;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public int getClientType() {
        return clientType;
    }

    public void setClientType(int clientType) {
        this.clientType = clientType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiscussUser that = (DiscussUser) o;
        return uin == that.uin &&
                clientType == that.clientType &&
                Objects.equals(nick, that.nick) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uin, nick, clientType, status);
    }

}
