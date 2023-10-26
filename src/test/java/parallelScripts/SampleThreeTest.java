package parallelScripts;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class SampleThreeTest {
		@Test
		public void testOne() {
			WebDriver driver = new ChromeDriver();
			long id = Thread.currentThread().getId();
			System.out.println("Test31 in SampleThree..." + id);
			driver.close();
		}
		
		@Test
		public void testTwo() {
			WebDriver driver = new ChromeDriver();
			long id = Thread.currentThread().getId();
			System.out.println("Test32 in SampleThree..." + id);
			driver.close();
		}
		
		@Test
		public void testThree() {
			WebDriver driver = new ChromeDriver();
			long id = Thread.currentThread().getId();
			System.out.println("Test33 in SampleThree..." + id);
			driver.close();
		}
		
		@Test(threadPoolSize = 3, invocationCount = 6)
		public void testFour() {
			WebDriver driver = new ChromeDriver();
			long id = Thread.currentThread().getId();
			System.out.println("Test34 in SampleThree..." + id);
			driver.close();
		}
	}
