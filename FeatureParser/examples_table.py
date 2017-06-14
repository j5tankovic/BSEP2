import textwrap

def generate_table(rules, type_of_url):
    examples = "| {0:<30} | {1:<20} | {2:<10} |\n".format(type_of_url, "action", "role")
    
    for key in rules:
        for rules_actions in rules[key]:
            examples += "| {0:<30} | {1:<20} | {2:<10} |\n".format(rules_actions[0], rules_actions[1], key)

    indented_examples = textwrap.indent(examples, '\t\t')

    return indented_examples