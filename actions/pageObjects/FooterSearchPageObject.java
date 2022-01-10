package pageObjects;

import commons.AbstractPage;
import commons.PageGeneratorManager;
import org.openqa.selenium.WebDriver;
import pageUIs.FooterNewProductPageUI;
import pageUIs.FooterSearchPageUI;

public class FooterSearchPageObject extends AbstractPage {
    private WebDriver driver;

    public FooterSearchPageObject(WebDriver _driver){
        driver = _driver;


    }

    public FooterNewProductPageObject openFooterNewProductPage() {
        waitForElementClickable(driver, FooterSearchPageUI.FOOTER_NEW_PRODUCT_LINK);
        clickToElement(driver, FooterSearchPageUI.FOOTER_NEW_PRODUCT_LINK);
        return PageGeneratorManager.getNewProductPage(driver);
    }
}
