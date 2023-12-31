package qtriptest.pages;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import qtriptest.SeleniumWrapper;

public class HistoryPage {

    RemoteWebDriver driver;

    @FindBy(id = "reservation-table")
    WebElement reservationTable;

    public HistoryPage(RemoteWebDriver driver) {
        this.driver = driver;
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
    }

    public void gotoHistoryPage() {
        SeleniumWrapper.navigate(this.driver,"https://qtripdynamic-qa-frontend.vercel.app/pages/adventures/reservations/index.html");
        // driver.get("https://qtripdynamic-qa-frontend.vercel.app/pages/adventures/reservations/index.html");
    }

    public List<ReservationHistory> getReservations() {
        List<ReservationHistory> history = new ArrayList<>();
        List<WebElement> tableRows = reservationTable.findElements(By.xpath("//tbody[@id='reservation-table']/tr"));
        List<WebElement> trS = driver.findElements(By.xpath("//tbody[@id='reservation-table']/tr/th"));
        for (int i=0;i<tableRows.size();i++) {
            WebElement row = tableRows.get(i);
            String transactionId, bookingName, adventure, person, date, price, bookingTime;
            transactionId = trS.get(i).getText();
            // transactionId = row.findElement(By.xpath("//th")).getText();
            bookingName = row.findElement(By.xpath("//td[1]")).getText();
            adventure = row.findElement(By.xpath("//td[2]")).getText();
            person = row.findElement(By.xpath("//td[3]")).getText();
            date = row.findElement(By.xpath("//td[4]")).getText();
            price = row.findElement(By.xpath("//td[5]")).getText();
            bookingTime = row.findElement(By.xpath("//td[6]")).getText();
            ReservationHistory rs = new ReservationHistory();
            var historyRecord = rs.createReservationHistory(transactionId, bookingName, adventure, person, date, price,
                    bookingTime);
            history.add(historyRecord);
        }
        // for (WebElement row : tableRows) {
        //     String transactionId, bookingName, adventure, person, date, price, bookingTime;
        //     transactionId = row.findElement(By.xpath("//th")).getText();
        //     bookingName = row.findElement(By.xpath("//td[1]")).getText();
        //     adventure = row.findElement(By.xpath("//td[2]")).getText();
        //     person = row.findElement(By.xpath("//td[3]")).getText();
        //     date = row.findElement(By.xpath("//td[4]")).getText();
        //     price = row.findElement(By.xpath("//td[5]")).getText();
        //     bookingTime = row.findElement(By.xpath("//td[6]")).getText();
        //     ReservationHistory rs = new ReservationHistory();
        //     var historyRecord = rs.CreateReservationHistory(transactionId, bookingName, adventure, person, date, price,
        //             bookingTime);
        //     history.add(historyRecord);
        // }
        return history;

    }

    public boolean cancelReservation(String transactionId) {
        try {
        By byCancelBtn = new By.ByXPath(String.format("//button[@id='%s']", transactionId));
        WebElement cancelBtn = driver.findElement(byCancelBtn);
        SeleniumWrapper.click(cancelBtn, this.driver);
        // cancelBtn.click();
        Thread.sleep(2000);
        return true;
        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public class ReservationHistory {
        String transactionId, bookingName, adventure, persons, date, price, bookingtime;

        public ReservationHistory createReservationHistory(String transactionId, String bookingName, String adventure,
                String persons, String date, String price, String bookingtime) {
            this.transactionId = transactionId;
            this.bookingName = bookingName;
            this.adventure = adventure;
            this.persons = persons;
            this.date = date;
            this.price = price;
            this.bookingtime = bookingtime;
            return this;
        }

        public String getTransactionId() {
            return this.transactionId;
        }

    }
}