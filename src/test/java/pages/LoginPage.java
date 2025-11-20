package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    private WebDriver driver;

    @FindBy(id = "Input_Email")
    private WebElement loginEmailInput;

    @FindBy(id = "Input_Password")
    private WebElement loginPasswordInput;

    @FindBy(id = "login-submit")
    private WebElement loginButton;

    public LoginPage(WebDriver driver) {
        this.driver = driver;

        // initializes @FindBy elements
        PageFactory.initElements(driver, this);
    }

    /**
     * Assuming we have navigated to login page already, enter credentials into input fields and click login button
     * @param email - email we want to authenticate with
     * @param password - password for user
     */
    public void login(String email, String password) {
        loginEmailInput.sendKeys(email);
        loginPasswordInput.sendKeys(password);
        loginButton.click();
    }
}
