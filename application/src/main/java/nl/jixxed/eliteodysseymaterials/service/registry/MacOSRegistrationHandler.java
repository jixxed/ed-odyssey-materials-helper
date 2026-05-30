package nl.jixxed.eliteodysseymaterials.service.registry;

public class MacOSRegistrationHandler implements RegistrationHandler {
     @Override
    public void register() {
        //NOOP
    }

    @Override
    public void unregister() {
        //NOOP
    }

    @Override
    public boolean isRegistered() {
        return true;
    }

    @Override
    public boolean hasMediaServices() {
        return true;
    }

    @Override
    public boolean isUACEnabled() {
        return true;
    }
}
