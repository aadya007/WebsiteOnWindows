package DataDrivenTest;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DataDrivenLoginPage
{
	
		WebDriver driver;
		@BeforeClass
		public void setUp()
		{

			ExtentHtmlReporter hr =new ExtentHtmlReporter("extent.html");
			ExtentReports er=new ExtentReports();
			er.attachReporter(hr);
			
			
			ExtentTest test1 =er.createTest("Hit the link of OrangeHRM","this test valid for amazon page");
			
			WebDriverManager.chromedriver().driverVersion("87.0").setup();
			driver = new ChromeDriver();
			
			er.flush();
		
		}
		@Test(priority = 0)
		public void search() throws Exception
		{
			System.out.println(" **** searching for Orange HRM");
			driver.get("https://opensource-demo.orangehrmlive.com/");
		
		Thread.sleep(2000);
		
		
		
	
			
		}
		@Test(priority = 1,dataProvider = "logindata")
		public void LoginTest(String user,String pass,String exp) throws Exception
		{
			
			
			System.out.println("**** taking username ******");
			WebElement username=driver.findElement(By.xpath("//*[@id=\"txtUsername\"]"));
			username.clear();
			username.sendKeys(user);
			
			System.out.println(" ****** taking password ******");
			Thread.sleep(2000);
			
			
			
			WebElement password=driver.findElement(By.xpath("//*[@id=\"txtPassword\"]"));
			password.clear();
			password.sendKeys(pass);
			
			System.out.println("**** click on login button******");
			Thread.sleep(2000);
			
			System.out.println("");
			driver.findElement(By.xpath("//*[@id=\"btnLogin\"]")).click();
			String expected_URL="OrangeHRM";
			String actual_URL=driver.getTitle();
			if(exp.equals("valid"))
			{
				if(expected_URL.equals(actual_URL))
				{
					System.out.println("***** Login successful ******");
					driver.findElement(By.xpath("//*[@id=\"welcome\"]")).click();

					Thread.sleep(2000);
					
					driver.findElement(By.xpath("//*[@id=\"welcome-menu\"]/ul/li[2]/a")).click();
					
					
				}
				else
				{
					System.out.println("***** page not found*******");
				}
			}
			else if(exp.equals("invalid"))
			{
				if(expected_URL.equals(actual_URL))
				{
					driver.get("https://opensource-demo.orangehrmlive.com/index.php/auth/login");
					
					System.out.println("***** page not found*******");
				}
				else
				{
					System.out.println("***** Login successful ******");
				}
			}
			Thread.sleep(3000);
		}
		
		@DataProvider(name="logindata")
		public String[][] LoginData()
		{
			String[][] arr= { {"admin","admin123","valid" },{"Admin","admin123","valid"},{"Aditi","admin321","invalid"}};
			return arr;
		}
		
		
		
		@AfterClass
		public void tearDown() throws InterruptedException
		{
			Thread.sleep(5000);
			driver.close();
		}
}
