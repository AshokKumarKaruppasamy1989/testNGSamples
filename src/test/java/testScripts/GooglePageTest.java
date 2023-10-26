package testScripts;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class GooglePageTest {
	WebDriver driver;
	
	ExtentReports reports;
	ExtentSparkReporter spark;
	ExtentTest test;
	
	@BeforeTest
	public void setupExtent() {
		reports = new ExtentReports();
		spark = new ExtentSparkReporter("target/spark.html");
		reports.attachReporter(spark);
	}

	@BeforeMethod
//	@BeforeTest
	@Parameters("browser")
	public void driverSetup(String browser) {
		if (browser.equalsIgnoreCase("chrome")) {
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("edge")) {
			driver = new EdgeDriver();
		} else if (browser.equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();
		}

		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
	}

	@Test(alwaysRun = true, dependsOnMethods = "seleniumSearchTest")
	public void javaSearchTest() {
		
		test = reports.createTest("javaSearchTest");
//		WebDriver driver = new ChromeDriver();
		driver.navigate().to("https://www.google.com");
		WebElement search = driver.findElement(By.id("APjFqb"));
		search.sendKeys("Java Tutorial");
		search.submit();
		Assert.assertEquals(driver.getTitle(), "Java Tutorial - Google Search");
//		driver.close();
	}

	@Test
	public void seleniumSearchTest() throws InterruptedException {
		
		test = reports.createTest("seleniumSearchTest");
//		WebDriver driver = new ChromeDriver();
		driver.navigate().to("https://www.google.com");
		Assert.assertEquals(driver.getTitle(), "Google");
		Thread.sleep(2000);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("APjFqb")));
		WebElement search = driver.findElement(By.id("APjFqb"));
		search.sendKeys("Selenium Tutorial");
		search.submit();
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("APjFqb")));
		Assert.assertEquals(driver.getTitle(), "Selenium Tutorial - Google Search");
//		driver.close();
	}

	@Test(enabled = false)
	public void cucumberSearchTest() {
//		WebDriver driver = new ChromeDriver();
		driver.navigate().to("https://www.google.com");
		WebElement search = driver.findElement(By.id("APjFqb"));
		search.sendKeys("Cucumber Tutorial");
		search.submit();
		Assert.assertEquals(driver.getTitle(), "Cucumber Tutorial - Google Search");
	}

	@Test(enabled = false)
	public void appiumSearchTest() {
//		WebDriver driver = new ChromeDriver();
		driver.navigate().to("https://www.google.com");
		WebElement search = driver.findElement(By.id("APjFqb"));
		search.sendKeys("Appium Tutorial");
		search.submit();
		Assert.assertEquals(driver.getTitle(), "Appium Tutorial - Google Search");
	}

	/* Soft Assertion */
//	@Test
//	public void seleniumSearchTest() {
//		WebDriver driver = new ChromeDriver();
//		driver.navigate().to("https://www.google.com");
//		SoftAssert soft = new SoftAssert();
//		soft.assertEquals(driver.getTitle(), "Google Page");
//		Assert.assertEquals(driver.getTitle(), "Google Page");
//		WebElement search = driver.findElement(By.id("APjFqb"));
//		search.sendKeys("Selenium Tutorial");
//		search.submit();
//		soft.assertEquals(driver.getTitle(), "Selenium Tutorial - Google Search");
//		soft.assertAll();
//		driver.close();
//	}

	@AfterMethod
//	@AfterTest
	public void closeDriver() {
		driver.close();
	}
	
	@AfterTest
	public void finishReport() {
		reports.flush();
	}
}
