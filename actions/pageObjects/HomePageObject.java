package pageObjects;

import commons.AbstractPage;
import commons.PageGeneratorManager;
import org.openqa.selenium.WebDriver;
import pageUIs.HomePageUI;

public class HomePageObject extends AbstractPage {
    private WebDriver driver;

    public HomePageObject(WebDriver webDriver){
        driver = webDriver;
    }

    public RegisterPageObject clickToRegisterLink() {
        waitForElementClickable(driver, HomePageUI.REGISTER_LINK);
        clickToElement(driver, HomePageUI.REGISTER_LINK);
        return PageGeneratorManager.getRegisterPage(driver);
    }

    public LoginPageObject clickToLoginLink() {
        waitForElementClickable(driver, HomePageUI.LOGIN_LINK);
        clickToElement(driver, HomePageUI.LOGIN_LINK);
        return PageGeneratorManager.getLoginPage(driver);
    }

    public boolean isMyAccountLinkDisplayed() {
        waitForAllElementVisible(driver, HomePageUI.HEADER_MY_ACCOUNT_LINK);
        return isElementDisplayed(driver, HomePageUI.HEADER_MY_ACCOUNT_LINK);
    }


    public FooterMyAccountPageObject openFooterMyAccountPage() {
        waitForAllElementVisible(driver, HomePageUI.FOOTER_MY_ACCOUNT_LINK);
        clickToElement(driver, HomePageUI.FOOTER_MY_ACCOUNT_LINK);
        return PageGeneratorManager.getFooterMyAccountPage(driver);
    }

    public FooterSearchPageObject openFooterSearchPage() {
        waitForAllElementVisible(driver, HomePageUI.FOOTER_SEARCH_LINK);
        clickToElement(driver, HomePageUI.FOOTER_SEARCH_LINK);
        return PageGeneratorManager.getFooterSearchPage(driver);
    }
}
