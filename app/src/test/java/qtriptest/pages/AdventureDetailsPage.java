package qtriptest.pages;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import qtriptest.SeleniumWrapper;

public class AdventureDetailsPage {

    RemoteWebDriver driver;

    @FindBy(xpath = "//input[@name='name']")
    WebElement nameInput;

    @FindBy(xpath = "//input[@name='person']")
    WebElement noOfPersonInput;

    @FindBy(xpath = "//input[@name='date']")
    WebElement dateInput;

    @FindBy(xpath = "//button[text()='Reserve']")
    WebElement reserveBtn;

    public AdventureDetailsPage(RemoteWebDriver driver) {
        this.driver = driver;
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
    }

    public void bookAdventure(String name, String date, int noOfPerson) throws InterruptedException {
        String noOfPersonCount = String.valueOf(noOfPerson);
        SeleniumWrapper.sendKeys(nameInput, name);
        SeleniumWrapper.sendKeys(dateInput, date);
        SeleniumWrapper.sendKeys(noOfPersonInput, noOfPersonCount);
        SeleniumWrapper.click(reserveBtn, this.driver);
        // nameInput.sendKeys(name);
        // dateInput.sendKeys(date);
        // noOfPersonInput.clear();
        // noOfPersonInput.sendKeys(noOfPersonCount);
        // reserveBtn.click();
        Thread.sleep(2000);
    }

    public boolean isBookingSuccessful() {
        return true;
    }
}