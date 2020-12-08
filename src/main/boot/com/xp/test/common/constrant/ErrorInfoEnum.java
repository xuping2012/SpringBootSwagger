package com.xp.test.common.constrant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import com.xp.test.common.returnValue.ReturnValue;

/**
 * 定义一些数据异常常量
 * 
 * @author qguan
 *
 */
@AllArgsConstructor
public enum ErrorInfoEnum {
	// 用户中心错误码以 1xxxx 为准
	/* 参数错误 错误码范围: 1000xx */
	PARAM_ERROR(100001, "参数错误", ""),

	/* 数据错误 错误码范围: 1001xx */
	DATA_ERROR(10101, "数据错误", ""), DATA_NOT_EXIST(10102, "数据错误", "数据不存在"), DATA_STATUS_ERROR(
			100103, "数据错误", "数据状态错误"), DATA_STATUS_FINISHED(100104, "数据错误",
			"数据已经被完成"), DATA_STATUS_INVALID(100105, "数据错误", "数据已经无效"), DATA_STATUS_EXPIRE(
			100105, "数据错误", "数据已经过期"),

	/** 短信 错误码范围: 1002xx */
	SMS_MOBILE_LIMIT(100201, "短信发送过于频繁", "短信发送过于频繁: 手机号限制"), SMS_IP_LIMIT(
			100202, "短信发送过于频繁", "短信发送过于频繁: ip限制"), SMS_VALID_CODE_EXPIRE(
			100203, "验证码已经失效", "验证码已经失效"), SMS_VALID_CODE_ERROR(100204,
			"验证码错误", "验证码错误"),

	/** 账户 错误码范围: 1003xx */
	ACCOUNT_DELETED(100301, "账户不可用", "账户已被逻辑删除"), ACCOUNT_INVALID(100302,
			"账户不可用", "账户处于无效状态"), ACCOUNT_UNSUBSCRIBE(100303, "账户不可用",
			"账户处于注销状态"),

	/** token 错误码范围: 1004xx */
	TOKEN_GENERATE_ERROR(100401, "token错误", "token生成失败"), TOKEN_NOT_MATH(
			100402, "token错误", "token不匹配"), TOKEN_VALID_ERROR(100403,
			"token错误", "token验证失败"), TOKEN_EXPIRE(100404, "token错误", "token失效"), TOKEN_IS_EMPTY(
			100405, "token错误", "token不能为空"), TOKEN_INVALID(100406, "token错误",
			"token无效"),

	/** 微信 错误码范围: 1005xx */
	WX_GET_ACCESS_TOKEN_FAILED(100501, "获取微信accessToken失败", "获取微信accessToken失败"), WX_GET_USER_INFO_FAILED(
			100502, "获取微信用户信息失败", "获取微信用户信息失败"), WX_OPEN_ID_NOT_EXIST(100503,
			"微信openId错误", "系统中不存在该openId"), WX_BENDED(100504, "重复绑定错误",
			"已经绑定过微信"), WX_MOBILE_BENDED(100505, "重复绑定错误", "该手机号已经绑定过其他微信"),

	/** QQ 错误码范围: 1006xx */
	QQ_GET_USER_INFO_FAILED(100601, "获取QQ用户信息失败", "获取QQ用户信息失败"), QQ_OPEN_ID_NOT_EXIST(
			100602, "QQ openId错误", "系统中不存在该openId"), QQ_BENDED(100603,
			"重复绑定错误", "已经绑定过QQ"), QQ_MOBILE_BENDED(100604, "重复绑定错误",
			"该手机号已经绑定过其他QQ"),

	;

	/**
	 * 错误码
	 */
	@Getter
	private int code;

	/**
	 * 错误描述(对外显示值)
	 */
	@Getter
	private String codeDesc;

	/**
	 * 错误详情(一般用于日志输出)
	 */
	@Getter
	private String detailDesc;

	public static <T> ReturnValue<T> returnValue(ErrorInfoEnum errorInfoEnum) {
		return ReturnValue.ofFailed(errorInfoEnum.getCode(),
				errorInfoEnum.getCodeDesc());
	}
}
