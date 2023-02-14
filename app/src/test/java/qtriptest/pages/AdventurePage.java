package qtriptest.pages;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.Select;
import qtriptest.SeleniumWrapper;

public class AdventurePage {

    RemoteWebDriver driver;

    @FindBy(id = "duration-select")
    WebElement durationFilter;

    @FindBy(id = "category-select")
    WebElement categoryFilter;

    @FindBy(xpath = "//div[@onclick='clearDuration(event)']")
    WebElement clearDuration;

    @FindBy(xpath = "//div[@onclick='clearCategory(event)']")
    WebElement clearCategory;

    public AdventurePage(RemoteWebDriver driver) {
        this.driver = driver;
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
    }

    public void setFilterValue(String value) {
        try {
            SeleniumWrapper.click(durationFilter, this.driver);
            // durationFilter.click();
            Select FilterDropdown = new Select(durationFilter);
            FilterDropdown.selectByVisibleText(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setCategoryValue(String value) {
        try {
            SeleniumWrapper.click(categoryFilter, this.driver);
            // categoryFilter.click();
            Select FilterDropdown = new Select(categoryFilter);
            FilterDropdown.selectByVisibleText(value);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public int getResultCount() {
        List<WebElement> adventureCards = this.driver.findElements(By.xpath("//div[@id='data']/div"));
        return adventureCards.size();
    }

    public void selectAdventure(String adventureName) {
        try {
            Thread.sleep(1000);
            WebElement adventure = this.driver.findElement(By.xpath(String.format("//h5[text()='%s']", adventureName)));
            SeleniumWrapper.click(adventure, this.driver);
            // adventure.click();
            // Thread.sleep(1000);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void clearFilters() {
        try {
            SeleniumWrapper.click(clearCategory, this.driver);
            SeleniumWrapper.click(clearDuration, this.driver);
            // clearDuration.click();
            // clearCategory.click();
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}