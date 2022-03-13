package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final NoteForm noteToBeSaved = new NoteForm(null, "Test To Do", "Write a test that creates a note, and verifies it is displayed.");
    private final NoteForm noteToBeEdited = new NoteForm(null, "Test To Edit", "Write a test that edits an existing note and verifies that the changes are displayed.");


    private final List<CredentialForm> credentialsToBeSaved = Arrays.asList(
            new CredentialForm(null, "www.cuploop.com", "Sina.M", "123"),
            new CredentialForm(null, "www.google.com", "ssssina", "asd"),
            new CredentialForm(null, "www.wise.com", "AAAA", "z")
    );

    private final List<CredentialForm> credentialsToBeEdited = Arrays.asList(
            new CredentialForm(null, "www.amazon.com", "Sina_Mohebbi", "123"),
            new CredentialForm(null, "www.facebook.com", "sinaaaaa", "asd"),
            new CredentialForm(null, "www.Revolut.com", "gggg", "z")
    );


    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        wait = new WebDriverWait(driver, 2);
    }

    @FindBy(id = "logout-button")
    private WebElement logoutButton;


    //Note Elements

    @FindBy(id = "nav-notes-tab")
    private WebElement noteTabLink;

    @FindBy(id = "add-new-note")
    private WebElement addNewNoteButton;

    @FindBy(xpath = "//button[contains(@class,'btn btn-success')]")
    private WebElement editNoteButton;

    @FindBy(xpath = "/html/body/div/div[@id='contentDiv']/div/div[@id='nav-notes']/div[1]/table/tbody/tr/td[1]/a")
    private WebElement deleteNoteButton;

    @FindBy(id = "saved-note-title")
    private WebElement savedNoteTitle;

    @FindBy(id = "saved-note-description")
    private WebElement savedNoteDescription;

    @FindBy(id = "note-title")
    private WebElement noteTitleInput;

    @FindBy(id = "note-description")
    private WebElement noteDescriptionInput;

    @FindBy(xpath = "//button[contains(@class,'btn btn-primary')]")
    private WebElement noteSubmitButton;


    //    Credential  Elements

    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialTabLink;

    @FindBy(xpath = "/html/body/div/div[@id='contentDiv']/div/div[@id='nav-credentials']/button")
    private WebElement addNewCredentialButton;

    @FindBy(xpath = "/html/body/div/div[@id='contentDiv']/div/div[@id='nav-credentials']/div[1]/table/tbody/tr")
    private List<WebElement> savedCredentials;

    @FindBy(xpath = "/html/body/div/div[@id='contentDiv']/div/div[@id='nav-credentials']/div[@id='credentialModal']/div/div/div[3]/button[1]")
    private WebElement credentialFormCloseButton;

    @FindBy(id = "credential-url")
    private WebElement credentialUrlInput;

    @FindBy(id = "credential-username")
    private WebElement credentialUsernameInput;

    @FindBy(id = "credential-password")
    private WebElement credentialPasswordInput;

    @FindBy(xpath = "/html/body/div/div[@id='contentDiv']/div/div[@id='nav-credentials']/div[@id='credentialModal']/div/div/div[3]/button[2]")
    private WebElement credentialSubmitButton;


    public void logout() {
        logoutButton.click();
    }

    // Note Methods

    public NoteForm createNote() {
        noteTabLink.click();
        wait.until(ExpectedConditions.elementToBeClickable(addNewNoteButton));
        addNewNoteButton.click();

        wait.until(ExpectedConditions.elementToBeClickable(noteTitleInput));
        noteTitleInput.sendKeys(noteToBeSaved.getNotetitle());
        noteDescriptionInput.sendKeys(noteToBeSaved.getNotedescription());
        noteSubmitButton.click();
        return noteToBeSaved;
    }

    public NoteForm editNote() {
        noteTabLink.click();
        wait.until(ExpectedConditions.visibilityOf(editNoteButton));
        editNoteButton.click();

        wait.until(ExpectedConditions.elementToBeClickable(noteTitleInput));
        noteTitleInput.clear();
        noteTitleInput.sendKeys(noteToBeEdited.getNotetitle());
        noteDescriptionInput.clear();
        noteDescriptionInput.sendKeys(noteToBeEdited.getNotedescription());
        noteSubmitButton.click();
        return noteToBeEdited;
    }

    public void deleteNote() {
        noteTabLink.click();
        wait.until(ExpectedConditions.visibilityOf(deleteNoteButton));
        deleteNoteButton.click();
    }

    public NoteForm checkSubmittedNote() {
        noteTabLink.click();
        wait.until(ExpectedConditions.visibilityOf(savedNoteTitle));
        return new NoteForm(null, savedNoteTitle.getText(), savedNoteDescription.getText());
    }


    // Credential Methods

    public List<CredentialForm> createCredential() {

        credentialTabLink.click();
        wait.until(WebDriver::getTitle);
        credentialsToBeSaved.forEach(credential -> {

            wait.until(ExpectedConditions.elementToBeClickable(addNewCredentialButton));
            addNewCredentialButton.click();

            wait.until(ExpectedConditions.elementToBeClickable(credentialUrlInput));
            credentialUrlInput.sendKeys(credential.getUrl());
            credentialUsernameInput.sendKeys(credential.getUsername());
            credentialPasswordInput.sendKeys(credential.getPassword());
            credentialSubmitButton.click();

            wait.until(WebDriver::getTitle);
            driver.findElement(By.id("success-message-back-home")).click();
            wait.until(WebDriver::getTitle);

        });

        return credentialsToBeSaved;

    }

    public List<CredentialForm> checkSubmittedCredentials() throws IndexOutOfBoundsException {
        credentialTabLink.click();
        wait.until(WebDriver::getTitle);

        List<CredentialForm> allSavedCredentials = new ArrayList<>();
        wait.until(ExpectedConditions.visibilityOf(savedCredentials.get(0)));
        savedCredentials.forEach(credential -> {
            List<WebElement> url = credential.findElements(By.tagName("th"));
            List<WebElement> usernamePassword = credential.findElements(By.tagName("td"));
            allSavedCredentials.add(new CredentialForm(null, url.get(0).getText(), usernamePassword.get(1).getText(), usernamePassword.get(2).getText()));
        });

        return allSavedCredentials;
    }

    public List<String> GetUnencryptedPasswordsAndEditCredentials() {
        credentialTabLink.click();
        wait.until(WebDriver::getTitle);
        List<String> unencryptedPasswords = new ArrayList<>();
        wait.until(ExpectedConditions.visibilityOf(savedCredentials.get(0)));



        for (int i = 0; i < credentialsToBeEdited.size(); i++) {
            wait.until(ExpectedConditions.elementToBeClickable(addNewCredentialButton));
            WebElement editButton = driver.findElement(By.xpath("/html/body/div/div[@id='contentDiv']/div/div[@id='nav-credentials']/div[1]/table/tbody["+( i + 1)+"]/tr/td[1]/button"));

            editButton.click();
            wait.until(ExpectedConditions.visibilityOf(credentialUrlInput));
            unencryptedPasswords.add(credentialPasswordInput.getAttribute("value"));

            credentialUrlInput.clear();
            credentialUrlInput.sendKeys(credentialsToBeEdited.get(i).getUrl());
            credentialUsernameInput.clear();
            credentialUsernameInput.sendKeys(credentialsToBeEdited.get(i).getUsername());
            credentialSubmitButton.click();

            wait.until(WebDriver::getTitle);
            driver.findElement(By.id("success-message-back-home")).click();
            wait.until(WebDriver::getTitle);
        }

        return unencryptedPasswords;
    }

    public void deleteCredentials(int numberOfElementsToDelete) {
        credentialTabLink.click();
        wait.until(ExpectedConditions.visibilityOf(savedCredentials.get(0)));
        for (int i=0; i < numberOfElementsToDelete;i++) {
            WebElement deleteButton = driver.findElement(By.xpath("/html/body/div/div[@id='contentDiv']/div/div[@id='nav-credentials']/div[1]/table/tbody/tr/td[1]/a"));
            deleteButton.click();

            wait.until(WebDriver::getTitle);
            driver.findElement(By.id("success-message-back-home")).click();
            wait.until(WebDriver::getTitle);
        }
    }

    public List<CredentialForm> getCredentialsToBeSaved() {
        return credentialsToBeSaved;
    }
}