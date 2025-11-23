Feature: Social Feed

  Scenario: Submit post with character count < 10
    Given I navigate to login page
    And I login with valid credentials
    And I try to submit basic post
    Then I should see validation message is shown "true"

  Scenario: Submit post with valid summary
    Given I navigate to login page
    And I login with valid credentials
    And I generate a random post
    And I add post summary message for "random"
    And I try to submit basic post
    Then I should see validation message is shown "false"
    Then I should see recently created post
    
  Scenario: I switch website theme to dark successfully
    Given I switch site theme to "dark"
    Then I should see theme dropdown text be "Theme: Dark"

  Scenario: I switch website theme to light successfully
    Given I switch site theme to "light"
    Then I should see theme dropdown text be "Theme: Light"

  Scenario: Clicking on upload with photos buttons displays personalized post modal
    Given I navigate to login page
    And I login with valid credentials
    And I click on upload with photos button
    Then I should see personalized post modal is shown "true"
