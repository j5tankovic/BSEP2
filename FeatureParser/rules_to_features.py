import csv
import sys
import textwrap

def read_access_rules(rules_file_path):
	allowed_rules = {}
	rules_actions = set()
	with open(rules_file_path) as csvfile:
		rules_reader = csv.reader(csvfile)
		next(rules_reader)
		for row in rules_reader:
			role = row[0].strip().upper();
			url = row[1].strip()
			action = row[2].strip()
			if (role not in allowed_rules.keys()):
				allowed_rules[role] = set()
			allowed_rules[role].add((url, action))
			rules_actions.add((url, action))

	return allowed_rules, rules_actions

def generate_not_allowed_rules(allowed_rules, rules_actions):
	not_allowed_rules = {}
	for key in allowed_rules:
		for rule_action in rules_actions:
			if rule_action not in allowed_rules[key]:
				if (key not in not_allowed_rules.keys()):
					not_allowed_rules[key] = set()
				not_allowed_rules[key].add(rule_action)

	return not_allowed_rules


def generate_scenario_for_allowed_rules(allowed_rules):
	test_tag = "\t@allowed_actions\n"
	test_scenario = """\
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
    """

	dedented_test_scenario = textwrap.dedent(test_scenario)
	examples = "| {0:<30} | {1:<20} | {2:<10} |\n".format("allowed_url", "action", "role")

	for key in allowed_rules:
		for rules_actions in allowed_rules[key]:
			examples += "| {0:<30} | {1:<20} | {2:<10} |\n".format(rules_actions[0], rules_actions[1], key)

	indented_examples = textwrap.indent(examples, '\t\t')
	whole_test_scenario = test_tag + dedented_test_scenario + indented_examples

	return whole_test_scenario

def generate_scenario_for_not_allowed_rules(not_allowed_rules):
	test_tag = "\n\t@not_allowed_actions\n"
	test_scenario = """\
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
    """

	dedented_test_scenario = textwrap.dedent(test_scenario)
	examples = "| {0:<30} | {1:<20} | {2:<10} |\n".format("not_allowed_url", "action", "role")

	for key in not_allowed_rules:
		for rules_actions in not_allowed_rules[key]:
			examples += "| {0:<30} | {1:<20} | {2:<10} |\n".format(rules_actions[0], rules_actions[1], key)

	indented_examples = textwrap.indent(examples, '\t\t')
	whole_test_scenario = test_tag + dedented_test_scenario + indented_examples

	return whole_test_scenario

def generate_feature_file(allowed_rules, not_allowed_rules):
	feature_file_path = "../bdd-security/src/test/resources/features/access_control_rules.feature"

	scenario_tag = '@access_control_rules\n'
	feature = 'Feature: Access Control\n  Verify that access control model is satisfied\n\n'
	allowed_resources_scenario = generate_scenario_for_allowed_rules(allowed_rules)
	not_allowed_resources_scenario = generate_scenario_for_not_allowed_rules(not_allowed_rules)

	lines = [scenario_tag, feature, allowed_resources_scenario, not_allowed_resources_scenario]
	features_file = open(feature_file_path, 'w')
	features_file.writelines(lines)
	features_file.close()

if __name__ == '__main__':
	input_file_path = sys.argv[1]
	allowed_rules, rules_actions = read_access_rules(input_file_path)
	not_allowed_rules = generate_not_allowed_rules(allowed_rules, rules_actions)
	generate_feature_file(allowed_rules, not_allowed_rules)