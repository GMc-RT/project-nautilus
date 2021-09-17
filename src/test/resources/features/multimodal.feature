@Multimodal
Feature: Multimodal

  @smoke
  Scenario: On MULTIMODAL check there are no console errors on page loads
    Given I open BadPage
    Then On Multimodal I see the title header is "MULTIMODAL ACCESS"   
    Then On Multimodal I see that there are no errors in the console log

  Scenario: On MULTIMODAL check the page response code (200, 302, 404, etc.)
    Given I open BadPage
    Then On Multimodal I see the title header is "MULTIMODAL ACCESS"
    Then On Multimodal I check the response code is "200"

  Scenario: On MULTIMODAL check that all links on the page go to another live (non 4xx) page
    Given I open BadPage
    Then On Multimodal I see the title header is "MULTIMODAL ACCESS"
    Then On Multimodal I see that none of the links on this page are broken