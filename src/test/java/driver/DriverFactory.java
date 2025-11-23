package driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.util.concurrent.Semaphore;

public class DriverFactory {
    private static final Semaphore semaphore = new Semaphore(3); // 2 browsers max
    static {
        System.out.println("Running global WebDriverManager setup...");
        WebDriverManager.edgedriver()
                .avoidBrowserDetection()
                .setup();
    }

    private static final ThreadLocal<WebDriver> driver = ThreadLocal.withInitial(() -> {
        try {
            semaphore.acquire(); // wait until a browser slot is free
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        EdgeOptions options = new EdgeOptions();
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--no-sandbox");
        return new EdgeDriver(options);
    });

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void cleanupDriver() {
        driver.get().quit();
        driver.remove();
        semaphore.release(); // free the slot for another browser
    }
}
