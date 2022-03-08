package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.NoSuchElementException;

public class ResultPage {

    private WebDriverWait wait;

    public ResultPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, 2);
    }

    @FindBy(id = "success-message-back-home")
    private List<WebElement> successMessageBackHomeElements;

    @FindBy(id = "failed-message-back-home")
    private List<WebElement> failedMessageBackHomeElements;

    public void goBackToHomePage() throws InterruptedException {

        if (successMessageBackHomeElements != null && successMessageBackHomeElements.size() > 0){
            successMessageBackHomeElements.get(0).click();
        }
        failedMessageBackHomeElements.get(0).click();
    }
}
