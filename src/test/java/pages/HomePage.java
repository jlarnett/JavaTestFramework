package pages;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    private WebDriver driver;

    @FindBy(id = "ContentFeed")
    private WebElement contentFeed;

    @FindBy(className = "post-container")
    private WebElement posts;

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

    public void checkValidationMessageIsDisplayed(Boolean shouldBeVisible) {

        if(shouldBeVisible) {
            Assert.assertTrue(validationMessage.isDisplayed());
        }
        else {
            Assert.assertFalse(validationMessage.isDisplayed());
        }
    }

}
