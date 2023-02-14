package qtriptest.tests;

import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.ReportSingleton;
import qtriptest.SeleniumWrapper;
import qtriptest.pages.AdventureDetailsPage;
import qtriptest.pages.AdventurePage;
import qtriptest.pages.HistoryPage;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import java.net.MalformedURLException;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class testCase_04 {

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
  
      @Test(description = "Verify that Booking history can be viewed", dataProvider = "data-provider", dataProviderClass = DP.class, groups = {
              "Reliability Flow" }, priority = 4)
      public static void TestCase04(String userName, String password, String dataset1, String dataset2,
              String dataset3) throws Exception {
          test = report.startTest("Verify that Booking history can be viewed");
          String[] DS1 = dataset1.split(";");
          String[] DS2 = dataset2.split(";");
          String[] DS3 = dataset3.split(";");
          HomePage home = new HomePage(driver);
          home.gotoHomePage();
          home.clickRegister();
          RegisterPage register = new RegisterPage(driver);
          register.registerNewUser(userName, password, password, true);
          String username = register.lastGeneratedUserName;
          LoginPage Login = new LoginPage(driver);
          Login.performLogin(username, password);

          String ds1City = DS1[0];
          String ds1Adventure = DS1[1];
          String ds1Name = DS1[2];
          String ds1Date = DS1[3];
          int ds1PersonsCount = Integer.parseInt(DS1[4]);

          String ds2City = DS2[0];
          String ds2Adventure = DS2[1];
          String ds2Name = DS2[2];
          String ds2Date = DS2[3];
          int ds2PersonsCount = Integer.parseInt(DS2[4]);


          String ds3City = DS3[0];
          String ds3Adventure = DS3[1];
          String ds3Name = DS3[2];
          String ds3Date = DS3[3];
          int ds3PersonsCount = Integer.parseInt(DS3[4]);

    
          home.searchCity(ds1City);
          home.selectCity(ds1City);
          // Thread.sleep(2000);

          AdventurePage adventures = new AdventurePage(driver);
          adventures.selectAdventure(ds1Adventure);
          AdventureDetailsPage adventureDetailsPage = new AdventureDetailsPage(driver);
          adventureDetailsPage.bookAdventure(ds1Name, ds1Date, ds1PersonsCount);

          // DATA2
          home.gotoHomePage();
          home.searchCity(ds2City);
          home.selectCity(ds2City);
          // Thread.sleep(2000);

          adventures.selectAdventure(ds2Adventure);
          adventureDetailsPage.bookAdventure(ds2Name, ds2Date, ds2PersonsCount);

          // DATA3
          home.gotoHomePage();
          home.searchCity(ds3City);
          home.selectCity(ds3City);
          // Thread.sleep(2000);

          adventures.selectAdventure(ds3Adventure);
          adventureDetailsPage.bookAdventure(ds3Name, ds3Date, ds3PersonsCount);
          // Thread.sleep(3000);

          HistoryPage history = new HistoryPage(driver);
          history.gotoHistoryPage();

          var reservations = history.getReservations();

          if (reservations.size() == 3) {
              test.log(LogStatus.PASS, "Successfully verified that all the reservations are displayed");
              // System.out.println("PASS: Successfully verified that all the reservations are displayed");
          } else {
              test.log(LogStatus.FAIL, "Failure while verifying the number of reservations");
              // System.out.println("FAIL: Failure while verifying the number of reservations");
          }
          home.logOutUser();
          test.log(LogStatus.INFO, test.addScreenCapture(SeleniumWrapper.takeScreenshot(driver, "PASS", "Reliability")));
      }
  
      @AfterSuite
      public static void quitDriver() {
        //   driver.quit();
          driver.manage().deleteAllCookies();
          report.endTest(test);
          driver.resetInputState();
          report.flush();
      }
}
