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
// import qtriptest.pages.HistoryPage.ReservationHistory;
import java.net.MalformedURLException;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class testCase_03 {

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
 
     @Test(description = "Verify that adventure booking and cancellation works fine", dataProvider = "data-provider", dataProviderClass = DP.class, groups = {
             "Booking and Cancellation Flow" }, priority = 3)
     public static void TestCase03(String userName, String password, String searchCity, String adventureName,
             String guestName, String date, String count) {
        try {
         test = report.startTest("Verify that adventure booking and cancellation works fine");
         HomePage home = new HomePage(driver);
         home.gotoHomePage();
         home.clickRegister();
         RegisterPage register = new RegisterPage(driver);
         register.registerNewUser(userName, password, password, true);
         String username = register.lastGeneratedUserName;
         LoginPage Login = new LoginPage(driver);
         Login.performLogin(username, password);
         home.searchCity(searchCity);
         home.selectCity(searchCity);
         AdventurePage adventures = new AdventurePage(driver);
         adventures.selectAdventure(adventureName);
         AdventureDetailsPage adventureDetailsPage = new AdventureDetailsPage(driver);
         adventureDetailsPage.bookAdventure(guestName, date, Integer.parseInt(count));
         HistoryPage history = new HistoryPage(driver);
         history.gotoHistoryPage();
         var reservations = history.getReservations();
         String transactionId = reservations.get(0).getTransactionId();
         if (reservations.size() == 1) {
            test.log(LogStatus.PASS, "Successfully verified that the reservation is done");
            //  System.out.println("PASS: Successfully verified that the reservation is done");
         } else {
             test.log(LogStatus.FAIL, "Failure while verifying the number of reservations");
            //  System.out.println("FAIL: Failure while verifying the number of reservations");
         }

         Boolean isReservationCancelled = history.cancelReservation(transactionId);
        //  List<WebElement> cancellationButtons = driver.findElements("//button[@class='cancel-button']");

         if (isReservationCancelled) {
            test.log(LogStatus.PASS, "Successfully verified that the reservation is cancelled");
            // System.out.println("PASS: Successfully verified that the reservation is cancelled");
         }
         else {
            test.log(LogStatus.FAIL, "Failure in cancelling the reservation");
            // System.out.println("FAIL: Failure in cancelling the reservation");
         }
         home.logOutUser();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
         test.log(LogStatus.INFO, test.addScreenCapture(SeleniumWrapper.takeScreenshot(driver, "PASS", "Booking")));
     }
 
     @AfterSuite
     public static void quitDriver() {
         driver.manage().deleteAllCookies();
         report.endTest(test);
         driver.resetInputState();
     }
}
