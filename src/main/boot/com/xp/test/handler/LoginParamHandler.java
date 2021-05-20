package com.xp.test.handler;

import org.springframework.stereotype.Component;

import com.xp.test.common.constrant.ErrorInfoEnum;
import com.xp.test.common.returnValue.ReturnValue;
import com.xp.test.controller.param.LoginParam;

import org.springframework.transaction.annotation.Transactional;

@Component
public class LoginParamHandler {

	/**
	 * 这里用来处理请求参数并且得到响应结果
	 * 
	 * @param input
	 * @return
	 */
	public ReturnValue<LoginParam.TokenOutput> getToken(
			LoginParam.getTokenByMobileInput input) {

		ReturnValue<?> returnValue = returnFailed(input);

		if (returnValue.isFailed()) {
			return ReturnValue.ofFailed(
					ErrorInfoEnum.ACCOUNT_INVALID.getCode(),
					ErrorInfoEnum.ACCOUNT_INVALID.getCodeDesc());
		}
		return ReturnValue.ofSuccessful(0, "登录成功!!!");
	}

	/**
	 * 
	 * 示例：给任意一个失败的结果，也可以结合其他handler处理逻辑来返回结果
	 * 
	 * @param input
	 * @return
	 */

	@Transactional(rollbackFor = Exception.class)
	public ReturnValue<LoginParam.TokenOutput> returnFailed(
			LoginParam.getTokenByMobileInput input) {

		// 指定一个结果，才能登录/注册成功，其他帐号都无效
		if (input.getMobile().equals("13266515340")) {
			return ReturnValue.ofSuccessful();
		}
		else if(input.getMobile().equals("13800138000")){
			return ReturnValue.ofSuccessful();
		}
		return ReturnValue.ofFailed(ErrorInfoEnum.ACCOUNT_INVALID.getCode());
	}
	// @Transactional(rollbackFor = Exception.class)
	// public ReturnValue<Tuple.TwoTuple<AuthParam.TokenOutput, Account>>
	// getToken(AuthParam.GetTokenByMobileInput input, AuthTypeEnum
	// authTypeEnum) {
	// // step1: 校验验证码是否正确
	// ReturnValue<?> checkSmsResult =
	// smsHandler.hasSendItAMomentGo(input.getZone(), input.getMobile(),
	// input.getCode());
	// if (checkSmsResult.isFailed()) {
	// return ReturnValue.ofFailed(checkSmsResult.getCode(),
	// checkSmsResult.getCodeDesc());
	// }
	//
	// // step2: 判断账户是否存在, 不存在则新增账户信息
	// Account account = accountHandler.tryAddAccount(input.getZone(),
	// input.getMobile());
	//
	// // step3: 记录相关日志
	// UserLoginLog userLoginLog = userLoginLogService.addLoginLog(authTypeEnum,
	// input.getDeviceInfo(), account);
	//
	// // step4: 判断该账户是否可用
	// ReturnValue<?> canLoginResult = accountHandler.canLogin(account);
	// if (canLoginResult.isFailed()) {
	// return ReturnValue.ofFailed(canLoginResult.getCode(),
	// canLoginResult.getCodeDesc());
	// }
	//
	// // step5: 生成token信息
	// ReturnValue<AuthParam.TokenOutput> tokenOutput =
	// userTokenHandler.createToken(account.getUserId(), "",
	// userLoginLog.getId());
	// return ReturnValue.ofSuccessful(Tuple.of(tokenOutput.getValue(),
	// account));
	// }
}
