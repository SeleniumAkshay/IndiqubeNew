package TestScripts;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReport {
	
	public static void main(String[] args) {
		
		ExtentSparkReporter sparkReporter =new ExtentSparkReporter("./Reports/RegSuite.html");
		
		ExtentReports report=new ExtentReports();
		
		report.attachReporter(sparkReporter);
		
		ExtentTest test = report.createTest("TestCase 1");
		
		test.log(Status.INFO,"Information About testcase 1");
		
		test.log(Status.PASS,"Execution is started");

		test.log(Status.FAIL,"Execution is stoped");
		
		report.flush();
		
	}

}
