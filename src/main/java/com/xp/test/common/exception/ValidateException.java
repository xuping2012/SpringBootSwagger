package com.xp.test.common.exception;

/**
 * 
 * 校验器异常类
 * 
 * @author qguan
 *
 */
@SuppressWarnings("serial")
public class ValidateException extends RuntimeException {
	public ValidateException(String message) {
		super(message);
	}
}
