package com.xp.test.http.ddt;

import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;

/**
 * testng本身框架支持的DataProvider注解
 * 
 * @author qguan
 *
 */
public class DataProviderByObject {

	/**
	 * 使用@DataProvider注解，定义当前方法中的返回对象作为测试脚本的测试数据集合，并且将测试数据集合命名为“searchWords”。
	 * 
	 * @return
	 */
	@DataProvider(name = "searchWords")
	public static Object[][] word() {
		return new Object[][] { { "蝙蝠侠", "主演", "迈克尔" }, { "超人", "导演", "唐纳" },
				{ "生化危机", "编剧", "安德森" } };
	}

	/**
	 * 测试方法中，使用(dataProvider="searchWords")来引入数据。
	 * dataProviderTest方法的三个入参分别是使用了searchWords测试数据集中的每个一维数组中的数据进行赋值
	 * 。这个测试方法会被调用三次，分别使用测试数据集合中的三组数据。
	 * 
	 * @param searchWord1
	 * @param searchWord2
	 * @param SearchResult
	 */
	@Test(dataProvider = "searchWords")
	public void dataProviderTest(String param1, String param2, String param3) {

		System.out.println("第一个参数遍历:" + param1);

	}

}