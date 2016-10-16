package com.scienjus.smartqq.exception;

/**
 * Smart QQ的运行时异常
 * 
 * @author <a href="mailto:xianguang.zhou@outlook.com">Xianguang Zhou</a>
 * @since 2016/07/30.
 */
public class SmartqqException extends RuntimeException {

	private static final long serialVersionUID = 1260154288832772350L;

	public SmartqqException() {
		super();
	}
	
	public SmartqqException(String message) {
        super(message);
    }
}
