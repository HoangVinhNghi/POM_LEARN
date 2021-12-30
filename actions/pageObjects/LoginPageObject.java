package pageObjects;

import commons.AbstractPage;
import org.openqa.selenium.WebDriver;
import pageUIs.LoginPageUI;

public class LoginPageObject extends AbstractPage {
private WebDriver driver;
    public LoginPageObject(WebDriver webDriver){
        driver = webDriver;
    }
    public void inputEmail(String email) {
        waitForElementVisible(driver, LoginPageUI.LOGIN_EMAIL_TEXTBOX);
        sendKeyToElement(driver, LoginPageUI.LOGIN_EMAIL_TEXTBOX, email);
    }

    public void inputPassword(String password) {
        waitForElementVisible(driver, LoginPageUI.LOGIN_PASSWORD_TEXTBOX);
        sendKeyToElement(driver, LoginPageUI.LOGIN_PASSWORD_TEXTBOX, password);
    }

    public HomePageObject clickToLoginButton() {
        waitForElementClickable(driver, LoginPageUI.LOGIN_BUTTON);
        clickToElement(driver, LoginPageUI.LOGIN_BUTTON);
        return new HomePageObject(driver);
    }
}
