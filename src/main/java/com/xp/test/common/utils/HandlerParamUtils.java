package com.xp.test.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.*;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

/**
 * 公用替换参数的类，其中封装了参数替换的方法，不定长传参转list
 * 
 * @author Administrator
 *
 */
public class HandlerParamUtils {

	/**
	 * 字符串不定长传参转list数组
	 * 
	 * @param args
	 * @return
	 */
	public static List<String> strToList(String... args) {
		List<String> list = new ArrayList<String>();

		for (int i = 0; i < args.length; i++) {
			list.add(args[i]);
		}
		return list;

	}

	/**
	 * json字符串转map对象
	 * 
	 * @param str_json
	 * @return
	 */
	public static Map<String, Object> json2map(String str_json) {
		Map<String, Object> res = null;
		try {
			Gson gson = new Gson();
			res = gson.fromJson(str_json, new TypeToken<Map<String, Object>>() {
			}.getType());
		} catch (JsonSyntaxException e) {
		}
		return res;
	}

	/**
	 * 传入需要替换参数的字符串,里面默认#(.+?)#正则表达式
	 * 
	 * @param content
	 * @param list
	 * @return
	 */
	public static String replaceParam(String content, List<String> list) {

		// boolean isMatch = Pattern.matches(pattern, content);
		String pattern = "#(.+?)#";
		Pattern pt = Pattern.compile(pattern);
		Matcher m = pt.matcher(content);

		StringBuffer newContent = new StringBuffer();
		int i = 0;
		while (m.find()) {
			m.appendReplacement(newContent, list.get(i));
			i++;
		}
		m.appendTail(newContent);
		return newContent.toString();
	}

	/**
	 * 支持jmeter中的beanshell脚本写法 传入一个以逗号分隔的字符串,返回以逗号分割的字符串数组 数组通过索引取值，可以遍历数组
	 * 
	 * @param args
	 * @return
	 */
	public static String[] method(String args) {
		return args.split(",");
	}

	/**
	 * replaceParam重载，传入一个字符串一维数组
	 * 
	 * @param content
	 * @param list
	 * @return
	 */
	public static String replaceParam(String content, String[] list) {
		// boolean isMatch = Pattern.matches(pattern, content);
		String pattern = "#(.+?)#";
		Pattern pt = Pattern.compile(pattern);
		Matcher m = pt.matcher(content);

		StringBuffer newContent = new StringBuffer();
		int i = 0;
		while (m.find()) {
			m.appendReplacement(newContent, list[i]);
			i++;
		}
		m.appendTail(newContent);
		return newContent.toString();
	}

}