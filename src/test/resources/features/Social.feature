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

