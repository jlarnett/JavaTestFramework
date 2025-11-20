package stepDefinitions;

import io.cucumber.java.en.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

import static org.junit.Assert.*;

public class LoginSteps {

    WebDriver driver;

    @Given("I open the browser")
    public void i_open_the_browser() {
        WebDriverManager.edgedriver().setup();
        driver = new EdgeDriver();
    }

    @Given("I navigate to {string}")
    public void i_navigate_to(String url) {
        driver.get(url);
    }

    @Then("I should see the title {string}")
    public void i_should_see_the_title(String expectedTitle) {
        assertEquals(expectedTitle, driver.getTitle());
        driver.quit();
    }
}