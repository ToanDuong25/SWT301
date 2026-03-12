package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;

public class LoginTest extends BaseTest {

    @Test(description = "TC-LG-001: Trang Login load thành công")
    public void testLoginLoad() throws InterruptedException {
        driver.get("https://personal-wkrgpp1g.outsystemscloud.com/ReadRune/Login");
        LoginPage loginPage = new LoginPage(driver);
        
        Assert.assertTrue(loginPage.isPageLoaded(), "Trang Login không load đầy đủ hoặc thiếu các trường thông tin email, password, nút login.");
    }

    @Test(description = "TC-LG-002: Đăng nhập thành công với thông tin hợp lệ")
    public void testValidLogin() throws InterruptedException {
        driver.get("https://personal-wkrgpp1g.outsystemscloud.com/ReadRune/Login");
        LoginPage loginPage = new LoginPage(driver);
        
        loginPage.login("testuser@gmail.com", "Test@12345");
        
        Thread.sleep(4000); 
        
        Assert.assertTrue(driver.getCurrentUrl().contains("Dashboard") || !driver.getCurrentUrl().contains("Login"), 
            "User should be navigated away from login page to dashboard upon success.");
    }

    @Test(description = "TC-LG-003: Đăng nhập thất bại khi email trống")
    public void testEmptyEmailLogin() throws InterruptedException {
        driver.get("https://personal-wkrgpp1g.outsystemscloud.com/ReadRune/Login");
        LoginPage loginPage = new LoginPage(driver);
        
        loginPage.login("", "Test@12345");
        
        Thread.sleep(2000);
        String errorMsg = loginPage.getErrorMessage();
        Assert.assertNotNull(errorMsg, "Phải hiển thị validation message yêu cầu nhập email.");
        Assert.assertFalse(errorMsg.isEmpty());
    }

    @Test(description = "TC-LG-004: Đăng nhập thất bại khi password trống")
    public void testEmptyPasswordLogin() throws InterruptedException {
        driver.get("https://personal-wkrgpp1g.outsystemscloud.com/ReadRune/Login");
        LoginPage loginPage = new LoginPage(driver);
        
        loginPage.login("testuser@gmail.com", "");
        
        Thread.sleep(2000);
        String errorMsg = loginPage.getErrorMessage();
        Assert.assertNotNull(errorMsg, "Phải hiển thị validation message yêu cầu nhập password.");
        Assert.assertFalse(errorMsg.isEmpty());
    }

    @Test(description = "TC-LG-005: Đăng nhập thất bại với email sai")
    public void testWrongEmailLogin() throws InterruptedException {
        driver.get("https://personal-wkrgpp1g.outsystemscloud.com/ReadRune/Login");
        LoginPage loginPage = new LoginPage(driver);
        
        loginPage.login("wrong_email@gmail.com", "Test@12345");
        
        Thread.sleep(2000);
        String errorMsg = loginPage.getErrorMessage();
        Assert.assertTrue(errorMsg != null && !errorMsg.isEmpty(), "Phải có thông báo lỗi về việc đăng nhập thất bại do sai email/password.");
    }

    @Test(description = "TC-LG-006: Đăng nhập thất bại với password sai")
    public void testWrongPasswordLogin() throws InterruptedException {
        driver.get("https://personal-wkrgpp1g.outsystemscloud.com/ReadRune/Login");
        LoginPage loginPage = new LoginPage(driver);
        
        loginPage.login("testuser@gmail.com", "WrongPassword123");
        
        Thread.sleep(2000);
        String errorMsg = loginPage.getErrorMessage();
        Assert.assertTrue(errorMsg != null && !errorMsg.isEmpty(), "Phải có thông báo lỗi do sai mật khẩu.");
    }

    @Test(description = "TC-LG-007: Đăng nhập thất bại khi cả 2 trường trống")
    public void testEmptyFieldsLogin() throws InterruptedException {
        driver.get("https://personal-wkrgpp1g.outsystemscloud.com/ReadRune/Login");
        LoginPage loginPage = new LoginPage(driver);
        
        loginPage.login("", "");
        
        Thread.sleep(2000);
        String errorMsg = loginPage.getErrorMessage();
        Assert.assertNotNull(errorMsg, "Phải hiển thị lỗi khi để trống cả 2 thông tin.");
        Assert.assertFalse(errorMsg.isEmpty());
    }

    @Test(description = "TC-LG-008: Chuyển sang trang Đăng ký")
    public void testNavigationToSignUp() throws InterruptedException {
        driver.get("https://personal-wkrgpp1g.outsystemscloud.com/ReadRune/Login");
        LoginPage loginPage = new LoginPage(driver);
        
        loginPage.clickSignUp();
        
        Thread.sleep(2000);
        Assert.assertTrue(driver.getCurrentUrl().contains("SignUp") || driver.getCurrentUrl().contains("Register"), 
            "URL phải chứa SignUp hoặc Register sau khi click nút Đăng ký");
    }
}
