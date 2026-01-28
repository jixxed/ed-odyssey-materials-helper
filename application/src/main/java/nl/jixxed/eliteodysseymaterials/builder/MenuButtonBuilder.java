package nl.jixxed.eliteodysseymaterials.builder;

import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCombination;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableMenuButton;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableMenuItem;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MenuButtonBuilder extends AbstractButtonBaseBuilder<MenuButtonBuilder> {
    private List<DestroyableMenuItem> items;

    public static MenuButtonBuilder builder() {
        return new MenuButtonBuilder();
    }

    public MenuButtonBuilder withMenuItems(final Map<String, EventHandler<ActionEvent>> menuItems) {
        this.items = menuItems.entrySet().stream().sorted(Map.Entry.comparingByKey()).map(stringEventHandlerEntry -> {
            final EventHandler<ActionEvent> eventHandler = stringEventHandlerEntry.getValue();
            final String textLocaleKey = stringEventHandlerEntry.getKey();
            return MenuItemBuilder.builder()
                    .withOnAction(eventHandler)
                    .withText(textLocaleKey)
                    .build();
        }).toList();
        return this;
    }

    public MenuButtonBuilder withMenuItems(final Map<String, EventHandler<ActionEvent>> menuItems, final Map<String, BooleanBinding> disableBindings) {
        this.items = menuItems.entrySet().stream().sorted(Map.Entry.comparingByKey()).map(stringEventHandlerEntry -> {
            final EventHandler<ActionEvent> eventHandler = stringEventHandlerEntry.getValue();
            final String textLocaleKey = stringEventHandlerEntry.getKey();
            MenuItemBuilder menuItemBuilder = MenuItemBuilder.builder()
                    .withOnAction(eventHandler)
                    .withText(textLocaleKey);
            if (disableBindings.containsKey(textLocaleKey)) {
                menuItemBuilder = menuItemBuilder.withDisableProperty(disableBindings.get(textLocaleKey));
            }
            return menuItemBuilder.build();
        }).toList();
        return this;
    }

    public MenuButtonBuilder withMenuItems(final Map<String, EventHandler<ActionEvent>> menuItems, final Map<String, BooleanBinding> disableBindings, final Map<String, KeyCombination> keyCombinations) {
        this.items = menuItems.entrySet().stream().sorted(Map.Entry.comparingByKey()).map(stringEventHandlerEntry -> {
            final EventHandler<ActionEvent> eventHandler = stringEventHandlerEntry.getValue();
            final String textLocaleKey = stringEventHandlerEntry.getKey();
            MenuItemBuilder menuItemBuilder = MenuItemBuilder.builder()
                    .withOnAction(eventHandler)
                    .withText(textLocaleKey);
            if (disableBindings.containsKey(textLocaleKey)) {
                menuItemBuilder = menuItemBuilder.withDisableProperty(disableBindings.get(textLocaleKey));
            }
            if (keyCombinations.containsKey(textLocaleKey)) {
                menuItemBuilder = menuItemBuilder.withKeyCombination(keyCombinations.get(textLocaleKey));
            }
            return menuItemBuilder.build();
        }).toList();
        return this;
    }

    @SuppressWarnings("unchecked")
    public DestroyableMenuButton build() {
        final DestroyableMenuButton menuButton = new DestroyableMenuButton();
        super.build(menuButton);
        if (this.items != null) {
            menuButton.registerAll(this.items);
            menuButton.getItems().addAll(this.items);
        }
        return menuButton;
    }

}
