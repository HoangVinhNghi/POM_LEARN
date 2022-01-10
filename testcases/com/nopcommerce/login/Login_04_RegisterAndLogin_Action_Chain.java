package com.nopcommerce.login;


import commons.AbstractTest;
import commons.PageGeneratorManager;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.*;

public class Login_04_RegisterAndLogin_Action_Chain extends AbstractTest {
    WebDriver driver;
    private String email,password, registerSuccessMsg;
    private HomePageObject homePage;
    private LoginPageObject loginPage;
    private RegisterPageObject registerPage;
    private FooterMyAccountPageObject myAccountPage;
    private FooterNewProductPageObject newProductPage;
    private FooterSearchPageObject searchPage;


    @Parameters({"browser", "url"})
    @BeforeClass
    public void beforeClass(String browserName, String uatURL) {
        // Get browser and uatURL
        driver = getBrowser(browserName, uatURL);

        // Khoi tao Page Object
        homePage = PageGeneratorManager.getHomepage(driver);
        loginPage = PageGeneratorManager.getLoginPage(driver);
        registerPage = PageGeneratorManager.getRegisterPage(driver);

        //Generate random email
        email = "corona"+ registerPage.getRandomNumber()+"@hotmail.com";
        password = "123456";
    }

    @Test
    public void TC_01_RegisterToSystem() {
        registerPage = homePage.clickToRegisterLink();

        registerPage.clickToMaleRadioButton();
        registerPage.inputToFirstNameTextbox("nghi");
        registerPage.inputToLastNameTextbox("hoang");
        registerPage.selectDayDropdown("1");
        registerPage.selectMonthDropdown("August");
        registerPage.selectYearDropdown("1988");
        registerPage.inputEmailTextbox(email);
        registerPage.inputCompanyTextbox("JK Company");
        registerPage.inputPasswordTextbox(password);
        registerPage.inputConfirmPasswordTextbox(password);
        registerPage.clickToRegisterButton();
        registerSuccessMsg = registerPage.getRegisterSuccessMsg();
        Assert.assertEquals(registerSuccessMsg, "Your registration completed");

        homePage = registerPage.clickToLogOutLink();
    }

    @Test
    public void TC_02_LoginToSystem() {
        loginPage = homePage.clickToLoginLink();
        loginPage.inputEmail(email);
        loginPage.inputPassword(password);
        homePage = loginPage.clickToLoginButton();
        Assert.assertTrue(homePage.isMyAccountLinkDisplayed());

    }

    @Test
    public void TC_03_ActionChain() {
        //Home Page > My Account (footer)
        myAccountPage = homePage.openFooterMyAccountPage();

        //My Account (footer) > Search Page
        searchPage = myAccountPage.openFooterNewProductPage();

        //Search Page > New Product Page
        newProductPage = searchPage.openFooterNewProductPage();

        //New Product Page > Home page
        homePage = newProductPage.openHomePage();

        //Home Page > Search Page
        searchPage = homePage.openFooterSearchPage();

    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
        driver.quit();
    }
}