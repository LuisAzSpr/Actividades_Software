package pages;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    protected static WebDriver driver;
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(7)); // se usara para hacer esperas explicitas de los elementos web

    //configuracion del webdriver para chrome
    static { // levanta un chrome driver cuando arranca la ejecucion
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    public BasePage(WebDriver driver) {
        BasePage.driver = driver;
    }
    public static void closeBrowser(){
        driver.quit();
    }

    public static void navigateTo(String url) {
        driver.get(url);
    }

    private WebElement Find(String locator) { //devuelve un web element, dada un xpath
        // solo esperamos una sola vez de manera global
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator)));
    }

    public void clickElement(String locator) {
        Find(locator).click();
    }

    public void write(String locator, String keysToSend){
        Find(locator).clear();
        Find(locator).sendKeys(keysToSend);
    }

    public void selectFromDropdownByValue(String locator, String value){
        Select dropdown = new Select(Find(locator));
        dropdown.selectByValue(value);
    }
    public void selectFromDropdownByIndex(String locator, int index){
        Select dropdown = new Select(Find(locator));
        dropdown.selectByIndex(index);
    }
    public int dropDownSize(String locator){
        Select dropdown = new Select(Find(locator));
        return dropdown.getOptions().size();
    }
    public boolean isPresentText(String locator,String text){
        return Find(locator).getText().contains(text);
    }

}
