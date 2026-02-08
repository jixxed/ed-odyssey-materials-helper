package nl.jixxed.eliteodysseymaterials.templates.destroyables;

import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.stage.Window;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.lang.ref.WeakReference;
import java.util.function.Supplier;

@Slf4j
public class DestroyableTooltip extends Tooltip implements DestroyableComponent {

    WeakReference<Node> nodeWeakReference;
    @Setter
    Supplier<Double> customX;
    @Setter
    Supplier<Double> customY;

    public <E extends Node & DestroyableComponent> void install(E node) {
        nodeWeakReference = new WeakReference<>(node);
        Tooltip.install(node, this);
        node.register(this);
    }

    public <E extends Node & DestroyableComponent> void uninstall(E node) {
        if (node != null) {
            Tooltip.uninstall(node, this);
            node.deregister(this);
        }
    }

    @Override
    public void destroyInternal() {
        DestroyableComponent.super.destroyInternal();
        uninstall((Node & DestroyableComponent) nodeWeakReference.get());
    }

    @Override
    public void show(Window window, double x, double y) {
        if(customX != null && customY != null) {
            super.show(window, customX.get(), customY.get());
        }else{
            super.show(window, x, y);
        }
    }
}
