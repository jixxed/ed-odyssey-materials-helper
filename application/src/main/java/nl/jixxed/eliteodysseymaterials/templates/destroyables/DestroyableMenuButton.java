package nl.jixxed.eliteodysseymaterials.templates.destroyables;

import javafx.scene.control.MenuButton;
import lombok.Getter;

import java.util.List;

@Getter
public class DestroyableMenuButton extends MenuButton implements DestroyableComponent {

    public void addAll(List<DestroyableMenuItem> menuItems) {
        registerAll(menuItems);
        this.getItems().addAll(menuItems);
    }

    public void add(DestroyableMenuItem menuItem) {
        this.getItems().addAll(register(menuItem));
    }

    public void clear() {
        getItems().stream().map(DestroyableMenuItem.class::cast).forEach(DestroyableMenuItem::destroy);
    }
}
