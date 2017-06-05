@grootauthorisation
Feature: Groot Authorisation and Access Control
  Verify that the access control model is enforced so that only the authorised users have access to their own data

  @allowed_urls
  Scenario Outline: Users can view restricted urls for which they are authorised
    Given a new browser or client instance for grootApp
    And the login page for grootApp
    And the role <role>
    When the user with the role logs in
    And they execute: <action>
    Then the url: <allowed_url> should be visible to user
    Examples:
      | allowed_url               | action              | role    |
      |/home                      | viewCourses         | teacher |
      |/courses/1/people          | viewPeople          | teacher |
      |/courses/1/announcements   | viewAnnouncements   | teacher |
      |/courses/1/announcements   | addAnnouncement     | teacher |
      |/courses/1/announcements/1 | viewAnnouncement    | teacher |
      |/courses/1/announcement/1  | editAnnouncement    | teacher |
      |/courses/1/announcement/1  | deleteAnnouncement  | teacher |
      |/profile                   | editProfile         | teacher |
      |/admin                     | viewAdminPanel      | admin   |
      |/admin/users               | addUser             | admin   |
      |/admin/courses             | addCourse           | admin   |
      |/home                      | viewCourses         | student |
      |/courses/1/people          | viewPeople          | student |
      |/courses/1/announcements   | viewAnnouncements   | student |
      |/courses/1/announcements/1 | viewAnnouncement    | student |
      |/profile                   | editProfile         | student |

  @not_allowed_urls
  Scenario Outline: Users must not be able to view urls for which they are not authorised
    Given a new browser or client instance for grootApp
    And the login page for grootApp
    And the role <role>
    When the user with the role logs in
    And they execute: <action>
    Then the url: <not_allowed_url> should not be visible to user
    Examples:
      | not_allowed_url             | action              | role    |
      |/admin                       | viewAdminPanel      | teacher |
      |/admin/users                 | addUser             | teacher |
      |/admin/courses               | addCourse           | teacher |
      |/courses/1/announcements     | viewAnnouncements   | admin   |
      |/home                        | viewCourses         | admin   |
      |/courses/1/people            | viewPeople          | admin   |
      |/courses/1/announcements     | addAnnouncement     | admin   |
      |/courses/1/announcements/1   | viewAnnouncement    | admin   |
      |/courses/1/announcements/1   | editAnnouncement    | admin   |
      |/courses/1/announcements/1   | deleteAnnouncement  | admin   |
      |/profile                     | editProfile         | admin   |
      |/courses/1/announcements     | addAnnouncement     | student |
      |/courses/1/announcements/1    | editAnnouncement    | student |
      |/courses/1/announcements/1    | deleteAnnouncement  | student |
      |/admin                       | viewAdminPanel      | student |
      |/admin/users                 | addUser             | student |
      |/admin/courses               | addCourse           | student |
