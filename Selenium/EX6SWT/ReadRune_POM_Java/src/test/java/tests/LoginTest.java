package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;

public class LoginTest extends BaseTest {

    @Test(description = "TC-LG-001: Trang Login load thành công")
    public void testLoginLoad() throws InterruptedException {
        driver.get("https://personal-wkrgpp1g.outsystemscloud.com/ReadRune/Login");
        LoginPage loginPage = new LoginPage(driver);

        Assert.assertTrue(loginPage.isPageLoaded(), "Trang Login không load đầy đủ hoặc thiếu các trường email, password, nút login.");
    }

    @Test(description = "TC-LG-002: Đăng nhập thành công với thông tin hợp lệ")
    public void testValidLogin() throws InterruptedException {
        driver.get("https://personal-wkrgpp1g.outsystemscloud.com/ReadRune/Login");
        LoginPage loginPage = new LoginPage(driver);

        loginPage.login("testuser@gmail.com", "Test@12345");
        Thread.sleep(5000);

        boolean isRedirected = !driver.getCurrentUrl().contains("Login");
        Assert.assertTrue(isRedirected, "Người dùng phải được chuyển hướng sau khi đăng nhập thành công.");
    }

    @Test(description = "TC-LG-003: Đăng nhập thất bại khi email trống")
    public void testEmptyEmailLogin() throws InterruptedException {
        driver.get("https://personal-wkrgpp1g.outsystemscloud.com/ReadRune/Login");
        LoginPage loginPage = new LoginPage(driver);

        loginPage.login("", "Test@12345");
        Thread.sleep(2000);

        String errorMsg = loginPage.getErrorMessage();
        boolean stayOnPage = driver.getCurrentUrl().contains("Login");
        Assert.assertTrue(stayOnPage || (errorMsg != null && !errorMsg.isEmpty()),
                "Phải hiển thị lỗi hoặc ở lại trang Login khi email trống.");
    }

    @Test(description = "TC-LG-004: Đăng nhập thất bại khi password trống")
    public void testEmptyPasswordLogin() throws InterruptedException {
        driver.get("https://personal-wkrgpp1g.outsystemscloud.com/ReadRune/Login");
        LoginPage loginPage = new LoginPage(driver);

        loginPage.login("testuser@gmail.com", "");
        Thread.sleep(2000);

        String errorMsg = loginPage.getErrorMessage();
        boolean stayOnPage = driver.getCurrentUrl().contains("Login");
        Assert.assertTrue(stayOnPage || (errorMsg != null && !errorMsg.isEmpty()),
                "Phải hiển thị lỗi hoặc ở lại trang Login khi password trống.");
    }

    @Test(description = "TC-LG-005: Đăng nhập thất bại với email sai")
    public void testWrongEmailLogin() throws InterruptedException {
        driver.get("https://personal-wkrgpp1g.outsystemscloud.com/ReadRune/Login");
        LoginPage loginPage = new LoginPage(driver);

        loginPage.login("wrong_email@gmail.com", "Test@12345");
        Thread.sleep(3000);

        String errorMsg = loginPage.getErrorMessage();
        boolean stayOnPage = driver.getCurrentUrl().contains("Login");
        Assert.assertTrue(stayOnPage || (errorMsg != null && !errorMsg.isEmpty()),
                "Phải hiển thị lỗi khi đăng nhập với email sai.");
    }

    @Test(description = "TC-LG-006: Đăng nhập thất bại với password sai")
    public void testWrongPasswordLogin() throws InterruptedException {
        driver.get("https://personal-wkrgpp1g.outsystemscloud.com/ReadRune/Login");
        LoginPage loginPage = new LoginPage(driver);

        loginPage.login("testuser@gmail.com", "WrongPassword123");
        Thread.sleep(3000);

        String errorMsg = loginPage.getErrorMessage();
        boolean stayOnPage = driver.getCurrentUrl().contains("Login");
        Assert.assertTrue(stayOnPage || (errorMsg != null && !errorMsg.isEmpty()),
                "Phải hiển thị lỗi khi đăng nhập với password sai.");
    }

    @Test(description = "TC-LG-007: Đăng nhập thất bại khi cả email và password trống")
    public void testEmptyFieldsLogin() throws InterruptedException {
        driver.get("https://personal-wkrgpp1g.outsystemscloud.com/ReadRune/Login");
        LoginPage loginPage = new LoginPage(driver);

        loginPage.clickLogin();
        Thread.sleep(2000);

        String errorMsg = loginPage.getErrorMessage();
        boolean stayOnPage = driver.getCurrentUrl().contains("Login");
        Assert.assertTrue(stayOnPage || (errorMsg != null && !errorMsg.isEmpty()),
                "Phải hiển thị lỗi khi để trống cả email và password.");
    }

    @Test(description = "TC-LG-008: Chuyển sang trang Đăng ký")
    public void testNavigationToSignUp() throws InterruptedException {
        driver.get("https://personal-wkrgpp1g.outsystemscloud.com/ReadRune/Login");
        LoginPage loginPage = new LoginPage(driver);

        loginPage.clickSignUp();
        Thread.sleep(2000);

        Assert.assertTrue(driver.getCurrentUrl().contains("SignUp"),
                "URL phải chứa /SignUp sau khi click link Đăng ký.");
    }
}

