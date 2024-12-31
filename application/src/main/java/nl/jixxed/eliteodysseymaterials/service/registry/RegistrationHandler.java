package nl.jixxed.eliteodysseymaterials.service.registry;

public interface RegistrationHandler {
    void register();

    void unregister();

    boolean isRegistered();
}
