package testScripts;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DatabaseObjectTest {

	WebDriver driver;
	Connection con;
	Statement stmt;
	ResultSet rs;

	@BeforeTest
	public void setUP() throws Exception {

		driver = new ChromeDriver();
		driver.get("https://only-testing-blog.blogspot.com/2014/05/form.html");
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));

		/* Load the driver class - h2 database */
		Class.forName("org.h2.Driver");
		
		/* Create the connection object */
		con = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa");
		
		/* Create the statement object */
		stmt = con.createStatement();

		/*Execute the query */
		String sq1 = "CREATE TABLE IF NOT EXISTS users (" + "fname varchar," + "lname varchar," + "email varchar,"
				+ "mobile varchar," + "company varchar" + ")";
		stmt.execute(sq1);

		String sql2 = "INSERT INTO users VALUES('Test', 'User1', 'TestUser1@xyz.com', '9988776655', 'ABC')";
		stmt.executeUpdate(sql2);

		String sql3 = "INSERT INTO users VALUES('Test', 'User2', 'TestUser2@xyz.com', '3344556677', 'PLQ')";
		stmt.executeUpdate(sql3);
	}

	@DataProvider
	public Object[][] dp() throws Exception {

		rs = stmt.executeQuery("SELECT COUNT(*) FROM users");
		rs.next();
		int row = rs.getInt("count(*)");
		
		rs = stmt.executeQuery("SELECT * FROM users");

		ResultSetMetaData rsmd = rs.getMetaData();
		int col = rsmd.getColumnCount();

		Object[][] data = new Object[row][col];
		int i = 0;

		while (rs.next()) {
			data[i][0] = rs.getString(1);
			data[i][1] = rs.getString(2);
			data[i][2] = rs.getString(3);
			data[i][3] = rs.getString(4);
			data[i][4] = rs.getString(5);
			i++;
		}
		return data;
	}

	@Test(dataProvider = "dp")
	public void readDataFromDB(String fname, String lname, String email, String mobile, String comp) {

		driver.findElement(By.name("FirstName")).sendKeys(fname);
		driver.findElement(By.name("LastName")).sendKeys(lname);
		driver.findElement(By.name("EmailID")).sendKeys(email);
		driver.findElement(By.name("MobNo")).sendKeys(mobile);
		driver.findElement(By.name("Company")).sendKeys(comp);
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		driver.switchTo().alert().accept();

	}

	@AfterTest
	public void closeConnection() throws Exception {
		String sql4 = "DELETE FROM users";
		stmt.execute(sql4);
		con.close();
		driver.close();
	}
}
