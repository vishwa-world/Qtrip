package qtriptest.tests;

import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.ReportSingleton;
import qtriptest.SeleniumWrapper;
import qtriptest.pages.AdventurePage;
import qtriptest.pages.HomePage;
import java.net.MalformedURLException;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class testCase_02 {

    static ExtentReports report;
    static ExtentTest test;
    static RemoteWebDriver driver;

    @BeforeTest(alwaysRun = true)
    public static void createDriver() throws MalformedURLException {
        ReportSingleton rpt = ReportSingleton.getInstanceOfSingletonReportClass();
        report = rpt.getReport();
        DriverSingleton ds1 = DriverSingleton.getInstanceOfSingletonBrowserClass();
        driver = ds1.getDriver();
    }

    @Test(description = "Verify that Search and filters works fine", dataProvider = "data-provider",
            dataProviderClass = DP.class, groups = {"Search and Filter flow"}, priority = 2)
    public static void TestCase02(String cityName, String categoryFilter, String durationFilter,
            String expectedFilteredResults, String expectedUnFilteredResults) {
        try {
            test = report.startTest("Verify that Search and filters work fine");
            HomePage home = new HomePage(driver);
            home.gotoHomePage();
            home.searchCity("titan");
            // Thread.sleep(3000);
            // System.out.println(home.isNoCityFound());
            Assert.assertTrue(home.isNoCityFound(), "Failed to verfiy that no city is found");
            home.searchCity(cityName);
            // Thread.sleep(2000);
            home.selectCity(cityName);
            AdventurePage adventures = new AdventurePage(driver);
            adventures.setCategoryValue(categoryFilter);
            adventures.setFilterValue(durationFilter);

            if (adventures.getResultCount() == Integer.parseInt(expectedFilteredResults)) {
                test.log(LogStatus.PASS, "Verify that the Result count is as expected");
                // System.out.println("Pass: Verify that the Result count is as expected");
            } else {
                test.log(LogStatus.FAIL, "Mismatchg in result count Expected vs actual ");
                // System.out.println("Fail: Mismatchg in result count Expected vs actual");
            }
            adventures.clearFilters();

            if (adventures.getResultCount() == Integer.parseInt(expectedUnFilteredResults)) {
                test.log(LogStatus.PASS, "Verify that the Result count is as expected after clearing filters");
                // System.out.println(
                //         "Pass: Verify that the Result count is as expected after clearing filters");
            } else {
                test.log(LogStatus.FAIL, "Mismatchg in result count Expected vs actual after clearing filters ");
                // System.out.println(
                //         "Fail: Mismatchg in result count Expected vs actual after clearing filters");
            }
            test.log(LogStatus.INFO, test.addScreenCapture(SeleniumWrapper.takeScreenshot(driver,"PASS", "Search")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterSuite
    public static void quitDriver() {
        driver.manage().deleteAllCookies();
        report.endTest(test);
        driver.resetInputState();
    }
}

