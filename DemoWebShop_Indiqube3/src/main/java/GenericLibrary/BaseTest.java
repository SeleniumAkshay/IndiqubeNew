package GenericLibrary;

import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class BaseTest {
	
	public WebDriver driver;
	public static WebDriver listenerDriver;
	public DataUtility dutil=new DataUtility();
	public UtilityMethods um=new UtilityMethods();
	public static ExtentSparkReporter sparkReporter ;
	public static ExtentReports report;
	public static ExtentTest test;
	
	@BeforeSuite
	public void beforeSuite() {
		System.out.println("@BeforeSuite");
		sparkReporter =new ExtentSparkReporter("./Reports/"+um.currentTime()+".html");
		report=new ExtentReports();
	}
	//Jenkins
	@BeforeTest
	public void beforeTest() {
		System.out.println("@BeforeTest");
		report.attachReporter(sparkReporter);
	}
	
	
	@BeforeClass
	public void launchBrowser(@Optional("chrome")String browser) throws IOException {
		System.out.println("@BeforeClass");
		
		if (browser.equals("chrome")) {
			driver=new ChromeDriver();		
		}
		else if (browser.equals("firefox")) {
			driver=new FirefoxDriver();			
		}
		else if (browser.equals("edge")) {
			driver=new EdgeDriver();
		}
		else {
			System.out.println(" Enter Valid Browser Name ");
		}		
		listenerDriver=driver;
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
		driver.get(dutil.getPropertyData("url"));
	}
	
	@BeforeMethod
	public void performLogin(Method method) throws IOException {
		System.out.println("@BeforeMethod");
		test=report.createTest(method.getName());
		
		driver.findElement(By.linkText("Log in")).click();
		driver.findElement(By.id("Email")).sendKeys(dutil.getPropertyData("email"));
		driver.findElement(By.id("Password")).sendKeys(dutil.getPropertyData("pass"));
		driver.findElement(By.xpath("//input[@value='Log in']")).click();
	}
	
	@AfterMethod
	public void performLogout() {
		System.out.println("@AfterMethod");
		
		driver.findElement(By.linkText("Log out")).click();
	}
	
	@AfterClass
	public void closeBrowser() {
		System.out.println("@AfterClass");
		
		driver.close();
	}
	
	@AfterTest
	public void afterTest() {
		System.out.println("@AfterTest");
	}
	
	@AfterSuite
	public void afterSuite() {
		System.out.println("@AfterSuite");
		report.flush();
	}
	
	

}
