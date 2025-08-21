package com.test.web;

import Page.HomePage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class WepAppTest {

    private HomePage homePage;

    @Parameters("browser")
    @BeforeMethod(alwaysRun = true)
    public void setUp(String browser) throws MalformedURLException {
        WebDriver driver;
        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new RemoteWebDriver(new URL("http://localhost:4444"), new ChromeOptions());
        } else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new RemoteWebDriver(new URL("http://localhost:4444"), new FirefoxOptions());
        } else if (browser.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            EdgeOptions options = new EdgeOptions();
            String userDataDir = System.getProperty("java.io.tmpdir") + "/edge_profile_" + System.currentTimeMillis();
            options.addArguments("--user-data-dir=" + userDataDir);
            driver = new RemoteWebDriver(new URL("http://localhost:4444"), options);
        } else if (browser.equalsIgnoreCase("safari")) {
            driver = new RemoteWebDriver(new URL("http://localhost:4444"), new SafariOptions());
        } else {
            throw new IllegalArgumentException("Browser not supported: " + browser);
        }
        homePage = new HomePage(driver);
    }

    @Test(groups = "smoke")
    public void launchApp() {
        homePage.navigateToHomePage();
        homePage.search("Selenium WebDriver");
        homePage.interactWithFeelingLucky();
        String title = homePage.getPageTitle();
        Assert.assertEquals(title, "Google");
        homePage.quit();
    }
}