package Page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ConfigReader;

/**
 * Page Object Model for the application's home page.
 * Encapsulates interactions with the home page elements and actions.
 */
public class HomePage {
    /** SLF4J logger for logging actions and errors. */
    private static final Logger logger = LoggerFactory.getLogger(HomePage.class);

    /** WebDriver instance used for browser interactions. */
    private final WebDriver driver;

    /** Search box input field located by name 'q'. */
    @FindBy(name = "q")
    private WebElement searchBox;

    /** "I'm Feeling Lucky" button located by name 'btnI'. */
    @FindBy(name = "btnI")
    private WebElement feelingLuckyButton;

    /**
     * Constructs a HomePage object and initializes web elements using PageFactory.
     *
     * @param driver the WebDriver instance to use
     */
    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Navigates the browser to the home page URL specified in the configuration.
     */
    public void navigateToHomePage() {
        final String baseUrl = ConfigReader.get("baseUrl");
        driver.get(baseUrl);
        logger.info("Navigated to home page: {}", baseUrl);
    }

    /**
     * Enters the given search query into the search box.
     *
     * @param query the search query to enter
     * @throws RuntimeException if unable to interact with the search box
     */
    public void search(String query) {
        try {
            searchBox.sendKeys(query);
            logger.info("Entered search query: {}", query);
        } catch (Exception e) {
            logger.error("Failed to enter search query", e);
            throw e;
        }
    }

    /**
     * Interacts with the "I'm Feeling Lucky" button if it is visible and enabled.
     *
     * @throws RuntimeException if unable to interact with the button
     */
    public void interactWithFeelingLucky() {
        try {
            if (feelingLuckyButton.isDisplayed() && feelingLuckyButton.isEnabled()) {
                feelingLuckyButton.isSelected();
                logger.info("Interacted with 'I'm Feeling Lucky' button");
            }
        } catch (Exception e) {
            logger.error("Failed to interact with 'I'm Feeling Lucky' button", e);
            throw e;
        }
    }

    /**
     * Retrieves the current page title.
     *
     * @return the title of the current page
     */
    public String getPageTitle() {
        final String pageTitle = driver.getTitle();
        logger.info("Page title: {}", pageTitle);
        return pageTitle;
    }

    /**
     * Quits the WebDriver instance, closing the browser.
     */
    public void quit() {
        if (driver != null) {
            driver.quit();
            logger.info("WebDriver quit successfully");
        }
    }
}