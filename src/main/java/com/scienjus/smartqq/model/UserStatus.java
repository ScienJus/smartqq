package com.scienjus.smartqq.model;

import java.util.HashMap;
import java.util.Map;

/**
 * QQ user status
 *
 * @author Xianguang Zhou <xianguang.zhou@outlook.com>
 * @date 2016/08/02.
 */
public enum UserStatus {
	// 在线
	ONLINE("online"),
	// Q我
	CALLME("callme"),
	// 离开
	AWAY("away"),
	// 忙碌
	BUSY("busy"),
	// 静音
	SILENT("silent"),
	// 隐身
	HIDDEN("hidden"),
	// 离线
	OFFLINE("offline");

	private String statusCode;

	private UserStatus(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusCode() {
		return statusCode;
	}

	private final static Map<String, UserStatus> codeToStatus = new HashMap<>();

	static {
		for (UserStatus userStatus : UserStatus.values()) {
			codeToStatus.put(userStatus.statusCode, userStatus);
		}
	}

	public static UserStatus fromCode(String code) {
		return codeToStatus.get(code);
	}

}
