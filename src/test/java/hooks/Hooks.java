package hooks;

import context.TestContext;
import driver.DriverFactory;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;

public class Hooks {

    private final TestContext context;
    private final String baseUrl = "https://nhaindustries.azurewebsites.net/";

    public Hooks(TestContext context) {
        this.context = context;
    }

    @Before
    public void setUp(io.cucumber.java.Scenario scenario) {
        WebDriverManager.edgedriver().setup();

        WebDriver driver = DriverFactory.getDriver();
        driver.get(baseUrl);

        System.out.println("Starting scenario: " + scenario.getName());
    }

    @After
    public void tearDown() {
        DriverFactory.cleanupDriver();
    }
}
