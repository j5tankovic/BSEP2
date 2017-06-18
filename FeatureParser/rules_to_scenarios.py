import csv
import sys

import scenarios_for_service
import scenarios_for_client

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

if __name__ == '__main__':
    input_file_path = sys.argv[1]
    allowed_rules, rules_actions = read_access_rules(input_file_path)
    not_allowed_rules = generate_not_allowed_rules(allowed_rules, rules_actions)
    scenarios_for_service.generate_feature_file(allowed_rules, not_allowed_rules)
    scenarios_for_client.generate_feature_file(allowed_rules, not_allowed_rules)