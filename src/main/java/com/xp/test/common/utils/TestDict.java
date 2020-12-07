package com.xp.test.common.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 * 
 * 同HandlerParamUtils类
 * 
 * @author Administrator
 *
 */
public class TestDict {

	private static String pattern = "#(.+?)#";

	final static Logger Log = Logger.getLogger(TestDict.class);

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
}