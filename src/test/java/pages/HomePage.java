package pages;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HomePage {
    private final WebDriver driver;

    @FindBy(id = "ContentFeed")
    private WebElement contentFeed;

    @FindBy(className = "post-container")
    private List<WebElement> posts;

    @FindBy(css = "#MainPostTextboxContainer .note-editable")
    private WebElement mainPostInput;

    @FindBy(id = "SubmitBtn")
    private WebElement basicPostSubmitBtn;

    @FindBy(id = "AddPhotos")
    private WebElement uploadWithPhotosBtn;
    
    @FindBy(id = "MainPostTextboxValidationMessage")
    private WebElement validationMessage;

    @FindBy(className = "hide-comments")
    private WebElement commentsButton;

    @FindBy(className = "post-like")
    private WebElement likeButton;

    @FindBy(className = "post-dislike")
    private WebElement dislikeButton;

    @FindBy(className = "note-hint-group")
    private WebElement hintGroup;


    public HomePage(WebDriver driver) {
        // initializes @FindBy elements
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    /**
     * Enters text into home page's post summary.
     * @param text - the text to fill the post summary with
     */
    public void setMainPostInput(String text) {
        mainPostInput.click();
        mainPostInput.sendKeys(text);
    }

    /**
     * Clicks on the basic post submission button. Tries to submit whatever text located in mainPostInput
     */
    public void clickBasicPostSubmitBtn() throws InterruptedException {
        basicPostSubmitBtn.click();
    }

    /**
     * Clicks on the upload with photos button. Will open the personalized post modal
     */
    public void clickUploadWithPhotosBtn() {
        uploadWithPhotosBtn.click();
    }

    public void likeFirstPost() {
        var firstPost = posts.getFirst();
        var firstPostLikeButton = firstPost.findElement(By.cssSelector(".post-like"));

        //Try to move to element first
        var action =  new Actions(driver);
        action.moveToElement(firstPostLikeButton).perform();
        firstPostLikeButton.click();
    }

    public void dislikeFirstPost() {
        var firstPost = posts.getFirst();
        var firstPostDislikeButton = firstPost.findElement(By.cssSelector(".post-dislike"));

        var action =  new Actions(driver);
        action.moveToElement(firstPostDislikeButton).perform();
        firstPostDislikeButton.click();
    }

    /**
     * Tries to locate and click on like icon for specified postId
     * @param postId - Post we want to try liking
     */
    public void likePostById(Integer postId) {
        driver.findElement(By.cssSelector(String.format(".post-like[post-id='%s']", postId))).click();
    }

    /**
     * Tries to locate adn click on the dislike icon for specified postId
     * @param postId - post we want to try disliking
     */
    public void dislikePostById(Integer postId) {
        driver.findElement(By.cssSelector(String.format(".post-dislike[post-id='%s']", postId))).click();
    }

    /**
     * Checks the first posts in social feed for error message visibility. Asserts true/false based upon boolean supplied
     * @param shouldBeVisible - whether the error message is expected to be visible or not.
     */
    public void checkFirstPostErrorMessageVisibility(boolean shouldBeVisible) {
        var firstPost = posts.getFirst();
        var firstPostErrorMessage = firstPost.findElement(By.cssSelector("span[data-testid='post-error-message']"));

        if(shouldBeVisible) {
            Assertions.assertTrue(firstPostErrorMessage.isDisplayed());
        }
        else {
            Assertions.assertFalse(firstPostErrorMessage.isDisplayed());
        }
    }

    /**
     * Locates the first post in feed and validates the error message matches text
     * @param text - the test we expect to see in error message
     */
    public void checkFirstPostErrorMessageText(String text) {
        var firstPost = posts.getFirst();
        var firstPostErrorMessage = firstPost.findElement(By.cssSelector("span[data-testid='post-error-message']")).getText();

        Assertions.assertEquals(text, firstPostErrorMessage);
    }

    /**
     * Validates that the text within the first post in social feed matches the supplied value.
     * @param firstPostText - the expected text, we should see.
     */
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

    /**
     * Assert that the passed in postId is visible in feed
     * @param postId - the post we are looking for
     */
    public void checkPostIsVisible(Integer postId) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));


        // Wait until the <p> inside the first post contains the expected text
        By postSelector = By.cssSelector(String.format(".post-container[post-id='%s']", postId));
        WebElement post = wait.until(ExpectedConditions.visibilityOfElementLocated(postSelector));

        assert post != null;
        Assertions.assertTrue(post.isDisplayed());
    }

    /**
     * Checks whether the basic post form validation message is shown based upon boolean passed in
     * @param shouldBeVisible - whether the validation message is expected to be shown or not. Controls Assertion
     */
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

    public Integer clickBasicPostSubmitBtnAndGetCreatedPostId(int timeoutSeconds) {
        String previousTopPostId = null;
        By firstPostSelector = By.cssSelector(".post-container:first-of-type");

        if (!posts.isEmpty()) {
            previousTopPostId = posts.getFirst().getAttribute("post-id");
        }

        basicPostSubmitBtn.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        String finalPreviousTopPostId = previousTopPostId;
        wait.until(d -> {
            WebElement firstPost = d.findElement(firstPostSelector);
            String currentTopPostId = firstPost.getAttribute("post-id");
            if (currentTopPostId == null || currentTopPostId.isBlank()) {
                return false;
            }
            return finalPreviousTopPostId == null || !currentTopPostId.equals(finalPreviousTopPostId);
        });

        String createdPostId = driver.findElement(firstPostSelector).getAttribute("post-id");
        return Integer.parseInt(createdPostId);
    }

    public void clickTagItemByText(String text) {
        driver.findElement(By.xpath("//div[contains(@class,'note-hint-item') and text()='@Fullmetal Alchemist']")).click();
    }

    public void clickFirstTagItem() {
        //Try to wait for tag item dropdown to appear
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(hintGroup));

        //Setup actions
        Actions actions = new Actions(driver);

        // Press multiple keys
        actions.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).perform();
    }
}
