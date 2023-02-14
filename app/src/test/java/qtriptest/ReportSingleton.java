package qtriptest;

import java.io.File;
import java.sql.Timestamp;
import com.relevantcodes.extentreports.ExtentReports;

public class ReportSingleton {

    private static ReportSingleton instanceOfSingletonReport = null;

    private ExtentReports report;

    public static String getTimeStamp() {
        // Get time stamp for generating a unique username
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return String.valueOf(timestamp.getTime());
    }

    // Constructor
    private ReportSingleton() {
        // CREATE an instance of ExtentReports
        System.out.println("Report singleton");
        System.out.println(System.getProperty("user.dir"));
        report = new ExtentReports("/home/crio-user/workspace/sarathkumar-criodo-ME_QTRIP_QA/app/"+ "ExtentReportResults_" + getTimeStamp() + ".html"); 
        report.loadConfig(new File("extent_customization_configs.xml"));
    }

    // TO create instance of class
    public static ReportSingleton getInstanceOfSingletonReportClass() {
        if (instanceOfSingletonReport == null) {
            instanceOfSingletonReport = new ReportSingleton();
        }
        return instanceOfSingletonReport;
    }

    public ExtentReports getReport() {
        return report;
    }

}