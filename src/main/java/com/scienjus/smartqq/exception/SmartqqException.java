package com.scienjus.smartqq.exception;

/**
 * Smart QQ的运行时异常
 * @author Xianguang Zhou <xianguang.zhou@outlook.com>
 * @date 2016/07/30.
 */
public class SmartqqException extends RuntimeException {

	private static final long serialVersionUID = 1260154288832772350L;

	public SmartqqException(String message) {
        super(message);
    }
}
