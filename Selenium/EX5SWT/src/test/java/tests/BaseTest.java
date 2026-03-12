package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import utils.DriverFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class BaseTest {
    protected static WebDriver driver;

    @BeforeAll
    public static void setUpBase() {
        try {
            driver = DriverFactory.createDriver();
            driver.manage().window().maximize();
        } catch (org.openqa.selenium.SessionNotCreatedException e) {
            if (e.getMessage().contains("ChromeDriver only supports Chrome version")) {
                System.err.println("\n❌ LỖI: ChromeDriver version không match Chrome browser version");
                System.err.println("   Lý do: " + e.getMessage());
                System.err.println("\n✓ GỢI Ý FIX:");
                System.err.println("   1. Download ChromeDriver 145 từ: https://googlechromelabs.github.io/chrome-for-testing/");
                System.err.println("   2. Hoặc: Downgrade Chrome về version 114");
                System.err.println("   3. Hoặc: Xóa folder ~/.wdm và chạy lại để download version mới\n");
                throw e;
            }
            throw e;
        }
    }

    @AfterEach
    void captureEvidence(TestInfo testInfo) {
        if (driver == null || !(driver instanceof TakesScreenshot)) {
            return;
        }

        String className = testInfo.getTestClass()
            .map(Class::getSimpleName)
            .orElse("UnknownClass");
        String methodName = testInfo.getTestMethod()
            .map(method -> method.getName().replaceAll("[^a-zA-Z0-9._-]", "_"))
            .orElse("unknownTest");

        Path screenshotDir = Paths.get("target", "test-artifacts", "screenshots", className);
        Path screenshotPath = screenshotDir.resolve(methodName + ".png");

        try {
            Files.createDirectories(screenshotDir);
            byte[] image = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Files.write(screenshotPath, image);
            System.out.println("📸 Screenshot saved: " + screenshotPath);
        } catch (IOException e) {
            System.err.println("⚠ Không thể lưu screenshot cho " + methodName + ": " + e.getMessage());
        }
    }

    @AfterAll
    public static void tearDownBase() {
        if (driver != null) {
            driver.quit();
        }
    }
}
