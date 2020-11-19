package com.xp.test.interfaceT;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import com.xp.test.base.BaseServer;
import com.xp.test.cmmon.ReturnValue.ReturnValue;
import com.xp.test.controller.LoginController;
import com.xp.test.param.LoginParam;

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
