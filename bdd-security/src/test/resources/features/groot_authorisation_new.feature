@grootauthorisationnew
Feature: Groot Authorisation and Access Control
  Verify that the access control model is enforced so that only the authorised users have access to their own data

  @allowed_urls_new
  Scenario Outline: Users can view restricted urls for which they are authorised new
    Given a new browser or client instance for grootAppNew
    And configured instance for logging proxy
    And clear prx logs
    And the login page for grootAppNew
    And the user with the role <role>
    When mister login
    And clear prx logs
    And he goes to the allowed url <allowed_url>
    And he executes <action>
    Then the status code in response should be 200
    Examples:
      | allowed_url               | action              |  role   |
      |/profile                   | editProfile         | TEACHER |

