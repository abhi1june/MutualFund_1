package base;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SystemUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class MutualFund {

	private WebDriver driver;

	@BeforeClass
	public static void setupClass() {
		System.out.println(System.getProperty("user.dir"));
		WebDriverManager.chromedriver().setup();
		//System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/Driver/chromedriver");
		
	}

	@BeforeMethod
	public void setupTest() {
		ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");     
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1400,800");  
		driver = new ChromeDriver();
	}

	@AfterMethod
	public void teardown() {
		if (driver != null) {
			driver.quit();
		}
	}

	@Test
	public void CompleteTest() throws InterruptedException, IOException {
		
		driver.manage().window().maximize();
		driver.get("https://upstox.com/mutual-funds/portfolio");
		Thread.sleep(5000);
		driver.findElement(By.xpath("//input[@name='userId']")).sendKeys("2ZANVR");
		Thread.sleep(5000);
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys("Most@wanted1");
		Thread.sleep(5000);
		driver.findElement(By.xpath("//button[@id='submit-btn']//div[contains(text(),'Sign')]")).click();
		Thread.sleep(5000);
		driver.findElement(By.xpath("//input[@name='yob']")).sendKeys("1993");
		Thread.sleep(10000);
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String output = System.getProperty("user.dir")+"\\ScreenShots\\FundPage.jpeg";
		System.out.println(output);
		try {
			FileUtils.copyFile(screenshot, new File(output));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		
		//===============================================================================

		String to = "abhishek1june@outlook.com";
        // Sender's email ID needs to be mentioned
        String from = "abhihek1june93@gmail.com";

        // Assuming you are sending email from through gmails smtp
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication("abhishek1june93@gmail.com", "Most@wanted2");

            }

        });

        // Used to debug SMTP issues
        session.setDebug(true);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("[Auto Generated] - Fund Page Snap");

            // Now set the actual message
            message.setText("Fund Page Snap");
            
            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(new File(output));
            
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(attachmentPart);
            
            message.setContent(multipart);

            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
		
		//==============================================================================
	}

}
