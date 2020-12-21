package com.xp.test.common.utils;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 封装JSON格式数据提取方法
 * 
 * 这个工具类也是为了提供给jmeter做接口自动化测试的
 * 
 * @author qguan
 *
 */
public class JSONExtractor {

	/**
	 * 1 json解析方法,在jmeter当中可以通过String
	 * res=prev.getResponseDataAsString();获取响应报文字符串格式； 通过JSONObject responseJson
	 * = JSON.parseObject(res);转成json对象；然后再调用此方法，jpath写法：如果是[]数组则需要携带角标取值，如果是{}则
	 * /取值
	 * 
	 * @param responseJson
	 *            :这个变量是拿到响应字符串通过json转换成json对象
	 * @param jpath
	 *            :这个jpath指的是用户想要查询json对象的值的路径写法 jpath写法举例： 1) per_page
	 *            2)data[1]/first_name ，data是一个json数组，[1]表示索引 /first_name
	 *            表示data数组下某一个元素下的json对象的名称为first_name
	 * @return，返回first_name这个json对象名称对应的值
	 */
	final static Logger Log = Logger.getLogger(JSONExtractor.class);

	public static String getValueByJPath(JSONObject responseJson, String jpath) {

		Object obj = responseJson;

		for (String s : jpath.split("/")) {
			if (!s.isEmpty()) {

				if (!(s.contains("[") || s.contains("]"))) {

					obj = ((JSONObject) obj).get(s);

				} else if (s.contains("[") || s.contains("]")) {

					obj = ((JSONArray) ((JSONObject) obj)
							.get(s.split("\\[")[0])).get(Integer.parseInt(s
							.split("\\[")[1].replaceAll("]", "")));

				}
			}
		}
		return obj.toString();
	}

	/**
	 * 内部转换String类型
	 * 
	 * @param str
	 * @param jpath
	 * @return
	 */
	public static String getValueByJPath(String str, String jpath) {

		Object obj = JSON.parseObject(str);

		for (String s : jpath.split("/")) {
			if (!s.isEmpty()) {

				if (!(s.contains("[") || s.contains("]"))) {

					obj = ((JSONObject) obj).get(s);

				} else if (s.contains("[") || s.contains("]")) {

					obj = ((JSONArray) ((JSONObject) obj)
							.get(s.split("\\[")[0])).get(Integer.parseInt(s
							.split("\\[")[1].replaceAll("]", "")));

				}
			}
		}
		return obj.toString();
	}

	/**
	 * 传入响应报文及期望结果
	 * 
	 * @param response
	 * @param jpath
	 * @return
	 */
	public static boolean jsonAssertResult(String response, String expected) {
		// String expected=getValueByJPath(response, jpath);
		if (response.contains(expected)) {
			return true;
		} else
			return false;

	}

}