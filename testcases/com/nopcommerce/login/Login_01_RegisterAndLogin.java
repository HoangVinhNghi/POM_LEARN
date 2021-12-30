package com.nopcommerce.login;


import atu.testrecorder.ATUTestRecorder;
import atu.testrecorder.exceptions.ATUTestRecorderException;

import commons.AbstractPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageObjects.HomePageObject;
import pageObjects.LoginPageObject;
import pageObjects.RegisterPageObject;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Login_01_RegisterAndLogin extends AbstractPage {
    WebDriver driver;
    private String email,password,registerSuccessMsg;
    private HomePageObject homePage;
    private LoginPageObject loginPage;
    private RegisterPageObject registerPage;

    @BeforeClass
    public void beforeClass() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        File file = new File(System.getProperty("user.dir") + "/Libs/Selectorhub3.3.crx");
        options.addArguments("--incognito");
        options.addExtensions(file);
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        openPageUrl(driver,"https://demo.nopcommerce.com/");
        //Generate random email
        email = "corona"+getRandomNumber()+"@hotmail.com";
        password = "123456";
    }

    @Test
    public void TC_01_RegisterToSystem() {
        // Click to Register link Home Page -> Register Page
        registerPage = homePage.clickToRegisterLink();
        // Click male radio button
        registerPage.clickToMaleRadioButton();
        // Input firstname
        registerPage.inputToFirstNameTextbox("");
        // Input lastname
        registerPage.inputToLastNameTextbox("");
        // Select day of birth
        registerPage.selectDayDropdown("");
        // Select month of birth
        registerPage.selectMonthDropdown("");
        // Select year of birth
        registerPage.selectYearDropdown("");
        // Input Email text box
        registerPage.inputEmailTextbox("");
        // Input Company text box
        registerPage.inputCopanyTextbox("");
        // Input Password text box
        registerPage.inputPasswordTextbox("");
        // Input Confirm Password text box
        registerPage.inputConfirmPasswordTextbox("");
        // Click to Register button
        registerPage.clickToRegisterButton();
        // Verify Register success message
        registerSuccessMsg = registerPage.getRegisterSuccessMsg();
        // Log out -> Home Page
        homePage = registerPage.clickToLogOutLink();

    }

    @Test
    public void TC_02_LoginToSystem() {
        // Click Login link -> Login page
        loginPage = homePage.clickToLoginLink();
        // Input email text box
        loginPage.inputEmail("");
        // Input password text box
        loginPage.inputPassword("");
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