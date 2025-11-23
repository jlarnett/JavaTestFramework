package steps;
import context.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import net.datafaker.Faker;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import pages.HomePage;
import pages.PersonalizedPostModal;

//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@Execution(ExecutionMode.CONCURRENT)
public class SocialSteps {

    private final TestContext context;

    private String randomPostText;

    public SocialSteps(TestContext context) {
        this.context = context;
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
    public void i_try_to_submit_basic_post() {
        var HomePage = new HomePage(context.getDriver());
        HomePage.clickBasicPostSubmitBtn();
    }

    @Given("I click on upload with photos button")
    public void i_click_on_upload_with_photos_button() {
        var HomePage = new HomePage(context.getDriver());
        HomePage.clickUploadWithPhotosBtn();
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

    @Then("I should see personalized post modal is shown {string}")
    public void i_should_see_personalized_post_modal_is_shown(String shouldBeVisible) {
        var personalizedPostModal = new PersonalizedPostModal(context.getDriver());
        personalizedPostModal.CheckCustomPostFormIsDisplayed(Boolean.parseBoolean(shouldBeVisible));
    }


}
