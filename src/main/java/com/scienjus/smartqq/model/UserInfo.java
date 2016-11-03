package com.scienjus.smartqq.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 用户.
 *
 * @author ScienJus
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @date 2015/12/24.
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

    @JSONField(name = "vip_info")
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

}
