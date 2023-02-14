package qtriptest.pages;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import qtriptest.SeleniumWrapper;

public class RegisterPage {
    
    RemoteWebDriver driver;
    public String lastGeneratedUserName = "";

    @FindBy(id = "floatingInput")
    WebElement emailInput;

    @FindBy(id = "floatingPassword")
    WebElement passwordInput;

    @FindBy(xpath = "//input[@placeholder='Retype Password to Confirm']")
    WebElement confirmpasswordInput;

    @FindBy(xpath = "//button[text()='Register Now']")
    WebElement registerBtn;

    public RegisterPage(RemoteWebDriver driver) {
        this.driver = driver;
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
    }

    public void registerNewUser(String emailId, String password, String confirmPassword, Boolean makeDynamic) {
        try {
            if (makeDynamic) {
                emailId = emailId + UUID.randomUUID().toString();
                this.lastGeneratedUserName = emailId;
            }
            SeleniumWrapper.sendKeys(emailInput, emailId);
            SeleniumWrapper.sendKeys(passwordInput, password);
            SeleniumWrapper.sendKeys(confirmpasswordInput, confirmPassword);
            // emailInput.sendKeys(emailId);
            // passwordInput.sendKeys(password);
            // confirmpasswordInput.sendKeys(confirmPassword);
            // registerBtn.click();
            SeleniumWrapper.click(registerBtn, this.driver);
            // Thread.sleep(3000);
            // SeleniumWrapper.click(registerNowButton, this.driver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
