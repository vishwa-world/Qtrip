package qtriptest.pages;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import qtriptest.SeleniumWrapper;

public class LoginPage {

    RemoteWebDriver driver;

    @FindBy(id = "floatingInput")
    WebElement emailInput;

    @FindBy(id = "floatingPassword")
    WebElement passwordInput;

    @FindBy(xpath = "//button[text()='Login to QTrip']")
    WebElement loginBtn;

    public LoginPage(RemoteWebDriver driver) {
        this.driver = driver;
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
    }

    public void performLogin(String emailId, String password) {
        try {
            // emailInput.sendKeys(emailId);
            // passwordInput.sendKeys(password);
            SeleniumWrapper.sendKeys(emailInput, emailId);
            SeleniumWrapper.sendKeys(passwordInput, password);
            SeleniumWrapper.click(loginBtn, this.driver);
            // loginBtn.click();
            // Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
