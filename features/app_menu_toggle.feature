Feature: Toggle the app menu by clicking

  User Story: As a launcher user, I want clicking the menu area to toggle the app menu, so that I can open and close it without relying on swipe gestures.

  Scenario: Open the app menu by clicking when it is closed
    Given the app menu is closed
    When I click the menu
    Then the app menu should be open

  Scenario: Close the app menu by clicking when it is open
    Given the app menu is open
    When I click the menu
    Then the app menu should be closed
