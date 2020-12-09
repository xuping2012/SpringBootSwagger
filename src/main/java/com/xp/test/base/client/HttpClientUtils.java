package com.xp.test.base.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author qguan
 *
 */
public class HttpClientUtils {
	private CloseableHttpClient httpClient;
	private CloseableHttpResponse response;
	private RequestConfig config;
	public String HTTPSTATUS = "HttpStatus";
	final static Logger log = Logger.getLogger(HttpClientUtils.class);

	/**
	 * 与类名相同的方法为构造函数
	 */
	public HttpClientUtils() {
		config = RequestConfig.custom().setConnectTimeout(5000)
				.setConnectionRequestTimeout(1000).setSocketTimeout(1000)
				.build();
	}

	public HttpClientUtils(int connecTimeOut, int connectionRequstTimeOut,
			int socketTimeOut) {
		config = RequestConfig.custom().setConnectTimeout(connecTimeOut)
				.setConnectionRequestTimeout(connectionRequstTimeOut)
				.setSocketTimeout(socketTimeOut).build();
	}

	/**
	 * 封装发送get请求的方法
	 * 
	 * @param url
	 * @param params
	 * @param headers
	 * @return
	 * @throws Exception
	 */
	public JSONObject sendGet(String url, HashMap<String, String> params,
			HashMap<String, String> headers) throws Exception {
		httpClient = HttpClients.createDefault();

		// 拼接url地址，将参数进行分解?拼接
		if (params != null) {
			List<NameValuePair> pairs = new ArrayList<NameValuePair>();
			for (Map.Entry<String, String> entry : params.entrySet()) {
				String value = entry.getValue();
				if (!value.isEmpty()) {
					pairs.add(new BasicNameValuePair(entry.getKey(), value));
				}
			}
			url += "?"
					+ EntityUtils.toString(new UrlEncodedFormEntity(pairs),
							"UTF-8");
		}
		log.info("get请求拼接的url地址：" + url);
		HttpGet httpGet = new HttpGet(url);
		try {
			httpGet.setConfig(config);

			// 加载请求头到httpGet对象
			if (headers != null) {
				for (Map.Entry<String, String> entry : headers.entrySet()) {
					httpGet.setHeader(entry.getKey(), entry.getValue());
				}
			}
			response = httpClient.execute(httpGet);
			// 获取返回参数
			HttpEntity entity = response.getEntity();
			String result = null;
			if (entity != null) {
				result = EntityUtils.toString(entity, "UTF-8");
			}
			// 释放请求
			EntityUtils.consume(entity);
			JSONObject jsonobj = JSON.parseObject(result);
			// 将响应状态码加入JSONObject对象
			jsonobj.put(HTTPSTATUS, response.getStatusLine().getStatusCode());
			return jsonobj;

		} finally {
			// 最终关闭连接
			httpClient.close();
			response.close();
		}
	}

	/**
	 * 重载方法，请求头为空，sengGet方法if判断headers为空的情况
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public JSONObject sendGet(String url, HashMap<String, String> params)
			throws Exception {
		return this.sendGet(url, params, null);
	}

	/**
	 * 重载方法，请求头为空，sengGet方法if判断headers\params为空的情况
	 * 
	 * @param url
	 * @return this关键字返回
	 * @throws Exception
	 */
	public JSONObject sendGet(String url) throws Exception {
		return this.sendGet(url, null, null);
	}

	/**
	 * 封装发送json对象的post请求方法
	 * 
	 * @param url
	 * @param obj
	 * @param headers
	 * @return
	 * @throws Exception
	 */
	public JSONObject sendPostByJson(String url, Object obj,
			HashMap<String, String> headers) throws Exception {
		// 初始化http请求对象
		httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		try {
			httpPost.setConfig(config);
			String json = JSON.toJSONString(obj);
			StringEntity entity = new StringEntity(json, "UTF-8");
			entity.setContentType("application/json");
			httpPost.setEntity(entity);

			// 加载请求头
			if (headers != null) {
				for (Map.Entry<String, String> entry : headers.entrySet()) {
					httpPost.setHeader(entry.getKey(), entry.getValue());
				}
			}
			response = httpClient.execute(httpPost);
			HttpEntity responseEntity = response.getEntity();
			String result = null;
			// 响应实例
			if (responseEntity != null) {
				result = EntityUtils.toString(responseEntity, "UTF-8");
			}
			EntityUtils.consume(responseEntity);
			JSONObject jsonobj = JSON.parseObject(result);
			jsonobj.put(HTTPSTATUS, response.getStatusLine().getStatusCode());
			return jsonobj;
		} finally {
			// 关闭http请求资源
			httpClient.close();
			response.close();
		}

	}

	/**
	 * 封装发送表单数据形式的post请求方法
	 * 
	 * @param url
	 * @param form
	 * @param headers
	 * @return
	 * @throws Exception
	 */
	public JSONObject sendPostByForm(String url, Map<String, String> form,
			HashMap<String, String> headers) throws Exception {
		// 初始化http请求对象
		httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		try {
			httpPost.setConfig(config);

			// 组装请求参数,默认表单请求头content-type
			if (form.size() > 0) {
				ArrayList<BasicNameValuePair> list = new ArrayList<>();
				form.forEach((key, value) -> list.add(new BasicNameValuePair(
						key, value)));
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,
						"UTF-8");
				entity.setContentType("application/x-www-form-urlencoded");
				httpPost.setEntity(entity);
			}

			// 加载请求头
			if (headers != null) {
				Set<String> set = headers.keySet();
				for (Iterator<String> iterator = set.iterator(); iterator
						.hasNext();) {
					String key = iterator.next();
					String value = headers.get(key);
					httpPost.setHeader(key, value);

				}
			}
			// 接收请求的响应数据
			response = httpClient.execute(httpPost);
			// 响应参数实体
			HttpEntity responseEntity = response.getEntity();
			String result = null;
			if (responseEntity != null) {
				result = EntityUtils.toString(responseEntity, "UTF-8");
			}
			EntityUtils.consume(responseEntity);
			JSONObject jsonobj = JSON.parseObject(result);
			// 将http请求响应状态码加入响应结果
			jsonobj.put(HTTPSTATUS, response.getStatusLine().getStatusCode());
			return jsonobj;
		} finally {
			httpClient.close();
			response.close();
		}

	}

	/**
	 * 重载发送post请求的方法，表单形式的接口，没有请求头参数headers
	 * 
	 * @param url
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public JSONObject sendPostByForm(String url, Map<String, String> form)
			throws Exception {
		return this.sendPostByForm(url, form, null);
	}

	/**
	 * 重载发送json对象的post请求方法，没有请求头参数
	 * 
	 * @param url
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public JSONObject sendPostByJson(String url, Object obj) throws Exception {
		return this.sendPostByJson(url, obj, null);
	}
}