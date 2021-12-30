package pageObjects;

import commons.AbstractPage;
import org.openqa.selenium.WebDriver;
import pageUIs.RegisterPageUI;

public class RegisterPageObject extends AbstractPage {
    private WebDriver driver;

    public RegisterPageObject(WebDriver webDriver){
        driver = webDriver;
    }

    public void clickToMaleRadioButton() {
        waitForElementVisible(driver, RegisterPageUI.GENDER_MALE_RADIO);
        clickToElement(driver, RegisterPageUI.GENDER_MALE_RADIO);
    }

    public void inputToFirstNameTextbox(String firstNameValue) {
        waitForElementVisible(driver, RegisterPageUI.FIRST_NAME_TEXT_BOX);
        sendKeyToElement(driver, RegisterPageUI.FIRST_NAME_TEXT_BOX, firstNameValue);
    }

    public void inputToLastNameTextbox(String lastNameValue) {
        waitForElementVisible(driver, RegisterPageUI.LAST_NAME_TEXT_BOX);
        sendKeyToElement(driver, RegisterPageUI.LAST_NAME_TEXT_BOX, lastNameValue);
    }

    public void selectDayDropdown(String dayValue) {
        waitForElementClickable(driver, RegisterPageUI.DAY_DROP_DOWN);
        selectDropdownByText(driver, RegisterPageUI.DAY_DROP_DOWN, dayValue);
    }

    public void selectMonthDropdown(String monthValue) {
        waitForElementClickable(driver, RegisterPageUI.MONTH_DROP_DOWN);
        selectDropdownByText(driver, RegisterPageUI.MONTH_DROP_DOWN, monthValue);
    }

    public void selectYearDropdown(String yearValue) {
        waitForElementClickable(driver, RegisterPageUI.YEAR_DROP_DOWN);
        selectDropdownByText(driver, RegisterPageUI.YEAR_DROP_DOWN, yearValue);
    }

    public void inputEmailTextbox(String email) {
        waitForElementVisible(driver, RegisterPageUI.EMAIL_TEXT_BOX);
        sendKeyToElement(driver, RegisterPageUI.EMAIL_TEXT_BOX, email);
    }

    public void inputCompanyTextbox(String companyName) {
        waitForElementVisible(driver, RegisterPageUI.COMPANY_TEXT_BOX);
        sendKeyToElement(driver, RegisterPageUI.COMPANY_TEXT_BOX, companyName);
    }

    public void inputPasswordTextbox(String password) {
        waitForElementVisible(driver, RegisterPageUI.PASSWORD_TEXT_BOX);
        sendKeyToElement(driver, RegisterPageUI.PASSWORD_TEXT_BOX, password);
    }

    public void inputConfirmPasswordTextbox(String password) {
        waitForElementVisible(driver, RegisterPageUI.CONFIRM_PASSWORD_TEXT_BOX);
        sendKeyToElement(driver, RegisterPageUI.CONFIRM_PASSWORD_TEXT_BOX, password);
    }

    public void clickToRegisterButton() {
        waitForElementVisible(driver, RegisterPageUI.REGISTER_BUTTON);
        clickToElement(driver, RegisterPageUI.REGISTER_BUTTON);
    }

    public String getRegisterSuccessMsg() {
        waitForElementVisible(driver,RegisterPageUI.REGISTER_SUCCESS_MESSAGE);
        return getElementText(driver,RegisterPageUI.REGISTER_SUCCESS_MESSAGE);
    }

    public HomePageObject clickToLogOutLink() {
        waitForElementVisible(driver, RegisterPageUI.LOGOUT_LINK);
        clickToElement(driver, RegisterPageUI.LOGOUT_LINK);
        return new HomePageObject(driver);
    }
}
