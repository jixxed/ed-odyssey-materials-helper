/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.templates.destroyables;

import javafx.beans.binding.StringBinding;
import javafx.util.Subscription;
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

    private final Subscription subscription;
    StringBinding stringBinding;

    public DestroyableHighlightTextFlow(StringBinding stringBinding, Object[] parameters) {
        super();
        this.parameters = parameters;
        this.stringBinding = stringBinding;
        subscription = stringBinding.subscribe(_ -> populateTextFlow());
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

    @Override
    public void destroyInternal() {
        super.destroyInternal();
        subscription.unsubscribe();
    }
}
