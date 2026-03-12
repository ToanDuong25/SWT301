package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class RegistrationPage extends BasePage {

    public RegistrationPage(WebDriver driver) {
        super(driver);
    }

    // ===== LOCATORS =====
    // Text fields
    private final By firstNameField = By.id("firstName");
    private final By lastNameField = By.id("lastName");
    private final By emailField = By.id("userEmail");
    private final By mobileField = By.id("userNumber");
    private final By subjectsField = By.id("subjectsInput");

    // Radio buttons (sử dụng input type radio)
    private final By maleRadio = By.xpath("//input[@id='gender-radio-1']/..");
    private final By femaleRadio = By.xpath("//input[@id='gender-radio-2']/..");
    private final By otherRadio = By.xpath("//input[@id='gender-radio-3']/..");

    // Checkboxes for hobbies
    private final By sportsCheckbox = By.xpath("//input[@id='hobbies-checkbox-1']/..");
    private final By readingCheckbox = By.xpath("//input[@id='hobbies-checkbox-2']/..");
    private final By musicCheckbox = By.xpath("//input[@id='hobbies-checkbox-3']/..");

    // File upload
    private final By uploadPictureBtn = By.id("uploadPicture");

    // Dropdown for state and city (sử dụng React Select)
    private final By stateDropdown = By.id("react-select-3-input");
    private final By cityDropdown = By.id("react-select-4-input");

    // Date of birth
    private final By dateOfBirthInput = By.id("dateOfBirthInput");

    // Submit button
    private final By submitBtn = By.id("submit");

    // Success message
    private final By successMessage = By.id("example-modal-sizes-title-lg");

    // ===== ACTIONS =====

    /**
     * Điều hướng đến trang form đăng ký
     */
    public void navigate() {
        navigateTo("https://demoqa.com/automation-practice-form");
    }

    /**
     * Nhập tên
     */
    public void enterFirstName(String firstName) {
        type(firstNameField, firstName);
    }

    /**
     * Nhập họ
     */
    public void enterLastName(String lastName) {
        type(lastNameField, lastName);
    }

    /**
     * Nhập email
     */
    public void enterEmail(String email) {
        type(emailField, email);
    }

    /**
     * Nhập số điện thoại
     */
    public void enterMobileNumber(String mobile) {
        type(mobileField, mobile);
    }

    /**
     * Chọn giới tính
     */
    public void selectGender(String gender) {
        By genderLocator;
        if (gender.equalsIgnoreCase("Male")) {
            genderLocator = maleRadio;
        } else if (gender.equalsIgnoreCase("Female")) {
            genderLocator = femaleRadio;
        } else {
            genderLocator = otherRadio;
        }
        click(genderLocator);
    }

    /**
     * Chọn sở thích (hobbies)
     */
    public void selectHobby(String hobby) {
        By hobbyLocator;
        if (hobby.equalsIgnoreCase("Sports")) {
            hobbyLocator = sportsCheckbox;
        } else if (hobby.equalsIgnoreCase("Reading")) {
            hobbyLocator = readingCheckbox;
        } else {
            hobbyLocator = musicCheckbox;
        }
        click(hobbyLocator);
    }

    /**
     * Nhập các sở thích (dùng cho trường subject)
     */
    public void enterSubjects(String subjects) {
        scrollToElement(subjectsField);
        type(subjectsField, subjects);
        // Chọn option đầu tiên từ dropdown suggestion
        waitForVisibility(By.xpath("//div[@class='subjects-auto-complete__option']")).click();
    }

    /**
     * Upload ảnh đại diện
     */
    public void uploadPicture(String filePath) {
        uploadFile(uploadPictureBtn, filePath);
    }

    /**
     * Chọn ngày sinh (sử dụng date picker)
     */
    public void selectDateOfBirth(String day, String month, String year) {
        scrollToElement(dateOfBirthInput);
        click(dateOfBirthInput);

        try {
            // Chọn năm
            By yearDropdown = By.className("react-datepicker__year-select");
            selectByValue(yearDropdown, year);

            // Chọn tháng
            By monthDropdown = By.className("react-datepicker__month-select");
            selectByValue(monthDropdown, month);

            // Chọn ngày
            By dayButton = By.xpath("//div[contains(@class, 'react-datepicker__day') and text()='" + day + "']");
            click(dayButton);
        } catch (Exception e) {
            System.out.println("Date selection failed: " + e.getMessage());
        }
    }

    /**
     * Chọn trạng thái (State)
     */
    public void selectState(String state) {
        scrollToElement(stateDropdown);
        click(stateDropdown);
        By stateOption = By.xpath("//div[contains(text(), '" + state + "')]");
        click(stateOption);
    }

    /**
     * Chọn thành phố (City)
     */
    public void selectCity(String city) {
        scrollToElement(cityDropdown);
        click(cityDropdown);
        By cityOption = By.xpath("//div[contains(text(), '" + city + "')]");
        click(cityOption);
    }

    /**
     * Gửi form
     */
    public void submitForm() {
        // Scroll xuống nhiều lần để button hoàn toàn visible
        scrollToElement(submitBtn);
        try {
            Thread.sleep(500);  // Chờ sau scroll
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        // Dùng JavaScript click nếu normal click không hoạt động
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();",
                waitForPresence(submitBtn));
        } catch (Exception e) {
            click(submitBtn);
        }
    }

    /**
     * Kiểm tra thông báo thành công
     */
    public boolean isSuccessMessageDisplayed() {
        return isElementVisible(successMessage);
    }

    /**
     * Lấy text từ thông báo thành công
     */
    public String getSuccessMessageText() {
        return getText(successMessage);
    }
}
