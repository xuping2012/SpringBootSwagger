package com.xp.test.common.utils;

import java.io.FileWriter;
import java.io.IOException;

public class HandlerCsvUtils {

	/**
	 * @param filePath
	 *            :文件路徑
	 * @param content
	 *            ：写入文件的内容
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

	// public static void main(String[] args) throws IOException {
	// // TODO Auto-generated method stub
	// String path = "D:/errorData1.csv";
	// AppendFile.appendFile(path, "列名1" + ',' + "列名2");
	// }
}
