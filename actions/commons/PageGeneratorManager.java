package commons;

import org.openqa.selenium.WebDriver;

import pageObjects.*;

public class PageGeneratorManager {

    //Cấp phát việc khởi tạo Home page
    public static HomePageObject getHomepage(WebDriver driver) {
        return new HomePageObject(driver);
    }

    public static RegisterPageObject getRegisterPage(WebDriver driver) {
        return new RegisterPageObject(driver);
    }

    public static LoginPageObject getLoginPage(WebDriver driver) {
        return new LoginPageObject(driver);
    }

    public static FooterMyAccountPageObject getFooterMyAccountPage(WebDriver driver) {
        return new FooterMyAccountPageObject(driver);
    }

    public static FooterNewProductPageObject getNewProductPage(WebDriver driver) {
        return new FooterNewProductPageObject(driver);
    }

    public static FooterSearchPageObject getFooterSearchPage(WebDriver driver) {
        return new FooterSearchPageObject(driver);
    }

}
