package hooks;

import context.TestContext;
import driver.DriverFactory;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import org.openqa.selenium.WebDriver;

public class Hooks {

    private final TestContext context;
    private String baseUrl = "https://nhaindustries.azurewebsites.net/";

    public Hooks(TestContext context) {
        this.context = context;
        this.baseUrl = context.dotenv.get("BASE_URL");
    }

    @Before
    public void setUp(io.cucumber.java.Scenario scenario) {
        WebDriver driver = DriverFactory.getDriver();
        driver.get(baseUrl);

        System.out.println("Starting scenario: " + scenario.getName());
    }

    @After
    public void tearDown()
    {
        DriverFactory.cleanupDriver();
    }

}
