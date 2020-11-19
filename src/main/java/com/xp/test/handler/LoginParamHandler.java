package com.xp.test.handler;

import com.xp.test.cmmon.ReturnValue.ReturnValue;
import com.xp.test.common.constrant.ErrorInfoEnum;
import com.xp.test.entity.Account;

public class LoginParamHandler {

	// @Transactional(rollbackFor = Exception.class)
	public Account tryAddAccount(String zone, String mobile) {
		// step1: 判断账号是否存在, 存在则直接返回, 不存在则新增账号
		// Account account = accountService.getByZoneAndMobile(zone, mobile);
		// if (Objects.nonNull(account)) {
		// log.debug("账号已经存在, 无需新增. zone: {}, mobile: {}", zone, mobile);
		// return account;
		// }
		Account account = new Account();
		if (!zone.equals("86")) {
			return null;
		}
		return account;
	}

	public ReturnValue<?> canLogin(Account account) {
		ReturnValue<?> result = ReturnValue.ofSuccessful();

		if (account.getDeleted()) {
			result = ReturnValue.ofFailed(
					ErrorInfoEnum.ACCOUNT_DELETED.getCode(),
					ErrorInfoEnum.ACCOUNT_DELETED.getCodeDesc());
		} else if (account.getStatus() == Account.STATUS_INVALID) {
			result = ReturnValue.ofFailed(
					ErrorInfoEnum.ACCOUNT_INVALID.getCode(),
					ErrorInfoEnum.ACCOUNT_INVALID.getCodeDesc());
		} else if (account.getStatus() == Account.STATUS_UNSUBSCRIBE) {
			result = ReturnValue.ofFailed(
					ErrorInfoEnum.ACCOUNT_UNSUBSCRIBE.getCode(),
					ErrorInfoEnum.ACCOUNT_UNSUBSCRIBE.getCodeDesc());
		}

		return result;
	}
}
