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
		| /courses/*/announcements/*     | editAnnouncement     | TEACHER    |
		| /courses/*/announcements       | addAnnouncement      | TEACHER    |
		| /courses/*/announcements/*     | deleteAnnouncement   | TEACHER    |
		| /profile                       | editProfile          | TEACHER    |
		| /admin/courses                 | deleteCourse         | ADMIN      |
		| /admin/users                   | addUser              | ADMIN      |
		| /admin/userToCourse            | addUserToCourse      | ADMIN      |
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
		| /admin/courses                 | deleteCourse         | STUDENT    |
		| /courses/*/announcements       | addAnnouncement      | STUDENT    |
		| /admin/users                   | addUser              | STUDENT    |
		| /admin/userToCourse            | addUserToCourse      | STUDENT    |
		| /courses/*/announcements/*     | deleteAnnouncement   | STUDENT    |
		| /courses/*/announcements/*     | editAnnouncement     | STUDENT    |
		| /admin/courses                 | addCourse            | STUDENT    |
		| /admin/users                   | deleteUser           | STUDENT    |
		| /admin/courses                 | deleteCourse         | TEACHER    |
		| /admin/users                   | addUser              | TEACHER    |
		| /admin/userToCourse            | addUserToCourse      | TEACHER    |
		| /admin/courses                 | addCourse            | TEACHER    |
		| /admin/users                   | deleteUser           | TEACHER    |
		| /courses/*/announcements/*     | editAnnouncement     | ADMIN      |
		| /courses/*/announcements       | addAnnouncement      | ADMIN      |
		| /courses/*/announcements/*     | deleteAnnouncement   | ADMIN      |
		| /profile                       | editProfile          | ADMIN      |
