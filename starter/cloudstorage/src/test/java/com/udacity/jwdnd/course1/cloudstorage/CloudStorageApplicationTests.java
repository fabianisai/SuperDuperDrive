package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
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

		driver.get("http://localhost:" + this.port + "/signup");
		SignUpPage signUpPage= new SignUpPage(driver);
		signUpPage.signup();

		driver.get("http://localhost:" + this.port + "/login");
		LoginPage loginPage= new LoginPage(driver);
		loginPage.login();

		Assertions.assertEquals("Home", driver.getTitle());

		HomePage homePage = new HomePage(driver);
		homePage.logout();
		Assertions.assertEquals("Login", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());

	}


	@Test
	public void notesTest() throws InterruptedException {
		//test add
		driver.get("http://localhost:" + this.port + "/signup");
		SignUpPage signUpPage= new SignUpPage(driver);
		signUpPage.signup();

		driver.get("http://localhost:" + this.port + "/login");
		LoginPage loginPage= new LoginPage(driver);
		loginPage.login();

		Assertions.assertEquals("Home", driver.getTitle());

		HomePage homePage = new HomePage(driver);
		homePage.addNoteSave();

		ResultPage resultPage=new ResultPage(driver);
		resultPage.returnSucces();

		Assertions.assertTrue(homePage.isNoteAdd());
//test edit
		homePage.editNoteSave();
		resultPage.returnSucces();
		Assertions.assertTrue(homePage.isNoteEdit());
//test delete
		homePage.deleteNoteSave();
		resultPage.returnSucces();
		Assertions.assertTrue(homePage.isDeleteNote(driver));

	}

	@Test
	public void credentialTest() throws InterruptedException {

		//test add

		driver.get("http://localhost:" + this.port + "/signup");
		SignUpPage signUpPage= new SignUpPage(driver);
		signUpPage.signup();

		driver.get("http://localhost:" + this.port + "/login");
		LoginPage loginPage= new LoginPage(driver);
		loginPage.login();

		Assertions.assertEquals("Home", driver.getTitle());

		HomePage homePage = new HomePage(driver);
		homePage.addCredentialSave();

		ResultPage resultPage=new ResultPage(driver);
		resultPage.returnSucces();

		Assertions.assertTrue(homePage.isCredAdd());

//test edit

		homePage.editCredSave();
		resultPage.returnSucces();
		Assertions.assertTrue(homePage.isCredEdit());

//test delete

		homePage.deleteCredSave();
		resultPage.returnSucces();
		Assertions.assertTrue(homePage.isDeleteCred(driver));


	}

}
