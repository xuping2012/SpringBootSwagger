package com.xp.test.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 灵活读取excel数据
 * 
 * @author qguan
 *
 */
public class HandleExcelUtils {

	final static Logger Log = Logger.getLogger(HandleExcelUtils.class);

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
	public static Object[][] getDataByExcel(String excelPath, int sheetIndex,
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

	/**
	 * 传入文件路径,默认读取第一个Sheet页的数据
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings({ "resource", "deprecation" })
	public Object[][] getDataByExcel(String filePath) throws IOException {
		File file = new File(filePath);
		FileInputStream inputStream = new FileInputStream(file);
		Workbook workbook = null;

		// 获取文件名参数的拓展名，判断是.xlsx还是xls
		String fileExtensionName = filePath.substring(filePath.indexOf("."));
		// 如果是.xlsx,则使用XSSFWorkbook对象进行实例化
		// 如果是.xls,则使用HSSFWorkbook对象进行实例化
		if (fileExtensionName.equals(".xlsx")) {
			workbook = new XSSFWorkbook(inputStream);
		} else if (fileExtensionName.equals(".xls")) {
			workbook = new HSSFWorkbook(inputStream);
		} else {
			Log.info("该文件不是excel文件");
		}

		// 通过sheetName参数，生成sheet对象
		Sheet sheet = workbook.getSheetAt(0);
		// 获取Excel数据文件sheet1中数据的行数，getLastRowNum方法获取数据的最后一行行号,即索引：01234如果是2,
		// getFirstRowNum方法获取数据的第一行行号，相减之后算出数据的行数
		// Excel的行号和列号都是从0开始的，下面計算出的是索引,即比行总数小1,所以取不到最後一行,可以在這裏+1,也可以在下面的for循環中遍歷條件用上=最後一行
		int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();

		// 创建名为records的list对象来存储从Excel中读取的数据
		List<Object[]> records = new ArrayList<Object[]>();

		// 使用两个for循环遍历Excel数据文件的所有数据。（除去第一行的列名称）
		for (int i = 1; i <= rowCount; i++) {
			Row row = sheet.getRow(i);
			// 声明一个数组files，用来存储excel数据文件每行数据，数组的大小用getLastCellNum方法来进行动态声明。
			String files[] = new String[row.getLastCellNum()];
			for (int j = 0; j < row.getLastCellNum(); j++) {
				// Cannot get a STRING value from a NUMERIC cell
				// ;为了解决单元格数据读取数字类型异常，先储存数据前强制转换String類型
				row.getCell(j).setCellType(XSSFCell.CELL_TYPE_STRING);
				files[j] = row.getCell(j).getStringCellValue();
			}
			// 把files的数据对象存在records中
			records.add(files);
		}
		Object[][] results = new Object[records.size()][];
		for (int i = 0; i < records.size(); i++) {
			results[i] = records.get(i);
		}
		return results;
	}

	/**
	 * 文件的路径，和sheet页名称
	 * 
	 * @param filePath
	 * @param sheetName
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public static Object[][] getDataByExcel(String filePath, String sheetName)
			throws IOException {
		File file = new File(filePath);
		FileInputStream inputStream = new FileInputStream(file);

		Workbook workbook = null;

		// 获取文件名参数的拓展名，判断是.xlsx还是xls
		String fileExtensionName = filePath.substring(filePath.indexOf("."));
		// 如果是.xlsx,则使用XSSFWorkbook对象进行实例化
		// 如果是.xls,则使用HSSFWorkbook对象进行实例化
		if (fileExtensionName.equals(".xlsx")) {
			workbook = new XSSFWorkbook(inputStream);
		} else if (fileExtensionName.equals(".xls")) {
			workbook = new HSSFWorkbook(inputStream);
		} else {
			Log.info("该文件不是excel文件");
		}

		// 通过sheetName参数，生成sheet对象
		Sheet sheet = workbook.getSheet(sheetName);
		// 获取Excel数据文件sheet1中数据的行数，getLastRowNum方法获取数据的最后一行行号
		// getFirstRowNum方法获取数据的第一行行号，相减之后算出数据的行数
		// Excel的行号和列号都是从0开始的
		int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();

		// 创建名为records的list对象来存储从Excel中读取的数据
		List<Object[]> records = new ArrayList<Object[]>();

		// 使用两个for循环遍历Excel数据文件的所有数据。（除去第一行的列名称）
		for (int i = 1; i < rowCount; i++) {
			Row row = sheet.getRow(i);
			// 声明一个数组files，用来存储excel数据文件每行数据，数组的大小用getLastCellNum方法来进行动态声明。
			String files[] = new String[row.getLastCellNum()];
			for (int j = 0; j < row.getLastCellNum(); j++) {
				files[j] = row.getCell(j).getStringCellValue();
			}
			// 将files的数据对象存在records中
			records.add(files);
		}
		Object[][] results = new Object[records.size()][];
		for (int i = 0; i < records.size(); i++) {
			results[i] = records.get(i);
		}

		return results;

	}

	/**
	 * 数据写入excel
	 * 
	 * @param filePath
	 * @param sheetIndex
	 * @param rowIndex
	 * @param cellIndex
	 * @param data
	 */
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