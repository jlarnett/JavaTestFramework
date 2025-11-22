package steps;

import io.cucumber.java.en.*;
import context.TestContext;
import io.cucumber.java.en.Given;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import pages.shared.NavigationBar;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Execution(ExecutionMode.CONCURRENT)
public class NavigationSteps {

    private final TestContext context;

    public NavigationSteps(TestContext context) {
        this.context = context;
    }

    @Given("I navigate to {string}")
    public void i_navigate_to(String url) {
        context.getDriver().get(url);
    }

    @Given("I navigate to anime roll page")
    public void i_navigate_to_anime_roll_page() {
        var navigationBar = new NavigationBar(context.getDriver());
        navigationBar.clickAnimeRoll();
    }

    @Given("I navigate to anime wiki index page")
    public void i_navigate_to_anime_wiki_index_page() {
        var navigationBar = new NavigationBar(context.getDriver());
        navigationBar.clickAnimeWiki();
    }

    @Given("I navigate to game wiki index page")
    public void i_navigate_to_game_wiki_index_page() {
        var navigationBar = new NavigationBar(context.getDriver());
        navigationBar.clickGameWiki();
    }

    @Given("I navigate to forum index page")
    public void i_navigate_to_forum_index_page() {
        var navigationBar = new NavigationBar(context.getDriver());
        navigationBar.clickForums();
    }

    @Given("I navigate to crypto page")
    public void i_navigate_to_crypto_page() {
        var navigationBar = new NavigationBar(context.getDriver());
        navigationBar.clickCrypto();
    }

}
