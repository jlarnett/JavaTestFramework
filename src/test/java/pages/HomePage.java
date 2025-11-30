package pages;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity_contracts.ApiResponse;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v142.network.Network;
import org.openqa.selenium.devtools.v142.network.model.RequestId;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class HomePage {
    private DevTools devTools;
    private final WebDriver driver;

    private static final ObjectMapper mapper = new ObjectMapper();

    @FindBy(id = "ContentFeed")
    private WebElement contentFeed;

    @FindBy(className = "post-container")
    private List<WebElement> posts;

    @FindBy(css = ".note-editable")
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

        // initialize DevTools directly
        if (driver instanceof org.openqa.selenium.edge.EdgeDriver edgeDriver) {
            devTools = edgeDriver.getDevTools();
            devTools.createSession();
            devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()));
        }

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
        firstPostLikeButton.click();
    }

    public void dislikeFirstPost() {
        var firstPost = posts.getFirst();
        var firstPostDislikeButton = firstPost.findElement(By.cssSelector(".post-dislike"));
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

    /**
     * Tries to submit the basic post and capture API Response.
     * @param urlFragment - the api url to look for
     * @param timeoutSeconds - how long before the response capturing times out
     * @return - ApiResponse object containing JSON of Post
     * @throws Exception - can throw exceptions if network response times out
     */
    public ApiResponse clickBasicPostSubmitBtnAndGetResponseBody(String urlFragment, int timeoutSeconds) throws Exception {
        final String[] responseBodyHolder = new String[1];
        CountDownLatch latch = new CountDownLatch(1);

        devTools.addListener(Network.responseReceived(), event -> {
            if (event.getResponse().getUrl().contains(urlFragment)) {
                RequestId requestId = event.getRequestId(); // <-- requestId comes from the event
                // get the response body
                responseBodyHolder[0] = devTools.send(Network.getResponseBody(requestId)).getBody();
                latch.countDown();
            }
        });

        // Trigger the request
        basicPostSubmitBtn.click();

        // Wait for the response
        latch.await(timeoutSeconds, TimeUnit.SECONDS);

        return mapper.readValue(responseBodyHolder[0], ApiResponse.class);
    }

    public void clickTagItemByText(String text) {
        driver.findElement(By.xpath("//div[contains(@class,'note-hint-item') and text()='@Fullmetal Alchemist']")).click();
    }

    public void clickFirstTagItem() {
        //Try to wait for tag item dropdown to appear
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(hintGroup));

        //Setup actions
        Actions actions = new Actions(driver);

        // Press multiple keys
        actions.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).perform();
    }
}
