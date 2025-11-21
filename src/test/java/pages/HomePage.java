package pages;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HomePage {

    private WebDriver driver;

    @FindBy(id = "ContentFeed")
    private WebElement contentFeed;

    @FindBy(className = "post-container")
    private List<WebElement> posts;

    @FindBy(css = ".note-editable")
    private WebElement mainPostInput;

    @FindBy(id = "SubmitBtn")
    private WebElement basicPostSubmitBtn;

    @FindBy(id = "MainPostTextboxValidationMessage")
    private WebElement validationMessage;

    public HomePage(WebDriver driver) {
        // initializes @FindBy elements
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void setMainPostInput(String text) {
        mainPostInput.click();
        mainPostInput.sendKeys(text);
    }

    public void clickBasicPostSubmitBtn() {
        basicPostSubmitBtn.click();
    }

    public void checkFirstPostContainsString(String firstPostText) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        // Wait until the <p> inside the first post contains the expected text
        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.cssSelector(".post-container:first-of-type p"),
                firstPostText
        ));

        var text = posts.getFirst().findElement(By.cssSelector("p")).getText();
        Assertions.assertTrue(text.contains(firstPostText));
    }

    public void checkValidationMessageIsDisplayed(Boolean shouldBeVisible) {

        if(shouldBeVisible) {
            //Try to wait until validation message is visible
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOf(validationMessage));
            Assertions.assertTrue(validationMessage.isDisplayed());
        }
        else {
            Assertions.assertFalse(validationMessage.isDisplayed());
        }
    }

}
