package nl.jixxed.eliteodysseymaterials.builder;

import javafx.beans.binding.StringBinding;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.jixxed.eliteodysseymaterials.enums.MainTabType;
import nl.jixxed.eliteodysseymaterials.enums.TabType;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.Destroyable;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableResizableImageView;
import nl.jixxed.eliteodysseymaterials.templates.generic.MainTab;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MainTabBuilder {
    private final List<String> styleClasses = new ArrayList<>();
    private boolean closable = false;
    private Node content;
    private StringBinding stringBinding;
    private String nonLocalizedText;
    private DestroyableResizableImageView graphic;
    private ObservableValue<Boolean> disableObservable;
    private TabType tabType;

    public static MainTabBuilder builder() {
        return new MainTabBuilder();
    }

    public MainTabBuilder withClosable(final boolean closable) {
        this.closable = closable;
        return this;
    }

    public MainTabBuilder withStyleClass(final String styleClass) {
        this.styleClasses.add(styleClass);
        return this;
    }

    public MainTabBuilder withStyleClasses(final String... styleClasses) {
        this.styleClasses.addAll(Arrays.asList(styleClasses));
        return this;
    }

    public MainTabBuilder withText(final StringBinding stringBinding) {
        this.stringBinding = stringBinding;
        return this;
    }

    public MainTabBuilder withText(final String stringBinding, Object... parameters) {
        this.stringBinding = LocaleService.getStringBinding(stringBinding, parameters);
        return this;
    }

    public MainTabBuilder withNonLocalizedText(final String nonLocalizedText) {
        this.nonLocalizedText = nonLocalizedText;
        return this;
    }

    public MainTabBuilder withGraphic(final DestroyableResizableImageView graphic) {
        this.graphic = graphic;
        return this;
    }

    public <E extends Node & Destroyable> MainTabBuilder withContent(E content) {
        this.content = content;
        return this;
    }

    public MainTabBuilder withDisableProperty(final ObservableValue<Boolean> disableObservable) {
        this.disableObservable = disableObservable;
        return this;
    }

    public MainTabBuilder withTabType(TabType tabType) {
        this.tabType = tabType;
        return this;
    }

    public MainTab build() {
        final MainTab tab = new MainTab() { };

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
        if (this.disableObservable != null) {
            tab.addBinding(tab.disableProperty(), this.disableObservable);
        }
        if(this.tabType != null) {
            tab.setTabType(this.tabType);
        }
        return tab;
    }

}
