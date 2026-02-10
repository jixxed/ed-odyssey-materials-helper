package nl.jixxed.codegen;

import com.fasterxml.jackson.databind.JsonNode;
import com.sun.codemodel.*;
import jakarta.validation.constraints.Email;
import org.apache.commons.lang3.StringUtils;
import org.jsonschema2pojo.JsonPointerUtils;
import org.jsonschema2pojo.Schema;
import org.jsonschema2pojo.rules.PropertyRule;
import org.jsonschema2pojo.rules.RuleFactory;


public class CustomPropertyRule extends PropertyRule {
    private final RuleFactory ruleFactory;

    public CustomPropertyRule(org.jsonschema2pojo.rules.RuleFactory ruleFactory) {
        super(ruleFactory);
        this.ruleFactory = ruleFactory;
    }


    public JDefinedClass apply(String nodeName, JsonNode node, JsonNode parent, JDefinedClass jclass, Schema schema) {
        String propertyName;
        if (StringUtils.isEmpty(nodeName)) {
            propertyName = "__EMPTY__";
        } else {
            propertyName = this.ruleFactory.getNameHelper().getPropertyName(nodeName, node);
        }

        String pathToProperty;
        if (schema.getId() != null && schema.getId().getFragment() != null) {
            String var10000 = schema.getId().getFragment();
            pathToProperty = "#" + var10000 + "/properties/" + JsonPointerUtils.encodeReferenceToken(nodeName);
        } else {
            pathToProperty = "#/properties/" + JsonPointerUtils.encodeReferenceToken(nodeName);
        }

        Schema propertySchema = this.ruleFactory.getSchemaStore().create(schema, pathToProperty, this.ruleFactory.getGenerationConfig().getRefFragmentPathDelimiters());
        JType propertyType = (JType) this.ruleFactory.getSchemaRule().apply(nodeName, node, parent, jclass, propertySchema);
        propertySchema.setJavaTypeIfEmpty(propertyType);
        boolean isIncludeGetters = this.ruleFactory.getGenerationConfig().isIncludeGetters();
        boolean isIncludeSetters = this.ruleFactory.getGenerationConfig().isIncludeSetters();
        node = this.resolveRefs(node, schema);
        int accessModifier = !isIncludeGetters && !isIncludeSetters ? 1 : 4;
        JFieldVar field = jclass.field(accessModifier, propertyType, propertyName);
        this.propertyAnnotations(nodeName, node, schema, field);
        this.formatAnnotation(field, jclass, node);
        this.ruleFactory.getAnnotator().propertyField(field, jclass, nodeName, node);
        if (isIncludeGetters) {
            JMethod getter = this.addGetter(jclass, field, nodeName, node, this.isRequired(nodeName, node, schema), this.useOptional(nodeName, node, schema));
            this.ruleFactory.getAnnotator().propertyGetter(getter, jclass, nodeName);
            this.propertyAnnotations(nodeName, node, schema, getter);
        }

        if (isIncludeSetters) {
            JMethod setter = this.addSetter(jclass, field, nodeName, node);
            this.ruleFactory.getAnnotator().propertySetter(setter, jclass, nodeName);
            this.propertyAnnotations(nodeName, node, schema, setter);
        }

        if (this.ruleFactory.getGenerationConfig().isGenerateBuilders()) {
            this.addBuilderMethod(jclass, field, nodeName, node);
        }

        if (node.has("pattern")) {
            this.ruleFactory.getPatternRule().apply(nodeName, node.get("pattern"), node, field, schema);
        }

        this.ruleFactory.getDefaultRule().apply(nodeName, node.get("default"), node, field, schema);
        this.ruleFactory.getMinimumMaximumRule().apply(nodeName, node, parent, field, schema);
        this.ruleFactory.getMinItemsMaxItemsRule().apply(nodeName, node, parent, field, schema);
        this.ruleFactory.getMinLengthMaxLengthRule().apply(nodeName, node, parent, field, schema);
        this.ruleFactory.getDigitsRule().apply(nodeName, node, parent, field, schema);
        if (this.isObject(node) || this.isArray(node)) {
            this.ruleFactory.getValidRule().apply(nodeName, node, parent, field, schema);
        }

        return jclass;
    }

    private boolean hasEnumerated(Schema schema, String arrayFieldName, String nodeName) {
        JsonNode array = schema.getContent().get(arrayFieldName);
        if (array != null) {
            for (JsonNode requiredNode : array) {
                if (nodeName.equals(requiredNode.asText())) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean hasFlag(JsonNode node, String fieldName) {
        if (node.has(fieldName)) {
            JsonNode requiredNode = node.get(fieldName);
            return requiredNode.asBoolean();
        } else {
            return false;
        }
    }

    private boolean isDeclaredAs(String type, String nodeName, JsonNode node, Schema schema) {
        return this.hasEnumerated(schema, type, nodeName) || this.hasFlag(node, type);
    }

    private boolean isRequired(String nodeName, JsonNode node, Schema schema) {
        return this.isDeclaredAs("required", nodeName, node, schema);
    }

    private boolean useOptional(String nodeName, JsonNode node, Schema schema) {
        return this.isDeclaredAs("javaOptional", nodeName, node, schema);
    }

    private void propertyAnnotations(String nodeName, JsonNode node, Schema schema, JDocCommentable generatedJavaConstruct) {
        if (node.has("title")) {
            this.ruleFactory.getTitleRule().apply(nodeName, node.get("title"), node, generatedJavaConstruct, schema);
        }

        if (node.has("javaName")) {
            this.ruleFactory.getJavaNameRule().apply(nodeName, node.get("javaName"), node, generatedJavaConstruct, schema);
        }

        if (node.has("description")) {
            this.ruleFactory.getDescriptionRule().apply(nodeName, node.get("description"), node, generatedJavaConstruct, schema);
        }

        if (node.has("$comment")) {
            this.ruleFactory.getCommentRule().apply(nodeName, node.get("$comment"), node, generatedJavaConstruct, schema);
        }
// Look up in the parent schema whether this property is required
        boolean isRequired = false;
        JsonNode parentNode = schema.getContent(); // top-level object properties
        JsonNode requiredArray = parentNode.get("required");
        if (requiredArray != null && requiredArray.isArray()) {
            for (JsonNode n : requiredArray) {
                if (nodeName.equals(n.asText())) {
                    isRequired = true;
                    break;
                }
            }
        }

// Call the required rule if required
        if (isRequired && this.ruleFactory.getRequiredRule() != null) {
            this.ruleFactory.getRequiredRule().apply(nodeName, node, parentNode, generatedJavaConstruct, schema);
        } else if (this.ruleFactory.getNotRequiredRule() != null) {
            this.ruleFactory.getNotRequiredRule().apply(nodeName, node, parentNode, generatedJavaConstruct, schema);
        }
//        if (node.has("required")) {
//            this.ruleFactory.getRequiredRule().apply(nodeName, node.get("required"), node, generatedJavaConstruct, schema);
//        } else {
//            this.ruleFactory.getNotRequiredRule().apply(nodeName, node.get("required"), node, generatedJavaConstruct, schema);
//        }

    }

    private void formatAnnotation(JFieldVar field, JDefinedClass clazz, JsonNode node) {
        String format = node.path("format").asText();
        if ("date-time".equalsIgnoreCase(format)) {
            this.ruleFactory.getAnnotator().dateTimeField(field, clazz, node);
        } else if ("date".equalsIgnoreCase(format)) {
            this.ruleFactory.getAnnotator().dateField(field, clazz, node);
        } else if ("time".equalsIgnoreCase(format)) {
            this.ruleFactory.getAnnotator().timeField(field, clazz, node);
        } else if ("email".equalsIgnoreCase(format) && this.ruleFactory.getGenerationConfig().isIncludeJsr303Annotations()) {
            if (this.ruleFactory.getGenerationConfig().isUseJakartaValidation()) {
                field.annotate(Email.class);
            } else {
                field.annotate(javax.validation.constraints.Email.class);
            }
        }

    }

    private JsonNode resolveRefs(JsonNode node, Schema parent) {
        if (node.has("$ref")) {
            Schema refSchema = this.ruleFactory.getSchemaStore().create(parent, node.get("$ref").asText(), this.ruleFactory.getGenerationConfig().getRefFragmentPathDelimiters());
            JsonNode refNode = refSchema.getContent();
            return this.resolveRefs(refNode, refSchema);
        } else {
            return node;
        }
    }

    private boolean isObject(JsonNode node) {
        return node.path("type").asText().equals("object");
    }

    private boolean isArray(JsonNode node) {
        return node.path("type").asText().equals("array");
    }
    private boolean allowsNull(JsonNode node) {
        JsonNode typeNode = node.get("type");
        if (typeNode == null) return false;
        if (typeNode.isArray()) {
            for (JsonNode t : typeNode) {
                if ("null".equals(t.asText())) return true;
            }
        } else if ("null".equals(typeNode.asText())) {
            return true;
        }
        return false;
    }

    private JType getReturnType(JDefinedClass c, JFieldVar field, JsonNode node, boolean isRequired, boolean usesOptional) {
        boolean nullable = allowsNull(node);
        boolean useOptional = nullable || !isRequired;
        JType returnType = field.type();
        if ((this.ruleFactory.getGenerationConfig().isUseOptionalForGetters()) && useOptional && field.type().isReference()) {
            returnType = c.owner().ref("java.util.Optional").narrow(field.type());
        }

        return returnType;
    }

    private JMethod addGetter(JDefinedClass c, JFieldVar field, String jsonPropertyName, JsonNode node, boolean isRequired, boolean usesOptional) {
        JType type = getReturnType(c, field, node, isRequired, usesOptional);
        JMethod getter = c.method(1, type, this.getGetterName(jsonPropertyName, field.type(), node));
        JBlock body = getter.body();

        if (type.fullName().startsWith("java.util.Optional")) {
            body._return(c.owner().ref("java.util.Optional").staticInvoke("ofNullable").arg(field));
        } else {
            body._return(field);
        }

        return getter;
    }

    private JMethod addSetter(JDefinedClass c, JFieldVar field, String jsonPropertyName, JsonNode node) {
        JMethod setter = c.method(1, Void.TYPE, this.getSetterName(jsonPropertyName, node));
        JVar param = setter.param(field.type(), field.name());
        JBlock body = setter.body();
        body.assign(JExpr._this().ref(field), param);
        return setter;
    }

    private JMethod addBuilderMethod(JDefinedClass c, JFieldVar field, String jsonPropertyName, JsonNode node) {
        JMethod result = null;
        if (this.ruleFactory.getGenerationConfig().isUseInnerClassBuilders()) {
            result = this.addInnerBuilderMethod(c, field, jsonPropertyName, node);
        } else {
            result = this.addLegacyBuilder(c, field, jsonPropertyName, node);
        }

        return result;
    }

    private JMethod addLegacyBuilder(JDefinedClass c, JFieldVar field, String jsonPropertyName, JsonNode node) {
        JMethod builder = c.method(1, c, this.getBuilderName(jsonPropertyName, node));
        JVar param = builder.param(field.type(), field.name());
        JBlock body = builder.body();
        body.assign(JExpr._this().ref(field), param);
        body._return(JExpr._this());
        return builder;
    }

    private JMethod addInnerBuilderMethod(JDefinedClass c, JFieldVar field, String jsonPropertyName, JsonNode node) {
        JDefinedClass builderClass = this.ruleFactory.getReflectionHelper().getBaseBuilderClass(c);
        JMethod builderMethod = builderClass.method(1, builderClass.narrow(builderClass.typeParams()), this.getBuilderName(jsonPropertyName, node));
        JVar param = builderMethod.param(field.type(), field.name());
        JBlock body = builderMethod.body();
        body.assign(JExpr.ref(JExpr.cast(c, JExpr._this().ref("instance")), field), param);
        body._return(JExpr._this());
        return builderMethod;
    }

    private String getBuilderName(String propertyName, JsonNode node) {
        return this.ruleFactory.getNameHelper().getBuilderName(propertyName, node);
    }

    private String getSetterName(String propertyName, JsonNode node) {
        return this.ruleFactory.getNameHelper().getSetterName(propertyName, node);
    }

    private String getGetterName(String propertyName, JType type, JsonNode node) {
        return this.ruleFactory.getNameHelper().getGetterName(propertyName, type, node);
    }
}
