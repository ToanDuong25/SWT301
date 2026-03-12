package tests;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

public class BaseTest {
    protected WebDriver driver;

    @BeforeSuite
    public void cleanUpOldScreenshots() {
        // ⚠ Tắt tính năng tự xóa ảnh để không mất ảnh khi chạy từng test riêng lẻ.
        //    Nếu muốn xóa ảnh cũ thủ công, hãy xóa nội dung thư mục Screenshots/ trước khi chạy.
        //
        // File screenshotDir = new File("Screenshots");
        // if (screenshotDir.exists() && screenshotDir.isDirectory()) {
        //     File[] files = screenshotDir.listFiles();
        //     if (files != null) {
        //         for (File file : files) {
        //             file.delete();
        //         }
        //     }
        //     System.out.println("🗑️ Đã dọn dẹp thư mục Screenshots cũ trước khi chạy test mới.");
        // }
        System.out.println("ℹ️  Auto-cleanup tắt — ảnh cũ được giữ lại để không bị mất khi chạy từng test.");
    }

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--remote-allow-origins=*");
        // options.addArguments("--headless=new"); // Tắt headless để browser hiện lên khi chạy test
        options.addArguments("--disable-gpu");
        
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        // Also wait for the page to fully load
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (driver != null) {
            try {
                // Tự động chụp màn hình kết quả trước khi đóng trình duyệt
                TakesScreenshot ts = (TakesScreenshot) driver;
                File source = ts.getScreenshotAs(OutputType.FILE);
                
                // Tạo thư mục Screenshots nếu chưa có
                File screenshotDir = new File("Screenshots");
                if (!screenshotDir.exists()) {
                    screenshotDir.mkdir();
                }
                
                // Lấy tên hàm test và trạng thái PASS/FAIL
                String methodName = result.getMethod().getMethodName();
                String status = (result.getStatus() == ITestResult.SUCCESS) ? "PASS" : "FAIL";
                String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                
                String fileName = methodName + "_" + status + "_" + timestamp + ".png";
                File destination = new File(screenshotDir, fileName);
                
                // Lưu ảnh
                Files.copy(source.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("📸 Đã chụp màn hình kết quả: " + fileName);
                
            } catch (Exception e) {
                System.out.println("⚠️ Lỗi khi chụp màn hình: " + e.getMessage());
            } finally {
                driver.quit();
            }
        }
    }
}
