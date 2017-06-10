import csv

def read_rules():
	path = 'groot_access_rules.csv';
	roles = ['student', 'teacher', 'admin']
	rules_dict = {'student': [], 'teacher': [], 'admin': []}
	rules_actions = [];
	with open(path) as csvfile:
		rules_reader = csv.reader(csvfile)
		next(rules_reader)
		for row in rules_reader:
			if row[0] in roles:
				rules_dict[row[0]].append((row[1].strip(), row[2].strip()))
			rules_actions.append((row[1].strip(), row[2].strip()))

	return rules_dict, rules_actions

def generate_not_allowed_rules(allowed_rules, rules_actions):
	not_allowed_rules_dict = {'student': [], 'teacher': [], 'admin': []}
	for key in allowed_rules:
		for rule_action in rules_actions:
			if rule_action not in allowed_rules[key]:
				not_allowed_rules_dict[key].append(rule_action)

	return not_allowed_rules_dict


def generate_scenario_for_allowed_rules(allowed_rules):
	scenario =  """ @allowed_urls
  					Scenario Outline: Users can view restricted urls for which they are authorised
				    	Given a new browser or client instance for grootApp
				    	And the login page for grootApp
				    	And the user with the role <role>
				    	When the user logs in
				    	And he goes to the <allowed_url>
				    	And he executes <action>
				    	Then the status code in response should be 200
				    	Examples: """
	# scenario =  " Scenario Outline: Users can view restricted resources for which they are authorised\n \
	# 			 Given a new browser or client instance\n \
	# 		     And the client/browser is configured to use an intercepting proxy\n \
	# 		     And the proxy logs are cleared\n \
	# 		     And the login page\n \
	# 		     And the username <username>\n \
	# 		     And the password <password>\n \
	# 		     When the user logs in\n \
	# 		     And the proxy logs are cleared\n\
	# 		     And the HTTP requests and responses are recorded\n\
	# 		     And they access the restricted resource: <method>\n\
	# 		     Then the string: <sensitiveData> should be present in one of the HTTP responses\n"
	examples = "\n\t\tallowed_url | action | role\n"

	for key in allowed_rules:
		for rules_actions in allowed_rules[key]:
			examples += "\t|{} | {} | {}|\n".format(rules_actions[0], rules_actions[1], key)

	scenario += examples

	return scenario

def generate_scenario_for_not_allowed_rules(not_allowed_rules):
	scenario =  """ @not_allowed_urls
  				   	Scenario Outline: Users must not be able to view urls for which they are not authorised
    					Given a new browser or client instance for grootApp
					    And the login page for grootApp
					    And the user with the role <role>
				    	When the user logs in
				    	And he goes to the <not_allowed_url>
				    	And he executes <action>
				    	Then the status code in response should be 403
					    Examples: """
	# scenario =  " Scenario Outline: Users must not be able to view resources for which they are not authorised\n \
 #    			Given the access control map for authorised users has been populated\n \
 #    			And a new browser or client instance\n\
 #    			And the username <username>\n \
 #    			And the password <password>\n \
 #    			And the login page\n \
 #    			When the user logs in\n \
 #    			And the previously recorded HTTP Requests for <method> are replayed using the current session ID\n \
 #    			Then the string: <sensitiveData> should not be present in any of the HTTP responses\n"
	examples = "\n\t\tnot_allowed_url | action | role\n"

	for key in not_allowed_rules:
		for rules_actions in not_allowed_rules[key]:
			examples += "\t|{} | {} | {}|\n".format(rules_actions[0], rules_actions[1], key)

	scenario += examples

	return scenario

def generate_feature_file(allowed_rules, not_allowed_rules):
	tag = '@grootauthorisationnew\n'
	feature = 'Feature: Groot Authorisation and Access Control\n  Verify that the access control model is enforced so that only the authorised users have access to their own data\n\n'
	allowed_resources_scenario = generate_scenario_for_allowed_rules(allowed_rules)
	not_allowed_resources_scenario = generate_scenario_for_not_allowed_rules(not_allowed_rules)

	lines = [tag, feature, allowed_resources_scenario, not_allowed_resources_scenario]
	features_file = open('groot_authorisation_new.feature', 'w')
	features_file.writelines(lines)
	features_file.close()

if __name__ == '__main__':
	allowed_rules, rules_actions = read_rules()
	not_allowed_rules = generate_not_allowed_rules(allowed_rules, rules_actions)
	generate_feature_file(allowed_rules, not_allowed_rules)