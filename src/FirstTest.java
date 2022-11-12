import com.google.common.base.Predicate;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.xml.xpath.XPath;
import java.net.URL;
import java.time.Duration;
import java.util.List;

import static com.google.common.base.Predicates.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class FirstTest {
    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "and80");
        capabilities.setCapability("platformVersion", "9");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "C:/JavaAppiumAutomation/JavaAppiumAutomation/apks/org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);

    }

    @After
    public void tearDown() {

        driver.quit();
    }

    @Test
    public void CheckElementIncludeText() {

        WebElement element_to_skip = driver.findElement(By.xpath("//*[contains(@text, 'SKIP')]"));
        element_to_skip.click();


        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));


        WebElement element = waitForElementPresent(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' text",
                5
        );

        assertElementHasText(
                element,
                "Search Wikipedia"
        );

        int i = driver.findElements(By.xpath("//*[@class = 'android.view.ViewGroup']//*[@resource-id = 'org.wikipedia:id/page_list_item_title'][contains(@text, 'Java')]")).size();




    }

    @Test

    public void SearchEx2() {
        WebElement element_to_skip = driver.findElement(By.xpath("//*[contains(@text, 'SKIP')]"));
        element_to_skip.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));


        findElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' text",
                5

        );

        waitElementAndSendKey(
                By.id("org.wikipedia:id/search_src_text"),
                "Java",
                "Cannot find any text",
                10
        );


// Проверить, что найденных совпадений больше 2
   /* driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        int i = driver.findElements(By.xpath("//*[@class = 'android.view.ViewGroup']//*[@resource-id = 'org.wikipedia:id/page_list_item_title']")).size();
        System.out.println(i);
        int count = 0;

        for (int n = 1; n < i; n++) {

            WebElement element = driver.findElement(By.xpath("//*[@class = 'android.view.ViewGroup'][" + (n) + "]//*[@resource-id = 'org.wikipedia:id/page_list_item_title'][contains(@text, 'Java')]"));
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

                if (boolElementHasText(element, "Java") == true || count<2) {
                    String s = element.getAttribute("text");
                    System.out.println(s);
                    count++;

                }
                if (count==2){
                    break;
                }

            }
        if (count<2)
        {
            System.out.println("Les then 2");
        }
*/
//Альтернативный вариант (так быстрее)
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        List<WebElement> list = driver.findElements(By.xpath("//*[@class = 'android.view.ViewGroup']" +
                "//*[@resource-id = 'org.wikipedia:id/page_list_item_title'][contains(@text, 'Java')]"));
        findTextByList(list, "Проверить наличие элементов", "Cannot find text");


        findElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find X",
                5

        );
        waitForElementNotPresent(
                By.id("org.wikipedia:id/search_results_display"),
                "WTF",
                3
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

            //System.out.println("Success! Element was find");
            return;
        }
        else {
            Assert.fail("Test failed! Element was not find");

        }

    }
    private WebElement findElementAndClick(By by, String error_message, long timeout_in_seconds)
    {
        WebElement element = waitForElementPresent(by, error_message, timeout_in_seconds);
        element.click();
        return element;
    }

    private WebElement waitElementAndSendKey(By by, String value, String error_message, long timeout_in_seconds) {
        WebElement element = waitForElementPresent(by, error_message, timeout_in_seconds);
        element.sendKeys(value);
        driver.navigate().back();
        return element;
    }

    /*   private boolean boolElementHasText(WebElement element, String expected_text) {
        String actual_text = element.getAttribute("text");
        if (actual_text.equals(expected_text)) {

            //System.out.println("Success! Element was find");
            return true;
        }
        else {
            return false;

        }

    }   */
    private void findTextByList(List<WebElement> list,String message,String error_message)
    {
        System.out.println(message);
        if (list.size()>=2)
        {
            int p =0;
            for(WebElement element:list){

                System.out.println(p+":"+element.getText());
                p++;
            }
        } else if (list.size() == 1) {

            System.out.println("Find only one result: " +list.get(0).getAttribute("text"));
        }
        else {

            System.out.println("Nothing found");
        }


    }
    private boolean waitForElementNotPresent(By by, String error_message, long timeout_in_seconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout_in_seconds));
        wait.withMessage(error_message + "\n");
        return wait.until (
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

}

