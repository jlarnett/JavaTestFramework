package hooks;

import context.TestContext;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.edge.EdgeDriver;

public class Hooks {

    private final TestContext context;

    public Hooks(TestContext context) {
        this.context = context;
    }

    @Before
    public void setUp(io.cucumber.java.Scenario scenario) {
        WebDriverManager.edgedriver().setup();
        context.driver = new EdgeDriver();
        System.out.println("Starting scenario: " + scenario.getName());
    }

    @After
    public void tearDown() {
        if (context.driver != null) {
            context.driver.quit();
        }
    }
}
