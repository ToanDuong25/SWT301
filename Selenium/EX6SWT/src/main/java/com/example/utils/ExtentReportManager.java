package com.example.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

/**
 * ExtentReportManager - Quản lý ExtentReports (Singleton)
 * Sinh báo cáo HTML đẹp vào target/ExtentReports/
 */
public class ExtentReportManager {

    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> testThreadLocal = new ThreadLocal<>();

    /**
     * Khởi tạo ExtentReports (gọi 1 lần)
     */
    public static ExtentReports getInstance() {
        if (extent == null) {
            String reportPath = System.getProperty("user.dir") + "/target/ExtentReports/report.html";
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
            sparkReporter.config().setDocumentTitle("ReadRune - Báo cáo kiểm thử");
            sparkReporter.config().setReportName("ReadRune Automation Test Report");
            sparkReporter.config().setTheme(Theme.STANDARD);
            sparkReporter.config().setEncoding("UTF-8");

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
            extent.setSystemInfo("Ứng dụng", "ReadRune");
            extent.setSystemInfo("Môi trường", "Test");
            extent.setSystemInfo("Trình duyệt", DriverFactory.getConfig("browser"));
            extent.setSystemInfo("Hệ điều hành", System.getProperty("os.name"));
            extent.setSystemInfo("Tester", "Toandt");
        }
        return extent;
    }

    /**
     * Tạo test mới trong báo cáo
     */
    public static ExtentTest createTest(String testName, String description) {
        ExtentTest test = getInstance().createTest(testName, description);
        testThreadLocal.set(test);
        return test;
    }

    /**
     * Lấy test hiện tại
     */
    public static ExtentTest getTest() {
        return testThreadLocal.get();
    }

    /**
     * Ghi báo cáo ra file
     */
    public static void flush() {
        if (extent != null) {
            extent.flush();
        }
    }
}
