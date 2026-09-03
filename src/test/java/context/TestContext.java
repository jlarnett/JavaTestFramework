package context;

import api.UsersApi;
import driver.DriverFactory;
import io.github.cdimascio.dotenv.Dotenv;
import org.openqa.selenium.WebDriver;

public class TestContext {

    public final Dotenv dotenv = Dotenv.load();
    private TestUser testUser;

    public WebDriver getDriver() {
        return DriverFactory.getDriver();
    }

    public TestUser getOrCreateTestUser() {
        if (testUser == null) {
            String password = dotenv.get("APP_PASSWORD");
            if (password == null || password.isBlank()) {
                password = "AutoTestPassword123!";
            }
            testUser = UsersApi.register(dotenv.get("NHA_API_BASE_URL"), password);
        }
        return testUser;
    }
}
