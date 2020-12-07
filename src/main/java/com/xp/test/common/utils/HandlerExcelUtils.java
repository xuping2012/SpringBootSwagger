package com.xp.test.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;


public class HandlerExcelUtils {

	final static Logger Log = Logger.getLogger(HandlerExcelUtils.class);

	/**
	 * @param excelPath
	 *            :文件路径
	 * @param sheetIndex
	 *            :excel的sheet索引，默认从0开始
	 * @param startRowOfInterval
	 *            :取开始的行号
	 * @param endRowOfInterval
	 *            :取结束的行号
	 * @param cols
	 *            :取对应的列号组成的数组
	 * @return
	 */
	@SuppressWarnings("finally")
	public static Object[][] getExcelDatas(String excelPath, int sheetIndex,
			int startRowOfInterval, int endRowOfInterval, int[] cols) {

		// 准备数组保存测试数据
		Object[][] datas = new Object[endRowOfInterval - startRowOfInterval + 1][cols.length];
		InputStream inStream = null;

		try {
			// 获取输入流对象
			inStream = new FileInputStream(new File(excelPath));
			// 创建工作簿对象，WorkbookFactory支持xls及xlsx两种格式
			Workbook workbook = WorkbookFactory.create(inStream);
			// 根据表单索引获取表单对象
			Sheet sheet = workbook.getSheetAt(sheetIndex);
			// 使用两个for循环遍历Excel数据文件的所有数据。（除去第一行的列名称）
			for (int i = startRowOfInterval; i <= endRowOfInterval; i++) {
				// 根据行索引取出行
				Row row = sheet.getRow(i - 1);
				// 循环取列
				for (int j = 0; j < cols.length; j++) {
					// 根据列索引，取出列对象
					Cell cell = row.getCell(cols[j],
							MissingCellPolicy.CREATE_NULL_AS_BLANK);
					cell.setCellType(CellType.STRING);
					// 取出值
					String cellValue = cell.getStringCellValue();
					// 保存到数组
					datas[i - startRowOfInterval][j] = cellValue;
				}
				// 把files的数据对象存在records中
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (inStream != null) {
				try {
					inStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			return datas;
		}
	}


	public static void writeDataToExcel(String filePath, int sheetIndex,
			int rowIndex, int cellIndex, String data) {
		InputStream inStream = null;
		OutputStream outStream = null;

		try {
			File file = new File(filePath);
			inStream = new FileInputStream(file);
			outStream = new FileOutputStream(file);

			Workbook wb = WorkbookFactory.create(inStream);
			
			Sheet st = wb.getSheetAt(sheetIndex);
			Row r = st.getRow(rowIndex);
			Cell c = r.getCell(cellIndex,
					MissingCellPolicy.CREATE_NULL_AS_BLANK);
			c.setCellType(CellType.STRING);
			c.setCellValue(data);

			wb.write(outStream);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				outStream.close();
				inStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}