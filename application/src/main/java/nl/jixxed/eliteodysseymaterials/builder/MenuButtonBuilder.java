/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
