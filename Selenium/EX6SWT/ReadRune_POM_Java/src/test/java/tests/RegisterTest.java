package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.RegisterPage;

public class RegisterTest extends BaseTest {

    @Test(description = "TC-RG-001: Trang Đăng ký load thành công")
    public void testRegisterLoad() throws InterruptedException {
        driver.get("https://personal-wkrgpp1g.outsystemscloud.com/ReadRune/SignUp");
        RegisterPage registerPage = new RegisterPage(driver);
        
        Assert.assertTrue(registerPage.isPageLoaded(), "Trang Register không load đầy đủ hoặc thiếu các trường thông tin username, email, password, nút register.");
    }

    @Test(description = "TC-RG-002: Đăng ký thành công với thông tin hợp lệ")
    public void testValidRegistration() throws InterruptedException {
        driver.get("https://personal-wkrgpp1g.outsystemscloud.com/ReadRune/SignUp");
        RegisterPage registerPage = new RegisterPage(driver);
        
        // Random email to ensure it's unique
        String uniqueEmail = "newuser_" + (int)(Math.random() * 10000) + "@gmail.com";
        registerPage.registerUser("Test User", uniqueEmail, "Test@12345", "Test@12345");
        
        // Wait substantially for OutSystems free tier to write to DB and respond with a redirect
        Thread.sleep(8000);
        
        boolean isRedirected = !driver.getCurrentUrl().contains("SignUp");
        Assert.assertTrue(isRedirected, "Người dùng phải được chuyển hướng (redirect) sau khi đăng ký thành công.");
    }

    @Test(description = "TC-RG-003: Đăng ký thất bại khi tất cả trường trống")
    public void testEmptyFieldsRegistration() throws InterruptedException {
        driver.get("https://personal-wkrgpp1g.outsystemscloud.com/ReadRune/SignUp");
        RegisterPage registerPage = new RegisterPage(driver);
        
        registerPage.registerUser("", "", "", "");
        
        Thread.sleep(2000);
        String errorMsg = registerPage.getErrorMessage();
        Assert.assertNotNull(errorMsg, "Phải hiển thị validation message khi để trống tất cả form đăng ký.");
        Assert.assertFalse(errorMsg.isEmpty());
    }

    @Test(description = "TC-RG-004: Đăng ký thất bại với email không hợp lệ")
    public void testInvalidEmailFormatRegistration() throws InterruptedException {
        driver.get("https://personal-wkrgpp1g.outsystemscloud.com/ReadRune/SignUp");
        RegisterPage registerPage = new RegisterPage(driver);
        
        registerPage.registerUser("Test User", "invalid-email", "Test@12345", "Test@12345");
        
        Thread.sleep(2000);
        String errorMsg = registerPage.getErrorMessage();
        Assert.assertNotNull(errorMsg, "Phải hiển thị validation message về format email bị sai.");
        Assert.assertFalse(errorMsg.isEmpty());
    }

    @Test(description = "TC-RG-005: Đăng ký thất bại với password quá ngắn")
    public void testShortPasswordRegistration() throws InterruptedException {
        driver.get("https://personal-wkrgpp1g.outsystemscloud.com/ReadRune/SignUp");
        RegisterPage registerPage = new RegisterPage(driver);
        
        registerPage.registerUser("Test User", "test@gmail.com", "123", "123");
        
        Thread.sleep(2000);
        String errorMsg = registerPage.getErrorMessage();
        Assert.assertNotNull(errorMsg, "Phải hiển thị alert/error message về password quá ngắn.");
        Assert.assertFalse(errorMsg.isEmpty());
    }

    @Test(description = "TC-RG-006: Đăng ký thất bại khi confirm password không khớp")
    public void testPasswordMismatchRegistration() throws InterruptedException {
        driver.get("https://personal-wkrgpp1g.outsystemscloud.com/ReadRune/SignUp");
        RegisterPage registerPage = new RegisterPage(driver);
        
        registerPage.registerUser("Test User", "test@gmail.com", "Test@12345", "WrongConfirm99");
        
        Thread.sleep(2000);
        String errorMsg = registerPage.getErrorMessage();
        Assert.assertTrue(errorMsg != null && !errorMsg.isEmpty(), "Phải hiển thị lỗi do xác nhận lại mật khẩu không chính xác.");
    }

    @Test(description = "TC-RG-007: Đăng ký thất bại khi email đã tồn tại")
    public void testDuplicateEmailRegistration() throws InterruptedException {
        driver.get("https://personal-wkrgpp1g.outsystemscloud.com/ReadRune/SignUp");
        RegisterPage registerPage = new RegisterPage(driver);
        
        // This email should already exist if the DB has any data from old tests
        registerPage.registerUser("Test User", "testuser@gmail.com", "Test@12345", "Test@12345");
        
        Thread.sleep(3000);
        String errorMsg = registerPage.getErrorMessage();
        Assert.assertTrue(errorMsg != null && !errorMsg.isEmpty(), "Hệ thống phải báo lỗi trùng email đã tồn tại.");
    }

    @Test(description = "TC-RG-008: Chuyển về trang Login")
    public void testNavigationToLogin() throws InterruptedException {
        driver.get("https://personal-wkrgpp1g.outsystemscloud.com/ReadRune/SignUp");
        RegisterPage registerPage = new RegisterPage(driver);
        
        registerPage.clickLoginLink();
        
        Thread.sleep(2000);
        Assert.assertTrue(driver.getCurrentUrl().contains("Login"), 
            "URL phải chứa /Login sau khi click nút Chuyển về trang Đăng nhập");
    }
}
