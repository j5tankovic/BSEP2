@access_control_rules
Feature: Groot Authorisation and Access Control
  Verify that the access control model is enforced so that only the authorised users have access to their own data

  @allowed_actions
  Scenario Outline: Users can view restricted urls for which they are authorised new
    Given a new intercepting proxy browser
    And cleared proxy logs
    And the login page
    And the user with the role <role>
    When user logs in
    And cleared proxy logs
    And user navigates to the allowed url <allowed_url>
    And he performs action <action>
    Then the status code in response should be 200
    Examples:
      | allowed_url               | action               |  role   |
      |/profile                   | editProfile          | STUDENT |
      |/courses/1/announcements   | addAnnouncement      | TEACHER |
      |/courses/1/announcements/1 | editAnnouncement     | TEACHER |
      |/courses/1/announcements/16| deleteAnnouncement   | TEACHER |
      |/profile                   | editProfile          | TEACHER |
      |/admin/users               | addUser              | ADMIN   |
      |/admin/users               | deleteUser           | ADMIN   |
      |/admin/courses             | addCourse            | ADMIN   |
      |/admin/courses             | deleteCourse         | ADMIN   |

  @forbidden_actions
  Scenario Outline: Users can view restricted urls for which they are authorised new
    Given a new intercepting proxy browser
    And cleared proxy logs
    And the login page
    And the user with the role <role>
    When user logs in
    And cleared proxy logs
    And user navigates to the not allowed url <not_allowed_url>
    And he performs action <action>
    Then the status code in response should be 403
    Examples:
      | not_allowed_url           | action               |  role   |
      |/admin/users               | addUser              | STUDENT |
      |/admin/users               | deleteUser           | STUDENT |
      |/admin/courses             | addCourse            | STUDENT |
      |/admin/courses             | deleteCourse         | STUDENT |
      |/courses/1/announcements   | addAnnouncement      | STUDENT |
      |/courses/1/announcements/2 | editAnnouncement     | STUDENT |
      |/courses/1/announcements/18| deleteAnnouncement   | STUDENT |
      |/admin/users               | addUser              | TEACHER |
      |/admin/users               | deleteUser           | TEACHER |
      |/admin/courses             | addCourse            | TEACHER |
      |/admin/courses             | deleteCourse         | TEACHER |

