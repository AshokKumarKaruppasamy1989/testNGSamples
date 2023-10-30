package testScripts;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ExcelObjectTest {

	WebDriver driver;

	@BeforeMethod
	public void setUP() {

		driver = new ChromeDriver();
		driver.get("https://the-internet.herokuapp.com/login");
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
	}

	@Test
	public void validLogin() {

		driver.findElement(By.id("username")).sendKeys("tomsmith");
		driver.findElement(By.id("password")).sendKeys("SuperSecretPassword!");
		driver.findElement(By.xpath("//i[@class='fa fa-2x fa-sign-in']")).click();

	}

	public String readObjPath(String objName) throws IOException {
		String objPath = "";

		String path = System.getProperty("user.dir") 
				+ "//src//test//resources//loginData.xlsx";
		FileInputStream file = new FileInputStream(path);
		
		XSSFWorkbook workbook = new XSSFWorkbook(path);
		XSSFSheet sheet = workbook.getSheet("loginPage");
		
		int lastRows = sheet.getLastRowNum();
		for (int i = 0; i <= lastRows; i++) {
			XSSFRow row = sheet.getRow(i);
			if (row.getCell(0).getStringCellValue().equalsIgnoreCase(objName)) {
				objPath = row.getCell(i).getStringCellValue();
			}
		}
		
		return objPath;

	}

	@AfterMethod
	public void driverClose() {
		driver.close();
	}

}
