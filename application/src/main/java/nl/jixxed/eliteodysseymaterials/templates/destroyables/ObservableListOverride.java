package nl.jixxed.eliteodysseymaterials.templates.destroyables;

import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import lombok.extern.slf4j.Slf4j;

import java.lang.ref.WeakReference;
import java.util.*;

@Slf4j
public class ObservableListOverride {
    private WeakReference<DestroyableParent> parentRef;
    private final WeakReference<ObservableList<Node>> childrenRef;

    //<P extends Node & DestroyableParent, <E extends Node & Destroyable>
    public <P extends DestroyableParent> ObservableListOverride(P parent, ObservableList<Node> children) {
        this.parentRef = new WeakReference<>(parent);
        this.childrenRef = new WeakReference<>(children);
    }

    private ObservableList<Node> children() {
        return Objects.requireNonNull(childrenRef.get());
    }

    private DestroyableParent parent() {
        return Objects.requireNonNull(parentRef.get());
    }

    @SafeVarargs
    public final <E extends Node & Destroyable> boolean addAll(E... elements) {
        parent().registerAll(elements);
        return children().addAll(elements);
    }

    public void addListener(ListChangeListener<? super Node> listener) {
        parent().addListListener(children(), listener);
        children().addListener(listener);
    }

    public void removeListener(ListChangeListener<? super Node> listener) {
        parent().removeListListener(children(), listener);
        children().removeListener(listener);
    }

    @SafeVarargs
    public final <E extends Node & Destroyable> boolean setAll(E... elements) {
        children().forEach(child -> parent().deregister((Destroyable) child));
        children().forEach(this::destroy);
        parent().registerAll(elements);
        return children().setAll(elements);
    }

    public <E extends Node & Destroyable> boolean setAll(Collection<E> collection) {
        children().forEach(this::destroy);
        parent().registerAll(collection);
        return children().setAll(collection);
    }

    public <E extends Node & Destroyable> boolean removeAll(E... elements) {
        Arrays.stream(elements).forEach(child -> parent().deregister((Destroyable) child));
        Arrays.stream(elements).forEach(this::destroy);
        return children().removeAll(elements);
    }

    public <E extends Node & Destroyable> boolean retainAll(E... elements) {
        children().stream().filter(node -> !Arrays.asList(elements).contains(node)).forEach(this::destroy);
        return children().retainAll(elements);
    }

    public void remove(int from, int to) {
        children().subList(from, to).forEach(child -> parent().deregister((Destroyable) child));
        children().subList(from, to).forEach(this::destroy);
        children().remove(from, to);
    }

    public int size() {
        return children().size();
    }

    public boolean isEmpty() {
        return children().isEmpty();
    }

    public <E extends Node & Destroyable> boolean contains(E node) {
        return children().contains(node);
    }

    public Iterator<Node> iterator() {
        return children().iterator();
    }

    public Object[] toArray() {
        return children().toArray();
    }

    public <T> T[] toArray(T[] a) {
        return children().toArray(a);
    }

    public <E extends Node & Destroyable> boolean add(E node) {
        parent().register(node);
        return children().add(node);
    }

    public <E extends Node & Destroyable> boolean remove(E node) {
        if (node != null) {
            parent().deregister(node);
            destroy(node);
            return children().remove(node);
        }
        return false;
    }

    public boolean containsAll(Collection<?> collection) {
        return children().containsAll(collection);
    }

    public <E extends Node & Destroyable> boolean addAll(Collection<? extends E> collection) {
        parent().registerAll(collection);
        return children().addAll(collection);
    }

    public <E extends Node & Destroyable> boolean addAll(int index, Collection<? extends E> collection) {
        parent().registerAll(collection);
        return children().addAll(index, collection);
    }

    public boolean removeAll(Collection<?> collection) {
        collection.forEach(child -> parent().deregister((Destroyable) child));
        collection.forEach(node -> {
            if (node instanceof Destroyable destroyable) {
                destroyable.destroy();
            }
        });
        return children().removeAll(collection);
    }

    public boolean retainAll(Collection<?> collection) {
        children().stream().filter(node -> !collection.contains(node)).forEach(child -> parent().deregister((Destroyable) child));
        children().stream().filter(node -> !collection.contains(node)).forEach(this::destroy);
        return children().retainAll(collection);
    }

    public void clear() {
        children().forEach(child -> parent().deregister((Destroyable) child));
        children().forEach(this::destroy);
        children().clear();
    }


    public <E extends Node & Destroyable> E get(int index) {
        return (E) children().get(index);
    }

    public <E extends Node & Destroyable> E set(int index, E element) {
        final E node = (E) children().set(index, element);
        parent().deregister(node);
        destroy(node);
        return node;
    }

    public <E extends Node & Destroyable> void add(int index, E element) {
        parent().register(element);
        children().add(index, element);
    }

    public <E extends Node & Destroyable> void addFirst(E element) {
        parent().register(element);
        children().addFirst(element);
    }

    public Node remove(int index) {
        parent().deregister((Destroyable) children().get(index));
        this.destroy(children().get(index));
        return children().remove(index);
    }

    public <E extends Node & Destroyable> int indexOf(E element) {
        return children().indexOf(element);
    }

    public <E extends Node & Destroyable> int lastIndexOf(E element) {
        return children().lastIndexOf(element);
    }

    public ListIterator<Node> listIterator() {
        return children().listIterator();
    }

    public ListIterator<Node> listIterator(int index) {
        return children().listIterator(index);
    }

    public List<Node> subList(int fromIndex, int toIndex) {
        return children().subList(fromIndex, toIndex);
    }

    public void addListener(InvalidationListener listener) {
        parent().addListListener(children(), listener);
        children().addListener(listener);
    }

    public void removeListener(InvalidationListener listener) {
        parent().removeListListener(children(), listener);
        children().removeListener(listener);
    }

    private void destroy(Node node) {
        try {
            if (node instanceof DestroyableEventTemplate template) {
                template.destroyTemplate();
            } else if (node instanceof DestroyableTemplate template) {
                template.destroyTemplate();
            } else if (node instanceof DestroyableParent p) {
                p.destroy();
            } else if (node instanceof DestroyableComponent c) {
                c.destroy();
            } else if (node instanceof Destroyable d) {
                d.destroy();
            } else {
                throw new IllegalArgumentException();
            }

        } catch (IllegalArgumentException e) {
            log.error("Failed to destroy node of type: {}", node.getClass().getName(), e);
        }
    }
}
