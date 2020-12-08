package com.xp.test.http.restClient;

import java.util.HashMap;

import javax.annotation.Resource;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.xp.test.base.client.RestClient;
import com.xp.test.common.config.BasicConfig;

public class RestClientTest {

	@Resource
	private RestClient restClient;
	
	@Resource
	private BasicConfig bconf;
	
	HashMap<String, String> headermap;
	
	@BeforeClass
	void bf() {
		// 创建http请求对象
		restClient = new RestClient();
		// 构造请求头
		headermap = new HashMap<String, String>();
		headermap.put("Content-Type", "application/json"); // 这个在postman中可以查询到
		// 读取配置文件
		bconf = new BasicConfig();
	}

	@Test
	void test_sendPost() {

		String url = "/api/users";

		String jsonBody = "{\"name\": \"morpheus\",\"job\": \"leader\"}";

		String host = bconf.prop.getProperty("HOST") + url;
		// String Content=;
		// 接收http响应结果
		CloseableHttpResponse closeableHttpResponse = restClient.sendPost(host,
				jsonBody, headermap);
		// 接收http响应状态码
		int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		Assert.assertEquals(statusCode, 200, "status code is not 200");

	}

}
