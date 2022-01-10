package pageObjects;

import commons.AbstractPage;
import commons.PageGeneratorManager;
import org.openqa.selenium.WebDriver;
import pageUIs.FooterMyAccountPageUI;
import pageUIs.HomePageUI;

public class FooterMyAccountPageObject extends AbstractPage {
    private WebDriver driver;

    public FooterMyAccountPageObject(WebDriver _driver){
        driver = _driver;

    }

    public FooterSearchPageObject openFooterNewProductPage() {
        waitForElementClickable(driver, FooterMyAccountPageUI.FOOTER_SEARCH_LINK);
        clickToElement(driver, FooterMyAccountPageUI.FOOTER_SEARCH_LINK);
        return PageGeneratorManager.getFooterSearchPage(driver);
    }
}
