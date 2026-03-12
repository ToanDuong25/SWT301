package com.example.pages;

import com.example.utils.TestUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * RegisterPage - Page Object cho trang đăng ký tài khoản ReadRune
 *
 * ============================================================
 * HƯỚNG DẪN CẬP NHẬT SELECTOR:
 * 1. Mở trang Register trên Chrome
 * 2. Nhấn F12 → Tab Elements
 * 3. Dùng Ctrl+Shift+C click vào từng ô input, nút bấm
 * 4. Copy id/name/class/xpath và thay vào các By.xxx bên dưới
 * ============================================================
 */
public class RegisterPage extends BasePage {

    // ==================== LOCATORS ====================
    // TODO: Cập nhật selector thực tế từ trang web ReadRune

    // Ô nhập Họ tên / Display Name
    private final By inputName = By.cssSelector("input[id*='Input_Name'], input[id*='Input_FullName'], input[placeholder*='name' i]");

    // Ô nhập Email
    private final By inputEmail = By.cssSelector("input[id*='Input_Email'], input[type='email'], input[placeholder*='email' i]");

    // Ô nhập Username (nếu có)
    private final By inputUsername = By.cssSelector("input[id*='Input_Username'], input[placeholder*='username' i]");

    // Ô nhập Password
    private final By inputPassword = By.cssSelector("input[id*='Input_Password']:not([id*='Confirm']), input[placeholder*='password' i]:first-of-type");

    // Ô nhập Xác nhận Password
    private final By inputConfirmPassword = By.cssSelector("input[id*='Input_ConfirmPassword'], input[id*='Confirm'], input[placeholder*='confirm' i]");

    // Nút Đăng ký
    private final By btnRegister = By.cssSelector("button[type='submit'], .btn-register, .register-button, button[class*='btn']");

    // Link quay về trang Login
    private final By linkLogin = By.cssSelector("a[href*='Login'], a[href*='login'], .login-link");

    // Thông báo lỗi
    private final By errorMessage = By.cssSelector(".feedback-message-error, .error-message, .alert-error, [class*='feedback'][class*='error']");

    // Thông báo thành công
    private final By successMessage = By.cssSelector(".feedback-message-success, .success-message, .alert-success, [class*='feedback'][class*='success']");

    // ==================== CONSTRUCTOR ====================
    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    // ==================== ACTIONS ====================

    /**
     * Mở trang đăng ký
     */
    public RegisterPage open(String registerUrl) {
        driver.get(registerUrl);
        waitForPageLoad();
        return this;
    }

    /**
     * Nhập họ tên
     */
    public RegisterPage enterName(String name) {
        type(inputName, name);
        return this;
    }

    /**
     * Nhập email
     */
    public RegisterPage enterEmail(String email) {
        type(inputEmail, email);
        return this;
    }

    /**
     * Nhập username (nếu có trường này)
     */
    public RegisterPage enterUsername(String username) {
        try {
            type(inputUsername, username);
        } catch (Exception e) {
            // Trường username có thể không tồn tại
            System.out.println("Trường username không tìm thấy, bỏ qua.");
        }
        return this;
    }

    /**
     * Nhập password
     */
    public RegisterPage enterPassword(String password) {
        type(inputPassword, password);
        return this;
    }

    /**
     * Nhập xác nhận password
     */
    public RegisterPage enterConfirmPassword(String confirmPassword) {
        type(inputConfirmPassword, confirmPassword);
        return this;
    }

    /**
     * Click nút Đăng ký
     */
    public RegisterPage clickRegister() {
        click(btnRegister);
        waitForPageLoad();
        return this;
    }

    /**
     * Thực hiện đăng ký đầy đủ
     */
    public RegisterPage register(String name, String email, String password, String confirmPassword) {
        enterName(name);
        enterEmail(email);
        enterPassword(password);
        enterConfirmPassword(confirmPassword);
        clickRegister();
        return this;
    }

    /**
     * Click link quay về trang Login
     */
    public void clickLoginLink() {
        click(linkLogin);
        waitForPageLoad();
    }

    // ==================== VERIFICATIONS ====================

    /**
     * Kiểm tra đăng ký thành công
     */
    public boolean isRegistrationSuccessful() {
        TestUtils.sleep(2000);
        String currentUrl = driver.getCurrentUrl();
        // Sau đăng ký thành công, thường chuyển về trang Login hoặc trang chính
        boolean redirected = currentUrl.contains("Login") || currentUrl.contains("Home")
                || currentUrl.contains("Landing") || !currentUrl.contains("Register");

        boolean hasSuccess = isDisplayed(successMessage);

        return redirected || hasSuccess;
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
     * Kiểm tra trang đăng ký đã load xong
     */
    public boolean isPageLoaded() {
        return isDisplayed(inputEmail) && isDisplayed(inputPassword);
    }
}
