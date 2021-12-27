package nl.jixxed.eliteodysseymaterials.builder;

import javafx.beans.binding.StringBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MenuButtonBuilder {
    private final List<String> styleClasses = new ArrayList<>();
    private List<MenuItem> items;
    private Tooltip tooltip;
    private StringBinding stringBinding;
    private String nonLocalizedText;

    public static MenuButtonBuilder builder() {
        return new MenuButtonBuilder();
    }

    public MenuButtonBuilder withStyleClass(final String styleClass) {
        this.styleClasses.add(styleClass);
        return this;
    }

    public MenuButtonBuilder withStyleClasses(final String... styleClasses) {
        this.styleClasses.addAll(Arrays.asList(styleClasses));
        return this;
    }

    public MenuButtonBuilder withMenuItems(final Map<String, EventHandler<ActionEvent>> menuItems) {
        this.items = menuItems.entrySet().stream().sorted(Map.Entry.comparingByKey()).map(stringEventHandlerEntry -> {
            final EventHandler<ActionEvent> eventHandler = stringEventHandlerEntry.getValue();
            final String textLocaleKey = stringEventHandlerEntry.getKey();
            final MenuItem menuItem = new MenuItem();
            menuItem.setOnAction(eventHandler);
            menuItem.textProperty().bind(LocaleService.getStringBinding(textLocaleKey));
            return menuItem;
        }).toList();
        return this;
    }

    public MenuButtonBuilder withText(final StringBinding stringBinding) {
        this.stringBinding = stringBinding;
        return this;
    }


    public MenuButtonBuilder withNonLocalizedText(final String nonLocalizedText) {
        this.nonLocalizedText = nonLocalizedText;
        return this;
    }


    public MenuButtonBuilder withToolTip(final Tooltip tooltip) {
        this.tooltip = tooltip;
        return this;
    }

    public MenuButton build() {
        final MenuButton menuButton = new MenuButton();
        menuButton.getStyleClass().addAll(this.styleClasses);
        if (this.items != null) {
            menuButton.getItems().addAll(this.items);
        }
        if (this.stringBinding != null) {
            menuButton.textProperty().bind(this.stringBinding);
        } else if (this.nonLocalizedText != null) {
            menuButton.setText(this.nonLocalizedText);
        }
        if (this.tooltip != null) {
            menuButton.setTooltip(this.tooltip);
        }
        return menuButton;
    }

}
