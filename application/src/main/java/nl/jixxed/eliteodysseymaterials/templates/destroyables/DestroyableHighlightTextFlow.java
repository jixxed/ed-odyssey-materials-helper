package nl.jixxed.eliteodysseymaterials.templates.destroyables;

import javafx.beans.binding.StringBinding;
import lombok.Getter;
import lombok.Setter;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DestroyableHighlightTextFlow extends DestroyableTextFlow {
    @Setter
    private Object[] parameters;
    @Getter
    private final List<String> highlightStyleClasses = new ArrayList<>();

    StringBinding stringBinding;

    public DestroyableHighlightTextFlow(StringBinding stringBinding, Object[] parameters) {
        super();
        this.parameters = parameters;
        this.stringBinding = stringBinding;
        stringBinding.subscribe(_ -> populateTextFlow());
    }

    public void populateTextFlow() {
        this.getNodes().clear();
        String patternString = "\\{(\\d+)}";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(stringBinding.get());
        int lastIndex = 0;
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            int paramIndex = Integer.parseInt(matcher.group(1));

            // Add the text before the variable part
            if (start > lastIndex) {
                final DestroyableText text = new DestroyableText();
                text.setText(stringBinding.get().substring(lastIndex, start));
                text.getStyleClass().addAll(this.getStyleClass());
                this.getNodes().add(text);
            }

            // Add the variable part with a different style
            DestroyableText variableText = new DestroyableText();
            variableText.setText(LocaleService.localizeParameter(parameters[paramIndex]).toString());
            variableText.getStyleClass().addAll(this.highlightStyleClasses);
            this.getNodes().add(variableText);


            lastIndex = end;
        }

        // Add the remaining text after the last variable part
        if (lastIndex < stringBinding.get().length()) {
            final DestroyableText text = new DestroyableText();
            text.setText(stringBinding.get().substring(lastIndex));
            text.getStyleClass().addAll(this.getStyleClass());
            this.getNodes().add(text);
        }
    }
}
