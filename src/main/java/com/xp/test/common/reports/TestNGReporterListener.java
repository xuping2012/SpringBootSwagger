package com.xp.test.common.reports;

import java.io.File;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.xml.XmlSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.ResourceCDN;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.model.TestAttribute;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

/**
 * 
 * TODO:直接引用的，拿来即用
 * 
 * @file TestNGReporterListener.java
 */
public class TestNGReporterListener implements IReporter {

	// 这里自己需要指定报告生成路径
	private static final String OUTPUT_DIR = "test-output/";
	private static final String FILE_NAME = "TestNGReporter_index.html";
	private ExtentReports extent;

	/**
	 * 下面这些所有的东西不要乱码，除了一些自己可以修改的，如报告名称等
	 */
	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites,
			String outputDirectory) {
		// TODO Auto-generated method stub
		init();
		boolean createSuiteNode = false;
		if (suites.size() > 1) {
			createSuiteNode = true;
		}
		for (ISuite suite : suites) {
			Map<String, ISuiteResult> result = suite.getResults();
			// 如果suite没有用例，直接跳过，不在报告中生成
			if (result.size() == 0) {
				continue;
			}
			// 统计suite下的成功、失败、跳过的总用例数
			int suiteFailSize = 0;
			int suitePassSize = 0;
			int suiteSkipSize = 0;
			ExtentTest suiteTest = null;
			// 存在多个suite的情况，在报告中将同一个suite结果归为一类，创建一级节点
			if (createSuiteNode) {
				suiteTest = extent.createTest(suite.getName()).assignCategory(
						suite.getName());
			}
			boolean createSuiteResultNode = false;
			if (result.size() > 1) {
				createSuiteResultNode = true;
			}
			for (ISuiteResult r : result.values()) {
				ExtentTest resultNode;
				ITestContext context = r.getTestContext();
				if (createSuiteResultNode) {
					// 没有创建suite的情况下，将在suiteresult的创建为一级节点，否则创建为suite的一个子节点
					if (null == suiteTest) {
						resultNode = extent.createTest(r.getTestContext()
								.getName());
					} else {
						resultNode = suiteTest.createNode(r.getTestContext()
								.getName());
					}
				} else {
					resultNode = suiteTest;
				}
				if (resultNode != null) {
					resultNode.getModel().setName(
							suite.getName() + ":"
									+ r.getTestContext().getName());
					if (resultNode.getModel().hasCategory()) {
						resultNode.assignCategory(r.getTestContext().getName());
					} else {
						resultNode.assignCategory(suite.getName(), r
								.getTestContext().getName());
					}
					resultNode.getModel().setStartTime(
							r.getTestContext().getStartDate());
					resultNode.getModel().setEndTime(
							r.getTestContext().getEndDate());
					// 统计suitetest下的数据
					int passSize = r.getTestContext().getPassedTests().size();
					int failSize = r.getTestContext().getFailedTests().size();
					int skipSize = r.getTestContext().getSkippedTests().size();
					suitePassSize += passSize;
					suiteFailSize += failSize;
					suiteSkipSize += skipSize;
					if (failSize > 0) {
						resultNode.getModel().setStatus(Status.FAIL);
					}
					resultNode.getModel().setDescription(
							String.format("Pass:%s;Fail:%s;Skip:%s", passSize,
									failSize, skipSize));
				}
				buildTestNodes(resultNode, context.getFailedTests(),
						Status.FAIL);
				buildTestNodes(resultNode, context.getSkippedTests(),
						Status.FAIL);
				buildTestNodes(resultNode, context.getPassedTests(),
						Status.FAIL);
			}
			if (suiteTest != null) {
				suiteTest.getModel().setDescription(
						String.format("Pass:%s;Fail:%s;Skip:%s", suitePassSize,
								suiteFailSize, suiteSkipSize));
				if (suiteFailSize > 0) {
					suiteTest.getModel().setStatus(Status.FAIL);
				}
			}
		}
		extent.flush();
	}

	/**
	 * 
	 * @param extenttest
	 * @param tests
	 * @param status
	 */
	private void buildTestNodes(ExtentTest extenttest, IResultMap tests,
			Status status) {
		// TODO Auto-generated method stub
		// 存在父节点，获取父节点的标签
		String[] categories = new String[0];
		if (extenttest != null) {
			List<TestAttribute> categoryList = extenttest.getModel()
					.getCategoryContext().getAll();
			categories = new String[categoryList.size()];
			for (int index = 0; index < categoryList.size(); index++) {
				categories[index] = categoryList.get(index).getName();
			}
		}
		ExtentTest test;
		if (tests.size() > 0) {
			Set<ITestResult> treeSet = new TreeSet<ITestResult>(
					new Comparator<ITestResult>() {

						public int compare(ITestResult o1, ITestResult o2) {
							// TODO Auto-generated method stub
							return o1.getStartMillis() < o2.getStartMillis() ? -1
									: 1;
						}
					});
			treeSet.addAll(tests.getAllResults());
			for (ITestResult result : treeSet) {
				Object[] parameters = result.getParameters();
				String name = "";

				for (Object param : parameters) {
					name += param.toString();
				}
				if (name.length() > 0) {
					if (name.length() > 50) {
						name = name.substring(0, 49) + "...";
					}
				} else {
					name = result.getMethod().getMethodName();
				}
				if (extenttest == null) {
					test = extent.createTest(name);
				} else {
					test = extenttest.createNode(name).assignCategory(
							categories);

				}
				for (String group : result.getMethod().getGroups()) {
					test.assignCategory(group);
					List<String> outputList = Reporter.getOutput(result);
					for (String output : outputList) {
						test.debug(output);
					}
					if (result.getThrowable() != null) {
						test.log(status, result.getThrowable());

					} else {
						test.log(status, "Test"
								+ status.toString().toLowerCase() + "ed");
					}
					test.getModel().setStartTime(
							getTime(result.getStartMillis()));
					test.getModel().setEndTime(getTime(result.getEndMillis()));
				}
			}

		}
	}

	/**
	 * 计算执行时间
	 * 
	 * @param startMillis
	 * @return
	 */
	private Date getTime(long startMillis) {
		// TODO Auto-generated method stub
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(startMillis);
		return calendar.getTime();
	}

	/**
	 * 初始化报告路径及文件名
	 */
	private void init() {
		// TODO Auto-generated method stub
		// 文件不存在进行创建
		File reportDir = new File(OUTPUT_DIR);
		if (!reportDir.exists() && !reportDir.isDirectory()) {
			reportDir.mkdir();
		}
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(OUTPUT_DIR
				+ FILE_NAME);
		// 解决dns问题，配置生成报告样式
		htmlReporter.config().setResourceCDN(ResourceCDN.EXTENTREPORTS);
		htmlReporter.config().setDocumentTitle("自动化接口测试报告");
		htmlReporter.config().setReportName("自动化接口测试报告");
		htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReporter.config().setTheme(Theme.STANDARD);
		htmlReporter
				.config()
				.setCSS(".node.level-1 ul{display:none;} .node.level-1.active ul{display:block;}");
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		extent.setReportUsesManualConfiguration(true);
	}

}
