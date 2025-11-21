Feature: Login Feature

  Scenario: Home Page - Title Check
    Then I should see the title "Anime Social - Feed"

  Scenario: Successful Login
    And I navigate to login page
    And I login with valid credentials
    Then profile picture is visible "true"

  Scenario: Successful Login and Logout
    And I navigate to login page
    And I login with valid credentials
    Then profile picture is visible "true"
    And I logout
    Then profile picture is visible "false"