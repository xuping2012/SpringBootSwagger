package com.xp.test.common.returnValue;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 响应结果类
 * 
 * @author qguan
 *
 * @param <T>
 */

@ApiModel(description = "API调用结果对象")
@Data
public class ReturnValue<T> {

	private boolean result;

	private Integer code;

	private String codeDesc;

	private String detailDesc;

	private T value;

	private ReturnValue(boolean result, Integer code, String codeDesc, T value) {
		this(result, code, codeDesc, null, value);
	}

	public ReturnValue(boolean result, Integer code, String codeDesc,
			String detailDesc, T value) {
		this.result = result;
		this.code = code;
		this.codeDesc = codeDesc;
		this.detailDesc = detailDesc;
		this.value = value;
	}

	/**
	 * 当只关心执行结果的时候调用
	 *
	 * @param <T>
	 * @return
	 */
	public static <T> ReturnValue<T> ofSuccessful() {
		return new ReturnValue<T>(true, null, null, null);
	}

	/**
	 * 只需要关心结果码
	 *
	 * @param code
	 * @param <T>
	 * @return
	 */
	public static <T> ReturnValue<T> ofSuccessful(int code) {
		return new ReturnValue<T>(true, code, null, null);
	}

	/**
	 * 只需要关心返回值
	 *
	 * @param value
	 * @param <T>
	 * @return
	 */
	public static <T> ReturnValue<T> ofSuccessful(T value) {
		return new ReturnValue<T>(true, null, null, value);
	}

	/**
	 * @param code
	 * @param codeDesc
	 * @param <T>
	 * @return
	 */
	public static <T> ReturnValue<T> ofSuccessful(int code, String codeDesc) {
		return new ReturnValue<T>(true, code, codeDesc, null);
	}

	public static <T> ReturnValue<T> ofSuccessful(int code, String codeDesc,
			T value) {
		return new ReturnValue<T>(true, code, codeDesc, value);
	}

	public static <T> ReturnValue<T> ofSuccessful(int code, String codeDesc,
			String detailDesc, T value) {
		return new ReturnValue<T>(true, code, codeDesc, detailDesc, value);
	}

	/**
	 * 当只关心执行结果的时候调用
	 *
	 * @param <T>
	 * @return
	 */
	public static <T> ReturnValue<T> ofFailed() {
		return new ReturnValue<T>(false, null, null, null);
	}

	public static <T> ReturnValue<T> ofFailed(int code) {
		return new ReturnValue<T>(false, code, null, null);
	}

	public static <T> ReturnValue<T> ofFailed(String codeDesc) {
		return new ReturnValue<T>(false, null, codeDesc, null);
	}

	public static <T> ReturnValue<T> ofFailed(int code, String codeDesc) {
		return new ReturnValue<T>(false, code, codeDesc, null);
	}

	public static <T> ReturnValue<T> ofFailed(int code, String codeDesc,
			String detailDesc) {
		return new ReturnValue<T>(false, code, codeDesc, detailDesc, null);
	}

	public static <T> ReturnValue<T> ofFailed(int code, String codeDesc,
			String detailDesc, T value) {
		return new ReturnValue<T>(false, code, codeDesc, detailDesc, value);
	}

	@JsonIgnore
	public boolean isSuccessful() {
		return result;
	}

	@JsonIgnore
	public boolean isFailed() {
		return !isSuccessful();
	}
}