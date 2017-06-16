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
		| /courses/*/announcements/*     | deleteAnnouncement   | TEACHER    |
		| /courses/*/announcements       | addAnnouncement      | TEACHER    |
		| /profile                       | editProfile          | TEACHER    |
		| /courses/*/announcements/*     | editAnnouncement     | TEACHER    |
		| /admin/courses                 | deleteCourse         | ADMIN      |
		| /admin/courses                 | addCourse            | ADMIN      |
		| /admin/users                   | deleteUser           | ADMIN      |
		| /admin/users                   | addUser              | ADMIN      |

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
		| /admin/courses                 | addCourse            | STUDENT    |
		| /admin/users                   | deleteUser           | STUDENT    |
		| /admin/courses                 | deleteCourse         | STUDENT    |
		| /courses/*/announcements/*     | editAnnouncement     | STUDENT    |
		| /courses/*/announcements/*     | deleteAnnouncement   | STUDENT    |
		| /admin/users                   | addUser              | STUDENT    |
		| /admin/courses                 | deleteCourse         | TEACHER    |
		| /admin/courses                 | addCourse            | TEACHER    |
		| /admin/users                   | deleteUser           | TEACHER    |
		| /admin/users                   | addUser              | TEACHER    |
		| /profile                       | editProfile          | ADMIN      |
		| /courses/*/announcements       | addAnnouncement      | ADMIN      |
		| /courses/*/announcements/*     | deleteAnnouncement   | ADMIN      |
		| /courses/*/announcements/*     | editAnnouncement     | ADMIN      |
