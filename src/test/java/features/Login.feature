Feature: Login Feature

  Scenario: Home Page - Title Check
    Given I navigate to "https://nhaindustries.azurewebsites.net/"
    Then I should see the title "Anime Social - Feed"

  Scenario: Successful Login
    Given I navigate to "https://nhaindustries.azurewebsites.net/"
    And I navigate to login page
    And I login with valid credentials
    Then profile picture is visible "true"

  Scenario: Successful Login and Logout
    Given I navigate to "https://nhaindustries.azurewebsites.net/"
    And I navigate to login page
    And I login with valid credentials
    Then profile picture is visible "true"
    And I logout
    Then profile picture is visible "false"