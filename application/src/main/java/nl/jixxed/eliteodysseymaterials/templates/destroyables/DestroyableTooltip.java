package nl.jixxed.eliteodysseymaterials.templates.destroyables;

import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import lombok.extern.slf4j.Slf4j;

import java.lang.ref.WeakReference;

@Slf4j
public class DestroyableTooltip extends Tooltip implements DestroyableComponent {

    WeakReference<Node> nodeWeakReference;

    public <E extends Node & DestroyableComponent> void install(E node) {
        nodeWeakReference = new WeakReference<>(node);
        Tooltip.install(node, this);
        node.register(this);
    }

    public <E extends Node & DestroyableComponent> void uninstall(E node) {
        if (node != null) {
            Tooltip.uninstall(node, this);
            node.deregister(this);
            log.info("Tooltip uninstalled");
        } else {
            log.error("Attempted to uninstall Tooltip with null node");
        }
    }

    @Override
    public void destroyInternal() {
        DestroyableComponent.super.destroyInternal();
        uninstall((Node & DestroyableComponent) nodeWeakReference.get());
    }
}
