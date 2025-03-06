package nl.jixxed.eliteodysseymaterials.builder;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HighlightTextFlowBuilder {
    private final List<String> styleClasses = new ArrayList<>();
    private final List<String> highlightStyleClasses = new ArrayList<>();
    private EventHandler<? super MouseEvent> onMouseClicked;
    private String localeKey;
    private Object[] parameters;

    public static HighlightTextFlowBuilder builder() {
        return new HighlightTextFlowBuilder();
    }

    public HighlightTextFlowBuilder withStyleClass(final String styleClass) {
        this.styleClasses.add(styleClass);
        return this;
    }

    public HighlightTextFlowBuilder withStyleClasses(final String... styleClasses) {
        this.styleClasses.addAll(Arrays.asList(styleClasses));
        return this;
    }

    public HighlightTextFlowBuilder withHighlightStyleClass(final String styleClass) {
        this.highlightStyleClasses.add(styleClass);
        return this;
    }

    public HighlightTextFlowBuilder withHighlightStyleClasses(final String... styleClasses) {
        this.highlightStyleClasses.addAll(Arrays.asList(styleClasses));
        return this;
    }

    public HighlightTextFlowBuilder withOnMouseClicked(final EventHandler<? super MouseEvent> onMouseClicked) {
        this.onMouseClicked = onMouseClicked;
        return this;
    }
    public HighlightTextFlowBuilder withText(final String key, final Object... parameters) {
        this.parameters = parameters;
        this.localeKey = key;
        return this;
    }

    public TextFlow build() {
        final TextFlow textFlow = new TextFlow();
        textFlow.getStyleClass().addAll(this.styleClasses);
        if (this.onMouseClicked != null) {
            textFlow.setOnMouseClicked(this.onMouseClicked);
        }

        String patternString = "\\{(\\d+)}";
        StringBinding localeStringBinding = LocaleService.getStringBinding(this.localeKey);
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
                final Text text = new Text();
                text.textProperty().bind(textBinding);
                textFlow.getChildren().add(text);
            }

            // Add the variable part with a different style
            StringBinding paramBinding = Bindings.createStringBinding(() -> LocaleService.localizeParameter(parameters[paramIndex]).toString(), localeStringBinding);
            Text variableText = new Text();
            variableText.textProperty().bind(paramBinding);
            variableText.getStyleClass().addAll(this.highlightStyleClasses);
            textFlow.getChildren().add(variableText);


            lastIndex = end;
        }

        // Add the remaining text after the last variable part
        if (lastIndex < localeStringBinding.get().length()) {
            final int finalLastIndex = lastIndex;
            StringBinding textBinding = Bindings.createStringBinding(() -> localeStringBinding.get().substring(finalLastIndex), localeStringBinding);
            final Text text = new Text();
            text.textProperty().bind(textBinding);
            textFlow.getChildren().add(text);
        }

        return textFlow;
    }
}
