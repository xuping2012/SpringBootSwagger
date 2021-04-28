package com.xp.test.common.reports;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.testng.IInvokedMethod;
import org.testng.ISuite;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestNG;
import org.testng.xml.XmlSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.ResourceCDN;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.vimalselvam.testng.EmailReporter;
import com.vimalselvam.testng.NodeName;
import com.vimalselvam.testng.SystemInfo;
import com.vimalselvam.testng.listener.ExtentTestNgFormatter;

/**
 * 
 * TODO:拿来主义
 *
 * @author Joe-Tester
 * @time 2021年4月14日
 * @file MyExtentTestNgListener.java
 */
public class MyExtentTestNgListener extends ExtentTestNgFormatter {

	// 报告目录
	private static final String REPORTER_ATTR = "extentTestNgReporter";
	private static final String SUITE_ATTR = "extentTestNgSuite";
	// aventstack这个类的报告
	private ExtentReports reporter;
	private List<String> testRunnerOutput;
	private Map<String, String> systemInfo;
	private ExtentHtmlReporter htmlReporter;
	// 环境信息
	private MySystemInfo mySystemInfo;
	// 报告文件名称
	private String MyReportPrefix = "TestNGExtentReport_";
	private String MyEmailReportPrefix = "EmailTestNGReport_";
	private static ExtentTestNgFormatter instance;

	/**
	 * 构造方法
	 */
	public MyExtentTestNgListener() {
		setInstance(this);
		// 报告加入环境信息
		mySystemInfo = new MySystemInfo();
		systemInfo = mySystemInfo.getSystemInfo();

		testRunnerOutput = new ArrayList<>();
		// 获取报告路径<包含文件名>，这个文件变量没有配置
		String reportPathStr = System.getProperty("reportPath");

		// 这是文件路径
		File reportPath;

		try {
			reportPath = new File(reportPathStr);
		} catch (NullPointerException e) {
			// testng报告默认输出目录
			reportPath = new File(TestNG.DEFAULT_OUTPUTDIR);
		}
		// 判断目录是否存在
		if (!reportPath.exists()) {
			if (!reportPath.mkdirs()) {
				throw new RuntimeException(
						"Failed to create output run directory");
			}
		}

		// 获取当前执行时间，生成报告文件
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-hh-mm");
		// 报告文件名及路径
		File reportFile = new File(reportPath, MyReportPrefix + df.format(date)
				+ ".html");
		File emailReportFile = new File(reportPath, MyEmailReportPrefix
				+ df.format(date) + ".html");
		// 报告分详细报告及邮件报告
		htmlReporter = new ExtentHtmlReporter(reportFile);
		EmailReporter emailReporter = new EmailReporter(emailReportFile);

		// 创建报告对象
		reporter = new ExtentReports();
		// 如果cdn.rawgit.com访问不了，可以设置为：ResourceCDN.EXTENTREPORTS或者ResourceCDN.GITHUB
		// 这里加入了报告的样式
		htmlReporter.config().setResourceCDN(ResourceCDN.EXTENTREPORTS);
		reporter.attachReporter(htmlReporter, emailReporter);

	}

	/**
	 * Gets the instance of the {@link ExtentTestNgFormatter}
	 *
	 * @return The instance of the {@link ExtentTestNgFormatter}
	 */
	public static ExtentTestNgFormatter getInstance() {
		return instance;
	}

	/**
	 * Sets the instance of the @link ExtentTestNgFormatter}
	 * 
	 * @param formatter
	 */
	private static void setInstance(ExtentTestNgFormatter formatter) {
		instance = formatter;
	}

	/**
	 * Gets the system information map
	 *
	 * @return The system information map
	 */
	public Map<String, String> getSystemInfo() {
		return systemInfo;
	}

	/**
	 * Sets the system information
	 *
	 * @param systemInfo
	 *            The system information map
	 */
	public void setSystemInfo(Map<String, String> systemInfo) {
		this.systemInfo = systemInfo;
	}

	/**
	 * 开始测试
	 */
	public void onStart(ISuite iSuite) {
		// 读取testng.xml测试套件
		if (iSuite.getXmlSuite().getTests().size() > 0) {
			ExtentTest suite = reporter.createTest(iSuite.getName());
			String configFile = iSuite.getParameter("report.config");

			if (!Strings.isNullOrEmpty(configFile)) {
				htmlReporter.loadXMLConfig(configFile);
			}

			String systemInfoCustomImplName = iSuite
					.getParameter("system.info");
			if (!Strings.isNullOrEmpty(systemInfoCustomImplName)) {
				generateSystemInfo(systemInfoCustomImplName);
			}

			iSuite.setAttribute(REPORTER_ATTR, reporter);
			iSuite.setAttribute(SUITE_ATTR, suite);
		}
	}

	/**
	 * 
	 * @param systemInfoCustomImplName
	 */
	private void generateSystemInfo(String systemInfoCustomImplName) {
		try {
			Class<?> systemInfoCustomImplClazz = Class
					.forName(systemInfoCustomImplName);
			if (!SystemInfo.class.isAssignableFrom(systemInfoCustomImplClazz)) {
				throw new IllegalArgumentException(
						"The given system.info class name <"
								+ systemInfoCustomImplName
								+ "> should implement the interface <"
								+ SystemInfo.class.getName() + ">");
			}

			SystemInfo t = (SystemInfo) systemInfoCustomImplClazz.newInstance();
			setSystemInfo(t.getSystemInfo());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException e) {
			throw new IllegalStateException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vimalselvam.testng.listener.ExtentTestNgFormatter#onFinish(org.testng
	 * .ISuite)
	 */
	public void onFinish(ISuite iSuite) {
	}

	/**
	 * 开始测试
	 */
	public void onTestStart(ITestResult iTestResult) {
		MyReporter.setTestName(iTestResult.getName());
	}

	/**
	 * 测试通过
	 */
	public void onTestSuccess(ITestResult iTestResult) {

	}

	/**
	 * 测试失败
	 */
	public void onTestFailure(ITestResult iTestResult) {

	}

	/**
	 * 取消测试
	 */
	public void onTestSkipped(ITestResult iTestResult) {

	}

	/**
	 * 测试结果失败不再统计成功百分比
	 */
	public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

	}

	/**
	 * 开始测试
	 */
	public void onStart(ITestContext iTestContext) {
		ISuite iSuite = iTestContext.getSuite();
		ExtentTest suite = (ExtentTest) iSuite.getAttribute(SUITE_ATTR);
		ExtentTest testContext = suite.createNode(iTestContext.getName());
		// 自定义报告
		MyReporter.report = testContext;
		iTestContext.setAttribute("testContext", testContext);
	}

	/**
	 * 完成测试，结果判断
	 */
	public void onFinish(ITestContext iTestContext) {
		ExtentTest testContext = (ExtentTest) iTestContext
				.getAttribute("testContext");
		if (iTestContext.getFailedTests().size() > 0) {
			testContext.fail("Failed");
		} else if (iTestContext.getSkippedTests().size() > 0) {
			testContext.skip("Skipped");
		} else {
			testContext.pass("Passed");
		}
	}

	/**
	 * 调用之前
	 */
	public void beforeInvocation(IInvokedMethod iInvokedMethod,
			ITestResult iTestResult) {
		if (iInvokedMethod.isTestMethod()) {
			ITestContext iTestContext = iTestResult.getTestContext();
			ExtentTest testContext = (ExtentTest) iTestContext
					.getAttribute("testContext");
			ExtentTest test = testContext.createNode(iTestResult.getName(),
					iInvokedMethod.getTestMethod().getDescription());
			iTestResult.setAttribute("test", test);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vimalselvam.testng.listener.ExtentTestNgFormatter#afterInvocation
	 * (org.testng.IInvokedMethod, org.testng.ITestResult)
	 */
	public void afterInvocation(IInvokedMethod iInvokedMethod,
			ITestResult iTestResult) {
		if (iInvokedMethod.isTestMethod()) {
			ExtentTest test = (ExtentTest) iTestResult.getAttribute("test");
			List<String> logs = Reporter.getOutput(iTestResult);
			for (String log : logs) {
				test.info(log);
			}

			int status = iTestResult.getStatus();
			if (ITestResult.SUCCESS == status) {
				test.pass("Passed");
			} else if (ITestResult.FAILURE == status) {
				test.fail(iTestResult.getThrowable());
			} else {
				test.skip("Skipped");
			}

			for (String group : iInvokedMethod.getTestMethod().getGroups()) {
				test.assignCategory(group);
			}
		}
	}

	/**
	 * Adds a screen shot image file to the report. This method should be used
	 * only in the configuration method and the {@link ITestResult} is the
	 * mandatory parameter
	 *
	 * @param iTestResult
	 *            The {@link ITestResult} object
	 * @param filePath
	 *            The image file path
	 * @throws IOException
	 *             {@link IOException}
	 */
	public void addScreenCaptureFromPath(ITestResult iTestResult,
			String filePath) throws IOException {
		ExtentTest test = (ExtentTest) iTestResult.getAttribute("test");
		test.addScreenCaptureFromPath(filePath);
	}

	/**
	 * Adds a screen shot image file to the report. This method should be used
	 * only in the {@link org.testng.annotations.Test} annotated method
	 *
	 * @param filePath
	 *            The image file path
	 * @throws IOException
	 *             {@link IOException}
	 */
	public void addScreenCaptureFromPath(String filePath) throws IOException {
		ITestResult iTestResult = Reporter.getCurrentTestResult();
		Preconditions.checkState(iTestResult != null);
		ExtentTest test = (ExtentTest) iTestResult.getAttribute("test");
		test.addScreenCaptureFromPath(filePath);
	}

	/**
	 * Sets the test runner output
	 *
	 * @param message
	 *            The message to be logged
	 */
	public void setTestRunnerOutput(String message) {
		testRunnerOutput.add(message);
	}

	/**
	 * 生成报告
	 */
	public void generateReport(List<XmlSuite> list, List<ISuite> list1, String s) {
		if (getSystemInfo() != null) {
			for (Map.Entry<String, String> entry : getSystemInfo().entrySet()) {
				reporter.setSystemInfo(entry.getKey(), entry.getValue());
			}
		}
		reporter.setTestRunnerOutput(testRunnerOutput);
		reporter.flush();
	}

	/**
	 * Adds the new node to the test. The node name should have been set already
	 * using {@link NodeName}
	 */
	public void addNewNodeToTest() {
		addNewNodeToTest(NodeName.getNodeName());
	}

	/**
	 * Adds the new node to the test with the given node name.
	 *
	 * @param nodeName
	 *            The name of the node to be created
	 */
	public void addNewNodeToTest(String nodeName) {
		addNewNode("test", nodeName);
	}

	/**
	 * Adds a new node to the suite. The node name should have been set already
	 * using {@link NodeName}
	 */
	public void addNewNodeToSuite() {
		addNewNodeToSuite(NodeName.getNodeName());
	}

	/**
	 * Adds a new node to the suite with the given node name
	 *
	 * @param nodeName
	 *            The name of the node to be created
	 */
	public void addNewNodeToSuite(String nodeName) {
		addNewNode(SUITE_ATTR, nodeName);
	}

	/**
	 * 
	 * @param parent
	 * @param nodeName
	 */
	private void addNewNode(String parent, String nodeName) {
		ITestResult result = Reporter.getCurrentTestResult();
		Preconditions.checkState(result != null);
		ExtentTest parentNode = (ExtentTest) result.getAttribute(parent);
		ExtentTest childNode = parentNode.createNode(nodeName);
		result.setAttribute(nodeName, childNode);
	}

	/**
	 * Adds a info log message to the node. The node name should have been set
	 * already using {@link NodeName}
	 *
	 * @param logMessage
	 *            The log message string
	 */
	public void addInfoLogToNode(String logMessage) {
		addInfoLogToNode(logMessage, NodeName.getNodeName());
	}

	/**
	 * Adds a info log message to the node
	 *
	 * @param logMessage
	 *            The log message string
	 * @param nodeName
	 *            The name of the node
	 */
	public void addInfoLogToNode(String logMessage, String nodeName) {
		ITestResult result = Reporter.getCurrentTestResult();
		Preconditions.checkState(result != null);
		ExtentTest test = (ExtentTest) result.getAttribute(nodeName);
		test.info(logMessage);
	}

	/**
	 * Marks the node as failed. The node name should have been set already
	 * using {@link NodeName}
	 *
	 * @param t
	 *            The {@link Throwable} object
	 */
	public void failTheNode(Throwable t) {
		failTheNode(NodeName.getNodeName(), t);
	}

	/**
	 * Marks the given node as failed
	 *
	 * @param nodeName
	 *            The name of the node
	 * @param t
	 *            The {@link Throwable} object
	 */
	public void failTheNode(String nodeName, Throwable t) {
		ITestResult result = Reporter.getCurrentTestResult();
		Preconditions.checkState(result != null);
		ExtentTest test = (ExtentTest) result.getAttribute(nodeName);
		test.fail(t);
	}

	/**
	 * Marks the node as failed. The node name should have been set already
	 * using {@link NodeName}
	 *
	 * @param logMessage
	 *            The message to be logged
	 */
	public void failTheNode(String logMessage) {
		failTheNode(NodeName.getNodeName(), logMessage);
	}

	/**
	 * Marks the given node as failed
	 *
	 * @param nodeName
	 *            The name of the node
	 * @param logMessage
	 *            The message to be logged
	 */
	public void failTheNode(String nodeName, String logMessage) {
		ITestResult result = Reporter.getCurrentTestResult();
		Preconditions.checkState(result != null);
		ExtentTest test = (ExtentTest) result.getAttribute(nodeName);
		test.fail(logMessage);
	}
}