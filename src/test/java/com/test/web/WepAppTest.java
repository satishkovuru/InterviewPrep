package com.test.web;

import Page.HomePage;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;


public class WepAppTest {

    private static final Logger logger = LoggerFactory.getLogger(WepAppTest.class);
    private HomePage homePage;


//    @Parameters("browser")
//    @BeforeMethod(alwaysRun = true)
//    public void setUp(@Optional("chrome") String browser) throws MalformedURLException {
//        WebDriver driver;
//        String gridUrl = System.getenv().getOrDefault("SELENIUM_GRID_URL", "http://localhost:4444/wd/hub");
//        // Always use Selenium Grid
//        try {
//            if (browser.equalsIgnoreCase("chrome")) {
//                driver = new RemoteWebDriver(new URL(gridUrl), new ChromeOptions());
//            } else if (browser.equalsIgnoreCase("firefox")) {
//                driver = new RemoteWebDriver(new URL(gridUrl), new FirefoxOptions());
//            } else if (browser.equalsIgnoreCase("edge")) {
//                EdgeOptions options = new EdgeOptions();
//                String userDataDir = System.getProperty("java.io.tmpdir") + "/edge_profile_" + System.currentTimeMillis();
//                options.addArguments("--user-data-dir=" + userDataDir);
//                driver = new RemoteWebDriver(new URL(gridUrl), options);
//            } else if (browser.equalsIgnoreCase("safari")) {
//                driver = new RemoteWebDriver(new URL(gridUrl), new SafariOptions());
//            } else {
//                throw new IllegalArgumentException("Browser not supported: " + browser);
//            }
//            homePage = new HomePage(driver);
//            logger.info("WebDriver initialized for browser: {}", browser);
//        } catch (Exception e) {
//            logger.error("Failed to initialize WebDriver for browser: {}", browser, e);
//            throw e;
//        }
//    }

    @BeforeMethod(alwaysRun = true)
    public void setUp(@Optional("chrome") String browser) throws MalformedURLException {
        WebDriver driver;
        MutableCapabilities capabilities = new MutableCapabilities();
        driver = new RemoteWebDriver(new URL("http://hub-cloud.browserstack.com/wd/hub"), capabilities);
        homePage = new HomePage(driver);
        logger.info("WebDriver initialized for browser: {}", browser);
    }


    @DataProvider(name = "searchQueries")
    public Object[][] searchQueries() {
        return new Object[][]{{"Selenium WebDriver"}, {"TestNG"}, {"PageFactory"}};
    }


    @Test(groups = "smoke", dataProvider = "searchQueries")
    public void launchApp(String query) {
        homePage.navigateToHomePage();
        homePage.search(query);
        homePage.interactWithFeelingLucky();
        String title = homePage.getPageTitle();
        Assert.assertEquals(title, "Google");
    }


    @DataProvider(name = "invalidSearchQueries")
    public Object[][] invalidSearchQueries() {
        return new Object[][]{{""}, {null}, {"   "}};
    }


    @Test(groups = "edge", dataProvider = "invalidSearchQueries")
    public void testInvalidSearchQueries(String query) {
        homePage.navigateToHomePage();
        try {
            homePage.search(query);
            homePage.interactWithFeelingLucky();
            String title = homePage.getPageTitle();
            Assert.assertNotNull(title);
        } catch (Exception e) {
            logger.warn("Exception occurred for invalid query: {}", query, e);
            Assert.assertTrue(e instanceof IllegalArgumentException || e instanceof NullPointerException);
        }
    }

    /**
     * Tests setup with an unsupported browser name.
     */
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUnsupportedBrowser() throws MalformedURLException {
        setUp("unsupportedBrowser");
    }

    /**
     * Cleans up the WebDriver instance after each test method.
     * Ensures the browser is closed properly.
     */
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (homePage != null) {
            homePage.quit();
        }
    }
}