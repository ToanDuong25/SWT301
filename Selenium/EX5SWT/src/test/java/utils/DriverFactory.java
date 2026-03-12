package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.edge.EdgeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DriverFactory {

    public static WebDriver createDriver() {
        // Tắt Selenium Manager để tránh cố download driver từ internet
        System.setProperty("SE_MANAGER_DISABLED", "true");
        System.setProperty("webdriver.http.factory", "jdk-http-client");

        String browser = System.getProperty("browser", "edge").toLowerCase();
        if ("chrome".equals(browser)) {
            return createChromeDriver();
        } else {
            return createEdgeDriver();
        }
    }

    private static WebDriver createChromeDriver() {
        System.out.println("🔧 Initializing WebDriver with Google Chrome...");
        try {
            WebDriverManager.chromedriver().setup();
        } catch (Exception e) {
            System.out.println("⚠ WebDriverManager setup failed: " + e.getMessage());
        }
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        System.out.println("✓ Chrome WebDriver initialized successfully!");
        return new ChromeDriver(options);
    }

    private static WebDriver createEdgeDriver() {
        System.out.println("🔧 Initializing WebDriver with Microsoft Edge...");

        EdgeOptions options = new EdgeOptions();
        options.addArguments("inprivate");

        // Bước 1: Đọc system property webdriver.edge.driver (set từ pom.xml hoặc -D)
        String sysPropDriver = System.getProperty("webdriver.edge.driver");
        if (sysPropDriver != null && new File(sysPropDriver).exists()) {
            System.out.println("✓ Using driver from system property: " + sysPropDriver);
            return startEdgeDriver(sysPropDriver, options);
        }

        // Bước 2: Thử WebDriverManager (dùng cache ~/.wdm nếu đã tải trước)
        try {
            WebDriverManager.edgedriver().setup();
            String wdmPath = WebDriverManager.edgedriver().getDownloadedDriverPath();
            if (wdmPath != null && new File(wdmPath).exists()) {
                System.out.println("✓ WebDriverManager found driver: " + wdmPath);
                return startEdgeDriver(wdmPath, options);
            }
        } catch (Exception e) {
            System.out.println("⚠ WebDriverManager failed: " + e.getMessage());
        }

        // Bước 3: Tìm msedgedriver.exe trong các vị trí thông dụng (>= 5MB để tránh file corrupt)
        String[] localPaths = {
            "D:\\EX5SWT\\msedgedriver.exe",
            "C:\\WebDriver\\msedgedriver.exe",
            "C:\\msedgedriver.exe",
            "C:\\Windows\\System32\\msedgedriver.exe"
        };
        for (String p : localPaths) {
            File f = new File(p);
            if (f.exists() && f.length() >= 5_000_000L) {
                System.out.println("✓ Found local msedgedriver (" + f.length()/1024/1024 + "MB): " + p);
                return startEdgeDriver(p, options);
            }
        }

        // Bước 4: Tìm trong WDM cache ~/.wdm
        try {
            Path wdmRoot = Paths.get(System.getProperty("user.home"), ".wdm", "drivers", "msedgedriver");
            if (Files.exists(wdmRoot)) {
                File found = findFile(wdmRoot.toFile(), "msedgedriver.exe");
                if (found != null && found.length() >= 5_000_000L) {
                    System.out.println("✓ Found msedgedriver in WDM cache: " + found.getAbsolutePath());
                    return startEdgeDriver(found.getAbsolutePath(), options);
                }
            }
        } catch (Exception e) {
            System.out.println("⚠ WDM cache search failed: " + e.getMessage());
        }

        throw new RuntimeException(
            "\n\n❌ Không tìm thấy msedgedriver.exe!\n" +
            "→ Tải tại: https://developer.microsoft.com/en-us/microsoft-edge/tools/webdriver/\n" +
            "→ Phiên bản cần: 145.x (khớp với Edge đang cài)\n" +
            "→ Đặt file vào: D:\\EX5SWT\\msedgedriver.exe\n"
        );
    }

    private static WebDriver startEdgeDriver(String driverPath, EdgeOptions options) {
        System.setProperty("webdriver.edge.driver", driverPath);
        EdgeDriverService service = new EdgeDriverService.Builder()
                .usingDriverExecutable(new File(driverPath))
                .usingAnyFreePort()
                .build();
        System.out.println("✓ Edge WebDriver started successfully!");
        return new EdgeDriver(service, options);
    }

    private static File findFile(File dir, String filename) {
        if (dir == null || !dir.isDirectory()) return null;
        File[] files = dir.listFiles();
        if (files == null) return null;
        for (File f : files) {
            if (f.isFile() && f.getName().equals(filename)) return f;
            if (f.isDirectory()) {
                File found = findFile(f, filename);
                if (found != null) return found;
            }
        }
        return null;
    }
}
