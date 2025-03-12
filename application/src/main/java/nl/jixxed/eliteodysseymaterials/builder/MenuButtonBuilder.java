package nl.jixxed.eliteodysseymaterials.builder;

import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableMenuButton;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MenuButtonBuilder extends AbstractButtonBaseBuilder<MenuButtonBuilder> {
    private List<MenuItem> items;

    public static MenuButtonBuilder builder() {
        return new MenuButtonBuilder();
    }

    public MenuButtonBuilder withMenuItems(final Map<String, EventHandler<ActionEvent>> menuItems) {
        this.items = menuItems.entrySet().stream().sorted(Map.Entry.comparingByKey()).map(stringEventHandlerEntry -> {
            final EventHandler<ActionEvent> eventHandler = stringEventHandlerEntry.getValue();
            final String textLocaleKey = stringEventHandlerEntry.getKey();
            return (MenuItem) MenuItemBuilder.builder()
                    .withOnAction(eventHandler)
                    .withText(textLocaleKey).build();
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
            return (MenuItem) menuItemBuilder.build();
        }).toList();
        return this;
    }

    @SuppressWarnings("unchecked")
    public DestroyableMenuButton build() {
        final DestroyableMenuButton menuButton = new DestroyableMenuButton();
        if (this.items != null) {
            menuButton.getItems().addAll(this.items);
        }
        return menuButton;
    }

}
