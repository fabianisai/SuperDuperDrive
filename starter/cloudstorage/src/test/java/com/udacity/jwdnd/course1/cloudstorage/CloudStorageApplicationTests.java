package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	private static String FISRT_NAME = "Fabian";
	private static String LAST_NAME = "Isai";
	private static String USER_NAME = "Admin";
	private static String PASSWORD = "pass";
	private static String NOTE_TITLE = "To do";
	private static String NOTE_DESCR = "nothing";
	private static String CRED_URL = "delete.com";
	private static String CRED_USR = "Isai";
	private static String CRED_PASS = "pass2";

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void unauthorizedTest() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/home");
		Thread.sleep(1500);
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void newUserTest() throws InterruptedException {

		signUp();
		login();
		Assertions.assertEquals("Home", driver.getTitle());


		//logout
		WebElement logoutButton = driver.findElement(By.id("logout"));
		logoutButton.click();
		Thread.sleep(1000);

		Assertions.assertEquals("Login", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void notesTest() throws InterruptedException {
		signUp();
		login();

		WebElement notesTab = driver.findElement(By.id("nav-notes-tab"));
		notesTab.click();
		Thread.sleep(1000);

		WebElement showNoteModel = driver.findElement(By.id("note-modal"));
		showNoteModel.click();
		Thread.sleep(1000);

		WebElement noteTitle = driver.findElement(By.id("note-title"));
		noteTitle.sendKeys(NOTE_TITLE);

		WebElement noteDescription = driver.findElement(By.id("note-description"));
		noteDescription.sendKeys(NOTE_DESCR);

		WebElement saveNote = driver.findElement(By.id("note-save"));
		saveNote.click();
		Thread.sleep(1000);

		WebElement homeReturn = driver.findElement(By.id("succes-return"));
		homeReturn.click();
		Thread.sleep(1000);

		WebElement notesTab1 = driver.findElement(By.id("nav-notes-tab"));
		notesTab1.click();
		Thread.sleep(1000);

		WebElement savedNote = driver.findElement(By.cssSelector("th.title-note"));
		Assertions.assertEquals(NOTE_TITLE, savedNote.getText());
		Thread.sleep(1000);

		WebElement editNote = driver.findElement(By.cssSelector("button.edit-note"));
		editNote.click();
		Thread.sleep(1000);

		WebElement noteTitleNew = driver.findElement(By.id("note-title"));
		noteTitleNew.sendKeys("x");
		WebElement modifNote = driver.findElement(By.id("note-save"));
		modifNote.click();
		Thread.sleep(1000);


		WebElement homeReturn1 = driver.findElement(By.id("succes-return"));
		homeReturn1.click();
		Thread.sleep(1000);

		WebElement notesTab2 = driver.findElement(By.id("nav-notes-tab"));
		notesTab2.click();
		Thread.sleep(300);

		WebElement savedNote1 = driver.findElement(By.cssSelector("th.title-note"));
		Assertions.assertEquals(NOTE_TITLE+"x", savedNote1.getText());

		WebElement deleteNote = driver.findElement(By.cssSelector("a.delete-note"));
		deleteNote.click();
		Thread.sleep(1000);

		WebElement homeReturn2 = driver.findElement(By.id("succes-return"));
		homeReturn2.click();
		Thread.sleep(1000);

		WebElement notesTab3 = driver.findElement(By.id("nav-notes-tab"));
		notesTab3.click();
		Thread.sleep(300);

		boolean isDelete = driver.findElements(By.cssSelector("th.title-note")).isEmpty();
		Assertions.assertTrue(isDelete);
	}

	@Test
	public void credentialTest() throws InterruptedException {
		signUp();
		login();

		WebElement credentialsTab = driver.findElement(By.id("nav-credentials-tab"));
		credentialsTab.click();
		Thread.sleep(1000);

		WebElement showCredentialsModel = driver.findElement(By.id("credentials-model"));
		showCredentialsModel.click();
		Thread.sleep(1000);

		WebElement credentialUrl = driver.findElement(By.id("credential-url"));
		credentialUrl.sendKeys(CRED_URL);

		WebElement credentialUsername = driver.findElement(By.id("credential-username"));
		credentialUsername.sendKeys(CRED_USR);

		WebElement credentialPassword = driver.findElement(By.id("credential-password"));
		credentialPassword.sendKeys(CRED_PASS);

		WebElement credentialSubmit = driver.findElement(By.id("credSubmit"));
		credentialSubmit.click();
		Thread.sleep(1000);

		WebElement homeReturn = driver.findElement(By.id("succes-return"));
		homeReturn.click();
		Thread.sleep(1000);

		WebElement credentialsTab1 = driver.findElement(By.id("nav-credentials-tab"));
		credentialsTab1.click();

		WebElement savedCred = driver.findElement(By.cssSelector("th.check-credential-url"));
		Assertions.assertEquals(CRED_URL, savedCred.getText());
		Thread.sleep(1000);

		WebElement editNote = driver.findElement(By.cssSelector("button.edit-credential"));
		editNote.click();
		Thread.sleep(1000);

		WebElement credentialUrl1 = driver.findElement(By.id("credential-url"));
		credentialUrl1.sendKeys(".org");
		WebElement saveCredential = driver.findElement(By.id("credSubmit"));
		saveCredential.click();
		Thread.sleep(1000);


		WebElement homeReturn1 = driver.findElement(By.id("succes-return"));
		homeReturn1.click();
		Thread.sleep(1000);

		WebElement notesTab2 = driver.findElement(By.id("nav-credentials-tab"));
		notesTab2.click();
		Thread.sleep(1000);

		WebElement savedCredential = driver.findElement(By.cssSelector("th.check-credential-url"));
		Assertions.assertEquals(CRED_URL+".org", savedCredential.getText());

		WebElement deleteCredential = driver.findElement(By.cssSelector("a.delete-credential"));
		deleteCredential.click();
		Thread.sleep(1000);

		WebElement homeReturn2 = driver.findElement(By.id("succes-return"));
		homeReturn2.click();
		Thread.sleep(1000);

		WebElement credentialTab3 = driver.findElement(By.id("nav-credentials-tab"));
		credentialTab3.click();
		Thread.sleep(1000);

		boolean isDelete = driver.findElements(By.cssSelector("th.check-credential-url"))
				.isEmpty();
		Assertions.assertTrue(isDelete);


	}

	private void signUp() throws InterruptedException {
		//WebDriverWait wait = new WebDriverWait(driver, 30);
		// signup
		driver.get("http://localhost:" + this.port + "/signup");
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.sendKeys(FISRT_NAME);
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.sendKeys(LAST_NAME);
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.sendKeys(USER_NAME);
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.sendKeys(PASSWORD);
		WebElement signUpButton = driver.findElement(By.id("signup"));
		signUpButton.click();

		Thread.sleep(1000);
	}

	private void login() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/login");
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.sendKeys(USER_NAME);
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.sendKeys(PASSWORD);
		WebElement loginButton = driver.findElement(By.id("login"));
		loginButton.click();
		Thread.sleep(1000);
	}




}
