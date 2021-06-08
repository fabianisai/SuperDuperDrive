package com.udacity.jwdnd.course1.cloudstorage;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    private static String NOTE_TITLE = "To do";
    private static String NOTE_DESCR = "nothing";
    private static String CRED_URL = "delete.com";
    private static String CRED_USR = "Isai";
    private static String CRED_PASS = "pass2";

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "logout")
    private WebElement logout;

//notes

    @FindBy(id = "nav-notes-tab")
    private WebElement navNotesTab;

    @FindBy(id = "note-modal")
    private WebElement noteModal;

    @FindBy(id = "note-title")
    private WebElement noteTitle;

    @FindBy(id = "note-description")
    private WebElement noteDescr;

    @FindBy(id = "note-save")
    private WebElement noteSave;

    @FindBy(css ="th.title-note")
    private WebElement noteRowTitle;

    @FindBy(css ="button.edit-note")
    private WebElement buttonEditNote;

    @FindBy(css ="a.delete-note")
    private WebElement buttonDeleteNote;

//credentials

    @FindBy(id = "nav-credentials-tab")
    private WebElement navCredTab;

    @FindBy(id = "credentials-model")
    private WebElement credModal;

    @FindBy(id = "credential-url")
    private WebElement credUrl;

    @FindBy(id = "credential-username")
    private WebElement credUserName;

    @FindBy(id = "credential-password")
    private WebElement credPassword;

    @FindBy(id = "credSubmit")
    private WebElement buttonCredSumit;

    @FindBy(css = "button.edit-credential")
    private WebElement buttonCredEdit;

    @FindBy(css = "th.check-credential-url")
    private WebElement checkCredUrl;

    @FindBy(css = "a.delete-credential")
    private WebElement buttonDeleteCred;



    public void logout(){
        logout.click();
    }


    public void addNoteSave() throws InterruptedException {
        navNotesTab.click();
        Thread.sleep(1000);
        noteModal.click();
        Thread.sleep(1000);
        noteTitle.sendKeys(NOTE_TITLE);
        Thread.sleep(1000);
        noteDescr.sendKeys(NOTE_DESCR);
        Thread.sleep(1000);
        noteSave.click();
        Thread.sleep(1000);
    }

    public boolean isNoteAdd() throws InterruptedException {
        boolean add=false;

        navNotesTab.click();
        Thread.sleep(1000);

        if(noteRowTitle.getText().equals(NOTE_TITLE)){
            return true;
        } else{
            return false;
        }

    }

    public void editNoteSave() throws InterruptedException {
        buttonEditNote.click();
        Thread.sleep(1000);
        noteTitle.clear();
        noteTitle.sendKeys("x");
        Thread.sleep(1000);
        noteSave.click();
        Thread.sleep(1000);
    }

    public boolean isNoteEdit() throws InterruptedException {
        boolean edit=false;

        navNotesTab.click();
        Thread.sleep(1000);

        if(noteRowTitle.getText().equals("x")){
            return true;
        } else{
            return false;
        }

    }

    public void deleteNoteSave() throws InterruptedException {
        buttonDeleteNote.click();
        Thread.sleep(1000);
    }

    public boolean isDeleteNote(WebDriver driver) throws InterruptedException {
        boolean delete=false;

        navNotesTab.click();
        Thread.sleep(1000);


        if(driver.findElements(By.cssSelector("th.title-note")).isEmpty()){
            return true;
        } else{
            return false;
        }

    }

    public void addCredentialSave() throws InterruptedException {
        navCredTab.click();
        Thread.sleep(1000);
        credModal.click();
        Thread.sleep(1000);
        credUrl.sendKeys(CRED_URL);
        Thread.sleep(1000);
        credUserName.sendKeys(CRED_USR);
        Thread.sleep(1000);
        credPassword.sendKeys(CRED_PASS);
        Thread.sleep(1000);
        buttonCredSumit.click();
        Thread.sleep(1000);


    }

    public boolean isCredAdd() throws InterruptedException {
        boolean add=false;

        navCredTab.click();
        Thread.sleep(1000);

        if(checkCredUrl.getText().equals(CRED_URL)){
            return true;
        } else{
            return false;
        }

    }

    public void editCredSave() throws InterruptedException {
        buttonCredEdit.click();
        Thread.sleep(1000);
        credUrl.clear();
        credUrl.sendKeys("modif.org");
        Thread.sleep(1000);
        buttonCredSumit.click();
        Thread.sleep(1000);


    }

    public boolean isCredEdit() throws InterruptedException {
        boolean edit=false;

        navCredTab.click();
        Thread.sleep(1000);

        if(checkCredUrl.getText().equals("modif.org")){
            return true;
        } else{
            return false;
        }

    }

    public void deleteCredSave() throws InterruptedException {
        buttonDeleteCred.click();
        Thread.sleep(1000);
    }

    public boolean isDeleteCred(WebDriver driver) throws InterruptedException {
        boolean delete=false;

        navCredTab.click();
        Thread.sleep(1000);


        if(driver.findElements(By.cssSelector("th.check-credential-url")).isEmpty()){
            return true;
        } else{
            return false;
        }

    }








}
