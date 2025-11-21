package steps;

import io.cucumber.java.en.*;
import context.TestContext;
import io.cucumber.java.en.Given;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Execution(ExecutionMode.CONCURRENT)
public class NavigationSteps {

    private final TestContext context;

    public NavigationSteps(TestContext context) {
        this.context = context;
    }

    @Given("I navigate to {string}")
    public void i_navigate_to(String url) {
        context.getDriver().get(url);
    }
}
