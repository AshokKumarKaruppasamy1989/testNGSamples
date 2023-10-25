package testScripts;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class GooglePageTest {
	WebDriver driver;

//	@BeforeMethod
	@BeforeTest
	public void driverSetup() {
		driver = new ChromeDriver();
	}

	@Test
	public void seleniumSearchTest() {
//		WebDriver driver = new ChromeDriver();
		driver.navigate().to("https://www.google.com");
		WebElement search = driver.findElement(By.id("APjFqb"));
		search.sendKeys("Selenium Tutorial");
		search.submit();
		Assert.assertEquals(driver.getTitle(), "Selenium Tutorial - Google Search");
//		driver.close();
	}

	@Test
	public void javaSearchTest() {
//		WebDriver driver = new ChromeDriver();
		driver.navigate().to("https://www.google.com");
		WebElement search = driver.findElement(By.id("APjFqb"));
		search.sendKeys("Java Tutorial");
		search.submit();
		Assert.assertEquals(driver.getTitle(), "Java Tutorial - Google Search");
//		driver.close();
	}

//	@AfterMethod
	@AfterTest
	public void closeDriver() {
		driver.close();
	}
}
