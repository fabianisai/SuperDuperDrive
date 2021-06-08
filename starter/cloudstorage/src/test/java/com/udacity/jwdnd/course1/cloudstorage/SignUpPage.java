package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignUpPage {

    private static String FISRT_NAME = "Fabian";
    private static String LAST_NAME = "Isai";
    private static String USER_NAME = "Admin";
    private static String PASSWORD = "pass";


    @FindBy(id = "inputFirstName")
    private WebElement firstname;

    @FindBy(id = "inputLastName")
    private WebElement lastName;

    @FindBy(id = "inputUsername")
    private WebElement userName;

    @FindBy(id = "inputPassword")
    private WebElement password;

    @FindBy(id = "signup")
    private WebElement signup;

    public SignUpPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void signup(){

        firstname.sendKeys(FISRT_NAME);
        lastName.sendKeys(LAST_NAME);
        userName.sendKeys(USER_NAME);
        password.sendKeys(PASSWORD);
        signup.click();
    }

}
