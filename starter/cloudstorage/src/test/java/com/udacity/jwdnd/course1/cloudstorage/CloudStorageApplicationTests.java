package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	private WebDriverWait wait;

	private HomePage homaPage;


	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		wait = new WebDriverWait(driver, 1);

		homaPage = new HomePage(driver);
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	@Order(1)
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		assertEquals("Login", driver.getTitle());
	}



	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/

	private void doMockSignUp(String firstName, String lastName, String userName, String password){
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
		
		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/* Check that the sign up was successful. 
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depening on the rest of your code.
		*/

		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
	}

	
	
	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling redirecting users 
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric: 
	 * https://review.udacity.com/#!/rubrics/2724/view 
	 */
	@Test
	@Order(2)
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");
		wait.until(ExpectedConditions.elementToBeClickable(By.id("login-button")));
		// Check if we have been redirected to the log in page.
		assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling bad URLs 
	 * gracefully, for example with a custom error page.
	 * 
	 * Read more about custom error pages at: 
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	@Order(3)
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");
		
		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code. 
	 * 
	 * Read more about file size limits here: 
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	@Order(4)
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

	}

	@Test
	@Order(5)
	public void unauthorizedUserAccessTest() {
		driver.get("http://localhost:" + this.port + "/login");
		wait.until(WebDriver::getTitle);
		assertEquals(driver.getTitle(),"Login");

		driver.get("http://localhost:" + this.port + "/signup");
		wait.until(WebDriver::getTitle);
		assertEquals(driver.getTitle(),"Sign Up");

		driver.get("http://localhost:" + this.port + "/home");
		wait.until(WebDriver::getTitle);
		assertNotEquals(driver.getTitle(),"Home");

		driver.get("http://localhost:" + this.port + "/result");
		wait.until(WebDriver::getTitle);
		assertNotEquals(driver.getTitle(),"Result");
	}

	@Test
	@Order(6)
	public void homePageAccessibilityTest() {
		doMockSignUp("homePageAccess", "Test" , "homePageAccess.Test","123");
		doLogIn("homePageAccess.Test", "123");
		assertEquals("http://localhost:" + this.port + "/home", driver.getCurrentUrl());

		homaPage.logout();

		wait.until(WebDriver::getTitle);
		driver.get("http://localhost:" + this.port + "/home");
		assertNotEquals("http://localhost:" + this.port + "/home", driver.getCurrentUrl());
	}

	@Test
	@Order(7)
	public void noteCreationFunctionalityTest() throws InterruptedException {
		doMockSignUp("note", "Test" , "note.Test","123");

		doLogIn("note.Test", "123");

		NoteForm submittedNote = homaPage.createNote();
		wait.until(WebDriver::getTitle);
		driver.findElement(By.id("success-message-back-home")).click();
		wait.until(WebDriver::getTitle);

		NoteForm savedNote = homaPage.checkSubmittedNote();
		assertEquals(submittedNote.getNotetitle(), savedNote.getNotetitle());
		assertEquals(submittedNote.getNotedescription(), savedNote.getNotedescription());
	}

	@Test
	@Order(8)
	public void noteEditFunctionalityTest() throws InterruptedException {

		doLogIn("note.Test", "123");

		NoteForm editedNote = homaPage.editNote();
		wait.until(WebDriver::getTitle);
		driver.findElement(By.id("success-message-back-home")).click();

		NoteForm savedNote = homaPage.checkSubmittedNote();
		assertEquals(editedNote.getNotetitle(), savedNote.getNotetitle());
		assertEquals(editedNote.getNotedescription(), savedNote.getNotedescription());

	}

	@Test
	@Order(9)
	public void noteDeleteFunctionalityTest() {
		doLogIn("note.Test", "123");

		wait.until(WebDriver::getTitle);
		homaPage.deleteNote();
		wait.until(WebDriver::getTitle);
		driver.findElement(By.id("success-message-back-home")).click();

		assertThrows(NoSuchElementException.class,() -> { driver.findElement(By.id("saved-note-description"));});
		assertThrows(NoSuchElementException.class,() -> { driver.findElement(By.id("saved-note-title"));});

	}

	@Test
	@Order(10)
	public void credentialCreationFunctionalityTest() {

		doMockSignUp("credential", "Test" , "credentialCreation.Test","123");
		doLogIn("credentialCreation.Test", "123");

		List<CredentialForm> credentialsToBeSaved = homaPage.createCredential();

		List<CredentialForm> allSavedCredentials = homaPage.checkSubmittedCredentials();

		credentialsToBeSaved.forEach( credential -> {
			assertEquals(credential.getUrl(),allSavedCredentials.get(credentialsToBeSaved.indexOf(credential)).getUrl());
			assertEquals(credential.getUsername(),allSavedCredentials.get(credentialsToBeSaved.indexOf(credential)).getUsername());
			assertNotEquals(credential.getPassword(),allSavedCredentials.get(credentialsToBeSaved.indexOf(credential)).getPassword());
		});
	}

	@Test
	@Order(11)
	public void credentialsEditFunctionalityTest() {
		doLogIn("credentialCreation.Test", "123");

		List<CredentialForm> credentialsToBeSaved =  homaPage.getCredentialsToBeSaved();

		List<String> credentialsUnencryptedPasswords = homaPage.GetUnencryptedPasswordsAndEditCredentials();

		List<CredentialForm> allSavedCredentials = homaPage.checkSubmittedCredentials();

		credentialsToBeSaved.forEach( credential -> {
			assertEquals(credential.getPassword(),credentialsUnencryptedPasswords.get(credentialsToBeSaved.indexOf(credential)));
			assertNotEquals(credential.getUrl(),allSavedCredentials.get(credentialsToBeSaved.indexOf(credential)).getUrl());
			assertNotEquals(credential.getUsername(),allSavedCredentials.get(credentialsToBeSaved.indexOf(credential)).getUsername());
		});
	}

	@Test
	@Order(12)
	public void credentialsDeleteFunctionalityTest() {

		doLogIn("credentialCreation.Test", "123");
		List<CredentialForm> savedCredentials =  homaPage.checkSubmittedCredentials();

		homaPage.deleteCredentials(savedCredentials.size());
		assertThrows(IndexOutOfBoundsException.class, () -> {homaPage.checkSubmittedCredentials();} );
	}
}
