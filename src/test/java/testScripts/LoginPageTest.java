package testScripts;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Properties;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.GeckoDriverInfo;
import org.openqa.selenium.remote.tracing.Propagator;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.opencsv.CSVReader;

public class LoginPageTest {

	WebDriver driver;
	Properties prop;

	@BeforeMethod
	public void setUp() {
		prop = new Properties();
		String path = System.getProperty("user.dir") + "//src//test//resources//configfiles//config.properties";
		FileInputStream file;
		try {			
			file = new FileInputStream(path);
			prop.load(file);			
		} catch (Exception e) {
			e.printStackTrace();
		}

		String browser = prop.getProperty("browser");
		if (browser.equalsIgnoreCase("chrome")) {
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();
		}
		
//		driver.get("https://the-internet.herokuapp.com/login");
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
	}

	@Test(dataProvider = "loginData")
	public void validLogin(String strUser, String strPwd) {
		driver.get(prop.getProperty("url"));
		driver.findElement(By.cssSelector("input#username")).sendKeys(strUser);
		driver.findElement(By.id("password")).sendKeys(strPwd);
		driver.findElement(By.xpath("//i[@class='fa fa-2x fa-sign-in']")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.flash.success")));

		boolean displayed = driver.findElement(By.cssSelector("div.flash.success")).isDisplayed();
		Assert.assertTrue(displayed);
	}
	
//	@DataProvider(name = "loginData")
//	public Object[][] getDataExcel() throws IOException, ParseException {
//		String path = System.getProperty("user.dir") + "//src//test//resources//configfiles//loginData.csv";
//
//		
//		while(reader.readNext() != null) {
//			String strUser = reader
//			String strPwd = (String) user.get("password");
//		}
//		
//		String[] nextRecord;
//		String arr[][] = new String[reader.size()][];
//		
//		while((nextRecord = reader.readNext()) != null) {
//			for (String cell: nextRecord) {
//				System.out.println(cell);
//			}
//		}
//	}

//	@DataProvider(name = "loginData")
	public String[][] getData() throws IOException, ParseException {
		String path = System.getProperty("user.dir") + "//src//test//resources//configfiles//loginData.json";
		FileReader file = new FileReader(path);
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(file);
		JSONObject jsonObj = (JSONObject) obj;
		JSONArray userArray = (JSONArray) jsonObj.get("userLogins");
		String arr[][] = new String[userArray.size()][];

		for (int i = 0; i < userArray.size(); i++) {
			JSONObject user = (JSONObject) userArray.get(i);
			String strUser = (String) user.get("username");
			String strPwd = (String) user.get("password");
			System.out.println("username : " + strUser);
			System.out.println("password : " + strPwd);
			String record[] = { strUser, strPwd };
			arr[i] = record;
		}
		return arr;
	}

	@AfterMethod
	public void closeDriver() {
		driver.close();
	}

}
