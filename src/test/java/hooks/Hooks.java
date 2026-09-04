package hooks;

import context.TestContext;
import driver.DriverFactory;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.BeforeAll;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Hooks {

    private final TestContext context;
    private String baseUrl = "https://nhaindustries.azurewebsites.net/";

    public Hooks(TestContext context) {
        this.context = context;
        String configuredBaseUrl = context.dotenv.get("BASE_URL");
        if (configuredBaseUrl != null && !configuredBaseUrl.isBlank()) {
            this.baseUrl = configuredBaseUrl;
        }

        Logger seleniumLogger = Logger.getLogger("org.openqa.selenium.devtools");
        seleniumLogger.setLevel(Level.SEVERE); // only errors
        Logger seleniumRootLogger = Logger.getLogger("org.openqa.selenium");
        seleniumRootLogger.setLevel(Level.SEVERE);
    }

    @Before
    public void setUp(io.cucumber.java.Scenario scenario) {
        WebDriver driver = DriverFactory.getDriver();
        driver.get(baseUrl);

        System.out.println("Current URL: " + driver.getCurrentUrl());
        System.out.println("Title: " + driver.getTitle());
        System.out.println("Page source length: " + driver.getPageSource().length());

        System.out.println("Starting scenario: " + scenario.getName());
    }

    @After
    public void tearDown(io.cucumber.java.Scenario scenario)
    {
        WebDriver driver = DriverFactory.getDriver();

        // Capture screenshot if scenario failed
        if (scenario.isFailed() && driver != null) {
            try {
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "Failed Scenario Screenshot");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Clean up driver
        if (driver != null) {
            DriverFactory.cleanupDriver();
        }
    }

}
