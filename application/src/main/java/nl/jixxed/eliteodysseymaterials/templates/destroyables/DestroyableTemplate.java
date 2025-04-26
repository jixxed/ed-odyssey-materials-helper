package nl.jixxed.eliteodysseymaterials.templates.destroyables;

public interface DestroyableTemplate extends Template, Destroyable {
    default void destroyTemplate() {
        //clean up more
        if (this instanceof DestroyableParent destroyableParent) {
            destroyableParent.destroy();
        } else if (this instanceof DestroyableComponent destroyableComponent) {
            destroyableComponent.destroy();
        } else {
            Destroyable.super.destroy();
        }
    }
}
