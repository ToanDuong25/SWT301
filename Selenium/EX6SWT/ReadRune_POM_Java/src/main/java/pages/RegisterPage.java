package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegisterPage extends BasePage {

    private By nameInput = By.cssSelector("input[id*='Name'], #Input_Name");
    private By emailInput = By.cssSelector("input[type='email'], #Input_Email");
    private By passwordInput = By.cssSelector("input[id*='Input_Password'], #Input_Password");
    private By confirmPasswordInput = By.cssSelector("input[id*='Password'], #Input_ConfirmPassword"); // Outsystems may use different IDs
    private By registerButton = By.cssSelector("button[type='submit'], button.btn-primary");
    
    // Outsystems dynamic error classes
    private By validationMessage = By.cssSelector("span.validation-message");
    private By feedbackMessage = By.cssSelector(".feedback-message-text, .feedback-message-error, .feedback-message-warning");
    
    // Fallback locator to handle encoding issues
    private By loginLink = By.xpath("//a[contains(@class, 'margin-left-s') or contains(@href, 'Login')]");

    public RegisterPage(WebDriver driver) {
        super(driver);
    }
    
    public void enterName(String name) {
        enterText(nameInput, name);
    }
    
    public void enterEmail(String email) {
        enterText(emailInput, email);
    }
    
    public void enterPassword(String password) {
        enterText(passwordInput, password);
    }
    
    public void enterConfirmPassword(String confirmPassword) {
        // Find all password inputs on the page. The last one is usually the confirm password in OutSystems if there are 2.
        try {
            java.util.List<org.openqa.selenium.WebElement> pwdInputs = driver.findElements(By.cssSelector("input[type='password']"));
            if(pwdInputs.size() > 1) {
                pwdInputs.get(1).clear();
                pwdInputs.get(1).sendKeys(confirmPassword);
                return;
            }
        } catch (Exception e) {}
        
        enterText(confirmPasswordInput, confirmPassword);
    }
    
    public void clickRegister() {
        click(registerButton);
    }

    public void clickLoginLink() {
        click(loginLink);
    }

    // Tương đương với yêu cầu test nhập đầy đủ (theo CSV TC-RG)
    public void registerUser(String name, String email, String password, String confirmPassword) {
        if (!name.isEmpty()) enterName(name);
        if (!email.isEmpty()) enterEmail(email);
        if (!password.isEmpty()) enterPassword(password);
        if (!confirmPassword.isEmpty()) enterConfirmPassword(confirmPassword);
        clickRegister();
    }
    
    public String getErrorMessage() {
        if(BasePage.isElementDisplayed(driver, feedbackMessage, 2)) {
             return getText(feedbackMessage);
        } else if (BasePage.isElementDisplayed(driver, validationMessage, 2)) {
             return getText(validationMessage);
        }
        return "";
    }
    
    public boolean isPageLoaded() {
        return BasePage.isElementDisplayed(driver, nameInput, 5) && 
               BasePage.isElementDisplayed(driver, emailInput, 5) && 
               BasePage.isElementDisplayed(driver, passwordInput, 5) &&
               BasePage.isElementDisplayed(driver, registerButton, 5);
    }
}
