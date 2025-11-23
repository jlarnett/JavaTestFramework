package pages.shared;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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

    @FindBy(id = "themeDropdown")
    private WebElement themeDropdown;
    
    @FindBy(linkText = "Anime Roll")
    private WebElement animeRollLinkButton;

    @FindBy(linkText = "Anime Wiki")
    private WebElement animeWikiLinkButton;

    @FindBy(linkText = "Game Wiki")
    private WebElement gameWikiLinkButton;

    @FindBy(linkText = "Forums")
    private WebElement forumsLinkButton;

    @FindBy(linkText = "Crypto")
    private WebElement cryptoLinkButton;


    @FindBy(linkText = "Light Mode")
    private WebElement lightModeDropdownOption;

    @FindBy(linkText = "Dark Mode")
    private WebElement darkModeDropdownOption;
    
    public NavigationBar(WebDriver driver) {
        this.driver = driver;

        // initializes @FindBy elements
        PageFactory.initElements(driver, this);
    }

    public void clickRegister() {
        registerButton.click();
    }

    public void clickAnimeRoll() {
        animeRollLinkButton.click();
    }
    
    public void clickAnimeWiki() {
        animeWikiLinkButton.click();
    }

    public void clickGameWiki() {
        gameWikiLinkButton.click();
    }

    public void clickForums() {
        forumsLinkButton.click();
    }

    public void clickCrypto() {
        cryptoLinkButton.click();
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

    public enum siteThemes {
        lightMode,
        darkMode
    }

    /**
     * Uses navigation bar to apply light or dark mode theme
     * @param theme - Themes Enum object -> lightMode, darkMode
     */
    public void applyTheme(siteThemes theme) {
        themeDropdown.click();

        if(theme.equals(siteThemes.lightMode)) {
            lightModeDropdownOption.click();
        }
        else if (theme.equals(siteThemes.darkMode)) {
            darkModeDropdownOption.click();
        }
    }

    /**
     * Validates that the profile picture element is visible in navbar
     */
    public void checkForProfilePicture(boolean shouldBeVisible) {
        if(shouldBeVisible) {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOf(profilePicture));
            final boolean isProfilePictureDisplayed = profilePicture.isDisplayed();
            Assertions.assertTrue(isProfilePictureDisplayed);
        }
        else {
            // Element should not exist at all
            List<WebElement> profilePictureElements = driver.findElements(By.id("ProfilePicture"));
            Assertions.assertEquals(0, profilePictureElements.size());
        }
    }

    public void checkThemeDropdownText(String expectedThemeDropdownText) {
        var text = themeDropdown.getText();
        Assertions.assertEquals(expectedThemeDropdownText, text);
    }
}
