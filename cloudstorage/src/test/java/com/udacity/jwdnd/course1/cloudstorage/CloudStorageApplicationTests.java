package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@Autowired
	CredentialService credentialService;

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
	public void unauthorizedUserccessHome(){
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login",driver.getTitle());
	}

	@Test
	public void unauthorizedUserAccessSignUp(){
		driver.get("http://localhost:" + this.port +"/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}

	@Test
    public void loginLogoutTest(){
		//sign up the user first
		driver.get("http:localhost:" + this.port + "/signup");
		WebElement elem = driver.findElement(By.id("inputFirstName"));
		elem.sendKeys("Jai");
		elem = driver.findElement(By.id("inputLastName"));
		elem.sendKeys("Priyadarshi");
		elem = driver.findElement(By.id("inputUsername"));
		elem.sendKeys("jai");
		elem = driver.findElement(By.id("inputPassword"));
		elem.sendKeys("1234Jai.");
		elem.submit();

		try {
			Thread.sleep(5000);
		}
		catch(Exception e){}

		//then login the user
		driver.get("http:localhost:" + this.port + "/login");
		elem = driver.findElement(By.id("inputUsername"));
		elem.sendKeys("jai");
		elem = driver.findElement(By.id("inputPassword"));
		elem.sendKeys("1234Jai.");
        elem.submit();
		driver.get("http:localhost:" + this.port + "/home");
		Assertions.assertEquals("Home", driver.getTitle());
		try {
			Thread.sleep(5000);
		}
		catch(Exception e){}
        elem = driver.findElement(By.name("logout"));
		elem.click();
		Assertions.assertEquals("Login", driver.getTitle());
		try {
			Thread.sleep(5000);
		}catch(Exception e){}
	}

	@Test
	public void CRUDNote(){
		//sign up the user first
		driver.get("http:localhost:" + this.port + "/signup");
		WebElement elem = driver.findElement(By.id("inputFirstName"));
		elem.sendKeys("Jai");
		elem = driver.findElement(By.id("inputLastName"));
		elem.sendKeys("Priyadarshi");
		elem = driver.findElement(By.id("inputUsername"));
		elem.sendKeys("jai");
		elem = driver.findElement(By.id("inputPassword"));
		elem.sendKeys("1234Jai.");
		elem.submit();
		try {
			Thread.sleep(5000);
		}catch(Exception e){}

		//then login the user
		driver.get("http:localhost:" + this.port + "/login");
		elem = driver.findElement(By.id("inputUsername"));
		elem.sendKeys("jai");
		elem = driver.findElement(By.id("inputPassword"));
		elem.sendKeys("1234Jai.");
		elem.submit();
		try {
			Thread.sleep(5000);
		}catch(Exception e){}

		driver.get("http:localhost:" + this.port + "/home");
		Assertions.assertEquals("Home", driver.getTitle());

		try {
			Thread.sleep(5000);
		}catch(Exception e){}

		//Start: Create Note
		elem = driver.findElement(By.xpath("//a[@href='#nav-notes']"));
		elem.click();

		try {
			Thread.sleep(5000);
		}catch(Exception e){}

		elem = driver.findElement(By.name("AddNote"));
		elem.click();

		try {
			Thread.sleep(5000);
		}catch(Exception e){}

		elem = driver.findElement(By.id("note-title"));
		elem.sendKeys("Test Note");

		elem = driver.findElement(By.id("note-description"));
		elem.sendKeys("Selenium created note");

		elem.submit();

		boolean noteTitleDescCreated = false;
		try {
			Thread.sleep(5000);
		}catch(Exception e){}

		elem = driver.findElement(By.xpath("//a[@href='#nav-notes']"));
		elem.click();
		elem = driver.findElement(By.id("userTable"));
		List <WebElement> thNodesUnderElem = elem.findElements(By.tagName("th"));
		List <WebElement> tdNodesUnderElem = elem.findElements(By.tagName("td"));
		for(int i = 0; i < thNodesUnderElem.size(); i++){
			if(thNodesUnderElem.get(i).getAttribute("innerHTML").equals("Test Note")){
				noteTitleDescCreated = true;
			}
		}
		Assertions.assertTrue(noteTitleDescCreated);
		noteTitleDescCreated = false;
		for(int i = 0; i < tdNodesUnderElem.size(); i++){
			if(tdNodesUnderElem.get(i).getAttribute("innerHTML").equals("Selenium created note")){
			   noteTitleDescCreated = true;
			}
		}
		Assertions.assertTrue(noteTitleDescCreated);

		try {
			Thread.sleep(5000);
		}catch(Exception e){}

		//Start: Create Note

		//Start: Edit Note
		WebElement editButtonElem = driver.findElement(By.id("userTable")).findElements(By.tagName("td")).get(0).findElement(By.tagName("button"));
		editButtonElem.click();

		try {
			Thread.sleep(5000);
		}catch(Exception e){}

		elem = driver.findElement(By.id("note-title"));
		elem.clear();
		elem.sendKeys("Test Note Edited");

		elem = driver.findElement(By.id("note-description"));
		elem.clear();
		elem.sendKeys("Selenium created note edited");

		try {
			Thread.sleep(5000);
		}catch(Exception e){}

		elem.submit();

		try {
			Thread.sleep(5000);
		}catch(Exception e){}

		noteTitleDescCreated = false;
		elem = driver.findElement(By.xpath("//a[@href='#nav-notes']"));
		elem.click();
		elem = driver.findElement(By.id("userTable"));
		thNodesUnderElem = elem.findElements(By.tagName("th"));
		tdNodesUnderElem = elem.findElements(By.tagName("td"));
		for(int i = 0; i < thNodesUnderElem.size(); i++){
			if(thNodesUnderElem.get(i).getAttribute("innerHTML").equals("Test Note Edited")){
				noteTitleDescCreated = true;
			}
		}
		Assertions.assertTrue(noteTitleDescCreated);
		noteTitleDescCreated = false;
		for(int i = 0; i < tdNodesUnderElem.size(); i++){
			if(tdNodesUnderElem.get(i).getAttribute("innerHTML").equals("Selenium created note edited")){
				noteTitleDescCreated = true;
			}
		}
		Assertions.assertTrue(noteTitleDescCreated);
		try {
			Thread.sleep(5000);
		}catch(Exception e){}
        //End: Edit Note

		//Start: Delete Note
		elem = driver.findElement(By.xpath("//a[@href='#nav-notes']"));
		elem.click();

		try {
			Thread.sleep(5000);
		}catch(Exception e){}

		noteTitleDescCreated = false;
		editButtonElem = driver.findElement(By.id("userTable")).findElements(By.tagName("td")).get(0).findElement(By.tagName("a"));
		editButtonElem.click();

		try {
			Thread.sleep(5000);
		}catch(Exception e){}

		elem = driver.findElement(By.xpath("//a[@href='#nav-notes']"));
		elem.click();

		try {
			Thread.sleep(5000);
		}catch(Exception e){}

		elem = driver.findElement(By.id("userTable"));
		thNodesUnderElem = elem.findElements(By.tagName("th"));
		tdNodesUnderElem = elem.findElements(By.tagName("td"));
		for(int i = 0; i < thNodesUnderElem.size(); i++){
			if(thNodesUnderElem.get(i).getAttribute("innerHTML").equals("Test Note Edited")){
				noteTitleDescCreated = true;
			}
		}
		Assertions.assertFalse(noteTitleDescCreated);
		noteTitleDescCreated = false;
		for(int i = 0; i < tdNodesUnderElem.size(); i++){
			if(tdNodesUnderElem.get(i).getAttribute("innerHTML").equals("Selenium created note edited")){
				noteTitleDescCreated = true;
			}
		}
		Assertions.assertFalse(noteTitleDescCreated);
		try {
			Thread.sleep(5000);
		}catch(Exception e){}
		//End: Delete Note
	}

	@Test
	public void CRUDCredential(){
		//sign up the user first
		driver.get("http:localhost:" + this.port + "/signup");
		WebElement elem = driver.findElement(By.id("inputFirstName"));
		elem.sendKeys("Jai");
		elem = driver.findElement(By.id("inputLastName"));
		elem.sendKeys("Priyadarshi");
		elem = driver.findElement(By.id("inputUsername"));
		elem.sendKeys("jai");
		elem = driver.findElement(By.id("inputPassword"));
		elem.sendKeys("1234Jai.");
		elem.submit();
		try {
			Thread.sleep(5000);
		}catch(Exception e){}

		//then login the user
		driver.get("http:localhost:" + this.port + "/login");
		elem = driver.findElement(By.id("inputUsername"));
		elem.sendKeys("jai");
		elem = driver.findElement(By.id("inputPassword"));
		elem.sendKeys("1234Jai.");
		elem.submit();
		try {
			Thread.sleep(5000);
		}catch(Exception e){}

		driver.get("http:localhost:" + this.port + "/home");
		Assertions.assertEquals("Home", driver.getTitle());

		try {
			Thread.sleep(5000);
		}catch(Exception e){}

		//Start: Create Credentials
		elem = driver.findElement(By.xpath("//a[@href='#nav-credentials']"));
		elem.click();

		try {
			Thread.sleep(5000);
		}catch(Exception e){}

		elem = driver.findElement(By.name("AddCred"));
		elem.click();

		try {
			Thread.sleep(5000);
		}catch(Exception e){}

		elem = driver.findElement(By.id("credential-url"));
		elem.sendKeys("http://localhost:8080");

		elem = driver.findElement(By.id("credential-username"));
		elem.sendKeys("jai");

		elem = driver.findElement(By.id("credential-password"));
		elem.sendKeys("1234Jai.");

		elem.submit();

		try {
			Thread.sleep(5000);
		}catch(Exception e){}

		elem = driver.findElement(By.xpath("//a[@href='#nav-credentials']"));
		elem.click();

		try {
			Thread.sleep(5000);
		}catch(Exception e){}


		boolean credCreated = false;
		elem = driver.findElement(By.id("credentialTable"));
		List <WebElement> thNodesUnderElem = elem.findElements(By.tagName("th"));
		List <WebElement> tdNodesUnderElem = elem.findElements(By.tagName("td"));
		for(int i = 0; i < thNodesUnderElem.size(); i++){
			if(thNodesUnderElem.get(i).getAttribute("innerHTML").equals("http://localhost:8080")){
				credCreated = true;
			}
		}
		Assertions.assertTrue(credCreated);
		boolean credUsername = false;
		boolean credPassword = false;
		for(int i = 0; i < tdNodesUnderElem.size(); i++){
			if(tdNodesUnderElem.get(i).getAttribute("innerHTML").equals("jai")){
				credUsername = true;
			}
			if(tdNodesUnderElem.get(i).getAttribute("innerHTML").equals(credentialService.getEncryptedPassword(1))){
				credPassword = true;
			}
		}
		Assertions.assertTrue(credUsername);
		Assertions.assertTrue(credPassword);
		try {
			Thread.sleep(5000);
		}catch(Exception e){}
        //End: Create Credentials


		//Start: Before Edit Credentials
		elem = driver.findElement(By.xpath("//a[@href='#nav-credentials']"));
		elem.click();
		try {
			Thread.sleep(5000);
		}catch(Exception e){}
		driver.findElement(By.id("credentialTable")).findElements(By.tagName("td")).get(0).findElement(By.tagName("button")).click();
		try {
			Thread.sleep(5000);
		}catch(Exception e){}
		Assertions.assertTrue(driver.findElement(By.id("credential-url")).getAttribute("value").equals("http://localhost:8080"));
		Assertions.assertTrue(driver.findElement(By.id("credential-username")).getAttribute("value").equals("jai"));
		Assertions.assertTrue(driver.findElement(By.id("credential-password")).getAttribute("value").equals("1234Jai."));
		//End: Before Edit Credentials

		//Start: After Edit Credentials
		elem = driver.findElement(By.id("credential-url"));
		elem.clear();
		elem.sendKeys("https://udacity.com");
		elem = driver.findElement(By.id("credential-username"));
		elem.clear();
		elem.sendKeys("bob");
		elem = driver.findElement(By.id("credential-password"));
		elem.clear();
		elem.sendKeys("1234bob.");
		elem.submit();
		try {
			Thread.sleep(5000);
		}catch(Exception e){}

		elem = driver.findElement(By.xpath("//a[@href='#nav-credentials']"));
		elem.click();

		try {
			Thread.sleep(5000);
		}catch(Exception e){}


		credCreated = false;
		elem = driver.findElement(By.id("credentialTable"));
		thNodesUnderElem = elem.findElements(By.tagName("th"));
		tdNodesUnderElem = elem.findElements(By.tagName("td"));
		for(int i = 0; i < thNodesUnderElem.size(); i++){
			if(thNodesUnderElem.get(i).getAttribute("innerHTML").equals("https://udacity.com")){
				credCreated = true;
			}
		}
		Assertions.assertTrue(credCreated);
		credUsername = false;
		credPassword = false;
		for(int i = 0; i < tdNodesUnderElem.size(); i++){
			if(tdNodesUnderElem.get(i).getAttribute("innerHTML").equals("bob")){
				credUsername = true;
			}
			if(tdNodesUnderElem.get(i).getAttribute("innerHTML").equals(credentialService.getEncryptedPassword(1))){
				credPassword = true;
			}
		}
		Assertions.assertTrue(credUsername);
		Assertions.assertTrue(credPassword);
		try {
			Thread.sleep(5000);
		}catch(Exception e){}
		//End: After Edit Credentials


		//Start: Delete Credentials
		driver.findElement(By.id("credentialTable")).findElements(By.tagName("td")).get(0).findElement(By.tagName("a")).click();
		try {
			Thread.sleep(5000);
		}catch(Exception e){}
		driver.findElement(By.xpath("//a[@href='#nav-credentials']")).click();
		try {
			Thread.sleep(5000);
		}catch(Exception e){}
		credCreated = false;
		elem = driver.findElement(By.id("credentialTable"));
		thNodesUnderElem = elem.findElements(By.tagName("th"));
		tdNodesUnderElem = elem.findElements(By.tagName("td"));
		for(int i = 0; i < thNodesUnderElem.size(); i++){
			if(thNodesUnderElem.get(i).getAttribute("innerHTML").equals("https://udacity.com")){
				credCreated = true;
			}
		}
		Assertions.assertFalse(credCreated);
		credUsername = false;
		credPassword = false;
		for(int i = 0; i < tdNodesUnderElem.size(); i++){
			if(tdNodesUnderElem.get(i).getAttribute("innerHTML").equals("bob")){
				credUsername = true;
			}
			if(tdNodesUnderElem.get(i).getAttribute("innerHTML").equals(credentialService.getEncryptedPassword(1))){
				credPassword = true;
			}
		}
		Assertions.assertFalse(credUsername);
		Assertions.assertFalse(credPassword);
		try {
			Thread.sleep(5000);
		}catch(Exception e){}
		//End: Delete Credentials

	}
}
