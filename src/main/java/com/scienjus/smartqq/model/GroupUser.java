package com.scienjus.smartqq.model;

import lombok.Data;

/**
 * 群成员
 * @author ScienJus
 * @date 2015/12/24.
 */
@Data
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

}
