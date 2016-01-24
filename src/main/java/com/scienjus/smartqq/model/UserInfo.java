package com.scienjus.smartqq.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 用户
 * @author ScienJus
 * @date 2015/12/24.
 */
@Data
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

}
