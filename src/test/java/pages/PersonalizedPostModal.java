package pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PersonalizedPostModal {
    private WebDriver driver;

    @FindBy(css = "#CustomPostTextboxContainer .note-editable")
    private WebElement mainPostInput;

    @FindBy(id = "CustomPostValidationMessage")
    private WebElement customPostValidationMessage;

    @FindBy(id = "SubmitCustomPostBtn")
    private WebElement submitCustomPostBtn;

    @FindBy(id = "CustomPostImageFileInput")
    private WebElement customPostImageFileInput;

    @FindBy(id = "CustomPostForm")
    private WebElement customPostForm;

    public PersonalizedPostModal(WebDriver driver) {
        // initializes @FindBy elements
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void CheckCustomPostFormIsDisplayed(boolean shouldBeVisible) {
        if (shouldBeVisible) {
            //Try to wait until validation message is visible
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOf(customPostForm));
            Assertions.assertTrue(customPostForm.isDisplayed());
        }
        else {
            Assertions.assertFalse(customPostForm.isDisplayed());
        }
    }


}
