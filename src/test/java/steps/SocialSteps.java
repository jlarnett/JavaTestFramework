package steps;
import context.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import net.datafaker.Faker;
import pages.HomePage;
import pages.PersonalizedPostModal;

import java.util.Stack;

public class SocialSteps {

    private final TestContext context;
    private String randomPostText;

    Stack<Integer> postIds;

    /**
     * Constructor
     * @param context - Test Context
     */
    public SocialSteps(TestContext context) {
        this.context = context;
        postIds = new Stack<>();
    }

    @Given("I generate a random post")
    public void i_generate_a_random_post() {
        Faker faker = new Faker();
        randomPostText = String.valueOf(faker.lorem().paragraphs(2));
    }

    @Given("I add post summary message for {string}")
    public void i_add_post_summary_for(String summary) {
        var HomePage = new HomePage(context.getDriver());

        if(!summary.equals("random")) {
            HomePage.setMainPostInput(summary);
        }
        else {
            HomePage.setMainPostInput(randomPostText);
        }
    }

    @Given("I try to submit basic post")
    public void i_try_to_submit_basic_post() throws Exception {
        var HomePage = new HomePage(context.getDriver());
        var response = HomePage.clickBasicPostSubmitBtnAndGetResponseBody("/api/Posts/BasicPost", 10);
        postIds.push(response.post.id);
    }

    @Given("I click on upload with photos button")
    public void i_click_on_upload_with_photos_button() {
        var HomePage = new HomePage(context.getDriver());
        HomePage.clickUploadWithPhotosBtn();
    }

    @Given("I try to like first post")
    public void i_try_to_like_first_post() {
        var HomePage = new HomePage(context.getDriver());
        HomePage.likeFirstPost();
    }

    @Given("I try to dislike first post")
    public void i_try_to_dislike_first_post() {
        var HomePage = new HomePage(context.getDriver());
        HomePage.dislikeFirstPost();
    }

    @Given("I try to click on tag dropdown with text {string}")
    public void i_try_to_click_on_tag_dropdown_with_text(String text) {
        var homePage = new HomePage(context.getDriver());
        homePage.clickTagItemByText(text);
    }

    @Given("I try to select first tag in dropdown")
    public void i_try_to_select_first_tag_in_dropdown() {
        var homePage = new HomePage(context.getDriver());
        homePage.clickFirstTagItem();
    }

    @Then("I should see validation message is shown {string}")
    public void validation_message_is_shown(String shouldBeVisible) {
        var homePage = new HomePage(context.getDriver());
        homePage.checkValidationMessageIsDisplayed(Boolean.parseBoolean(shouldBeVisible));
    }

    @Then("I should see recently created post")
    public void i_should_see_recently_created_post() {
        var homePage = new HomePage(context.getDriver());
        homePage.checkFirstPostContainsString(randomPostText);
    }

    @Then("I should see recently created post by id")
    public void i_should_see_recently_created_post_by_id() {
        var homePage = new HomePage(context.getDriver());
        homePage.checkPostIsVisible(postIds.pop());
    }

    @Then("I should see personalized post modal is shown {string}")
    public void i_should_see_personalized_post_modal_is_shown(String shouldBeVisible) {
        var personalizedPostModal = new PersonalizedPostModal(context.getDriver());
        personalizedPostModal.CheckCustomPostFormIsDisplayed(Boolean.parseBoolean(shouldBeVisible));
    }

    @Then("I should see first post error message is shown {string}")
    public void i_should_see_First_post_error_message_is_shown(String shouldBeVisible) {
        var homePage = new HomePage(context.getDriver());
        homePage.checkFirstPostErrorMessageVisibility(Boolean.parseBoolean(shouldBeVisible));
    }

    @Then("I should see first post error message text is {string}")
    public void i_should_see_First_post_error_message_text_is(String text) {
        var homePage = new HomePage(context.getDriver());
        homePage.checkFirstPostErrorMessageText(text);
    }

}
