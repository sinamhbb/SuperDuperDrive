package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

    private WebDriverWait wait;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
         wait = new WebDriverWait(driver,2);
    }

    @FindBy(id="logout-button")
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

    @FindBy(id="saved-note-title")
    private WebElement savedNoteTitle;

    @FindBy(id="saved-note-description")
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

    @FindBy(xpath ="//button[contains(@class, 'btn btn-info float-right')]")
    private WebElement addnewCredentialButton;

//    @FindBy(id = "")





    // Note Methods

    public NoteForm createNote() {
        noteTabLink.click();
        wait.until(ExpectedConditions.elementToBeClickable(addNewNoteButton));
        addNewNoteButton.click();

        NoteForm noteToBeSaved = new NoteForm(null,"Test To Do", "Write a test that creates a note, and verifies it is displayed.");
        wait.until(ExpectedConditions.elementToBeClickable(noteTitleInput));
        noteTitleInput.sendKeys(noteToBeSaved.getNotetitle());
        noteDescriptionInput.sendKeys(noteToBeSaved.getNotedescription());
        noteSubmitButton.click();
        return noteToBeSaved;
    }

    public NoteForm editNote() {
        wait.until(ExpectedConditions.visibilityOf(editNoteButton));
        editNoteButton.click();

        NoteForm noteToBeSaved = new NoteForm(null,"Test To Edit", "Write a test that edits an existing note and verifies that the changes are displayed.");
        wait.until(ExpectedConditions.elementToBeClickable(noteTitleInput));
        noteTitleInput.clear();
        noteDescriptionInput.clear();
        noteTitleInput.sendKeys(noteToBeSaved.getNotetitle());
        noteDescriptionInput.sendKeys(noteToBeSaved.getNotedescription());
        noteSubmitButton.click();
        return noteToBeSaved;
    }

    public void deleteNote() {
        wait.until(ExpectedConditions.visibilityOf(deleteNoteButton));
        deleteNoteButton.click();
    }

    public NoteForm checkSubmittedNote() {
        wait.until(ExpectedConditions.visibilityOf(savedNoteTitle));
        return new NoteForm(null, savedNoteTitle.getText(), savedNoteDescription.getText());
    }

    public void logout() {
        logoutButton.click();
    }

}
