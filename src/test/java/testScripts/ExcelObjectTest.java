package testScripts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ExcelObjectTest {

	WebDriver driver;

	@BeforeMethod
	public void setUP() {

		driver = new ChromeDriver();
		driver.get("https://the-internet.herokuapp.com/login");
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
	}

	@Test(dataProvider = "loginData")
	public void validLogin(String strUser, String strPwd) {

		driver.findElement(By.id("username")).sendKeys(strUser);
		driver.findElement(By.id("password")).sendKeys(strPwd);
		driver.findElement(By.xpath("//i[@class='fa fa-2x fa-sign-in']")).click();

	}

//	HSSFWorkbook is used to read and write .xls format
//	XSSFWorkbook is used to read and write .xlsx format

	@DataProvider(name = "loginData")
	public String[][] getDataFromExcel() throws IOException {

//		String path = "C:\\Users\\Ashok\\Desktop\\loginDataLocal.xlt";
		File file = new File("loginData.xls");
		FileInputStream fis = new FileInputStream(file);

		HSSFWorkbook workbook = new HSSFWorkbook(fis);
		HSSFSheet sheet = workbook.getSheet("loginData");
		String strUser = "";
		String strPwd = "";

		int lastRows = sheet.getLastRowNum();
		String arr[][] = new String[lastRows + 1][];

		for (int i = 0; i <= lastRows; i++) {
			HSSFRow row = sheet.getRow(i);
			if (row.getCell(0).getStringCellValue() != null) {
				strUser = row.getCell(0).getStringCellValue();
			}
			if (row.getCell(1).getStringCellValue() != null) {
				strPwd = row.getCell(1).getStringCellValue();
			}
			String record[] = { strUser, strPwd };
			System.out.println(record);
			arr[i] = record;
		}

		workbook.close();
		fis.close();
		return arr;

	}

//	@DataProvider(name = "loginData")
//	public String readObjPathXSSF(String objName) throws IOException {
//		String objPath = "";
//
//		String path = "C:\\Users\\Ashok\\Desktop\\loginDataLocal.xlsx";
//		FileInputStream file = new FileInputStream(path);
//
//		XSSFWorkbook workbook = new XSSFWorkbook(file);
//		XSSFSheet sheet = workbook.getSheet("loginData");
//
//		int lastRows = sheet.getLastRowNum();
//		for (int i = 0; i <= lastRows; i++) {
//			XSSFRow row = sheet.getRow(i);
//			if (row.getCell(0).getStringCellValue().equalsIgnoreCase(objName)) {
//				objPath = row.getCell(i).getStringCellValue();
//			}
//		}
//
//		workbook.close();
//		file.close();
//		return objPath;
//
//	}

	@AfterMethod
	public void driverClose() {
		driver.close();
	}

}
