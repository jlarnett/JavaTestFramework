package context;

import io.github.cdimascio.dotenv.Dotenv;
import org.openqa.selenium.WebDriver;

public class TestContext {
    public WebDriver driver;
    public final Dotenv dotenv = Dotenv.load();
}
