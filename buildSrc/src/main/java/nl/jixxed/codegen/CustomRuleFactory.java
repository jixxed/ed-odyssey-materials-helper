package nl.jixxed.codegen;

import org.jsonschema2pojo.rules.RequiredRule;
import org.jsonschema2pojo.rules.RuleFactory;

public class CustomRuleFactory extends RuleFactory {
    // Public no-arg constructor required by the Gradle plugin
    public CustomRuleFactory() {
        super();
    }

    @Override
    public RequiredRule getRequiredRule() {
        return new CustomRequiredRule(this);
    }
    @Override
    public CustomPropertyRule getPropertyRule() {
        return new CustomPropertyRule(this);
    }

    @Override
    public CustomRequiredArrayRule getRequiredArrayRule() {
        return new CustomRequiredArrayRule(this);
    }
}