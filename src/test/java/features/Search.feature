Feature: Login Feature

  Scenario: Valid Site Anime Search
    Given I search for "FullMetal Alchemist"
    And I select first search result
    Then I should see the title "Anime Social - Fullmetal Alchemist"
