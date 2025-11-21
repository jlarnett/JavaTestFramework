package steps;
import io.cucumber.java.en.*;
import context.TestContext;
import org.junit.jupiter.api.Assertions;
import pages.LoginPage;
import pages.shared.NavigationBar;

public class LoginSteps {

    private final TestContext context;
    private LoginPage loginPage;
    private NavigationBar navigationBar;

    public LoginSteps(TestContext context) {   // <-- INJECTION HERE
        this.context = context;
    }

    @Given("I navigate to login page")
    public void i_navigate_to_login_page() {
        navigationBar = new NavigationBar(context.driver);
        loginPage = navigationBar.clickLogin();
    }

    @Given("I login with valid credentials")
    public void i_login_with_valid_credentials() {
        loginPage.login(context.dotenv.get("APP_USERNAME"), context.dotenv.get("APP_PASSWORD"));
    }

    @Given("I logout")
    public void i_logout() {
        navigationBar.clickLoginOut();
    }

    @Then("I should see the title {string}")
    public void i_should_see_the_title(String expectedTitle) {
        Assertions.assertEquals(expectedTitle, context.driver.getTitle());
    }

    @Then("profile picture is visible {string}")
    public void i_should_see_profile_picture(String shouldBeVisible) {
        navigationBar.checkForProfilePicture(Boolean.parseBoolean(shouldBeVisible));
    }
}