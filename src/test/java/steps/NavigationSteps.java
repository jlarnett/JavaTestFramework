package steps;

import io.cucumber.java.en.*;
import context.TestContext;
import io.cucumber.java.en.Given;

public class NavigationSteps {

    private final TestContext context;

    public NavigationSteps(TestContext context) {
        this.context = context;
    }

    @Given("I navigate to {string}")
    public void i_navigate_to(String url) {
        context.driver.get(url);
    }
}
