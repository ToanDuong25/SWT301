package com.example.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;

/**
 * DriverFactory - Quản lý WebDriver (Singleton pattern)
 * Hỗ trợ Chrome, Firefox, Edge với chế độ headless
 */
public class DriverFactory {

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private static Properties config;

    static {
        config = new Properties();
        try (InputStream is = DriverFactory.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (is != null) {
                config.load(is);
            }
        } catch (IOException e) {
            System.err.println("Không thể đọc config.properties: " + e.getMessage());
        }
    }

    /**
     * Tạo và trả về WebDriver dựa trên cấu hình
     */
    public static WebDriver getDriver() {
        if (driverThreadLocal.get() == null) {
            String browser = System.getProperty("browser", config.getProperty("browser", "chrome"));
            boolean headless = Boolean.parseBoolean(
                    System.getProperty("headless", config.getProperty("headless", "false")));

            WebDriver driver = createDriver(browser, headless);

            int implicitWait = Integer.parseInt(config.getProperty("implicit.wait", "10"));
            int pageLoadTimeout = Integer.parseInt(config.getProperty("page.load.timeout", "30"));

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(pageLoadTimeout));
            driver.manage().window().maximize();

            driverThreadLocal.set(driver);
        }
        return driverThreadLocal.get();
    }

    /**
     * Tạo WebDriver theo loại browser
     */
    private static WebDriver createDriver(String browser, boolean headless) {
        switch (browser.toLowerCase()) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions ffOptions = new FirefoxOptions();
                if (headless) ffOptions.addArguments("--headless");
                return new FirefoxDriver(ffOptions);

            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                if (headless) edgeOptions.addArguments("--headless");
                return new EdgeDriver(edgeOptions);

            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                if (headless) {
                    chromeOptions.addArguments("--headless=new");
                    chromeOptions.addArguments("--disable-gpu");
                    chromeOptions.addArguments("--window-size=1920,1080");
                }
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                chromeOptions.addArguments("--remote-allow-origins=*");
                return new ChromeDriver(chromeOptions);
        }
    }

    /**
     * Đóng và giải phóng WebDriver
     */
    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            driver.quit();
            driverThreadLocal.remove();
        }
    }

    /**
     * Lấy giá trị cấu hình từ config.properties
     */
    public static String getConfig(String key) {
        return config.getProperty(key);
    }
}
