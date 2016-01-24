package com.scienjus.smartqq.model;

import lombok.Data;

/**
 * 最近会话
 * @author ScienJus
 * @date 2015/12/24.
 */
@Data
public class Recent {

    private long uin;

    //0:好友、1:群、2:讨论组
    private int type;

}
