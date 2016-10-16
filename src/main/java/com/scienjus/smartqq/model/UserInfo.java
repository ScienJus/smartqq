package com.scienjus.smartqq.model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

/**
 * 用户
 * @author ScienJus
 * @author <a href="mailto:xianguang.zhou@outlook.com">Xianguang Zhou</a>
 * @since 2015/12/24.
 */
public class UserInfo {

    private Birthday birthday;

    private String phone;

    private String occupation;

    private String college;

    private String uin;

    private int blood;

    private String lnick;   //签名

    private String homepage;

    @SerializedName("vip_info")
    private int vipInfo;

    private String city;

    private String country;

    private String province;

    private String personal;

    private int shengxiao;

    private String nick;

    private String email;

    private String account;

    private String gender;

    private String mobile;

    public Birthday getBirthday() {
        return birthday;
    }

    public void setBirthday(Birthday birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getUin() {
        return uin;
    }

    public void setUin(String uin) {
        this.uin = uin;
    }

    public int getBlood() {
        return blood;
    }

    public void setBlood(int blood) {
        this.blood = blood;
    }

    public String getLnick() {
        return lnick;
    }

    public void setLnick(String lnick) {
        this.lnick = lnick;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public int getVipInfo() {
        return vipInfo;
    }

    public void setVipInfo(int vipInfo) {
        this.vipInfo = vipInfo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPersonal() {
        return personal;
    }

    public void setPersonal(String personal) {
        this.personal = personal;
    }

    public int getShengxiao() {
        return shengxiao;
    }

    public void setShengxiao(int shengxiao) {
        this.shengxiao = shengxiao;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInfo userInfo = (UserInfo) o;
        return blood == userInfo.blood &&
                vipInfo == userInfo.vipInfo &&
                shengxiao == userInfo.shengxiao &&
                Objects.equals(birthday, userInfo.birthday) &&
                Objects.equals(phone, userInfo.phone) &&
                Objects.equals(occupation, userInfo.occupation) &&
                Objects.equals(college, userInfo.college) &&
                Objects.equals(uin, userInfo.uin) &&
                Objects.equals(lnick, userInfo.lnick) &&
                Objects.equals(homepage, userInfo.homepage) &&
                Objects.equals(city, userInfo.city) &&
                Objects.equals(country, userInfo.country) &&
                Objects.equals(province, userInfo.province) &&
                Objects.equals(personal, userInfo.personal) &&
                Objects.equals(nick, userInfo.nick) &&
                Objects.equals(email, userInfo.email) &&
                Objects.equals(account, userInfo.account) &&
                Objects.equals(gender, userInfo.gender) &&
                Objects.equals(mobile, userInfo.mobile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(birthday, phone, occupation, college, uin, blood, lnick, homepage, vipInfo, city, country, province, personal, shengxiao, nick, email, account, gender, mobile);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserInfo{");
        sb.append("birthday=").append(birthday);
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", occupation='").append(occupation).append('\'');
        sb.append(", college='").append(college).append('\'');
        sb.append(", uin='").append(uin).append('\'');
        sb.append(", blood=").append(blood);
        sb.append(", lnick='").append(lnick).append('\'');
        sb.append(", homepage='").append(homepage).append('\'');
        sb.append(", vipInfo=").append(vipInfo);
        sb.append(", city='").append(city).append('\'');
        sb.append(", country='").append(country).append('\'');
        sb.append(", province='").append(province).append('\'');
        sb.append(", personal='").append(personal).append('\'');
        sb.append(", shengxiao=").append(shengxiao);
        sb.append(", nick='").append(nick).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", account='").append(account).append('\'');
        sb.append(", gender='").append(gender).append('\'');
        sb.append(", mobile='").append(mobile).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
