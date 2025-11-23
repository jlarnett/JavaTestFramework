package steps;

import context.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import pages.shared.NavigationBar;

public class SharedSteps {

    private final TestContext context;

    private NavigationBar navigationBar;

    public SharedSteps(TestContext context) {
        this.context = context;
    }

    @Given("I switch site theme to {string}")
    public void i_switch_site_theme_to(String theme) {
        navigationBar = new NavigationBar(context.getDriver());

        if(theme.equalsIgnoreCase("dark")) {
            navigationBar.applyTheme(NavigationBar.siteThemes.darkMode);
        }
        else if(theme.equalsIgnoreCase("light")) {
            navigationBar.applyTheme(NavigationBar.siteThemes.lightMode);
        }
        else {
            throw new IllegalStateException("Unrecognized theme " + theme);
        }
    }

    @Then("I should see theme dropdown text be {string}")
    public void i_(String themeDropdownText) {
        navigationBar = new NavigationBar(context.getDriver());
        navigationBar.checkThemeDropdownText(themeDropdownText);
    }
}