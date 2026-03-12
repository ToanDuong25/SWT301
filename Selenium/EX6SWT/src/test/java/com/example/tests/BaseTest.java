package com.example.tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.example.utils.DriverFactory;
import com.example.utils.ExtentReportManager;
import com.example.utils.TestUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.WebDriver;

/**
 * BaseTest - Lớp cơ sở cho tất cả Test class
 * Quản lý setup/teardown của WebDriver và ExtentReports
 */
public abstract class BaseTest {

    protected WebDriver driver;
    protected ExtentTest extentTest;

    @BeforeAll
    static void initReport() {
        ExtentReportManager.getInstance();
    }

    @BeforeEach
    void setUp(TestInfo testInfo) {
        driver = DriverFactory.getDriver();
        String testName = testInfo.getDisplayName();
        extentTest = ExtentReportManager.createTest(testName,
                testInfo.getTags().stream().findFirst().orElse(""));
        extentTest.log(Status.INFO, "🚀 Bắt đầu test: " + testName);
    }

    @AfterEach
    void tearDown(TestInfo testInfo) {
        if (extentTest != null) {
            // Chụp ảnh màn hình sau mỗi test
            String screenshotPath = TestUtils.takeScreenshot(driver, testInfo.getDisplayName());
            if (screenshotPath != null) {
                try {
                    extentTest.addScreenCaptureFromPath(screenshotPath);
                } catch (Exception e) {
                    // Bỏ qua nếu không attach được ảnh
                }
            }
            extentTest.log(Status.INFO, "✅ Kết thúc test: " + testInfo.getDisplayName());
        }
        DriverFactory.quitDriver();
    }

    @AfterAll
    static void flushReport() {
        ExtentReportManager.flush();
    }

    /**
     * Lấy cấu hình từ config.properties
     */
    protected String config(String key) {
        return DriverFactory.getConfig(key);
    }
}
