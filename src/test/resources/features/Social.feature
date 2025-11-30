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

  Scenario: Clicking like button while not logged in shows validation message
    Given I try to like first post
    Then I should see first post error message is shown "true"
    Then I should see first post error message text is "Login required to like social media posts & comments"

  Scenario: Clicking dislike button while not logged in shows validation message
    Given I try to dislike first post
    Then I should see first post error message is shown "true"
    Then I should see first post error message text is "Login required to dislike social media posts & comments"

  Scenario: Submit post with valid summary and like post
    Given I navigate to login page
    And I login with valid credentials
    And I generate a random post
    And I add post summary message for "random"
    And I try to submit basic post
    Then I should see validation message is shown "false"
    Then I should see recently created post
    And I try to like first post
    Then I should see first post error message is shown "false"

  Scenario: Submit post with tagged anime
    Given I navigate to login page
    And I login with valid credentials
    And I add post summary message for "@fullmetal"
    And I try to select first tag in dropdown
    And I try to submit basic post
    Then I should see validation message is shown "false"
    Then I should see recently created post by id