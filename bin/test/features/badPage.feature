@BadPage
Feature: BadPage

  # @smoke
  # Scenario: On BADPAGE check there are no console errors on page loads
  #   Given I open BadPage
  #   Then On BadPage I see the title header is "DOCUMENT NOT FOUND"   
  #   # Then On BadPage I see that there are no errors in the console log

  Scenario: On BADPAGE check the page response code (200, 302, 404, etc.)
    Given I open BadPage
    Then On BadPage I see the title header is "DOCUMENT NOT FOUND"
    Then On BadPage I check the response code is "404"

  # Scenario: On BADPAGE check that all links on the page go to another live (non 4xx) page
  #   Given I open BadPage
  #   Then On BadPage I see the title header is "DOCUMENT NOT FOUND"
  #   # Then On BadPage I see that none of the links on this page are broken