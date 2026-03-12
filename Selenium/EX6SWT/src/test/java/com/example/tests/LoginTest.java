package com.example.tests;

import com.aventstack.extentreports.Status;
import com.example.pages.LoginPage;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * LoginTest - Kiểm thử chức năng Đăng nhập ReadRune
 *
 * Test Cases:
 * TC-LG-001: Đăng nhập thành công với thông tin hợp lệ
 * TC-LG-002: Đăng nhập thất bại với email rỗng
 * TC-LG-003: Đăng nhập thất bại với password rỗng
 * TC-LG-004: Đăng nhập thất bại với email sai
 * TC-LG-005: Đăng nhập thất bại với password sai
 * TC-LG-006: Đăng nhập thất bại với cả email và password rỗng
 * TC-LG-007: Kiểm tra trang Login load thành công
 * TC-LG-008: Kiểm tra link chuyển sang trang Đăng ký
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Kiểm thử chức năng Đăng nhập - ReadRune")
public class LoginTest extends BaseTest {

    private LoginPage loginPage;
    private String loginUrl;

    @BeforeEach
    void initPage() {
        loginPage = new LoginPage(driver);
        loginUrl = config("login.url");
    }

    // ==================== TEST CASES ====================

    @Test
    @Order(1)
    @DisplayName("TC-LG-001: Kiểm tra trang Login load thành công")
    void testLoginPageLoadsSuccessfully() {
        extentTest.log(Status.INFO, "Mở trang Login: " + loginUrl);
        loginPage.open(loginUrl);

        extentTest.log(Status.INFO, "Kiểm tra trang đã load");
        boolean loaded = loginPage.isPageLoaded();

        if (loaded) {
            extentTest.log(Status.PASS, "✅ Trang Login load thành công");
        } else {
            extentTest.log(Status.FAIL, "❌ Trang Login không load được");
        }
        assertTrue(loaded, "Trang Login phải load thành công với đầy đủ các trường input");
    }

    @Test
    @Order(2)
    @DisplayName("TC-LG-002: Đăng nhập thành công với thông tin hợp lệ")
    void testLoginWithValidCredentials() {
        String email = config("valid.username");
        String password = config("valid.password");

        extentTest.log(Status.INFO, "Mở trang Login");
        loginPage.open(loginUrl);

        extentTest.log(Status.INFO, "Nhập email: " + email);
        loginPage.enterEmail(email);

        extentTest.log(Status.INFO, "Nhập password: ********");
        loginPage.enterPassword(password);

        extentTest.log(Status.INFO, "Click nút Login");
        loginPage.clickLogin();

        boolean success = loginPage.isLoginSuccessful();
        if (success) {
            extentTest.log(Status.PASS, "✅ Đăng nhập thành công, chuyển hướng đến trang chính");
        } else {
            extentTest.log(Status.FAIL, "❌ Đăng nhập thất bại dù thông tin hợp lệ");
        }
        assertTrue(success, "Đăng nhập với thông tin hợp lệ phải thành công");
    }

    @Test
    @Order(3)
    @DisplayName("TC-LG-003: Đăng nhập thất bại khi để trống email")
    void testLoginWithEmptyEmail() {
        extentTest.log(Status.INFO, "Mở trang Login");
        loginPage.open(loginUrl);

        extentTest.log(Status.INFO, "Để trống email, nhập password");
        loginPage.enterPassword("Test@12345");

        extentTest.log(Status.INFO, "Click nút Login");
        loginPage.clickLogin();

        boolean hasError = loginPage.isErrorMessageDisplayed() || !loginPage.isLoginSuccessful();
        if (hasError) {
            extentTest.log(Status.PASS, "✅ Hiển thị lỗi khi email trống");
        } else {
            extentTest.log(Status.FAIL, "❌ Không hiển thị lỗi khi email trống");
        }
        assertTrue(hasError, "Phải hiển thị lỗi khi email trống");
    }

    @Test
    @Order(4)
    @DisplayName("TC-LG-004: Đăng nhập thất bại khi để trống password")
    void testLoginWithEmptyPassword() {
        extentTest.log(Status.INFO, "Mở trang Login");
        loginPage.open(loginUrl);

        extentTest.log(Status.INFO, "Nhập email, để trống password");
        loginPage.enterEmail(config("valid.username"));

        extentTest.log(Status.INFO, "Click nút Login");
        loginPage.clickLogin();

        boolean hasError = loginPage.isErrorMessageDisplayed() || !loginPage.isLoginSuccessful();
        if (hasError) {
            extentTest.log(Status.PASS, "✅ Hiển thị lỗi khi password trống");
        } else {
            extentTest.log(Status.FAIL, "❌ Không hiển thị lỗi khi password trống");
        }
        assertTrue(hasError, "Phải hiển thị lỗi khi password trống");
    }

    @Test
    @Order(5)
    @DisplayName("TC-LG-005: Đăng nhập thất bại với email sai")
    void testLoginWithInvalidEmail() {
        extentTest.log(Status.INFO, "Mở trang Login");
        loginPage.open(loginUrl);

        extentTest.log(Status.INFO, "Nhập email sai");
        loginPage.enterEmail("wrong_email@gmail.com");

        extentTest.log(Status.INFO, "Nhập password hợp lệ");
        loginPage.enterPassword(config("valid.password"));

        extentTest.log(Status.INFO, "Click nút Login");
        loginPage.clickLogin();

        boolean loginFailed = loginPage.isErrorMessageDisplayed() || !loginPage.isLoginSuccessful();
        if (loginFailed) {
            extentTest.log(Status.PASS, "✅ Đăng nhập thất bại với email sai");
        } else {
            extentTest.log(Status.FAIL, "❌ Không hiển thị lỗi khi email sai");
        }
        assertTrue(loginFailed, "Đăng nhập phải thất bại với email không tồn tại");
    }

    @Test
    @Order(6)
    @DisplayName("TC-LG-006: Đăng nhập thất bại với password sai")
    void testLoginWithInvalidPassword() {
        extentTest.log(Status.INFO, "Mở trang Login");
        loginPage.open(loginUrl);

        extentTest.log(Status.INFO, "Nhập email hợp lệ");
        loginPage.enterEmail(config("valid.username"));

        extentTest.log(Status.INFO, "Nhập password sai");
        loginPage.enterPassword("WrongPassword123");

        extentTest.log(Status.INFO, "Click nút Login");
        loginPage.clickLogin();

        boolean loginFailed = loginPage.isErrorMessageDisplayed() || !loginPage.isLoginSuccessful();
        if (loginFailed) {
            extentTest.log(Status.PASS, "✅ Đăng nhập thất bại với password sai");
        } else {
            extentTest.log(Status.FAIL, "❌ Không hiển thị lỗi khi password sai");
        }
        assertTrue(loginFailed, "Đăng nhập phải thất bại với password sai");
    }

    @Test
    @Order(7)
    @DisplayName("TC-LG-007: Đăng nhập thất bại khi cả email và password đều trống")
    void testLoginWithAllFieldsEmpty() {
        extentTest.log(Status.INFO, "Mở trang Login");
        loginPage.open(loginUrl);

        extentTest.log(Status.INFO, "Để trống cả email và password");
        extentTest.log(Status.INFO, "Click nút Login");
        loginPage.clickLogin();

        boolean hasError = loginPage.isErrorMessageDisplayed() || !loginPage.isLoginSuccessful();
        if (hasError) {
            extentTest.log(Status.PASS, "✅ Hiển thị lỗi khi cả 2 trường trống");
        } else {
            extentTest.log(Status.FAIL, "❌ Không hiển thị lỗi khi cả 2 trường trống");
        }
        assertTrue(hasError, "Phải hiển thị lỗi khi cả email và password đều trống");
    }

    @Test
    @Order(8)
    @DisplayName("TC-LG-008: Kiểm tra link chuyển sang trang Đăng ký")
    void testNavigateToRegisterPage() {
        extentTest.log(Status.INFO, "Mở trang Login");
        loginPage.open(loginUrl);

        extentTest.log(Status.INFO, "Click link Đăng ký");
        loginPage.clickRegisterLink();

        String currentUrl = driver.getCurrentUrl();
        boolean navigated = currentUrl.contains("Register") || currentUrl.contains("SignUp")
                || currentUrl.contains("register") || currentUrl.contains("signup");

        if (navigated) {
            extentTest.log(Status.PASS, "✅ Chuyển sang trang Đăng ký thành công: " + currentUrl);
        } else {
            extentTest.log(Status.FAIL, "❌ Không chuyển được sang trang Đăng ký. URL hiện tại: " + currentUrl);
        }
        assertTrue(navigated, "Phải chuyển được sang trang Đăng ký");
    }
}
