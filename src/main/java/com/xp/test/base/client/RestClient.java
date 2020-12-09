package com.xp.test.base.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 封裝http請求的方法；restful风格
 * 
 * @author qguan
 *
 */
public class RestClient {

	final static Logger Log = Logger.getLogger(RestClient.class);

	/**
	 * 封装不带请求头的get方法
	 * 
	 * @param url
	 * @return 返回响应对象
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public CloseableHttpResponse sendGet(String url)
			throws ClientProtocolException, IOException {
		// 创建一个可关闭的HttpClient对象
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 创建一个HttpGet的请求对象
		HttpGet httpget = new HttpGet(url);
		// 执行请求,然后响应给HttpResponse对象接收
		Log.info("start send get request...");
		CloseableHttpResponse httpResponse = httpclient.execute(httpget);
		Log.info("send success！start get response Object。");
		return httpResponse;
	}

	/**
	 * 封装不带请求头的get方法
	 * 
	 * @param url
	 * @param param
	 *            :account=&passwd=
	 * @return 返回响应对象
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public CloseableHttpResponse sendGet(String url, String param) {

		// 创建一个可关闭的HttpClient对象
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 创建一个HttpGet的请求对象
		String urlname = url + '?' + param;
		HttpGet httpget = new HttpGet(urlname);
		// 执行请求,然后响应给HttpResponse对象接收
		Log.info("start send get request...");
		CloseableHttpResponse httpResponse = null;
		try {
			httpResponse = httpclient.execute(httpget);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.info("send success！start get response Object。");
		return httpResponse;
	}

	/**
	 * 带请求头信息的get方法
	 * 
	 * @param url
	 * @param headermap键值对形式
	 * @return 返回响应对象
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public CloseableHttpResponse sendGet(String url,
			HashMap<String, String> headermap) {

		// 创建一个可关闭的HttpClient对象
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 创建一个HttpGet的请求对象
		HttpGet httpget = new HttpGet(url);

		// 加载请求头到httpget对象
		for (Map.Entry<String, String> entry : headermap.entrySet()) {
			httpget.addHeader(entry.getKey(), entry.getValue());
		}
		// 执行请求,然后响应给HttpResponse对象接收
		CloseableHttpResponse httpResponse = null;
		try {
			httpResponse = httpclient.execute(httpget);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.info("start send has requestHeader get request...");
		return httpResponse;
	}

	/**
	 * 封装带请求头的get方法，并传递参数
	 * 
	 * @param url
	 * @param headermap
	 * @param param
	 * @return 返回响应对象
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public CloseableHttpResponse sendGet(String url,
			HashMap<String, String> headermap, String param) {

		// 创建一个可关闭的HttpClient对象
		CloseableHttpClient httpclient = HttpClients.createDefault();

		String urlname = url + '?' + param;
		// 创建一个HttpGet的请求对象
		HttpGet httpget = new HttpGet(urlname);

		// 加载请求头到httpget对象
		for (Map.Entry<String, String> entry : headermap.entrySet()) {
			httpget.addHeader(entry.getKey(), entry.getValue());
		}
		// 执行请求,然后响应给HttpResponse对象接收
		CloseableHttpResponse httpResponse = null;
		try {
			httpResponse = httpclient.execute(httpget);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.info("start send has requestHeader get request...");
		return httpResponse;
	}

	/**
	 * 封装带请求头的post方法
	 * 
	 * @param url
	 * @param entityString
	 *            ，其实就是设置请求json参数
	 * @param headermap
	 *            ，带请求头
	 * @return 返回响应对象
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public CloseableHttpResponse sendPost(String url, String entityString,
			Map<String, String> headermap) {
		// 创建一个可关闭的HttpClient对象
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 创建一个HttpPost的请求对象
		HttpPost httppost = new HttpPost(url);
		// 设置payload
		try {
			httppost.setEntity(new StringEntity(entityString));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 加载请求头到httppost对象
		for (Map.Entry<String, String> entry : headermap.entrySet()) {
			httppost.addHeader(entry.getKey(), entry.getValue());
		}
		// 发送post请求,然后响应给HttpResponse对象接收
		CloseableHttpResponse httpResponse = null;
		try {
			httpResponse = httpclient.execute(httppost);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.info("start send post request");
		return httpResponse;
	}

	/**
	 * 封裝不帶請求頭的post方法
	 * 
	 * @param url
	 * @param entityString
	 * @return:返回的是CloseableHttpResponse对象，是需要解析的对象
	 */
	public CloseableHttpResponse sendPost(String url, String entityString) {
		// 创建一个可关闭的HttpClient对象
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 创建一个HttpPost的请求对象
		HttpPost httppost = new HttpPost(url);
		// 设置payload
		try {
			httppost.setEntity(new StringEntity(entityString));
			CloseableHttpResponse httpResponse = httpclient.execute(httppost);
			return httpResponse;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 发送post请求,然后响应给HttpResponse对象接收

		Log.info("start send post request");
		return null;
	}

	/**
	 * 封装 put请求方法，参数和post方法一样
	 * 
	 * @param url
	 * @param entityString
	 *            ，这个主要是设置payload,一般来说就是json串
	 * @param headerMap
	 *            ，带请求的头信息，格式是键值对，所以这里使用hashmap
	 * @return 返回响应对象
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public CloseableHttpResponse sendPut(String url, String entityString,
			HashMap<String, String> headerMap) throws ClientProtocolException,
			IOException {

		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPut httpput = new HttpPut(url);
		httpput.setEntity(new StringEntity(entityString));

		for (Map.Entry<String, String> entry : headerMap.entrySet()) {
			httpput.addHeader(entry.getKey(), entry.getValue());
		}
		// 发送put请求,执行请求,然后响应给HttpResponse对象接收
		CloseableHttpResponse httpResponse = httpclient.execute(httpput);
		return httpResponse;
	}

	/**
	 * 封装 delete请求方法，参数和get方法一样
	 * 
	 * @param url
	 *            ， 接口url完整地址
	 * @return，返回一个response对象，方便进行得到状态码和json解析动作
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public CloseableHttpResponse sendDelete(String url)
			throws ClientProtocolException, IOException {

		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpDelete httpdel = new HttpDelete(url);

		// 发送delete请求,执行请求,然后响应给HttpResponse对象接收
		CloseableHttpResponse httpResponse = httpclient.execute(httpdel);
		return httpResponse;
	}

	/**
	 * 获取响应状态码，常用来和TestBase中定义的状态码常量去测试断言使用
	 * 
	 * @param response
	 * @return 返回int类型状态码
	 */
	public int getStatusCode(CloseableHttpResponse response) {
		int statusCode = response.getStatusLine().getStatusCode();
		Log.info("analyze，getHttpResponseStatusCode:" + statusCode);
		return statusCode;
	}

	/**
	 * 
	 * @param response
	 *            , 任何请求返回返回的响应对象
	 * @return， 返回响应体的json格式对象，方便接下来对JSON对象内容解析
	 *          接下来，一般会继续调用TestUtil类下的json解析方法得到某一个json对象的值
	 * @throws ParseException
	 * @throws IOException
	 */
	public JSONObject getResponseJson(CloseableHttpResponse response)
			throws ParseException, IOException {
		Log.info("get response object String type");
		String responseString = EntityUtils.toString(response.getEntity(),
				"UTF-8");
		JSONObject responseJson = JSON.parseObject(responseString);
		Log.info("return responseData JSON type");
		return responseJson;
	}

	/**
	 * 封装post请求参数以键值对的json数据
	 * 
	 * @param params
	 *            ：json格式的字符串
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<NameValuePair> getParamList(String params) {
		// 准备提交参数
		List<NameValuePair> paramPairs = new ArrayList<NameValuePair>();
		// 从params这歌json格式的字符串解析参数
		Map<String, String> map = (Map<String, String>) JSONObject
				.parse(params);
		// 取出所有键
		Set<String> keys = map.keySet();
		// 高级for取出map集合中的每一个键值对封装成BasicNameValuePair对象
		// 没明白为什么不用keyvaluepair来表示键值对
		for (String key : keys) {
			String value = map.get(key);
			BasicNameValuePair pair = new BasicNameValuePair(key, value);
			paramPairs.add(pair);
		}
		return paramPairs;
	}

}