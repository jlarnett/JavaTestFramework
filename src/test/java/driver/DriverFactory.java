package driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.cdimascio.dotenv.Dotenv;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.util.concurrent.Semaphore;

public class DriverFactory {
    private static final Dotenv dotenv = Dotenv.load();
    private static final Semaphore semaphore = new Semaphore(Integer.parseInt(dotenv.get("WORKERS", "1")));
    static {
        System.out.println("Running global WebDriverManager setup...");
        WebDriverManager.edgedriver()
                .setup();
    }

    private static final ThreadLocal<WebDriver> driver = ThreadLocal.withInitial(() -> {
        try {
            semaphore.acquire(); // wait until a browser slot is free
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        EdgeOptions options = new EdgeOptions();
        boolean headless = Boolean.parseBoolean(dotenv.get("HEADLESS", "true"));
        if (headless) {
            options.addArguments("--headless");
        }
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");
        var edgeDriver = new EdgeDriver(options);

        //Try to force maximize
        //edgeDriver.manage().window().setSize(new Dimension(1920, 1080));
        //edgeDriver.manage().window().maximize();
        return edgeDriver;
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
