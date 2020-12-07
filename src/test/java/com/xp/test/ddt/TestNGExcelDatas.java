package com.xp.test.ddt;

import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TestNGExcelDatas {

	final static Logger Log = Logger.getLogger(TestNGExcelDatas.class);

	static String sourceFile;

	/**
	 * 文件路径写死工程目录，但是文件名可能需要变化
	 * 
	 * @return
	 * @throws IOException
	 */
	public static String getPath(String fileName) throws IOException {
		File directory = new File(".");
		sourceFile = directory.getCanonicalPath() + "/src/test/resources/"
				+ fileName;
		Log.info("獲取文件路徑：" + sourceFile);
		return sourceFile;
	}

	/**
	 * 文件路径写死工程目录
	 * 
	 * @return
	 * @throws IOException
	 */
	public static String getPath() throws IOException {
		File directory = new File(".");
		sourceFile = directory.getCanonicalPath() + "/src/test/resources/"
				+ "test.xls";
		Log.info("獲取文件路徑：" + sourceFile);
		return sourceFile;
	}

	/**
	 * 获取excel数据源
	 * 
	 * @return
	 * @throws IOException
	 */
	@DataProvider(name = "testDate")
	public static Object[][] word() throws IOException {
		return getTestData(getPath("test.xls"));
	}

	/**
	 * 参数化测试用例
	 * 
	 * @param searchWord1
	 * @param searchWord2
	 * @param searchResult
	 */
	@Test(dataProvider = "testDate")
	public void testDataDrivernByExcelFile(String param1, String param2,
			String param3) {
		Log.info("測試：" + param1 + ',' + param2);
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
	public static Object[][] getTestData(String filePath, String sheetName)
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
	 * 传入文件路径,默认读取第一个Sheet页的数据
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings({ "resource", "deprecation" })
	public static Object[][] getTestData(String filePath) throws IOException {
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
}