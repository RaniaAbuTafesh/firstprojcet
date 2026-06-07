import static org.testng.Assert.assertEquals;

import java.net.http.WebSocket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class MyTestCases {

	WebDriver driver = new ChromeDriver();
	String MyWebsite = "https://automationteststore.com/";

	Random rand = new Random();

	Connection con;

	Statement stmt;

	ResultSet rs;

	String firstname;
	
	String lastname;
	
	String password;
	
	String email;
	
	String numberAsText;
	String city;

	@BeforeTest
	public void MyStaup() throws SQLException {

		driver.get(MyWebsite);
		driver.manage().window().maximize();

		// بعملها مرةوحدة عشان اعمل كونكشين مع الداتا بيز
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/classicmodels", "root", "rania");
	}

	@Test(priority = 1, enabled = true)
	public void InsertData() throws SQLException {
		
		
		//اضافة الكويري او داتا على الداتا بيز بكتب هادول سطرين قبل كلشيء
		stmt = con.createStatement();
		
		
		
		String query = "INSERT INTO customers (" 
		        + "customerNumber, "
		        + "customerName, "
		        + "contactLastName, "
		        + "contactFirstName, "
		        + "phone, "
		        + "addressLine1, "
		        + "city, "
		        + "country, "
		        + "salesRepEmployeeNumber, "
		        + "creditLimit"
		        + ") VALUES ("
		        + "992, "
		        + "'Future Technologies', "
		        + "'Khaled', "
		        + "'Sara', "
		        + "'+962788888888', "
		        + "'Mecca Street', "
		        + "'Amman', "
		        + "'Jordan', "
		        + "1504, "
		        + "75000.00"
		        + ");";
//		String query = "INSERT INTO customers (" + "customerNumber, " + "customerName, " + "contactLastName, "
//				+ "contactFirstName, " + "phone, " + "addressLine1, " + "city, " + "country, "
//				+ "salesRepEmployeeNumber, " + "creditLimit" + ") VALUES (" + "991, " + "'Tech Solutions Ltd', "
//				+ "'Ahmad', " + "'Rania', " + "'+962799999999', " + "'Queen Rania Street', " + "'Amman', "
//				+ "'Jordan', " + "1370, " + "50000.00" + ");";
//وظيفتها انه بعمل ابديت على الداتا او اضافة او حذف كلهم بستخددم الهم ابديت ا
		stmt.executeUpdate(query);

//		driver.navigate().to("https://automationteststore.com/index.php?rt=account/create");

	}

	@Test(priority = 2, enabled = true)
	public void UpdetData() throws SQLException {

		stmt = con.createStatement();

//		String query= "update customers set contactFirstName='aya'where customerNumber=991";
		String query= "update customers set contactFirstName= 'noor'where customerNumber=992";
		stmt.executeUpdate(query);

	}

	@Test(priority = 3, enabled = true)
	public void DaletData() throws SQLException {
		stmt = con.createStatement();
//		String query = "delete from customers where customerNumber=991";
		String query= "delete from customers where customerNumber=992";
		stmt.executeUpdate(query);
	}

	@Test(priority = 4, enabled = false)
	public void ReadData() throws SQLException {
		stmt = con.createStatement();
		String[] randomIDs = { "121", "141", "124", "125", "171" };
		int randomindex = rand.nextInt(randomIDs.length);
		String therandomID = randomIDs[randomindex];

		String query = "select * from customers where  customerNumber=" + therandomID;

		rs = stmt.executeQuery(query);
		while (rs.next()) {

			int a = rand.nextInt(150);
			int b = rand.nextInt(488);
			int numberr = a + b;
			numberAsText = Integer.toString(numberr);

			firstname = rs.getString("contactFirstName").trim();
			password = rs.getString("customerNumber") + rs.getString("contactLastName");
			email = rs.getString("contactFirstName").trim() + rs.getString("contactLastName").trim()
					+ rs.getString("customerNumber") + numberAsText + "@gmail.com";
			lastname = rs.getString("contactLastName").trim();

			city=rs.getString("city");
		}

	}

	@Test(priority = 5, enabled = false)
	public void Singnup() {
		driver.navigate().to("https://automationteststore.com/index.php?rt=account/create");

		driver.findElement(By.id("AccountFrm_loginname")).sendKeys(firstname.trim() + numberAsText);

		driver.findElement(By.id("AccountFrm_password")).sendKeys(password);

		driver.findElement(By.id("AccountFrm_email")).sendKeys(email);

		driver.findElement(By.id("AccountFrm_firstname")).sendKeys(firstname);

		driver.findElement(By.id("AccountFrm_lastname")).sendKeys(lastname);

		driver.findElement(By.id("AccountFrm_address_1")).sendKeys("bgvvff");

		driver.findElement(By.id("AccountFrm_city")).sendKeys(city);

		WebElement Selectstat = driver.findElement(By.id("AccountFrm_zone_id"));
		Select myselector = new Select(Selectstat);
		myselector.selectByIndex(1);
		driver.findElement(By.id("AccountFrm_postcode")).sendKeys("oub87");

		driver.findElement(By.id("AccountFrm_confirm")).sendKeys(password);

		driver.findElement(By.id("AccountFrm_agree")).click();

		driver.findElement(By.cssSelector(".btn.btn-orange.pull-right.lock-on-click")).click();

		String Actualvalue = driver.getCurrentUrl();
		//يوجد طريقتين لحتى اتاكد وانت يلي بريحك 
		Assert.assertEquals(Actualvalue,"https://automationteststore.com/index.php?rt=account/success");
		Assert.assertEquals(Actualvalue.contains("success"), true);

	}

	@Test(priority = 6, enabled = false)
	public void LogOut() throws InterruptedException {

		driver.navigate().to("https://automationteststore.com/index.php?rt=account/logout");

		String Actualvalue = driver.getCurrentUrl();
		Assert.assertEquals(Actualvalue.contains("logout"), true);
	}

	@Test(priority = 7, enabled = false)
	public void Login() throws InterruptedException {

		driver.navigate().to("https://automationteststore.com/index.php?rt=account/login");

		driver.findElement(By.id("loginFrm_loginname")).sendKeys(firstname.trim() + numberAsText);
		driver.findElement(By.id("loginFrm_password")).sendKeys(password);

		driver.findElement(By.xpath("//button[@title='Login']")).click();
		Thread.sleep(3000);
		//Assert.assertEquals(driver.getPageSource().contains("Welcome back"), true);
		Assert.assertEquals(driver.getPageSource().contains(firstname.trim()), true);
	}

	@AfterTest

	public void PostTesting() {
	}

}
