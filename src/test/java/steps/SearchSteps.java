package steps;

import context.TestContext;
import io.cucumber.java.en.Given;
import pages.shared.NavigationBar;

public class SearchSteps {

    private final TestContext context;

    private NavigationBar navigationBar;

    public SearchSteps(TestContext context) {
        this.context = context;
    }

    @Given("I search for {string}")
    public void i_search_for(String searchTerm) {
        navigationBar = new NavigationBar(context.getDriver());
        navigationBar.setSearchInput(searchTerm);
    }

    @Given("I select first search result")
    public void i_select_first_search_result() {
        navigationBar.selectFirstSearchResult();
    }
}
