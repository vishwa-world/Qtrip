package qtriptest.tests;

import qtriptest.DP;
// import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.ReportSingleton;
import qtriptest.SeleniumWrapper;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import java.net.MalformedURLException;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
// import org.apache.logging.log4j.core.util.Assert;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class testCase_01 {

     static ExtentReports report;
     static ExtentTest test;
     static RemoteWebDriver driver;
 
     @BeforeTest(alwaysRun = true)
     public static void createDriver() throws MalformedURLException {
        ReportSingleton rs1 = ReportSingleton.getInstanceOfSingletonReportClass();
        report = rs1.getReport(); 
        // IMPORTANT!: Enter the Driver Location here
        DriverSingleton ds1 = DriverSingleton.getInstanceOfSingletonBrowserClass();
        driver = ds1.getDriver();
     }
 
     @Test(description = "Verify user registration - login - logout", dataProvider = "data-provider", dataProviderClass = DP.class, groups = {
             "Login Flow" }, priority = 1)
      public static void TestCase01(String userName, String password) throws InterruptedException {
            test = report.startTest("Verify user registration - login - logout");
            // String userName = "sam";
            // String password = "samson";`
            HomePage home = new HomePage(driver);
            home.gotoHomePage();
            home.clickRegister();
            RegisterPage register = new RegisterPage(driver);
            register.registerNewUser(userName, password, password, true);
            // Thread.sleep(3000);
            String username = register.lastGeneratedUserName;
            LoginPage Login = new LoginPage(driver);
            Login.performLogin(username, password);
            //  Assert.assertTrue(home.isUserLoggedIn());
            if (home.isUserLoggedIn()) {
                test.log(LogStatus.PASS, "Successfully logged in with the registered user");
               // System.out.println("Successfully logged in with the registered user");
            } else {
                test.log(LogStatus.FAIL, "Failure to Login using registered user");
               // System.out.println("Failure to Login using registered user");
            }
            home.logOutUser(); 
            //  Assert.assertFalse(home.isUserLoggedIn());
            if (!home.isUserLoggedIn()) {
                test.log(LogStatus.PASS, "Successfully verified that the user is logged out");
               // System.out.println("Successfully verified that the user is logged out");
            } else {
                test.log(LogStatus.FAIL, "Failure in verification of user logout");
               // System.out.println("Failure in verification of user logout");
            } 
            home.gotoHomePage();
            test.log(LogStatus.INFO, test.addScreenCapture(SeleniumWrapper.takeScreenshot(driver, "PASS", "Login")));
     }
 
     @AfterSuite
     public static void quitDriver() {
        try {
            driver.manage().deleteAllCookies();
            report.endTest(test);
            driver.resetInputState();
            // driver.quit();
            // driver.quit();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
     }

     public static void main(String[] args) {
         try {
            createDriver();
            TestCase01("sam", "samson");
            quitDriver();
         } catch (Exception e) {
            e.printStackTrace();
         }
     }
}
