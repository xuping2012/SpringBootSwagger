package com.xp.test.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.*;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

/**
 * 公用替换参数的类，其中封装了参数替换的方法，不定长传参转list
 * 
 * 这个类本来是提供给jmeter做接口自动化使用
 * 
 * @author Administrator
 *
 */
public class HandleParamUtils {

	private static String pattern = "#(.+?)#";

	final static Logger Log = Logger.getLogger(HandleParamUtils.class);

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
	 * <T> 泛型的使用，指代某一种类型，以及不定长传参 当然没必要这么麻烦，可以使用List<Object> 就可以了
	 * 
	 * @param args
	 * @return
	 */
	// 在声明具有模糊类型（比如：泛型）的可变参数的构造函数或方法时，Java编译器会报unchecked警告。鉴于这些情况，如果程序员断定声明的构造函数和方法的主体不会对其varargs参数执行潜在的不安全的操作，可使用@SafeVarargs进行标记，这样的话，Java编译器就不会报unchecked警告。
	// 使用的时候要注意：@SafeVarargs注解，对于非static或非final声明的方法，不适用，会编译不通过。
	// 非static申明的方法，可能需要在不定长参数类型前加上：@SuppressWarnings("unchecked")
	@SafeVarargs
	public static <T> List<T> getList(T... args) {

		List<T> list = new ArrayList<T>();

		for (int i = 0; i < args.length; i++) {
			list.add(args[i]);
		}

		return list;
	}

	/**
	 * list集合去除空字符串元素
	 * 
	 * @param lp
	 * @return
	 */
	public static List<Object> removeAll(List<Object> lp) {

		List<Object> newList = new ArrayList<Object>();

		for (int i = 0; i < lp.size(); i++) {
			if (lp.get(i) != "") {
				newList.add((Object) lp.get(i));
			}
		}
		return newList;
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
	 * 将list数组，根据需要替换参数的字符串生成map对象
	 * 
	 * @param content
	 * @param lp
	 * @return
	 */
	public static Map<String, Object> TextToDict(String content, List<Object> lp) {
		Map<String, Object> dict = new HashMap<String, Object>();
		Pattern pt = Pattern.compile(pattern);
		Matcher m = pt.matcher(content);

		int i = 0;
		while (m.find()) {
			if (lp.get(i) != "") {
				dict.put(m.group(1), (Object) lp.get(i));
				i++;
			}
			i++;
		}
		Log.info("list数组转成map对象");
		return dict;
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

	/**
	 * 使用替换的map参数替换json字符串中的参数变量
	 * 
	 * @param content
	 * @param dict
	 * @return
	 */
	public static String replaceParam(String content, Map<String, Object> dict) {

		// boolean isMatch = Pattern.matches(pattern, content);
		Pattern pt = Pattern.compile(pattern);
		Matcher m = pt.matcher(content);
		StringBuffer newContent = new StringBuffer();
		while (m.find()) {
			if (m.group().contains(m.group(1))) {
				m.appendReplacement(newContent, (String) dict.get(m.group(1)));
			}
		}
		m.appendTail(newContent);
		return newContent.toString();
	}

	public static void main(String[] args) {
		// 不定长字符串传参，拼接成数组返回，用来储存数据
		List<String> st = HandleParamUtils.strToList("1", "3", "44", "3");
		System.out.println("获取数组：" + st);
		for (int i = 0; i < st.size(); i++) {
			System.out.println("遍历数组取值：" + st.get(i));
		}
		System.out.println("\n***************************\n");
		// 在restful接口风格，接口响应为json对象，在jmeter中获取的是json对象的字符串，需要转成map对象
		String ss = "{\"a\":\"1\",\"b\":\"aa\"}";
		Map<String, Object> n = HandleParamUtils.json2map(ss);
		System.out.println("获取map对象：" + n);
		System.out.println("获取key：a的值：" + n.get("a"));

		for (String k : n.keySet()) {
			System.out.println("遍历map对象获取key的值：" + n.get(k));
		}

	}

}