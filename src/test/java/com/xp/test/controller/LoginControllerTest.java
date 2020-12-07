package com.xp.test.controller;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import com.xp.test.base.BaseServer;
import com.xp.test.common.ReturnValue.ReturnValue;
import com.xp.test.controller.LoginController;
import com.xp.test.controller.param.LoginParam;

/**
 * Unit test for simple App.
 */
public class LoginControllerTest extends BaseServer {

	@Resource
	private LoginController loginController;

	@Test
	public void shouldAnswerWithTrue() {
		LoginParam.getTokenByMobileInput input = new LoginParam.getTokenByMobileInput();
		input.setZone(null);
		input.setMobile("1326615340");

		ReturnValue<?> res = loginController.Login(input);
		System.out.println(res);
	}
}
