package com.xp.test.boot.controller;

import javax.annotation.Resource;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.xp.test.boot.base.BaseServer;
import com.xp.test.common.returnValue.ReturnValue;
import com.xp.test.controller.LoginController;
import com.xp.test.controller.param.LoginParam;

/**
 * Unit test for simple App. springboot框架单元测试demo
 */
public class LoginControllerTest extends BaseServer {

	@Resource
	private LoginController loginController;

	@Test
	public void shouldAnswerWithTrue() {
		LoginParam.getTokenByMobileInput input = new LoginParam.getTokenByMobileInput();
		input.setZone("86");
		input.setMobile("13266515340");
		input.setPasswd("Aa123456");

		ReturnValue<?> res = loginController.Login(input);
		SoftAssert sa = new SoftAssert();
		sa.assertEquals(res.isResult(), true);
		sa.assertEquals(res.getCodeDesc(), "登录成功!!!");
		sa.assertAll();

	}
}
