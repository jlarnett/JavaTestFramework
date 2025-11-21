package pages.shared;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pages.LoginPage;

import java.time.Duration;
import java.util.List;

public class NavigationBar {

    private final WebDriver driver;

    @FindBy(linkText = "Login")
    private WebElement loginButton;

    @FindBy(linkText = "Register")
    private WebElement registerButton;

    @FindBy(id = "ProfilePicture")
    private WebElement profilePicture;

    @FindBy(linkText = "Logout")
    private WebElement loginOutButton;

    @FindBy(id = "searchInput")
    private WebElement searchInput;

    @FindBy(id = "searchDropdown")
    private WebElement searchDropdown;

    public NavigationBar(WebDriver driver) {
        this.driver = driver;

        // initializes @FindBy elements
        PageFactory.initElements(driver, this);
    }

    public void clickRegister() {
        registerButton.click();
    }

    public LoginPage clickLogin() {
        loginButton.click();
        return new LoginPage(driver);
    }

    public void clickLoginOut() {
        loginOutButton.click();
    }

    public void setSearchInput(String text) {
        searchInput.sendKeys(text);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
    }

    public void selectFirstSearchResult() {
        var results = searchDropdown.findElements(By.cssSelector("li"));

        if(!results.isEmpty()) {
            results.getFirst().click();
        }

    }

    /**
     * Validates that the profile picture element is visible in navbar
     */
    public void checkForProfilePicture(boolean shouldBeVisible) {
        if(shouldBeVisible) {
            final boolean isProfilePictureDisplayed = profilePicture.isDisplayed();
            Assertions.assertTrue(isProfilePictureDisplayed);
        }
        else {
            // Element should not exist at all
            List<WebElement> profilePictureElements = driver.findElements(By.id("ProfilePicture"));
            Assertions.assertEquals(0, profilePictureElements.size());
        }
    }
}
