import textwrap
import examples_table

def generate_scenario_for_allowed_rules(allowed_rules):
    test_tag = "\t@allowed_actions_client\n"
    test_scenario = """\
    Scenario Outline: Users can perform actions for restricted resources
      Given a new browser
      And a url <allowed_url> for action <action> is populated
      And the login page
      And the user with the role <role>
      When user logs in
      And user navigates to the given url
      Then the current url should be the same as given url
      And ui element according to the action should be visible
      Examples:\n"""

    examples = examples_table.generate_table(allowed_rules, "allowed_url")
    whole_test_scenario = test_tag + test_scenario + examples
    return whole_test_scenario

def generate_scenario_for_not_allowed_rules(not_allowed_rules):
    test_tag = "\n\t@not_allowed_actions_client\n"
    test_scenario = """\
    Scenario Outline: Users can not perform actions for restricted resources
      Given a new browser
      And a url <not_allowed_url> for action <action> is populated
      And the login page
      And the user with the role <role>
      When user logs in
      And user navigates to the given url
      Then the current url is /unauthorized or ui element according to action is not visible
      Examples:\n"""

    examples = examples_table.generate_table(not_allowed_rules, "not_allowed_url")
    whole_test_scenario = test_tag + test_scenario + examples
    return whole_test_scenario

def generate_feature_file(allowed_rules, not_allowed_rules):
    feature_file_path = "../bdd-security/src/test/resources/features/access_control_client.feature"

    scenario_tag= '@access_control_client\n'
    feature_desc= 'Feature: Access Control on client\n  Verify that access control model on client is satisfied\n\n'
    allowed_scenario = generate_scenario_for_allowed_rules(allowed_rules)
    not_allowed_scenario= generate_scenario_for_not_allowed_rules(not_allowed_rules)

    lines = [scenario_tag, feature_desc, allowed_scenario, not_allowed_scenario]
    features_file = open(feature_file_path, 'w')
    features_file.writelines(lines)
    features_file.close()