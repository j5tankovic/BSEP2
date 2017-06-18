@access_control_service
Feature: Access Control on service
  Verify that access control model on service is satisfied

	@allowed_actions_service
    Scenario Outline: Users can perform actions for restricted resources
      Given a new intercepting proxy browser
      And cleared proxy logs
      And a url <allowed_url> for action <action> is populated
      And the login page
      And the user with the role <role>
      When user logs in
      And cleared proxy logs
      And user navigates to the given url
      And he performs action <action>
      Then the status code in response should be 200
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

	@not_allowed_actions_service
    Scenario Outline: Users can not perform actions for restricted resources
      Given a new intercepting proxy browser
      And cleared proxy logs
      And a url <not_allowed_url> for action <action> is populated
      And the login page
      And the user with the role <role>
      When user logs in
      And cleared proxy logs
      And user navigates to the given url
      And he performs action <action>
      Then the status code in response should be 403
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
