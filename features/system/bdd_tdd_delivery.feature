Feature: BDD and TDD delivery validation

  User Story: As a project maintainer, I want each delivery cycle to validate product behavior, system specifications, unit tests, and builds, so that releases stay aligned with documented requirements.

  Scenario: CI runs both behave BDD suites
    Given the GitHub Actions build workflow
    Then it should install the behave BDD runner
    And it should run the functional behave suite
    And it should run the system behave suite

  Scenario: CI runs unit tests before building the debug APK
    Given the GitHub Actions build workflow
    Then it should run the debug unit tests
    And it should build the debug APK after the debug unit tests

  Scenario: Developer documentation lists the end-of-cycle validation commands
    Given the project README
    Then it should document the functional behave suite command
    And it should document the system behave suite command
    And it should document the debug unit test command
    And it should document the debug APK build command
