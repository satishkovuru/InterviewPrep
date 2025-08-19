package practice;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class FirstTest {

    /**
     * This test launches a Chrome browser using Selenium Grid and verifies the title of the Google homepage.
     * It uses WebDriverManager to manage the ChromeDriver version.
     */
    @Test
    public void launchApp() throws MalformedURLException {
        ChromeOptions chromeOptions = new ChromeOptions();
        WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444"), chromeOptions);
        driver.get("http://www.google.com");
        String pageTitle = driver.getTitle();
        driver.findElement(By.name("q")).sendKeys("Selenium WebDriver");
        // Perform actions on the element
        WebElement element = driver.findElement(By.name("btnI"));
        element.isDisplayed();
        element.isEnabled();
        element.isSelected();
        System.out.println("Page title: " + pageTitle);
        Assert.assertEquals("Google", pageTitle);
        driver.quit();
    }

    @Test
    public void launchChromeDriverManager() throws MalformedURLException {
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444"), chromeOptions);
        driver.get("http://www.google.com");
        String pageTitle = driver.getTitle();
        System.out.println("Page title: " + pageTitle);
        Assert.assertEquals("Google", pageTitle);
        driver.quit();
    }

    @Test
    public void launchAppWithFireFox() throws MalformedURLException {
        WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444"), new FirefoxOptions());
        driver.get("http://www.google.com");
        String pageTitle = driver.getTitle();
        System.out.println("Page title: " + pageTitle);
        Assert.assertEquals("Google", pageTitle);
        driver.quit();
    }

    @Test
    public void launchAppWithEdge() throws MalformedURLException {
        WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444"), new EdgeOptions());
        driver.get("http://www.google.com");
        String pageTitle = driver.getTitle();
        System.out.println("Page title: " + pageTitle);
        Assert.assertEquals("Google", pageTitle);
        driver.quit();
    }

    @Test
    public void launchAppWithSafari() throws MalformedURLException {
        SafariOptions options = new SafariOptions();
        WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444"), options);
        driver.get("http://www.google.com");
        String pageTitle = driver.getTitle();
        System.out.println("Page title: " + pageTitle);
        Assert.assertEquals("Google", pageTitle);
        driver.quit();
    }
}