package utils;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import pagesobjects.BasePage;

public class ReportUtil implements ITestListener {
	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public static ExtentTest test;
	static WebDriver driver;
	String repName;

	private static Logger logger = LogManager.getLogger(ReportUtil.class);

	public static void setDriver(WebDriver driver) {
		ReportUtil.driver = driver;
	}
	
	public static void addStepLog(Status status, String message) {
		if (test != null) {
	        test.log(status, message);
	    }
	}
	public void onStart(ITestContext testContext) {

		/*
		 * SimpleDateFormat df= new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss"); Date dt=
		 * new Date(); String currentdatetimestamp=df.format(dt); // creating a time
		 * stamp and returning in a string format.
		 */

		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());// creating a time stamp and
																							// returning in a string
																							// format.
		repName = "Test-Report-" + timeStamp + ".html";// creating a report name with .html format
		sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "\\reports\\" + repName);// specify the
																											// location
																											// of the
																											// report.

		sparkReporter.config().setDocumentTitle("opencart Automation Report");// Title of the report
		sparkReporter.config().setReportName("opencart Functional Testing");// name of the report.
		sparkReporter.config().setTheme(Theme.DARK);// Theme of the report.

		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Application", "opencart");//
		extent.setSystemInfo("Module", "Admin");
		extent.setSystemInfo("Sub Module", "Customers");
		extent.setSystemInfo("User Name", System.getProperty("user.name"));// displays the tester name
		extent.setSystemInfo("Environment", "QA");

		String os = testContext.getCurrentXmlTest().getParameter("os");
		extent.setSystemInfo("Operating System", os);// operating system info

		String browser = testContext.getCurrentXmlTest().getParameter("browser");
		extent.setSystemInfo("Browser", browser); // browser info

		List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();
		if (!includedGroups.isEmpty()) {
			extent.setSystemInfo("Groups", includedGroups.toString()); // displays the info about the groupings
		}
		
		logger.info("Test execution started for: {}", testContext.getSuite().getName());
	}

	@Override
	public void onTestStart(ITestResult result) {
		// Log when a test starts

		logger.info("Test '{}' started running.", result.getName());

	}

	public void onTestSuccess(ITestResult result) {
		test = extent.createTest(result.getTestClass().getName());// on success getting the class name
		test.assignCategory(result.getMethod().getGroups()); // on success getting the groups in the report
		test.log(Status.PASS, result.getName() + " --> got successfully executed.");
		logger.info("Test '{}'Test PASSED.", result.getName());
	}

	public void onTestFailure(ITestResult result) {
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());

		test.log(Status.FAIL, result.getName() + " got failed.");
		test.log(Status.INFO, result.getThrowable().getMessage());// displays the fail message
		if (driver != null) {
			try {
				String imgPath = new BasePage(driver).captureScreen(result.getName());// taking the screen shot when the
																						// test
																						// fails .
				test.addScreenCaptureFromPath(imgPath);
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		} else {
			test.log(Status.WARNING, "WebDriver is not initialized. Cannot capture screenshot.");
			System.out.println("Screenshot not taken: driver is null.");
		}
		logger.info("Test '{}'Test FAILED.", result.getName());
	}

	public void onFinish(ITestContext testContext) {
		extent.flush();
		String pathOfExtentReport = System.getProperty("user.dir") + "\\reports\\" + repName;
		File extentReport = new File(pathOfExtentReport);

		try {
			Desktop.getDesktop().browse(extentReport.toURI()); // opens the report automatically in the browser when the
																// testing is complete.
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.info("Test execution Finished for: {}", testContext.getSuite().getName());

		// E-mailing the report

	}

}