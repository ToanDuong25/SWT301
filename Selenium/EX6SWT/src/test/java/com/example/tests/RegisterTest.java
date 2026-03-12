package com.example.tests;

import com.aventstack.extentreports.Status;
import com.example.pages.RegisterPage;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * RegisterTest - Kiểm thử chức năng Đăng ký tài khoản ReadRune
 *
 * Test Cases:
 * TC-RG-001: Kiểm tra trang Đăng ký load thành công
 * TC-RG-002: Đăng ký thành công với thông tin hợp lệ
 * TC-RG-003: Đăng ký thất bại khi để trống tất cả
 * TC-RG-004: Đăng ký thất bại khi email không hợp lệ
 * TC-RG-005: Đăng ký thất bại khi password quá ngắn
 * TC-RG-006: Đăng ký thất bại khi confirm password không khớp
 * TC-RG-007: Đăng ký thất bại khi email đã tồn tại
 * TC-RG-008: Kiểm tra link quay lại trang Login
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Kiểm thử chức năng Đăng ký - ReadRune")
public class RegisterTest extends BaseTest {

    private RegisterPage registerPage;
    private String registerUrl;

    @BeforeEach
    void initPage() {
        registerPage = new RegisterPage(driver);
        registerUrl = config("register.url");
    }

    // ==================== TEST CASES ====================

    @Test
    @Order(1)
    @DisplayName("TC-RG-001: Kiểm tra trang Đăng ký load thành công")
    void testRegisterPageLoadsSuccessfully() {
        extentTest.log(Status.INFO, "Mở trang Đăng ký: " + registerUrl);
        registerPage.open(registerUrl);

        extentTest.log(Status.INFO, "Kiểm tra trang đã load");
        boolean loaded = registerPage.isPageLoaded();

        if (loaded) {
            extentTest.log(Status.PASS, "✅ Trang Đăng ký load thành công");
        } else {
            extentTest.log(Status.FAIL, "❌ Trang Đăng ký không load được");
        }
        assertTrue(loaded, "Trang Đăng ký phải load thành công với đầy đủ các trường input");
    }

    @Test
    @Order(2)
    @DisplayName("TC-RG-002: Đăng ký thành công với thông tin hợp lệ")
    void testRegisterWithValidInfo() {
        String name = config("register.name");
        // Tạo email unique để tránh trùng
        String email = "testuser_" + System.currentTimeMillis() + "@gmail.com";
        String password = config("register.password");
        String confirmPassword = config("register.confirm.password");

        extentTest.log(Status.INFO, "Mở trang Đăng ký");
        registerPage.open(registerUrl);

        extentTest.log(Status.INFO, "Nhập tên: " + name);
        registerPage.enterName(name);

        extentTest.log(Status.INFO, "Nhập email: " + email);
        registerPage.enterEmail(email);

        extentTest.log(Status.INFO, "Nhập password: ********");
        registerPage.enterPassword(password);

        extentTest.log(Status.INFO, "Nhập xác nhận password: ********");
        registerPage.enterConfirmPassword(confirmPassword);

        extentTest.log(Status.INFO, "Click nút Đăng ký");
        registerPage.clickRegister();

        boolean success = registerPage.isRegistrationSuccessful();
        if (success) {
            extentTest.log(Status.PASS, "✅ Đăng ký thành công");
        } else {
            extentTest.log(Status.FAIL, "❌ Đăng ký thất bại dù thông tin hợp lệ");
        }
        assertTrue(success, "Đăng ký với thông tin hợp lệ phải thành công");
    }

    @Test
    @Order(3)
    @DisplayName("TC-RG-003: Đăng ký thất bại khi để trống tất cả các trường")
    void testRegisterWithAllFieldsEmpty() {
        extentTest.log(Status.INFO, "Mở trang Đăng ký");
        registerPage.open(registerUrl);

        extentTest.log(Status.INFO, "Để trống tất cả các trường");
        extentTest.log(Status.INFO, "Click nút Đăng ký");
        registerPage.clickRegister();

        boolean hasError = registerPage.isErrorMessageDisplayed() || !registerPage.isRegistrationSuccessful();
        if (hasError) {
            extentTest.log(Status.PASS, "✅ Hiển thị lỗi khi tất cả trường trống");
        } else {
            extentTest.log(Status.FAIL, "❌ Không hiển thị lỗi khi tất cả trường trống");
        }
        assertTrue(hasError, "Phải hiển thị lỗi khi tất cả các trường đều trống");
    }

    @Test
    @Order(4)
    @DisplayName("TC-RG-004: Đăng ký thất bại với email không hợp lệ")
    void testRegisterWithInvalidEmail() {
        extentTest.log(Status.INFO, "Mở trang Đăng ký");
        registerPage.open(registerUrl);

        registerPage.enterName("Test User");
        extentTest.log(Status.INFO, "Nhập email không hợp lệ: invalid-email");
        registerPage.enterEmail("invalid-email");
        registerPage.enterPassword("Test@12345");
        registerPage.enterConfirmPassword("Test@12345");

        extentTest.log(Status.INFO, "Click nút Đăng ký");
        registerPage.clickRegister();

        boolean hasError = registerPage.isErrorMessageDisplayed() || !registerPage.isRegistrationSuccessful();
        if (hasError) {
            extentTest.log(Status.PASS, "✅ Hiển thị lỗi khi email không hợp lệ");
        } else {
            extentTest.log(Status.FAIL, "❌ Không hiển thị lỗi khi email không hợp lệ");
        }
        assertTrue(hasError, "Phải hiển thị lỗi khi email không đúng định dạng");
    }

    @Test
    @Order(5)
    @DisplayName("TC-RG-005: Đăng ký thất bại với password quá ngắn")
    void testRegisterWithShortPassword() {
        extentTest.log(Status.INFO, "Mở trang Đăng ký");
        registerPage.open(registerUrl);

        registerPage.enterName("Test User");
        registerPage.enterEmail("test_short_pw@gmail.com");

        extentTest.log(Status.INFO, "Nhập password quá ngắn: 123");
        registerPage.enterPassword("123");
        registerPage.enterConfirmPassword("123");

        extentTest.log(Status.INFO, "Click nút Đăng ký");
        registerPage.clickRegister();

        boolean hasError = registerPage.isErrorMessageDisplayed() || !registerPage.isRegistrationSuccessful();
        if (hasError) {
            extentTest.log(Status.PASS, "✅ Hiển thị lỗi khi password quá ngắn");
        } else {
            extentTest.log(Status.FAIL, "❌ Không hiển thị lỗi khi password quá ngắn");
        }
        assertTrue(hasError, "Phải hiển thị lỗi khi password quá ngắn");
    }

    @Test
    @Order(6)
    @DisplayName("TC-RG-006: Đăng ký thất bại khi xác nhận password không khớp")
    void testRegisterWithMismatchedPasswords() {
        extentTest.log(Status.INFO, "Mở trang Đăng ký");
        registerPage.open(registerUrl);

        registerPage.enterName("Test User");
        registerPage.enterEmail("test_mismatch@gmail.com");

        extentTest.log(Status.INFO, "Nhập password: Test@12345");
        registerPage.enterPassword("Test@12345");

        extentTest.log(Status.INFO, "Nhập confirm password khác: WrongConfirm99");
        registerPage.enterConfirmPassword("WrongConfirm99");

        extentTest.log(Status.INFO, "Click nút Đăng ký");
        registerPage.clickRegister();

        boolean hasError = registerPage.isErrorMessageDisplayed() || !registerPage.isRegistrationSuccessful();
        if (hasError) {
            extentTest.log(Status.PASS, "✅ Hiển thị lỗi khi confirm password không khớp");
        } else {
            extentTest.log(Status.FAIL, "❌ Không hiển thị lỗi khi confirm password không khớp");
        }
        assertTrue(hasError, "Phải hiển thị lỗi khi xác nhận password không khớp");
    }

    @Test
    @Order(7)
    @DisplayName("TC-RG-007: Đăng ký thất bại khi email đã tồn tại")
    void testRegisterWithExistingEmail() {
        extentTest.log(Status.INFO, "Mở trang Đăng ký");
        registerPage.open(registerUrl);

        registerPage.enterName("Existing User");

        // Sử dụng email đã tồn tại (email tài khoản test)
        String existingEmail = config("valid.username");
        extentTest.log(Status.INFO, "Nhập email đã tồn tại: " + existingEmail);
        registerPage.enterEmail(existingEmail);
        registerPage.enterPassword("Test@12345");
        registerPage.enterConfirmPassword("Test@12345");

        extentTest.log(Status.INFO, "Click nút Đăng ký");
        registerPage.clickRegister();

        boolean hasError = registerPage.isErrorMessageDisplayed() || !registerPage.isRegistrationSuccessful();
        if (hasError) {
            extentTest.log(Status.PASS, "✅ Hiển thị lỗi khi email đã tồn tại");
        } else {
            extentTest.log(Status.FAIL, "❌ Không hiển thị lỗi khi email đã tồn tại");
        }
        assertTrue(hasError, "Phải hiển thị lỗi khi email đã được đăng ký");
    }

    @Test
    @Order(8)
    @DisplayName("TC-RG-008: Kiểm tra link quay lại trang Login")
    void testNavigateToLoginPage() {
        extentTest.log(Status.INFO, "Mở trang Đăng ký");
        registerPage.open(registerUrl);

        extentTest.log(Status.INFO, "Click link quay lại Login");
        registerPage.clickLoginLink();

        String currentUrl = driver.getCurrentUrl();
        boolean navigated = currentUrl.contains("Login") || currentUrl.contains("login");

        if (navigated) {
            extentTest.log(Status.PASS, "✅ Quay lại trang Login thành công: " + currentUrl);
        } else {
            extentTest.log(Status.FAIL, "❌ Không quay lại được trang Login. URL hiện tại: " + currentUrl);
        }
        assertTrue(navigated, "Phải chuyển được về trang Login");
    }
}
