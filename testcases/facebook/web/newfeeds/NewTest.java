package facebook.web.newfeeds;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class NewTest {
	WebDriver driver;
	String projectLocation = System.getProperty("user.dir");
	
	@BeforeTest
  public void beforeTest() {
		System.setProperty("webdriver.chrome.driver", projectLocation + "\\libs\\chromedriver.exe");
		driver = new ChromeDriver();	
		
	//Navigating through a particular website
	driver.get("https://www.browserstack.com");
  }

	@Test
	public void f() {
	}

	@AfterTest
	public void afterTest() {
	}

}
