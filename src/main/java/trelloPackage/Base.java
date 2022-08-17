package trelloPackage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class Base {
    protected static WebDriver driver;
    protected static PropertyValue keyValue;

    @BeforeTest
    void setupDriver(){
        keyValue = new PropertyValue();
        System.setProperty("webdriver.chrome.driver","D:\\Project Tool\\chromedriver_win32\\chromedriver.exe");
        driver = new ChromeDriver();
    }
    @AfterTest
    void closeBrowser(){
       driver.close();
    }

}
