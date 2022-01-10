package pageObjects;

import commons.AbstractPage;
import commons.PageGeneratorManager;
import org.openqa.selenium.WebDriver;
import pageUIs.FooterNewProductPageUI;

public class FooterNewProductPageObject extends AbstractPage {
    private WebDriver driver;

    public FooterNewProductPageObject(WebDriver _driver){
        driver = _driver;
    }

    public HomePageObject openHomePage() {
        waitForElementClickable(driver, FooterNewProductPageUI.HOME_PAGE_IMAGE);
        clickToElement(driver, FooterNewProductPageUI.HOME_PAGE_IMAGE);
        return PageGeneratorManager.getHomepage(driver);
    }
}
