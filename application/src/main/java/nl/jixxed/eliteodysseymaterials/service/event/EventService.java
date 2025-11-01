package nl.jixxed.eliteodysseymaterials.service.event;

import io.reactivex.rxjava3.annotations.CheckReturnValue;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.ref.WeakReference;
import java.util.*;
import java.util.function.Consumer;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("java:S3740")
@Slf4j
public class EventService {
    private static final Map<Class<? extends Event>, List<WeakReference<EventListener<? extends Event>>>> LISTENERS_MAP = new HashMap<>();
//    static boolean isInit = false;

    public synchronized static <T extends Event> void publish(final T event) {
//        if (event instanceof JournalInitEvent jie) {
//            isInit = jie.isInitialised();
//        }
//        if (isInit) {
//            log.debug("Publishing event {}", event);
//            log.debug(Thread.currentThread().getStackTrace()[2].toString());
//        }
        //clean up null references
        LISTENERS_MAP.getOrDefault(event.getClass(), Collections.emptyList()).removeIf(ref -> ref.get() == null);
        LISTENERS_MAP.getOrDefault(event.getClass(), Collections.emptyList()).stream()
                .filter(ref -> ref.get() != null)
                .sorted(Comparator.comparingInt(ref -> ref.get().getPriority()))
                .forEachOrdered(ref -> {
                    if (event instanceof TerminateApplicationEvent) {
                        //make sure we call each TerminateApplicationEvent listener and try to close all running threads
                        try {
                            ((EventListener<T>) ref.get()).handleEvent(event);
                        } catch (final Exception ex) {
                            log.error(ex.getMessage(), ex);
                        }
                    } else if (ref.get() != null) {
                        ((EventListener<T>) ref.get()).handleEvent(event);
                    }
                });
    }

    @CheckReturnValue
    public synchronized static <T extends Event> EventListener<T> addListener(final Object owner, final Class<T> eventClass, final Consumer<T> consumer) {
        return addListener(false, owner, eventClass, consumer);
    }

    @CheckReturnValue
    public synchronized static <T extends Event> EventListener<T> addListener(final boolean fxThread, final Object owner, final Class<T> eventClass, final Consumer<T> consumer) {
        return addListener(fxThread, owner, 5, eventClass, consumer);
    }

    @CheckReturnValue
    public synchronized static <T extends Event> EventListener<T> addListener(final Object owner, final Integer priority, final Class<T> eventClass, final Consumer<T> consumer) {
        return addListener(false, owner, priority, eventClass, consumer);
    }

    @CheckReturnValue
    public synchronized static <T extends Event> EventListener<T> addListener(final boolean fxThread, final Object owner, final Integer priority, final Class<T> eventClass, final Consumer<T> consumer) {
        final NonStaticEventListener<T> listener = new NonStaticEventListener<>(fxThread, owner, priority, eventClass, consumer);
        logListener(owner, listener, "register");
        final List<WeakReference<EventListener<? extends Event>>> eventListeners = LISTENERS_MAP.getOrDefault(eventClass, new ArrayList<>());
        eventListeners.add(new WeakReference<>(listener));
        LISTENERS_MAP.putIfAbsent(eventClass, eventListeners);
        logListenerSize();
        return listener;
    }

    @CheckReturnValue
    public synchronized static <T extends Event> EventListener<T> addStaticListener(final Class<T> eventClass, final Consumer<T> consumer) {
        return addStaticListener(5, eventClass, consumer);
    }

    @CheckReturnValue
    public synchronized static <T extends Event> EventListener<T> addStaticListener(final boolean fxThread, final Class<T> eventClass, final Consumer<T> consumer) {
        return addStaticListener(fxThread, 5, eventClass, consumer);
    }

    @CheckReturnValue
    public synchronized static <T extends Event> EventListener<T> addStaticListener(final Integer priority, final Class<T> eventClass, final Consumer<T> consumer) {
        return addStaticListener(false, priority, eventClass, consumer);
    }

    @CheckReturnValue
    public synchronized static <T extends Event> EventListener<T> addStaticListener(final boolean fxThread, final Integer priority, final Class<T> eventClass, final Consumer<T> consumer) {
        final EventListener<T> listener = new EventListener<>(fxThread, priority, eventClass, consumer);
        logListener(null, listener, "register static");
        final List<WeakReference<EventListener<? extends Event>>> eventListeners = LISTENERS_MAP.getOrDefault(eventClass, new ArrayList<>());
        eventListeners.add(new WeakReference<>(listener));
        LISTENERS_MAP.putIfAbsent(eventClass, eventListeners);
        logListenerSize();
        return listener;
    }

    public synchronized static void removeListener(final Consumer<? extends Event> eventConsumer, final Class<? extends Event> eventClass) {
        LISTENERS_MAP.computeIfPresent(eventClass, (aClass, eventListeners) -> {
            eventListeners.removeIf(ref -> {
                if (ref.get() == null) {
                    return true;
                }
                final boolean remove = ref.get().getConsumer().equals(eventConsumer);
                if (remove) {
                    logListener(null, ref.get(), "deregister");
                }
                return remove;
            });
            return eventListeners;
        });
        logListenerSize();
    }

    public synchronized static void removeListener(final EventListener<? extends Event> eventListener) {
        logListener(null, eventListener, "deregister");
        LISTENERS_MAP.computeIfPresent(eventListener.getEventClass(), (aClass, eventListeners) -> {
            eventListener.setDisabled(true);
            eventListener.consumer = null;
            eventListeners.removeIf(ref -> ref.get() == eventListener);
            return eventListeners;
        });
        logListenerSize();
    }

    public synchronized static void removeListener(final Object owner) {
        LISTENERS_MAP.values().forEach(eventListeners -> eventListeners.removeIf(ref -> {
            if (ref.get() == null) {
                return true;
            }
            final boolean remove = ref.get() instanceof NonStaticEventListener nonStaticEventListener && nonStaticEventListener.hasOwner(owner);
            if (remove) {
                ref.get().setDisabled(true);
                ref.get().consumer = null;
                logListener(owner, ref.get(), "deregister");
            }
            return remove;
        }));
        logListenerSize();
    }

    @SuppressWarnings({"java:S1172", "java:S125"})
    private static <T extends Event> void logListener(final Object owner, final EventListener<T> listener, final String action) {
//        log.debug(action + " listener: " + listener.getEventClass() + ", HASH:" + listener.hashCode() + (owner != null ? ", OWNER:" + owner.getClass().getName() + "(" + owner.hashCode() + ")" : ""));
    }

    @SuppressWarnings("java:S125")
    private static void logListenerSize() {
//        log.debug("listener size: " + (Integer) LISTENERS_MAP.values().stream().mapToInt(List::size).sum());
    }

    public synchronized static void shutdown() {
        LISTENERS_MAP.clear();
    }
}
