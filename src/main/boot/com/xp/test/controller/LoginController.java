package com.xp.test.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.xp.test.common.returnValue.Result;
import com.xp.test.common.returnValue.ReturnValue;
import com.xp.test.common.utils.ValidatorUtils;
import com.xp.test.controller.param.LoginParam;
import com.xp.test.handler.LoginParamHandler;

@RestController
@RequestMapping(value = "/api")
@Api(tags = "用户相关接口")
public class LoginController {

	private static final Logger log = LogManager
			.getLogger(LoginController.class.getName());

	/**
	 * 引入接口处理资源
	 */
	@Resource
	private LoginParamHandler loginParamHandler;

	/**
	 * 定义post接口
	 * 
	 * @param input
	 * @return
	 */
	@ApiOperation(value = "登录")
	@PostMapping("/login_mobile")
	public ReturnValue<?> Login(
			@RequestBody LoginParam.getTokenByMobileInput input) {

		// 校验请求参数
		ValidatorUtils.validateEntity(input);

		// 处理接口请求
		ReturnValue<?> returnValue = loginParamHandler.getToken(input);

		return returnValue;
	}

	/**
	 * 我的第一个接口
	 * 
	 * @return
	 */
	@RequestMapping(value = "/helloworld", method = RequestMethod.GET)
	@ApiOperation(value = "打个招呼")
	public Result helloword() {
		log.info("这是我编写的第一个接口!");
		return new Result(0, "hello world，这是我的第一个接口!");
	}

	/**
	 * mock接口返回数据时，使用map集合
	 * 
	 * @return
	 */
	@RequestMapping(value = "/map_to_json", method = RequestMethod.GET)
	@ApiOperation(value = "map集合输出json对象")
	public Result login() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("token",
				"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWV9.TJVA95OrM7E2cBab30RMHrHDcEfxjoYZgeFONFh7HgQ");
		return new Result(0, "success", map);
	}

	/**
	 * 演示mock接口响应结果，可以使用string类型转换成json对象输出
	 * 
	 * @return
	 */
	@PostMapping(value = "/str_to_json")
	@ApiOperation(value = "直接通过字符串解析成json对象返回")
	public Object objectA() {
		String res = "{\"1\":{\"name\":\"张三\",\"age\":10},\"2\":{\"name\":\"李四\",\"age\":10},\"3\":{\"name\":\"王五\",\"age\":10}}";
		// String s = JSONArray.parseObject(res).getClass().toString();
		// System.out.println("数据类型：" + s);
		return JSONArray.parseObject(res);
	}

	/**
	 * 指定method为Get请求的方法
	 * 
	 * @return
	 */
	@GetMapping(value = "/loginout/{id}")
	@ApiOperation(value = "登出")
	public Result loginout(@PathVariable(value = "id") int id,
			@RequestParam(value = "desc") String desc) {
		return new Result(id, desc);
	}
}