package testScripts;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

public class RemoteDriverSample {

	@Test
	public void sampleTest() throws MalformedURLException, InterruptedException {

		ChromeOptions options = new ChromeOptions();
//		EdgeOptions options = new EdgeOptions();
//		FirefoxOptions options = new FirefoxOptions();
		options.setCapability(CapabilityType.PLATFORM_NAME, Platform.WINDOWS);
		options.setCapability(CapabilityType.BROWSER_VERSION, "116");

		String hub = "http://172.31.7.46:4444/";
		RemoteWebDriver driver = new RemoteWebDriver(new URL(hub), options);

		driver.navigate().to("https://www.google.com");
		WebElement search = driver.findElement(By.id("APjFqb"));
		search.sendKeys("Java Tutorial");
		search.submit();
		Thread.sleep(3000);
		System.out.println(driver.getTitle());
		
	}
}
