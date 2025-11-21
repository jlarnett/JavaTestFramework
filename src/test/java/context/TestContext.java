package context;

import driver.DriverFactory;
import io.github.cdimascio.dotenv.Dotenv;
import org.openqa.selenium.WebDriver;

public class TestContext {

    public final Dotenv dotenv = Dotenv.load();

    public WebDriver getDriver() {
        return DriverFactory.getDriver();
    }
}
