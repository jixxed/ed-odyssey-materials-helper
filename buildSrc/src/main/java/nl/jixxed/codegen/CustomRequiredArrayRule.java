package nl.jixxed.codegen;

import com.fasterxml.jackson.databind.JsonNode;
import com.sun.codemodel.*;
import jakarta.validation.constraints.NotNull;
import org.jsonschema2pojo.Schema;
import org.jsonschema2pojo.rules.RequiredArrayRule;
import org.jsonschema2pojo.rules.RuleFactory;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CustomRequiredArrayRule extends RequiredArrayRule {
    private final RuleFactory ruleFactory;
    public static final String REQUIRED_COMMENT_TEXT = "\n(Required)";

    protected CustomRequiredArrayRule(RuleFactory ruleFactory) {
        super(ruleFactory);
        this.ruleFactory = ruleFactory;
    }

    public JDefinedClass apply(String nodeName, JsonNode node, JsonNode parent, JDefinedClass jclass, Schema schema) {
        List<String> requiredFieldMethods = new ArrayList();
        JsonNode properties = schema.getContent().get("properties");
        Iterator<JsonNode> iterator = node.elements();

        while (iterator.hasNext()) {
            String requiredArrayItem = iterator.next().asText();
            if (!requiredArrayItem.isEmpty()) {
                JFieldVar field = null;
                JsonNode propertyNode = null;

                if (properties != null && properties.has(requiredArrayItem)) {
                    propertyNode = properties.get(requiredArrayItem);
                }

                String fieldName = this.ruleFactory.getNameHelper().getPropertyName(requiredArrayItem, propertyNode);
                field = (JFieldVar) jclass.fields().get(fieldName);

                if (field != null) {
                    this.addJavaDoc(field);

                    boolean allowsNull = false;
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

                    if (!allowsNull) {
                        if (this.ruleFactory.getGenerationConfig().isIncludeJsr303Annotations()) {
                            this.addNotNullAnnotation(field);
                        }

                        if (this.ruleFactory.getGenerationConfig().isIncludeJsr305Annotations()) {
                            this.addNonnullAnnotation(field);
                        }
                    }

                    requiredFieldMethods.add(this.getGetterName(fieldName, field.type(), node));
                    requiredFieldMethods.add(this.getSetterName(fieldName, node));
                }
            }
        }

        this.updateGetterSetterJavaDoc(jclass, requiredFieldMethods);
        return jclass;
    }

    private void updateGetterSetterJavaDoc(JDefinedClass jclass, List<String> requiredFieldMethods) {
        for(JMethod method : jclass.methods()) {
            if (requiredFieldMethods.contains(method.name())) {
                this.addJavaDoc(method);
            }
        }

    }

    private void addNotNullAnnotation(JFieldVar field) {
        Class<? extends Annotation> notNullClass = this.ruleFactory.getGenerationConfig().isUseJakartaValidation() ? NotNull.class : javax.validation.constraints.NotNull.class;
        field.annotate(notNullClass);
    }

    private void addNonnullAnnotation(JFieldVar field) {
        field.annotate(Nonnull.class);
    }

    private void addJavaDoc(JDocCommentable docCommentable) {
        JDocComment javadoc = docCommentable.javadoc();
        javadoc.append("\n(Required)");
    }

    private String getSetterName(String propertyName, JsonNode node) {
        return this.ruleFactory.getNameHelper().getSetterName(propertyName, node);
    }

    private String getGetterName(String propertyName, JType type, JsonNode node) {
        return this.ruleFactory.getNameHelper().getGetterName(propertyName, type, node);
    }
}
