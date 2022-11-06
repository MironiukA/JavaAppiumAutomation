import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class FirstTest {
    private AppiumDriver  driver;
    @Before
    public void setUp() throws Exception
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability( "deviceName", "and80");
        capabilities.setCapability("platformVersion", "9");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "C:/JavaAppiumAutomation/JavaAppiumAutomation/apks/org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);

    }
    @After
    public  void tearDown(){

        driver.quit();
    }

    @Test
    public void CheckElementIncludeText()
    {

        WebElement element_to_skip = driver.findElement(By.xpath("//*[contains(@text, 'SKIP')]"));
        element_to_skip.click();

       driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));


        WebElement element = waitForElementPresent(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' text",
                15
        );

        assertElementHasText(
                element,
                "Search Wikipedia1"
        );






    }



    private WebElement waitForElementPresent(By by, String error_message, long timeout_in_seconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout_in_seconds));
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    private WebElement waitForElementPresent(By by, String error_message){
        return waitForElementPresent(by, error_message, 15);
    }

    private void assertElementHasText(WebElement element, String expected_text) {
        String actual_text = element.getAttribute("text");
        if (actual_text.equals(expected_text)) {

            System.out.println("Success! Element was find");
        }
        else {
            Assert.fail("Test failed! Element was not find");

        }

    }

}

