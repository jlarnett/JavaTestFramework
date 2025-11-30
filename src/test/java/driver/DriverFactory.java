package driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.util.concurrent.Semaphore;

public class DriverFactory {
    private static final Semaphore semaphore = new Semaphore(5); // 5 browsers max
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

        var edgeDriver = new EdgeDriver(options);

        //Try to force maximize
        edgeDriver.manage().window().setSize(new Dimension(1920, 1080));
        edgeDriver.manage().window().maximize();

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
