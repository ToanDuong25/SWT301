package com.example.pages;

import com.example.utils.TestUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * LoginPage - Page Object cho trang đăng nhập ReadRune
 *
 * ============================================================
 * HƯỚNG DẪN CẬP NHẬT SELECTOR:
 * 1. Mở trang Login trên Chrome
 * 2. Nhấn F12 → Tab Elements
 * 3. Dùng Ctrl+Shift+C click vào từng ô input, nút bấm
 * 4. Copy id/name/class/xpath và thay vào các By.xxx bên dưới
 *
 * Ví dụ OutSystems thường sinh id dạng:
 *   - Input: id chứa "Input_Username" hoặc "Input_Email"
 *   - Button: class chứa "btn" hoặc "login-btn"
 * ============================================================
 */
public class LoginPage extends BasePage {

    // ==================== LOCATORS ====================
    // TODO: Cập nhật selector thực tế từ trang web ReadRune

    // Ô nhập Email/Username
    private final By inputEmail = By.cssSelector("input[id*='Input_UsernameVal']");

    // Ô nhập Password
    private final By inputPassword = By.cssSelector("input[id*='Input_PasswordVal']");

    // Nút Login
    private final By btnLogin = By.cssSelector("button[class*='btn'][type='submit'], .login-button, .btn-login");

    // Link "Đăng ký" / "Sign Up" trên trang login
    private final By linkRegister = By.cssSelector("a[href*='Register'], a[href*='SignUp'], .register-link");

    // Thông báo lỗi đăng nhập
    private final By errorMessage = By.cssSelector(".feedback-message-error, .error-message, .alert-error, [class*='feedback'][class*='error']");

    // Element xác nhận đã đăng nhập thành công (xuất hiện sau login)
    private final By successIndicator = By.cssSelector(".user-info, .logged-in, .welcome-message, [class*='UserInfo'], .avatar, .user-menu");

    // ==================== CONSTRUCTOR ====================
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // ==================== ACTIONS ====================

    /**
     * Mở trang Login
     */
    public LoginPage open(String loginUrl) {
        driver.get(loginUrl);
        waitForPageLoad();
        return this;
    }

    /**
     * Nhập email/username
     */
    public LoginPage enterEmail(String email) {
        type(inputEmail, email);
        return this;
    }

    /**
     * Nhập password
     */
    public LoginPage enterPassword(String password) {
        type(inputPassword, password);
        return this;
    }

    /**
     * Click nút Login
     */
    public LoginPage clickLogin() {
        click(btnLogin);
        waitForPageLoad();
        return this;
    }

    /**
     * Thực hiện đăng nhập đầy đủ
     */
    public LoginPage login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLogin();
        return this;
    }

    /**
     * Click link chuyển sang trang Đăng ký
     */
    public void clickRegisterLink() {
        click(linkRegister);
        waitForPageLoad();
    }

    // ==================== VERIFICATIONS ====================

    /**
     * Kiểm tra đăng nhập thành công
     * (dựa trên URL chuyển hướng hoặc element hiển thị)
     */
    public boolean isLoginSuccessful() {
        TestUtils.sleep(2000); // Chờ redirect
        // Kiểm tra URL không còn ở trang Login
        String currentUrl = driver.getCurrentUrl();
        boolean urlChanged = !currentUrl.contains("Login") && !currentUrl.contains("login");

        // Hoặc kiểm tra element thành công
        boolean hasSuccessElement = isDisplayed(successIndicator);

        return urlChanged || hasSuccessElement;
    }

    /**
     * Kiểm tra có thông báo lỗi không
     */
    public boolean isErrorMessageDisplayed() {
        TestUtils.sleep(1000);
        return isDisplayed(errorMessage);
    }

    /**
     * Lấy nội dung thông báo lỗi
     */
    public String getErrorMessage() {
        try {
            return getText(errorMessage);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Kiểm tra trang Login đã load xong
     */
    public boolean isPageLoaded() {
        return isDisplayed(inputEmail) && isDisplayed(inputPassword);
    }
}
