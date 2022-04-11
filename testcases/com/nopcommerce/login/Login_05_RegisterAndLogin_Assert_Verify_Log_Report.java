package com.nopcommerce.login;


import commons.AbstractTest;
import commons.PageGeneratorManager;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pageObjects.*;

public class Login_05_RegisterAndLogin_Assert_Verify_Log_Report extends AbstractTest {
    WebDriver driver;
    private HomePageObject homePage;

    public Login_05_RegisterAndLogin_Assert_Verify_Log_Report() {
    }


    @BeforeClass
    public void beforeClass() {
        // Get browser and uatURL
//        driver = homePage.getBrowserDriver(browserName);

        // Khoi tao Page Object
//        homePage = PageGeneratorManager.getHomepage(driver);

    }

    @Test
    public void TC_01_Assert(){
        System.out.println("TC_01 - Step 01: Open Footer My Account Page");

        System.out.println("TC_01 - Step 02: Verify Footer My Account Page display");
        Assert.assertTrue(true);

        System.out.println("TC_01 - Step 03: Verify Footer My Account Page NOT display");
        Assert.assertTrue(false);

        System.out.println("TC_01 - Step 04: Verify Home Page display");
        Assert.assertTrue(true);

    }

    @Test
    public void TC_02_Verify(){
        System.out.println("TC_01 - Step 01: Open New Customer Page");

        System.out.println("TC_01 - Step 02: Verify New Customer Page display");
        verifyTrue(true);

        System.out.println("TC_01 - Step 03: Verify New Customer Page NOT display");
        verifyTrue(false);

        System.out.println("TC_01 - Step 04: Verify Home Page display");
        verifyTrue(true);
    }

    @AfterClass()
    public void afterClass() {
        driver.quit();
    }
}