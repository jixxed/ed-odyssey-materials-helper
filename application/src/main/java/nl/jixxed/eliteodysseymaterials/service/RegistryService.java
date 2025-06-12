package nl.jixxed.eliteodysseymaterials.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.service.registry.RegistrationHandler;
import nl.jixxed.eliteodysseymaterials.service.registry.UbuntuRegistrationHandler;
import nl.jixxed.eliteodysseymaterials.service.registry.WindowsRegistrationHandler;
import nu.redpois0n.oslib.AbstractOperatingSystem;
import nu.redpois0n.oslib.OperatingSystem;
import nu.redpois0n.oslib.linux.Distro;
import nu.redpois0n.oslib.linux.LinuxOperatingSystem;
import nu.redpois0n.oslib.windows.WindowsOperatingSystem;
import nu.redpois0n.oslib.windows.WindowsVersion;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

@SuppressWarnings({"java:S1075", "java:S2142", "java:S5665"})
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RegistryService {

    private static final AbstractOperatingSystem CURRENT_OS = OperatingSystem.getOperatingSystem();

    private static final Map<Distro, Supplier<RegistrationHandler>> linuxRegistrationHandlers = Map.of(
            Distro.UBUNTU, UbuntuRegistrationHandler::new
    );
    private static final Map<WindowsVersion, Supplier<RegistrationHandler>> windowsRegistrationHandlers = Map.of(
            WindowsVersion.WIN10, WindowsRegistrationHandler::new
    );

    public static void registerApplication() {
        getHandler().ifPresent(RegistrationHandler::register);
    }

    public static void unregisterApplication() {
        getHandler().ifPresent(RegistrationHandler::unregister);
    }

    public static boolean isRegistered() {
        return getHandler().map(RegistrationHandler::isRegistered).orElse(false);
    }

    private static Optional<RegistrationHandler> getHandler() {
        if (CURRENT_OS.getType() == OperatingSystem.WINDOWS) {
            WindowsOperatingSystem wos = (WindowsOperatingSystem) CURRENT_OS;
            return Optional.of(windowsRegistrationHandlers.getOrDefault(wos.getVersion(), WindowsRegistrationHandler::new).get());
        } else if (CURRENT_OS.getType() == OperatingSystem.LINUX) {
            LinuxOperatingSystem los = (LinuxOperatingSystem) CURRENT_OS;
            return Optional.of(linuxRegistrationHandlers.getOrDefault(los.getDistro(), UbuntuRegistrationHandler::new).get());
        }
        return Optional.empty();
    }

    public static boolean hasMediaServices() {
        return getHandler().map(RegistrationHandler::hasMediaServices).orElse(false);
    }
}