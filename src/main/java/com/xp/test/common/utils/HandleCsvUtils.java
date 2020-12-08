package com.xp.test.common.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.csvreader.CsvReader;

public class HandleCsvUtils {

	public static char separator = ',';

	/**
	 * @param filePath
	 *            :文件路徑
	 * @param content
	 *            ：写入文件的内容,csv 支持逗号,换列
	 * @throws IOException
	 */
	public static void appendFile(String filePath, String content)
			throws IOException {
		FileWriter writer = null;
		try {
			writer = new FileWriter(filePath, true); // true 换行追加记录,false每次执行替换
			content = content + "\n";
			writer.write(content);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	/**
	 * 读取csv格式数据
	 *
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static Object[][] getDatasByCSV(String filePath) throws IOException {

		List<Object[]> records = new ArrayList<Object[]>();
		String record;

		/**
		 * 设定UTF-8字符集，使用带缓冲区的字符输入流BufferedReader读取文件内容
		 */
		BufferedReader bf = new BufferedReader(new InputStreamReader(
				new FileInputStream(filePath), "utf-8"));

		/**
		 * 目录要src开始，如src/test/java/testdata/testforpeople.csv 忽略读取CSV文件的标题行（第一行）
		 */
		bf.readLine();

		/**
		 * 遍历读取文件中除第一行外的其他所有行内容 并存储在名为records的ArrayList中
		 * 每一个recods中存储的对象为一个String数组
		 */
		while ((record = bf.readLine()) != null) {
			String fields[] = record.replaceAll("\"\"", "\"").split(
					",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)");

			for (int i = 0; i < fields.length; i++) {
				// 处理之后的单元格数据重新写入一维数组
				System.out.println(fields[i]);
				// fields[i] = fields[i].replaceAll("^\"",
				// "").replaceAll("\"$","");
			}

			records.add(fields);
		}
		bf.close();

		/**
		 * 定义函数返回值，即Object[][] 将存储测试数据的list转换为一个Object的二维数组
		 */
		Object[][] results = new Object[records.size()][];

		for (int i = 0; i < records.size(); i++) {
			results[i] = records.get(i);
		}
		return results;
	}

	public static ArrayList<String[]> readCSV(String filePath) throws Exception {
		CsvReader reader = null;
		ArrayList<String[]> dataList = new ArrayList<String[]>();
		
		try {
			// 如果生产文件乱码，windows下用gbk，linux用UTF-8
			reader = new CsvReader(filePath, separator, Charset.forName("utf-8"));

			// 读取表头
			reader.readHeaders();
			
			String[] headArray = reader.getHeaders();// 获取标题
			System.out.println("我是"+headArray[0] + headArray[1] + headArray[2]);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != reader) {
				reader.close();
			}
		}

		return dataList;
	}

	public static void main(String[] args) throws Exception {
		String destFile = "D:\\javaworkspace\\springbootSwagger\\src\\test\\resources\\test.csv";
		Object[][] ob = HandleCsvUtils.getDatasByCSV(destFile);

		for (int i = 0; i < ob.length; i++) {
			for (int j = 0; j < ob[i].length; j++) {
				System.out.println(ob[i][j]);
			}

		}
		List<String[]> res = HandleCsvUtils.readCSV(destFile);

		for (int i = 0; i < res.size(); i++) {
			System.out.println(res.get(i));
		}
	}
}