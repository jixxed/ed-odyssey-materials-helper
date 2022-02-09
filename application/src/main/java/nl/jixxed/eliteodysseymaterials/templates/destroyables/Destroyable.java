package nl.jixxed.eliteodysseymaterials.templates.destroyables;

public interface Destroyable {

    void destroy();

    default void destroyInternal() {

    }

}
