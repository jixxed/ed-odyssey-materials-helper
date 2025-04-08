package nl.jixxed.eliteodysseymaterials.builder;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.StringBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableMenuItem;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableResizableImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MenuItemBuilder {
    private final List<String> styleClasses = new ArrayList<>();
    private boolean visibility = true;
    private EventHandler<ActionEvent> onAction;
    private StringBinding stringBinding;
    private String nonLocalizedText;
    private DestroyableResizableImageView graphic;
    private BooleanBinding disabledBinding;

    public static MenuItemBuilder builder() {
        return new MenuItemBuilder();
    }

    public MenuItemBuilder withStyleClass(final String styleClass) {
        this.styleClasses.add(styleClass);
        return this;
    }

    public MenuItemBuilder withStyleClasses(final String... styleClasses) {
        this.styleClasses.addAll(Arrays.asList(styleClasses));
        return this;
    }

    public MenuItemBuilder withVisibility(final boolean visibility) {
        this.visibility = visibility;
        return this;
    }

    public MenuItemBuilder withText(final StringBinding stringBinding) {
        this.stringBinding = stringBinding;
        return this;
    }

    public MenuItemBuilder withText(final String localeKey, Object... parameters) {
        this.stringBinding = LocaleService.getStringBinding(localeKey, parameters);
        return this;
    }

    public MenuItemBuilder withNonLocalizedText(final String nonLocalizedText) {
        this.nonLocalizedText = nonLocalizedText;
        return this;
    }

    public MenuItemBuilder withGraphic(final DestroyableResizableImageView graphic) {
        this.graphic = graphic;
        return this;
    }

    public MenuItemBuilder withOnAction(final EventHandler<ActionEvent> onAction) {
        this.onAction = onAction;
        return this;
    }

    public MenuItemBuilder withDisableProperty(BooleanBinding disabledBinding) {
        this.disabledBinding = disabledBinding;
        return this;
    }

    public DestroyableMenuItem build() {
        final DestroyableMenuItem menuItem = new DestroyableMenuItem();

        menuItem.getStyleClass().addAll(this.styleClasses);

        if (this.stringBinding != null) {
            menuItem.addBinding(menuItem.textProperty(), this.stringBinding);
        } else if (this.nonLocalizedText != null) {
            menuItem.setText(this.nonLocalizedText);
        }
        if (this.graphic != null) {
            menuItem.setGraphic(this.graphic);
        }
        if (this.onAction != null) {
            menuItem.addActionEventBinding(menuItem.onActionProperty(), this.onAction);
        }
        if (this.disabledBinding != null) {
            menuItem.addBinding(menuItem.disableProperty(), this.disabledBinding);
        }
        menuItem.setVisible(this.visibility);

        return menuItem;
    }

}
