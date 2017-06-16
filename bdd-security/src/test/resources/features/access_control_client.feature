@access_control_client
Feature: Access Control on client
  Verify that access control model on client is satisfied

	@allowed_actions_client
    Scenario Outline: Users can perform actions for restricted resources
      Given a new browser
      And a url <allowed_url> for action <action> is populated
      And the login page
      And the user with the role <role>
      When user logs in
      And user navigates to the given url
      Then the current url should be the same as given url
      And ui element according to the action should be visible
      Examples:
		| allowed_url                    | action               | role       |
		| /profile                       | editProfile          | STUDENT    |
		| /profile                       | editProfile          | TEACHER    |
		| /courses/*/announcements/*     | deleteAnnouncement   | TEACHER    |
		| /courses/*/announcements       | addAnnouncement      | TEACHER    |
		| /courses/*/announcements/*     | editAnnouncement     | TEACHER    |
		| /admin/courses                 | deleteCourse         | ADMIN      |
		| /admin/users                   | addUser              | ADMIN      |
		| /admin/courses                 | addCourse            | ADMIN      |
		| /admin/users                   | deleteUser           | ADMIN      |

	@not_allowed_actions_client
    Scenario Outline: Users can not perform actions for restricted resources
      Given a new browser
      And a url <not_allowed_url> for action <action> is populated
      And the login page
      And the user with the role <role>
      When user logs in
      And user navigates to the given url
      Then the current url is /unauthorized or ui element according to action is not visible
      Examples:
		| not_allowed_url                | action               | role       |
		| /courses/*/announcements       | addAnnouncement      | STUDENT    |
		| /admin/courses                 | deleteCourse         | STUDENT    |
		| /courses/*/announcements/*     | deleteAnnouncement   | STUDENT    |
		| /admin/users                   | addUser              | STUDENT    |
		| /courses/*/announcements/*     | editAnnouncement     | STUDENT    |
		| /admin/courses                 | addCourse            | STUDENT    |
		| /admin/users                   | deleteUser           | STUDENT    |
		| /admin/courses                 | deleteCourse         | TEACHER    |
		| /admin/users                   | addUser              | TEACHER    |
		| /admin/courses                 | addCourse            | TEACHER    |
		| /admin/users                   | deleteUser           | TEACHER    |
		| /courses/*/announcements/*     | deleteAnnouncement   | ADMIN      |
		| /courses/*/announcements       | addAnnouncement      | ADMIN      |
		| /profile                       | editProfile          | ADMIN      |
		| /courses/*/announcements/*     | editAnnouncement     | ADMIN      |
