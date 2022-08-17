package trelloPackage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginBase extends Base {
    void loginTrelloDashboard()  {
        try {
            // Get the login url from config file
            String pageURL = keyValue.getPropertyValues("LOGIN_PAGE_URL");

            // Visit trello login page
            driver.get(pageURL);
            driver.manage().window().maximize();

            // Click on login button
            WebElement loginLink = driver.findElement(By.linkText("Log in"));
            loginLink.click();
            new WebDriverWait(driver, Duration.ofSeconds(60)).until(ExpectedConditions.visibilityOfElementLocated(By.id("user")));

            // Type in the email, password and log in
            WebElement userEmail = driver.findElement(By.id("user"));
            userEmail.sendKeys(keyValue.getPropertyValues("USER_EMAIL"));
            new WebDriverWait(driver, Duration.ofSeconds(60)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@value='Log in with Atlassian']")));
            driver.findElement(By.xpath("//input[@value='Log in with Atlassian']")).click();


            WebElement password = new WebDriverWait(driver, Duration.ofSeconds(60)).until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
            password.sendKeys(keyValue.getPropertyValues("USER_PASSWORD"));
            driver.findElement(By.id("login-submit")).click();
            new WebDriverWait(driver, Duration.ofSeconds(60)).until(ExpectedConditions.visibilityOfElementLocated(By.className("boards-page-section-header-name")));
        }catch (Exception e){
            System.out.println("Exception: " + e);
        }
    }

}
