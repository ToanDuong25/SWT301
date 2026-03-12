package tests;

import org.junit.jupiter.api.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.RegistrationPage;

import java.time.Duration;

/**
 * BÀI TẬP 5: Kiểm thử Form Đăng Ký (Registration Form)
 * Link: https://demoqa.com/automation-practice-form
 *
 * Yêu cầu: Tạo các test case để kiểm thử form đăng ký với:
 * - Tên (First Name)
 * - Họ (Last Name)
 * - Email
 * - Số điện thoại (Mobile Number)
 * - Giới tính (Gender)
 * - Sở thích (Hobbies)
 * - Trạng thái (State)
 * - Thành phố (City)
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("BÀITẬP 5: Kiểm thử Form Đăng Ký DemoQA - Registration Form Tests")
public class RegistrationTestV2 extends BaseTest {
    private RegistrationPage registrationPage;
    private WebDriverWait wait;

    @BeforeEach
    void setUp() {
        registrationPage = new RegistrationPage(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Test 1: Điều hướng đến trang form
     */
    @Test
    @Order(1)
    @DisplayName("Test 1: Truy cập trang form đăng ký thành công")
    void testNavigateToRegistrationForm() {
        registrationPage.navigate();
        // Kiểm tra trang đã load
        System.out.println("✓ Đã truy cập trang form đăng ký");
    }

    /**
     * Test 2: Điền thông tin tên
     */
    @Test
    @Order(2)
    @DisplayName("Test 2: Điền thông tin Tên và Họ")
    void testFillFirstNameAndLastName() {
        registrationPage.navigate();
        registrationPage.enterFirstName("Nguyễn");
        registrationPage.enterLastName("Văn A");
        System.out.println("✓ Đã điền tên: Nguyễn Văn A");
    }

    /**
     * Test 3: Điền email
     */
    @Test
    @Order(3)
    @DisplayName("Test 3: Điền thông tin Email")
    void testFillEmail() {
        registrationPage.navigate();
        registrationPage.enterEmail("test@example.com");
        System.out.println("✓ Đã điền email: test@example.com");
    }

    /**
     * Test 4: Điền số điện thoại
     */
    @Test
    @Order(4)
    @DisplayName("Test 4: Điền thông tin Số điện thoại")
    void testFillMobileNumber() {
        registrationPage.navigate();
        registrationPage.enterMobileNumber("0123456789");
        System.out.println("✓ Đã điền số điện thoại: 0123456789");
    }

    /**
     * Test 5: Chọn giới tính
     */
    @Test
    @Order(5)
    @DisplayName("Test 5: Chọn giới tính")
    void testSelectGender() {
        registrationPage.navigate();
        registrationPage.selectGender("Male");
        System.out.println("✓ Đã chọn giới tính: Male");
    }

    /**
     * Test 6: Chọn sở thích
     */
    @Test
    @Order(6)
    @DisplayName("Test 6: Chọn sở thích")
    void testSelectHobbies() {
        registrationPage.navigate();
        registrationPage.selectHobby("Sports");
        registrationPage.selectHobby("Reading");
        System.out.println("✓ Đã chọn sở thích: Sports, Reading");
    }

    /**
     * Test 7: Gửi form (đơn giản)
     */
    @Test
    @Order(7)
    @DisplayName("Test 7: Gửi form")
    void testSubmitForm() {
        registrationPage.navigate();

        // Điền thông tin cơ bản
        registrationPage.enterFirstName("Test");
        registrationPage.enterLastName("User");
        registrationPage.enterEmail("test@test.com");
        registrationPage.enterMobileNumber("1234567890");
        registrationPage.selectGender("Male");

        // Gửi form
        registrationPage.submitForm();
        System.out.println("✓ Đã gửi form");
    }

    /**
     * Test 8: Kiểm tra thông báo thành công
     */
    @Test
    @Order(8)
    @DisplayName("Test 8: Kiểm tra thông báo thành công")
    void testSuccessMessage() {
        registrationPage.navigate();

        registrationPage.enterFirstName("Success");
        registrationPage.enterLastName("Test");
        registrationPage.enterEmail("success@test.com");
        registrationPage.enterMobileNumber("9999999999");
        registrationPage.selectGender("Female");

        registrationPage.submitForm();

        if (registrationPage.isSuccessMessageDisplayed()) {
            System.out.println("✓ Thông báo thành công được hiển thị");
        } else {
            System.out.println("⚠ Thông báo thành công không hiển thị");
        }
    }
}
