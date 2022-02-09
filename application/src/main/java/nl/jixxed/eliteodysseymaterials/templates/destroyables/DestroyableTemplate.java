package nl.jixxed.eliteodysseymaterials.templates.destroyables;

import nl.jixxed.eliteodysseymaterials.templates.Template;

import java.util.List;

public interface DestroyableTemplate extends Template, Destroyable {
    default void addDestroyableNode(final DestroyableComponent destroyable) {
        getDestroyablesList().add(destroyable);
    }

    @Override
    default void destroy() {
        getDestroyablesList().forEach(Destroyable::destroy);
        getDestroyablesList().clear();
        destroyInternal();
    }

    List<Destroyable> getDestroyablesList();
}
