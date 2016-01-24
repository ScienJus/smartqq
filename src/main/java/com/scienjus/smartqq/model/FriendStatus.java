package com.scienjus.smartqq.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 好友状态
 * @author ScienJus
 * @date 2015/12/24.
 */
@Data
public class FriendStatus {

    private long uin;

    private String status;

    @JSONField(name = "client_type")
    private int clientType;

}
