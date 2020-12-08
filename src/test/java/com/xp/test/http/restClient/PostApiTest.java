package com.xp.test.http.restClient;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xp.test.base.client.RestClient;
import com.xp.test.common.config.BasicConfig;
import com.xp.test.common.param.Users;
import com.xp.test.common.utils.JSONExtractor;

public class PostApiTest extends BasicConfig {
	BasicConfig testBase;
	String host;
	String url;
	RestClient restClient;
	CloseableHttpResponse closeableHttpResponse;

	final static Logger Log = Logger.getLogger(PostApiTest.class);

	@BeforeClass
	public void setUp() {
		testBase = new BasicConfig();
		host = prop.getProperty("HOST");
		url = host + "/api/users";

	}

	@Test
	public void postApiTest() throws ClientProtocolException, IOException {
		restClient = new RestClient();
		// 准备请求头信息
		HashMap<String, String> headermap = new HashMap<String, String>();
		headermap.put("Content-Type", "application/json"); // 这个在postman中可以查询到
		// 对象转换成Json字符串
		Users user = new Users("Anthony", "tester");
		Log.info("对象输出：" + user);
		String userJsonString = JSON.toJSONString(user);
		Log.info("对象转字符串输出：" + userJsonString);
		// System.out.println(userJsonString);

		closeableHttpResponse = restClient.sendPost(url, userJsonString,
				headermap);

		// 验证状态码是不是200
		int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		Assert.assertEquals(statusCode, RESPNSE_STATUS_CODE_201,
				"status code is not 201");

		// 断言响应json内容中name和job是不是期待结果
		String responseString = EntityUtils.toString(closeableHttpResponse
				.getEntity());
		JSONObject responseJson = JSON.parseObject(responseString);
		// System.out.println(responseString);
		String name = JSONExtractor.getValueByJPath(responseJson, "name");
		String job = JSONExtractor.getValueByJPath(responseJson, "job");
		Assert.assertEquals(name, "Anthony", "name is not same");
		Assert.assertEquals(job, "tester", "job is not same");

	}
	
}
