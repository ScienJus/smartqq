package com.scienjus.smartqq.model;

import java.util.Objects;

/**
 * 群成员
 * @author ScienJus
 * @author Xianguang Zhou <xianguang.zhou@outlook.com>
 * @date 2015/12/24.
 */
public class GroupUser {

    private String nick;

    private String province;

    private String gender;

    private long uin;

    private String country;

    private String city;

    private String card;

    private int clientType;

    private int status;

    private boolean vip;

    private int vipLevel;

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public long getUin() {
        return uin;
    }

    public void setUin(long uin) {
        this.uin = uin;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public int getClientType() {
        return clientType;
    }

    public void setClientType(int clientType) {
        this.clientType = clientType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
        GroupUser groupUser = (GroupUser) o;
        return uin == groupUser.uin &&
                clientType == groupUser.clientType &&
                status == groupUser.status &&
                vip == groupUser.vip &&
                vipLevel == groupUser.vipLevel &&
                Objects.equals(nick, groupUser.nick) &&
                Objects.equals(province, groupUser.province) &&
                Objects.equals(gender, groupUser.gender) &&
                Objects.equals(country, groupUser.country) &&
                Objects.equals(city, groupUser.city) &&
                Objects.equals(card, groupUser.card);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nick, province, gender, uin, country, city, card, clientType, status, vip, vipLevel);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GroupUser{");
        sb.append("nick='").append(nick).append('\'');
        sb.append(", province='").append(province).append('\'');
        sb.append(", gender='").append(gender).append('\'');
        sb.append(", uin=").append(uin);
        sb.append(", country='").append(country).append('\'');
        sb.append(", city='").append(city).append('\'');
        sb.append(", card='").append(card).append('\'');
        sb.append(", clientType=").append(clientType);
        sb.append(", status=").append(status);
        sb.append(", vip=").append(vip);
        sb.append(", vipLevel=").append(vipLevel);
        sb.append('}');
        return sb.toString();
    }
}
