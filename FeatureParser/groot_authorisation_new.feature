@grootauthorisationnew
Feature: Groot Authorisation and Access Control
  Verify that the access control model is enforced so that only the authorised users have access to their own data

 @allowed_urls
  					Scenario Outline: Users can view restricted urls for which they are authorised
				    	Given a new browser or client instance for grootApp
				    	And the login page for grootApp
				    	And the user with the role <role>
				    	When the user logs in
				    	And he goes to the <allowed_url>
				    	And he executes <action>
				    	Then the status code in response should be 200
				    	Examples: 
		allowed_url | action | role
	|/home | viewCourses | teacher|
	|/courses/1/people | viewPeople | teacher|
	|/courses/1/announcements | viewAnnouncements | teacher|
	|/courses/1/announcements | addAnnouncement | teacher|
	|/courses/1/announcements/1 | viewAnnouncement | teacher|
	|/courses/1/announcement/1 | editAnnouncement | teacher|
	|/courses/1/announcement/1 | deleteAnnouncement | teacher|
	|/profile | editProfile | teacher|
	|/home | viewCourses | student|
	|/courses/1/people | viewPeople | student|
	|/courses/1/announcements | viewAnnouncements | student|
	|/courses/1/announcements/1 | viewAnnouncement | student|
	|/profile | editProfile | student|
	|/admin | viewAdminPanel | admin|
	|/admin/users | addUser | admin|
	|/admin/users | removeUser | admin|
	|/admin/courses | addCourse | admin|
	|/admin/courses | removeCourse | admin|
 @not_allowed_urls
  				   	Scenario Outline: Users must not be able to view urls for which they are not authorised
    					Given a new browser or client instance for grootApp
					    And the login page for grootApp
					    And the user with the role <role>
				    	When the user logs in
				    	And he goes to the <not_allowed_url>
				    	And he executes <action>
				    	Then the status code in response should be 403
					    Examples: 
		not_allowed_url | action | role
	|/admin | viewAdminPanel | teacher|
	|/admin/users | addUser | teacher|
	|/admin/users | removeUser | teacher|
	|/admin/courses | addCourse | teacher|
	|/admin/courses | removeCourse | teacher|
	|/courses/1/announcements | addAnnouncement | student|
	|/courses/1/announcement/1 | editAnnouncement | student|
	|/courses/1/announcement/1 | deleteAnnouncement | student|
	|/admin | viewAdminPanel | student|
	|/admin/users | addUser | student|
	|/admin/users | removeUser | student|
	|/admin/courses | addCourse | student|
	|/admin/courses | removeCourse | student|
	|/home | viewCourses | admin|
	|/courses/1/people | viewPeople | admin|
	|/courses/1/announcements | viewAnnouncements | admin|
	|/courses/1/announcements/1 | viewAnnouncement | admin|
	|/profile | editProfile | admin|
	|/home | viewCourses | admin|
	|/courses/1/people | viewPeople | admin|
	|/courses/1/announcements | viewAnnouncements | admin|
	|/courses/1/announcements | addAnnouncement | admin|
	|/courses/1/announcements/1 | viewAnnouncement | admin|
	|/courses/1/announcement/1 | editAnnouncement | admin|
	|/courses/1/announcement/1 | deleteAnnouncement | admin|
	|/profile | editProfile | admin|
