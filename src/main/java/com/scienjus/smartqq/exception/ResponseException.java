package com.scienjus.smartqq.exception;

/**
 * Smart QQ的HTTP响应异常
 * 
 * @author Xianguang Zhou <xianguang.zhou@outlook.com>
 * @date 2016/08/17.
 */
public class ResponseException extends SmartqqException {

	private static final long serialVersionUID = -7704223157905544814L;

	public ResponseException(int statusCode) {
		super(String.format("HTTP响应异常，状态码[%d]", statusCode));
	}

}
