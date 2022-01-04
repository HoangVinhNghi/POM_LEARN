package com.nopcommerce.login;


import commons.PageGeneratorManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageObjects.HomePageObject;
import pageObjects.LoginPageObject;
import pageObjects.RegisterPageObject;

import java.util.concurrent.TimeUnit;

public class Login_02_RegisterAndLogin_Page_Generator {
    WebDriver driver;
    private String email,password, registerSuccessMsg;
    private HomePageObject homePage;
    private LoginPageObject loginPage;
    private RegisterPageObject registerPage;

    @BeforeClass
    public void beforeClass() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("https://demo.nopcommerce.com/");

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
        // Click to Register link Home Page -> Register Page
        registerPage = homePage.clickToRegisterLink();

        // Click male radio button
        registerPage.clickToMaleRadioButton();

        // Input firstname
        registerPage.inputToFirstNameTextbox("nghi");

        // Input lastname
        registerPage.inputToLastNameTextbox("hoang");

        // Select day of birth
        registerPage.selectDayDropdown("1");

        // Select month of birth
        registerPage.selectMonthDropdown("August");

        // Select year of birth
        registerPage.selectYearDropdown("1988");

        // Input Email text box
        registerPage.inputEmailTextbox(email);

        // Input Company text box
        registerPage.inputCompanyTextbox("JK Company");

        // Input Password text box
        registerPage.inputPasswordTextbox(password);

        // Input Confirm Password text box
        registerPage.inputConfirmPasswordTextbox(password);

        // Click to Register button
        registerPage.clickToRegisterButton();

        // Verify Register success message
        registerSuccessMsg = registerPage.getRegisterSuccessMsg();
        Assert.assertEquals(registerSuccessMsg, "Your registration completed");

        // Log out -> Home Page
        homePage = registerPage.clickToLogOutLink();
    }

    @Test
    public void TC_02_LoginToSystem() {
        // Click Login link -> Login page
        loginPage = homePage.clickToLoginLink();
        // Input email text box
        loginPage.inputEmail(email);
        // Input password text box
        loginPage.inputPassword(password);
        // Click to Login button
        homePage = loginPage.clickToLoginButton();
        // Verify My Account Link is display
        Assert.assertTrue(homePage.isMyAccountLinkDisplayed());

    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
        driver.quit();
    }
}