package driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class DriverFactory {

    private static final ThreadLocal<WebDriver> driver = ThreadLocal.withInitial(() -> {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");

        return new EdgeDriver(options);
    });

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void cleanupDriver() {
        driver.get().quit();
        driver.remove();
    }
}