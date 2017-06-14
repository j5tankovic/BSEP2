@access_control_service
Feature: Access Control
  Verify that access control model on service is satisfied

	@allowed_actions_service
    Scenario Outline: Users can perform actions for restricted resources
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
		| allowed_url                    | action               | role       |
		| /profile                       | editProfile          | STUDENT    |
		| /courses/1/announcements       | addAnnouncement      | TEACHER    |
		| /courses/1/announcements/1     | editAnnouncement     | TEACHER    |
		| /courses/1/announcements/1     | deleteAnnouncement   | TEACHER    |
		| /profile                       | editProfile          | TEACHER    |
		| /admin/users                   | deleteUser           | ADMIN      |
		| /admin/courses                 | deleteCourse         | ADMIN      |
		| /admin/courses                 | addCourse            | ADMIN      |
		| /admin/users                   | addUser              | ADMIN      |

	@not_allowed_actions_service
    Scenario Outline: Users can not perform actions for restricted resources
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
		| not_allowed_url                | action               | role       |
		| /admin/users                   | deleteUser           | STUDENT    |
		| /courses/1/announcements       | addAnnouncement      | STUDENT    |
		| /admin/courses                 | deleteCourse         | STUDENT    |
		| /courses/1/announcements/1     | editAnnouncement     | STUDENT    |
		| /courses/1/announcements/1     | deleteAnnouncement   | STUDENT    |
		| /admin/courses                 | addCourse            | STUDENT    |
		| /admin/users                   | addUser              | STUDENT    |
		| /admin/users                   | deleteUser           | TEACHER    |
		| /admin/users                   | addUser              | TEACHER    |
		| /admin/courses                 | addCourse            | TEACHER    |
		| /admin/courses                 | deleteCourse         | TEACHER    |
		| /courses/1/announcements       | addAnnouncement      | ADMIN      |
		| /courses/1/announcements/1     | editAnnouncement     | ADMIN      |
		| /courses/1/announcements/1     | deleteAnnouncement   | ADMIN      |
		| /profile                       | editProfile          | ADMIN      |
