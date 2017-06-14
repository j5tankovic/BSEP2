import textwrap
import examples_table

def generate_scenario_for_allowed_rules(allowed_rules):
    test_tag = "\t@allowed_actions_client\n"
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
     Examples:\n"""

    examples = examples_table.generate_table(allowed_rules, "allowed_url")
    whole_test_scenario = test_tag + test_scenario + examples
    return whole_test_scenario

def generate_scenario_for_not_allowed_rules(not_allowed_rules):
    test_tag = "\n\t@not_allowed_actions_client\n"
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
     Examples:\n"""

    examples = examples_table.generate_table(not_allowed_rules, "not_allowed_url")
    whole_test_scenario = test_tag + test_scenario + examples
    return whole_test_scenario

def generate_feature_file(allowed_rules, not_allowed_rules):
    feature_file_path = "../bdd-security/src/test/resources/features/access_control_client.feature"

    scenario_tag= '@access_control_client\n'
    feature_desc= 'Feature: Access Control\n  Verify that access control model on client is satisfied\n\n'
    allowed_scenario = generate_scenario_for_allowed_rules(allowed_rules)
    not_allowed_scenario= generate_scenario_for_not_allowed_rules(not_allowed_rules)

    lines = [scenario_tag, feature_desc, allowed_scenario, not_allowed_scenario]
    features_file = open(feature_file_path, 'w')
    features_file.writelines(lines)
    features_file.close()