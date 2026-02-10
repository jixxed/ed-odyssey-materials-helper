package nl.jixxed.codegen;

import com.fasterxml.jackson.databind.JsonNode;
import com.sun.codemodel.JDocCommentable;
import com.sun.codemodel.JFieldVar;
import jakarta.validation.constraints.NotNull;
import org.jsonschema2pojo.Schema;
import org.jsonschema2pojo.rules.RequiredRule;
import org.jsonschema2pojo.rules.RuleFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.annotation.Annotation;

public class CustomRequiredRule extends RequiredRule {

    private final RuleFactory ruleFactory;

    public CustomRequiredRule(RuleFactory ruleFactory) {
        super(ruleFactory);
        this.ruleFactory = ruleFactory;
    }

    @Override
    public JDocCommentable apply(String nodeName, JsonNode node, JsonNode parent,
                                 JDocCommentable generatableType, Schema schema) {
        boolean isRequired = node.asBoolean();

        // check if field type includes "null"
        boolean allowsNull = false;
        JsonNode propertyNode = parent != null ? parent.get(nodeName) : null;
        if (propertyNode != null) {
            JsonNode typeNode = propertyNode.get("type");
            if (typeNode != null && typeNode.isArray()) {
                for (JsonNode t : typeNode) {
                    if ("null".equals(t.asText())) {
                        allowsNull = true;
                        break;
                    }
                }
            }
        }

        if (isRequired) {
            generatableType.javadoc().append("\n(Required)");

            if (!allowsNull &&
                    this.ruleFactory.getGenerationConfig().isIncludeJsr303Annotations() &&
                    generatableType instanceof JFieldVar) {
                Class<? extends Annotation> notNullClass =
                        this.ruleFactory.getGenerationConfig().isUseJakartaValidation() ?
                                NotNull.class :
                                javax.validation.constraints.NotNull.class;

                ((JFieldVar) generatableType).annotate(notNullClass);
            }

            if (this.ruleFactory.getGenerationConfig().isIncludeJsr305Annotations() &&
                    generatableType instanceof JFieldVar) {
                ((JFieldVar) generatableType).annotate(Nonnull.class);
            }
        } else if (this.ruleFactory.getGenerationConfig().isIncludeJsr305Annotations() &&
                generatableType instanceof JFieldVar) {
            ((JFieldVar) generatableType).annotate(Nullable.class);
        }

        return generatableType;
    }
}