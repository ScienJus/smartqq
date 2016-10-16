package com.scienjus.smartqq.exception;

/**
 * Smart QQ的API异常
 * 
 * @author <a href="mailto:xianguang.zhou@outlook.com">Xianguang Zhou</a>
 * @since 2016/08/17.
 */
public class ApiException extends SmartqqException {

	private static final long serialVersionUID = -8380675096950093785L;

	public ApiException(String message) {
		super(message);
	}
	
	public ApiException(int returnCode) {
		super(String.format("API异常，API返回码[%d]", returnCode));
	}

}
