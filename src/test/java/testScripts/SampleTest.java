package testScripts;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SampleTest {
	@Test
	public void cypressSearchTest() {

		WebDriver driver = new ChromeDriver();

		driver.navigate().to("https://www.google.com");
		WebElement search = driver.findElement(By.id("APjFqb"));
		search.sendKeys("Cypress Tutorial");
		search.submit();
		Assert.assertEquals(driver.getTitle(),
				"Cypress Tutorial - Google Search");
		
		driver.close();
		
	}
}
