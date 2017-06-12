@authorisation
Feature: Access Control
  Verify that access control model is satisfied

	@allowed_actions
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
		| allowed_url               | action               | role       |
		| /profile                  | editProfile          | STUDENT    |
		| /courses/1/announcements  | addAnnouncement      | TEACHER    |
		| /profile                  | editProfile          | TEACHER    |
		| /courses/1/announcement/1 | editAnnouncement     | TEACHER    |
		| /courses/1/announcement/1 | deleteAnnouncement   | TEACHER    |
		| /admin/courses            | removeCourse         | ADMIN      |
		| /admin/courses            | addCourse            | ADMIN      |
		| /admin/users              | removeUser           | ADMIN      |
		| /admin/users              | addUser              | ADMIN      |

	@not_allowed_actions
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
		| not_allowed_url           | action               | role       |
		| /admin/courses            | addCourse            | STUDENT    |
		| /courses/1/announcement/1 | deleteAnnouncement   | STUDENT    |
		| /admin/users              | addUser              | STUDENT    |
		| /courses/1/announcements  | addAnnouncement      | STUDENT    |
		| /admin/courses            | removeCourse         | STUDENT    |
		| /courses/1/announcement/1 | editAnnouncement     | STUDENT    |
		| /admin/users              | removeUser           | STUDENT    |
		| /admin/courses            | removeCourse         | TEACHER    |
		| /admin/courses            | addCourse            | TEACHER    |
		| /admin/users              | removeUser           | TEACHER    |
		| /admin/users              | addUser              | TEACHER    |
		| /courses/1/announcements  | addAnnouncement      | ADMIN      |
		| /profile                  | editProfile          | ADMIN      |
		| /courses/1/announcement/1 | deleteAnnouncement   | ADMIN      |
		| /courses/1/announcement/1 | editAnnouncement     | ADMIN      |
