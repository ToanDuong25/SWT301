package com.example.pages;

import com.example.utils.TestUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * BasePage - Lớp cơ sở cho tất cả Page Object
 * Chứa các phương thức dùng chung
 */
public abstract class BasePage {

    protected WebDriver driver;
    protected static final int TIMEOUT = 15;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Chờ và click vào element
     */
    protected void click(By locator) {
        TestUtils.waitForClickable(driver, locator, TIMEOUT).click();
    }

    /**
     * Chờ element hiển thị, xóa nội dung cũ và nhập text mới
     */
    protected void type(By locator, String text) {
        WebElement element = TestUtils.waitForElement(driver, locator, TIMEOUT);
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Lấy text của element
     */
    protected String getText(By locator) {
        return TestUtils.waitForElement(driver, locator, TIMEOUT).getText();
    }

    /**
     * Kiểm tra element có hiển thị không
     */
    protected boolean isDisplayed(By locator) {
        return TestUtils.isElementDisplayed(driver, locator);
    }

    /**
     * Lấy title của trang
     */
    public String getPageTitle() {
        return driver.getTitle();
    }

    /**
     * Lấy URL hiện tại
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * Chờ trang load xong
     */
    protected void waitForPageLoad() {
        TestUtils.waitForPageLoad(driver, TIMEOUT);
    }
}
