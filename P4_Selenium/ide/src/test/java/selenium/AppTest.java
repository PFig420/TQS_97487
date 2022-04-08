package selenium;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
//import org.openqa.selenium.firefox.FirefoxDriver;


/**
 * Unit test for simple App.
 */
public class AppTest 
{
    WebDriver browser;

    @BeforeEach
    public void SetUp(){

        //System.setProperty("webdriver.chrome.driver", "/where/you/put/chromedriver");  
        //browser = new ChromeDriver();
        ChromeOptions options = new ChromeOptions();
        browser = new ChromeDriver(options);
    }

    @AfterEach
    public void tearDown(){
        browser.close(); 
    }

    @Test
    public void site_header_is_on_home_page() {

        browser.get("https://www.saucelabs.com"); 
        WebElement href = browser.findElement(By.xpath("//a[@href='https://accounts.saucelabs.com/']"));        
        assertTrue((href.isDisplayed()));  

    }
}
