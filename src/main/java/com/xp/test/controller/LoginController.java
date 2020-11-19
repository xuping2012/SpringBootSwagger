package com.xp.test.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.xp.test.cmmon.ReturnValue.Result;
import com.xp.test.cmmon.ReturnValue.ReturnValue;
import com.xp.test.common.utils.ValidatorUtils;
import com.xp.test.param.LoginParam;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api")
@Api(tags = "用户相关接口")
public class LoginController {

	private static final Logger log = LogManager
			.getLogger(LoginController.class.getName());

	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	@ApiOperation(value = "hello，this my first api!!!")
	public ReturnValue<?> hello() {
		// System.out.println("执行成功");
		log.info("这是我编写的第一个接口!");
		return new ReturnValue<Object>(true, 1, "hello world", null, null);
	}

	@RequestMapping(value = "/helloworld", method = RequestMethod.GET)
	@ApiOperation(value = "打个招呼")
	public Result helloword2() {
		// System.out.println("执行成功");
		return new Result(0, "hello world，这是我的第一个接口!");
	}

	// @RequestMapping(value = "/login", method = RequestMethod.GET)
	@ApiOperation(value = "登录1")
	@PostMapping("/login_mobile")
	public ReturnValue<?> Login(
			@RequestBody LoginParam.getTokenByMobileInput input) {
		ValidatorUtils.validateEntity(input);

		return new ReturnValue<Object>(true, 1, "success", "login success",
				null);
	}

	@ApiOperation(value = "登录2")
	@GetMapping("/login_qq")
	// @RequestMapping(value = "/get_login", method = RequestMethod.GET)
	public ReturnValue<?> getLogin(@RequestParam(value = "zone") String zone,
			@RequestParam(value = "mobile") String mobile) {

		return new ReturnValue<Object>(true, 1, "success", "login success",
				null);
	}

	@RequestMapping(value = "/login_wx", method = RequestMethod.GET)
	@ApiOperation(value = "登录3")
	public Result login() {
		// System.out.println("执行成功");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("token", "123456");
		return new Result(0, "success", map);
	}

	@RequestMapping(value = "/json_object", method = RequestMethod.GET)
	@ApiOperation(value = "直接通过字符串解析成json对象返回")
	public Object objectA() {
		String res = "{\"1\":{\"name\":\"张三\",\"age\":10},\"2\":{\"name\":\"李四\",\"age\":10},\"3\":{\"name\":\"王五\",\"age\":10}}";
		String s = JSONArray.parseObject(res).getClass().toString();
		System.out.println("数据类型：" + s);
		return JSONArray.parseObject(res);
	}

	@RequestMapping(value = "/loginout", method = RequestMethod.GET)
	@ApiOperation(value = "退出接口")
	public Result loginout() {
		return new Result(0, null);
	}

}
