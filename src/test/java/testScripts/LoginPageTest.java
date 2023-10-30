package testScripts;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.tracing.Propagator;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginPageTest {

	WebDriver driver;

	@BeforeMethod
	public void setUp() {
		driver = new ChromeDriver();
//		driver.get("https://the-internet.herokuapp.com/login");
//		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
	}

	@Test(dataProvider = "loginData")
	public void validLogin(String strUser, String strPwd) {
//		driver.get(prop.getPropertry("url"));

		driver.get("https://the-internet.herokuapp.com/login");
//		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
		
//		driver.findElement(By.cssSelector("input#username")).sendKeys(strUser);

		driver.findElement(By.id("username")).sendKeys(strUser);
		driver.findElement(By.id("password")).sendKeys(strPwd);
		driver.findElement(By.xpath("//i[@class='fa fa-2x fa-sign-in']")).click();
		
	}

	@DataProvider(name = "loginData")
	public String[][] getData() throws IOException, ParseException {
		String path = System.getProperty("user.dir") + "//src//test//resources//loginData.json";
		FileReader file = new FileReader(path);
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(file);
		JSONObject jsonObj = (JSONObject) obj;
		JSONArray userArray = (JSONArray)jsonObj.get("userLogins");
		String arr[][] = new String[userArray.size()][];
		
		for (int i=0; i<userArray.size(); i++) {
			JSONObject user = (JSONObject)userArray.get(i);
			String strUser = (String) user.get("username");
			String strPwd = (String) user.get("password");
			System.out.println("username : " + strUser);
			System.out.println("password : " + strPwd);
			String record[] = {strUser, strPwd};
			arr[i] = record;
		}
		return arr;

	}

	@AfterMethod
	public void closeDriver() {
		driver.close();
	}

}
