package pages.shared;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pages.LoginPage;

public class NavigationBar {

    private WebDriver driver;

    @FindBy(linkText = "Login")
    private WebElement loginButton;

    @FindBy(linkText = "Register")
    private WebElement registerButton;

    @FindBy(id = "ProfilePicture")
    private WebElement profilePicture;

    @FindBy(linkText = "Logout")
    private WebElement loginOutButton;

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

    /**
     * Validates that the profile picture element is visible in navbar
     */
    public void checkForProfilePicture(boolean shouldBeVisible) {
        final boolean isProfilePictureDisplayed = profilePicture.isDisplayed();

        if(shouldBeVisible)
            Assert.assertTrue(isProfilePictureDisplayed);
        else
            Assert.assertFalse(isProfilePictureDisplayed);
    }
}
