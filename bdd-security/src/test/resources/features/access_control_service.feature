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
		| /profile                       | editProfile          | TEACHER    |
		| /courses/*/announcements/*     | deleteAnnouncement   | TEACHER    |
		| /courses/*/announcements       | addAnnouncement      | TEACHER    |
		| /courses/*/announcements/*     | editAnnouncement     | TEACHER    |
		| /admin/courses                 | deleteCourse         | ADMIN      |
		| /admin/users                   | addUser              | ADMIN      |
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
