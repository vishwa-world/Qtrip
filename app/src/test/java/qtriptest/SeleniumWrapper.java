package qtriptest;

import java.io.File;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SeleniumWrapper {

    public static boolean click(WebElement elementToClick, WebDriver driver) throws InterruptedException {
        if (elementToClick.isDisplayed()) {
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elementToClick);
                Thread.sleep(1500);
                elementToClick.click();
                return true;        
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    /*
     * clear the existing text on the inputBox
     * Type in the new keys
     */
    public static boolean sendKeys(WebElement inputBox, String keysToSend) {
        try {
            inputBox.clear();
            Thread.sleep(500);
            inputBox.sendKeys(keysToSend);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /*
     * Navigate to the given url if the current url is not equal to the given url
     * after navigation , ensure that the current url is updated as per the given
     * url
     */
    public static boolean navigate(WebDriver driver, String url) {
        try {
            if (driver.getCurrentUrl().equals(url)) {
                return true;
            } else {
                driver.get(url);
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /*
     * Try to find the webelement with the given By
     * if the element is not found, retry for the given number of times
     * if you do not want retry , set the retry value to 0
     */
    public static WebElement findElementWithRetry(WebDriver driver, By by, int retryCount) throws Exception {
        int counter = 0;
        Exception ex = new Exception();
        while (counter <= retryCount) {
            try {
                return driver.findElement(by);
            } catch (Exception e) {
                counter++;
                ex = e;
            }

        }
        throw new Exception(ex.getCause());
    }

    public static String takeScreenshot(WebDriver driver, String screenshotType, String description) {
        try {
            File theDir = new File("/screenshots");
            if (!theDir.exists()) {
                theDir.mkdirs();
            }
            String timestamp = String.valueOf(java.time.LocalDateTime.now());
            String fileName = String.format("screenshot_%s_%s_%s.png", timestamp, screenshotType, description);
            TakesScreenshot scrShot = ((TakesScreenshot) driver);
            File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
            File DestFile = new File("screenshots/" + fileName);
            FileUtils.copyFile(SrcFile, DestFile);
            return DestFile.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
