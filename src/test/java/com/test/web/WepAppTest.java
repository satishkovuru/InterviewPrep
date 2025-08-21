package com.test.web;

import Page.HomePage;
import io.github.bonigarcia.wdm.WebDriverManager;
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

/**
 * Test class for web application functionality.
 * Uses TestNG for test execution and Selenium WebDriver for browser automation.
 */
public class WepAppTest {

    private static final Logger logger = LoggerFactory.getLogger(WepAppTest.class);
    private HomePage homePage;

    /**
     * Helper to check if Selenium Grid is available.
     */
    private boolean isGridAvailable(String gridUrl) {
        try {
            URL url = new URL(gridUrl);
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(2000);
            conn.connect();
            int code = conn.getResponseCode();
            return code < 500; // Grid returns 200 or 4xx if up
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Sets up the WebDriver instance based on the specified browser.
     *
     * @param browser The name of the browser to use (e.g., "chrome", "firefox", "edge", "safari").
     * @throws MalformedURLException If the WebDriver URL is invalid.
     */
    @Parameters("browser")
    @BeforeMethod(alwaysRun = true)
    public void setUp(@Optional("chrome") String browser) throws MalformedURLException {
        WebDriver driver;
        String gridUrl = System.getenv().getOrDefault("SELENIUM_GRID_URL", "http://localhost:4444/wd/hub");
        boolean useGrid = isGridAvailable(gridUrl);
        try {
            if (browser.equalsIgnoreCase("chrome")) {
                WebDriverManager.chromedriver().setup();
                if (useGrid) {
                    driver = new RemoteWebDriver(new URL(gridUrl), new ChromeOptions());
                } else {
                    driver = WebDriverManager.chromedriver().create();
                }
            } else if (browser.equalsIgnoreCase("firefox")) {
                WebDriverManager.firefoxdriver().setup();
                if (useGrid) {
                    driver = new RemoteWebDriver(new URL(gridUrl), new FirefoxOptions());
                } else {
                    driver = WebDriverManager.firefoxdriver().create();
                }
            } else if (browser.equalsIgnoreCase("edge")) {
                WebDriverManager.edgedriver().setup();
                EdgeOptions options = new EdgeOptions();
                String userDataDir = System.getProperty("java.io.tmpdir") + "/edge_profile_" + System.currentTimeMillis();
                options.addArguments("--user-data-dir=" + userDataDir);
                if (useGrid) {
                    driver = new RemoteWebDriver(new URL(gridUrl), options);
                } else {
                    driver = WebDriverManager.edgedriver().create();
                }
            } else if (browser.equalsIgnoreCase("safari")) {
                if (useGrid) {
                    driver = new RemoteWebDriver(new URL(gridUrl), new SafariOptions());
                } else {
                    driver = new org.openqa.selenium.safari.SafariDriver();
                }
            } else {
                throw new IllegalArgumentException("Browser not supported: " + browser);
            }
            homePage = new HomePage(driver);
            logger.info("WebDriver initialized for browser: {} (grid: {})", browser, useGrid);
        } catch (Exception e) {
            logger.error("Failed to initialize WebDriver for browser: {} (grid: {})", browser, useGrid, e);
            throw e;
        }
    }

    /**
     * Provides test data for search queries.
     *
     * @return A two-dimensional array of objects, where each inner array contains
     * a single search query string to be used as test data.
     */
    @DataProvider(name = "searchQueries")
    public Object[][] searchQueries() {
        return new Object[][]{{"Selenium WebDriver"}, {"TestNG"}, {"PageFactory"}};
    }

    /**
     * Tests the application by navigating to the home page, performing a search,
     * interacting with the "I'm Feeling Lucky" button, and verifying the page title.
     *
     * @param query The search query to use in the test.
     */
    @Test(groups = "smoke", dataProvider = "searchQueries")
    public void launchApp(String query) {
        homePage.navigateToHomePage();
        homePage.search(query);
        homePage.interactWithFeelingLucky();
        String title = homePage.getPageTitle();
        Assert.assertEquals(title, "Google");
    }

    /**
     * Provides test data for invalid search queries.
     *
     * @return A two-dimensional array of objects, where each inner array contains
     * a single invalid search query string to be used as test data.
     */
    @DataProvider(name = "invalidSearchQueries")
    public Object[][] invalidSearchQueries() {
        return new Object[][]{{""}, {null}, {"   "}};
    }

    /**
     * Tests the application with invalid search queries (empty, null, whitespace).
     */
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