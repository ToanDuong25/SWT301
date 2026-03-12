package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    // Actual OutSystems CSS selectors from live page inspection
    private By usernameInput = By.cssSelector("input[type='email'], #Input_UsernameVal");
    private By passwordInput = By.cssSelector("input[type='password'], #Input_PasswordVal");
    private By loginButton = By.cssSelector("button[type='submit'], button.btn-primary.OSFillParent");
    
    // Outsystems dynamic error classes
    private By validationMessage = By.cssSelector("span.validation-message");
    private By feedbackMessage = By.cssSelector(".feedback-message-text, .feedback-message-error");
    
    // Fallback locator to handle encoding issues
    private By signUpLink = By.xpath("//a[contains(@class, 'margin-left-s') or contains(@href, 'SignUp')]");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void enterUsername(String username) {
        enterText(usernameInput, username);
    }

    public void enterPassword(String password) {
        enterText(passwordInput, password);
    }

    public void clickLogin() {
        click(loginButton);
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    public String getErrorMessage() {
        if(BasePage.isElementDisplayed(driver, feedbackMessage, 2)) {
             return getText(feedbackMessage);
        } else if (BasePage.isElementDisplayed(driver, validationMessage, 2)) {
             return getText(validationMessage);
        }
        return "";
    }
    
    public void clickSignUp() {
        click(signUpLink);
    }
    
    public boolean isPageLoaded() {
        return BasePage.isElementDisplayed(driver, usernameInput, 5) && 
               BasePage.isElementDisplayed(driver, passwordInput, 5) && 
               BasePage.isElementDisplayed(driver, loginButton, 5);
    }
}
