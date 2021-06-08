package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ResultPage {

    public ResultPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "succes-return")
    private WebElement returnSucces;

    public void returnSucces(){
        returnSucces.click();
    }

}
