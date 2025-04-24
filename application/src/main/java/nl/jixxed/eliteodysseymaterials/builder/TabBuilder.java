package nl.jixxed.eliteodysseymaterials.builder;

import javafx.beans.binding.StringBinding;
import javafx.scene.Node;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.Destroyable;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableResizableImageView;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTab;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TabBuilder {
    private final List<String> styleClasses = new ArrayList<>();
    private boolean closable = false;
    private Node content;
    private StringBinding stringBinding;
    private String nonLocalizedText;
    private DestroyableResizableImageView graphic;

    public static TabBuilder builder() {
        return new TabBuilder();
    }

    public TabBuilder withClosable(final boolean closable) {
        this.closable = closable;
        return this;
    }

    public TabBuilder withStyleClass(final String styleClass) {
        this.styleClasses.add(styleClass);
        return this;
    }

    public TabBuilder withStyleClasses(final String... styleClasses) {
        this.styleClasses.addAll(Arrays.asList(styleClasses));
        return this;
    }

    public TabBuilder withText(final StringBinding stringBinding) {
        this.stringBinding = stringBinding;
        return this;
    }

    public TabBuilder withText(final String stringBinding, Object... parameters) {
        this.stringBinding = LocaleService.getStringBinding(stringBinding, parameters);
        return this;
    }

    public TabBuilder withNonLocalizedText(final String nonLocalizedText) {
        this.nonLocalizedText = nonLocalizedText;
        return this;
    }

    public TabBuilder withGraphic(final DestroyableResizableImageView graphic) {
        this.graphic = graphic;
        return this;
    }

    public <E extends Node & Destroyable> TabBuilder withContent(E content) {
        this.content = content;
        return this;
    }

    public DestroyableTab build() {
        final DestroyableTab tab = new DestroyableTab();

        tab.getStyleClass().addAll(this.styleClasses);
        tab.setClosable(this.closable);
        if (this.stringBinding != null) {
            tab.addBinding(tab.textProperty(), this.stringBinding);
        } else if (this.nonLocalizedText != null) {
            tab.setText(this.nonLocalizedText);
        }
        if (this.graphic != null) {
            tab.setGraphic(this.graphic);
            tab.register(this.graphic);
        }
        if (content != null) {
            tab.setContent(content);
            tab.register((Node & Destroyable) content);
        }
        return tab;
    }

}
