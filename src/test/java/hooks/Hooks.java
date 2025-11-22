package hooks;

import context.TestContext;
import driver.DriverFactory;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.After;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;

public class Hooks {

    private final TestContext context;
    private String baseUrl = "https://nhaindustries.azurewebsites.net/";

    public Hooks(TestContext context) {
        this.context = context;
        this.baseUrl = context.dotenv.get("BASE_URL");
    }

    @BeforeAll
    public static void globalSetup() {
        // Pre-download the driver BEFORE parallel threads start
        WebDriverManager.edgedriver()
                .avoidBrowserDetection()
                .setup();
    }
    
    @Before
    public void setUp(io.cucumber.java.Scenario scenario) {
        WebDriver driver = DriverFactory.getDriver();
        driver.get(baseUrl);

        System.out.println("Starting scenario: " + scenario.getName());
    }

    @After
    public void tearDown() {
        DriverFactory.cleanupDriver();
    }
}
