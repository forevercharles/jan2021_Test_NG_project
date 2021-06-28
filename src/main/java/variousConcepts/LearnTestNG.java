package variousConcepts;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LearnTestNG {
	
	WebDriver driver;
    String browser = null;
//  String browser ="firefox";
    String url123;
    
    @BeforeClass
    public void readconfig() {
    	
    //BufferedReader //InputStream //FileReader // Scanner
    	
//  if we were reading an excel file then we would have created a excel object instead of properties object.
//  if reading a text file, then create a text file object.
    	
    	Properties prop = new Properties();
    	
    	try {
    		
    		InputStream input = new FileInputStream("src\\main\\java\\config\\config.properties");
    		prop.load(input);
    		browser = prop.getProperty("browser");
    		System.out.println("Browser used: " + browser);
    		url123 = prop.getProperty("url");
    		
    	}catch(IOException e) {
    		
    		e.printStackTrace();
    	}
    }
    
    
	@BeforeMethod
	public void launchBrowser() {
//		System.out.println("======this is Launch Browser");
		
		if(browser.equalsIgnoreCase("Chrome")){
		    System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
			driver = new ChromeDriver();		
		}//else {
//			System.setProperty("webdriver.gecko.driver", "drivers\\geckodriver.exe");
//			driver = new FirefoxDriver();
//		}
		else if(browser.equalsIgnoreCase("Firefox")){
			System.setProperty("webdriver.gecko.driver", "drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
		}
		
		// go to website
//		driver.get("https://techfios.com/billing/");
		
		driver.get(url123);

		// maximize the window
		driver.manage().window().maximize();
		// delete the cookies
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

//	@Test (priority=1)
	public void loginTest() throws InterruptedException {
		//Assert equals does comparisons between actual and expected
//        Assert.assertEquals(driver.getTitle(), "Login - iBilling1", "Wrong page!!!");
		
		Assert.assertEquals(driver.getTitle(),"Login - iBilling", "Wrong page!!!");

		//element library
		WebElement USER_NAME_ELEMENT = driver.findElement(By.xpath("//input[@id='username']"));
		WebElement PASSWORD_ELEMENT = driver.findElement(By.xpath("//input[@id='password']"));
		WebElement SUBMIT_BUTTON_ELEMENT = driver.findElement(By.xpath("//button[@type='submit']"));
		
		
//		login data
		String loginId = "demo@techfios.com";
		String password = "abc123";
		
		USER_NAME_ELEMENT.sendKeys(loginId);
		PASSWORD_ELEMENT.sendKeys(password);
		SUBMIT_BUTTON_ELEMENT.click();
		
		WebElement DASHBOARD_TITLE_ELEMENT = driver.findElement(By.xpath("//h2[contains(text(), 'Dashboard')]"));
		
		//this is just for debugging purposes. we do not need it
//		System.out.println("============" +DASHBOARD_TITLE_ELEMENT.getText());
		
		Assert.assertEquals(DASHBOARD_TITLE_ELEMENT.getText(), "Dashboard", "Wrong page!!!");
		
	}	
	
	@Test (priority=2)
	public void addcustomer() {
		
		Assert.assertEquals(driver.getTitle(),"Login - iBilling", "Wrong page!!!");

		//element library
		WebElement USER_NAME_ELEMENT = driver.findElement(By.xpath("//input[@id='username']"));
		WebElement PASSWORD_ELEMENT = driver.findElement(By.xpath("//input[@id='password']"));
		WebElement SUBMIT_BUTTON_ELEMENT = driver.findElement(By.xpath("//button[@type='submit']"));
		
		
//		login data
		String loginId = "demo@techfios.com";
		String password = "abc123";
		
		//Test Data or Mock Data
		String FullName = "Test January";
		String CompanyName = "Google";
		String Email = "techfios@gmail.com";
		String phone = "4235678";
		
		
		
		USER_NAME_ELEMENT.sendKeys(loginId);
		PASSWORD_ELEMENT.sendKeys(password);
		SUBMIT_BUTTON_ELEMENT.click();
		
		WebElement DASHBOARD_TITLE_ELEMENT = driver.findElement(By.xpath("//h2[contains(text(), 'Dashboard')]"));
		
		//this is just for debugging purposes. we do not need it
//		System.out.println("============" +DASHBOARD_TITLE_ELEMENT.getText());
		
		Assert.assertEquals(DASHBOARD_TITLE_ELEMENT.getText(), "Dashboard", "Wrong page!!!");
		
		WebElement CUSTOMER_ELEMENT = driver.findElement(By.xpath("//*[@id=\"side-menu\"]/li[3]/a/span[1]"));
		
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.visibilityOf(CUSTOMER_ELEMENT));
			
		CUSTOMER_ELEMENT.click();
		
		WebElement ADD_CUSTOMER_ELEMENT = driver.findElement(By.xpath("//*[@id=\"side-menu\"]/li[3]/ul/li[1]/a"));
		
//		WebDriverWait wait1 = new WebDriverWait(driver, 5);
//		wait1.until(ExpectedConditions.visibilityOf(ADD_CUSTOMER_ELEMENT));
		
		waitForElement(driver, 5,ADD_CUSTOMER_ELEMENT);
		
		ADD_CUSTOMER_ELEMENT.click();
		
		
		WebElement FULL_NAME_ELEMENT = driver.findElement(By.xpath("//*[@id=\"account\"]"));
		WebElement COMPANY_DROPDOWN_ELEMENT = driver.findElement(By.xpath("//select[@id=\"cid\"]"));
		WebElement Email_ELEMENT = driver.findElement(By.xpath("//*[@id=\"email\"]"));
		
		waitForElement(driver, 10,FULL_NAME_ELEMENT );
		FULL_NAME_ELEMENT.sendKeys(FullName);
		
		//dealing with dropdown by creating object of the select class
		Select sel = new Select(COMPANY_DROPDOWN_ELEMENT);
		sel.selectByVisibleText(CompanyName);
		
		//Use Generate Random numbers concept for modifying/randomize the email address test data(its a java Concept)
		Random rnd = new Random();
		int randomNum = rnd.nextInt(999);
		
		//fill email
		Email_ELEMENT.sendKeys(randomNum + Email);
//		999techfios@gmail.com
		
	}

    public void waitForElement(WebDriver driver, int waitTime, WebElement element) {
    	WebDriverWait wait1 = new WebDriverWait(driver,waitTime );
		wait1.until(ExpectedConditions.visibilityOf(element));
		
	}


//	@AfterMethod
	public void tearDown() {
		
		driver.close();
		driver.quit();
	}
	
		
	
}
