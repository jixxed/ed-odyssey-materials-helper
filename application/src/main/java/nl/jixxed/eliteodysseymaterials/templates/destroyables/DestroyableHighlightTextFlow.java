package nl.jixxed.eliteodysseymaterials.templates.destroyables;

import javafx.beans.binding.Bindings;
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

    //TODO should this be behaviour of the component or the builder?
    public void populateTextFlow(StringBinding localeStringBinding) {
        this.getNodes().clear();
        String patternString = "\\{(\\d+)}";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(localeStringBinding.get());
        //TODO: this is bugged, throws errors when switching locale because parameter position changes within the string. Fix this.
        int lastIndex = 0;
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            int paramIndex = Integer.parseInt(matcher.group(1));

            // Add the text before the variable part
            if (start > lastIndex) {
                final int finalLastIndex = lastIndex;
                final int finalStart = start;
                StringBinding textBinding = Bindings.createStringBinding(() -> localeStringBinding.get().substring(finalLastIndex, finalStart), localeStringBinding);
                final DestroyableText text = new DestroyableText();
                text.addBinding(text.textProperty(), textBinding);
                this.getNodes().add(text);
            }

            // Add the variable part with a different style
            StringBinding paramBinding = Bindings.createStringBinding(() -> LocaleService.localizeParameter(parameters[paramIndex]).toString(), localeStringBinding);
            DestroyableText variableText = new DestroyableText();
            variableText.addBinding(variableText.textProperty(), paramBinding);
            variableText.getStyleClass().addAll(this.highlightStyleClasses);
            this.getNodes().add(variableText);


            lastIndex = end;
        }

        // Add the remaining text after the last variable part
        if (lastIndex < localeStringBinding.get().length()) {
            final int finalLastIndex = lastIndex;
            StringBinding textBinding = Bindings.createStringBinding(() -> localeStringBinding.get().substring(finalLastIndex), localeStringBinding);
            final DestroyableText text = new DestroyableText();
            text.addBinding(text.textProperty(), textBinding);
            this.getNodes().add(text);
        }
    }
}
