package Page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import util.ConfigReader;

public class HomePage {
    private final WebDriver driver;
    private final By searchBox = By.name("q");
    private final By feelingLuckyButton = By.name("btnI");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void navigateToHomePage() {
        String baseUrl = ConfigReader.get("baseUrl");
        driver.get(baseUrl);
    }

    public void search(String query) {
        driver.findElement(searchBox).sendKeys(query);
    }

    public void interactWithFeelingLucky() {
        WebElement element = driver.findElement(feelingLuckyButton);
        element.isDisplayed();
        element.isEnabled();
        element.isSelected();
    }

    public String getPageTitle() {
        String pageTitle = driver.getTitle();
        System.out.println("Page title: " + pageTitle);
        return pageTitle;
    }

    public void quit() {
        driver.quit();
    }
}