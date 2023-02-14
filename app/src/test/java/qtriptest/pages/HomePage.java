package qtriptest.pages;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import qtriptest.SeleniumWrapper;

public class HomePage {

    RemoteWebDriver driver;
    
    @FindBy(xpath = "//a[text()='Register']")
    WebElement registerBtn;
    
    @FindBy(id = "autocomplete")
    WebElement searchBox;

    @FindBy(xpath = "//div[text()='Logout']")
    WebElement logoutBtn;

    @FindBy(xpath = "//h5[text()='No City found']")
    WebElement noCityfound;

    public HomePage(RemoteWebDriver driver) {
        this.driver = driver;
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
    }

    public void gotoHomePage() {
        SeleniumWrapper.navigate(this.driver, "https://qtripdynamic-qa-frontend.vercel.app/");
        // driver.get("https://qtripdynamic-qa-frontend.vercel.app/");
        // Thread.sleep(2000);
    }

    public void clickRegister() {
        try {
        SeleniumWrapper.click(registerBtn, this.driver);
        // registerBtn.click();
        Thread.sleep(3000);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public Boolean isUserLoggedIn() {
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        try {
            return logoutBtn.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void logOutUser() {
        try {
            SeleniumWrapper.click(logoutBtn, this.driver);
            // logoutBtn.click();
            Thread.sleep(3000);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public boolean assertAutoCompleteText(String cityName) {
        By byCityInList;
        WebElement cityFoundInListEle = null;
        try {
            byCityInList = new By.ByXPath(String.format("//li[@id='%s']", cityName));
            // cityFoundInListEle = driver.findElement(byCityInList);
            // Thread.sleep(1000);
            cityFoundInListEle = SeleniumWrapper.findElementWithRetry(this.driver, byCityInList, 3);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return cityFoundInListEle.isDisplayed();
        // return cityFoundInListEle.isDisplayed(); 
    }

    public void selectCity(String cityName) {
        WebElement cityEle = null;
        try {
            // Thread.sleep(2000);
            By byCity = new By.ByXPath(String.format("//li[@id='%s']", cityName.toLowerCase()));
            cityEle = SeleniumWrapper.findElementWithRetry(this.driver, byCity, 3);
            // driver.findElement(byCity).click();
            SeleniumWrapper.click(cityEle, this.driver);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void searchCity(String cityName) {
        try {
            // searchBox.clear();
            // searchBox.click();
            Thread.sleep(2000);
            SeleniumWrapper.sendKeys(searchBox, cityName);
            Thread.sleep(2000);
            // searchBox.sendKeys(cityName);
            // return true;
            // return true;
            // searchBox.sendKeys("F");
        }
        catch(Exception e) {
            e.printStackTrace();
            // return false;
        }
    }

    public boolean isNoCityFound() {
        try {
            return noCityfound.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
