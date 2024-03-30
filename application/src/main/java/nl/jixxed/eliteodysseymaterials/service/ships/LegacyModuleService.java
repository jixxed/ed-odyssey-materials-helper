package nl.jixxed.eliteodysseymaterials.service.ships;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.AppConstants;
import nl.jixxed.eliteodysseymaterials.constants.OsConstants;
import nl.jixxed.eliteodysseymaterials.domain.*;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipModule;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class LegacyModuleService {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    public static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private static final List<EventListener<?>> EVENT_LISTENERS = new ArrayList<>();

    public static void saveLegacyModule(ShipModule shipModule){
        APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
            ShipLegacyModule shipLegacyModule = new ShipLegacyModule(shipModule);
            final ShipLegacyModules shipLegacyModules = loadModules(commander);
            shipLegacyModules.getLegacyModules().add(shipLegacyModule);
            saveModules(commander, shipLegacyModules);
        });
    }
    public static void updateLegacyModule(String uuid, String name, ShipModule shipModule){
        APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
            final ShipLegacyModules shipLegacyModules = loadModules(commander);
            ShipLegacyModule shipLegacyModule = shipLegacyModules.getLegacyModules().stream().filter(module->module.getUuid().equals(uuid)).findFirst().orElseThrow(IllegalArgumentException::new);
            var modifiers = shipModule.getModifiers().entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            var modifications = shipModule.getModifications().stream().map(modification -> new ShipConfigurationModification(modification.getModification(), modification.getGrade(), modification.getModificationCompleteness().orElse(BigDecimal.ZERO))).toList();
            var effects = shipModule.getExperimentalEffects().stream().map(ShipConfigurationExperimentalEffect::new).toList();
            shipLegacyModule.setId(shipModule.getId());
            shipLegacyModule.setName(name);
            shipLegacyModule.setModifiers(modifiers);
            shipLegacyModule.setModification(modifications);
            shipLegacyModule.setExperimentalEffect(effects);
            saveModules(commander, shipLegacyModules);
        });
    }
    public static void deleteLegacyModule(String uuid){
        APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
            final ShipLegacyModules shipLegacyModules = loadModules(commander);
            shipLegacyModules.getLegacyModules().removeIf(module->module.getUuid().equals(uuid));
            saveModules(commander, shipLegacyModules);
        });
    }
    public static ShipLegacyModules loadModules(final Commander commander){
        try {
            final String pathname = commander.getCommanderFolder();
            final File commanderFolder = new File(pathname);
            commanderFolder.mkdirs();
            final File shipLegacyModulesFile = new File(pathname + OsConstants.OS_SLASH + AppConstants.SHIP_LEGACY_MODULES_FILE);
            String shipLegacyModulesFileContents;
            if (shipLegacyModulesFile.exists()) {//load from file if exists
                shipLegacyModulesFileContents = Files.readString(shipLegacyModulesFile.toPath());
                if (shipLegacyModulesFileContents.isEmpty()) {
                    createShipLegacyModules(commander);
                }
            } else {
                createShipLegacyModules(commander);
            }
            shipLegacyModulesFileContents = Files.readString(shipLegacyModulesFile.toPath());
            try {
                return OBJECT_MAPPER.readValue(shipLegacyModulesFileContents, ShipLegacyModules.class);
            } catch (final IOException e) {
                log.warn("Unable to load legacy modules from configuration. Try to create new one.", e);
                createShipLegacyModules(commander);
                shipLegacyModulesFileContents = Files.readString(shipLegacyModulesFile.toPath());
                return OBJECT_MAPPER.readValue(shipLegacyModulesFileContents, ShipLegacyModules.class);
            }
        } catch (final IOException e) {
            log.error("Unable to load legacy modules from configuration.", e);
            throw new RuntimeException(e);
        }
    }

    private static void createShipLegacyModules(Commander commander) {

        final ShipLegacyModules shipLegacyModules = new ShipLegacyModules();
        saveModules(commander, shipLegacyModules);
    }

    public static void saveModules(final Commander commander, final ShipLegacyModules shipLegacyModules) {
        try {
            final String shipLegacyModulesJson = OBJECT_MAPPER.writeValueAsString(shipLegacyModules);
            final String pathname = commander.getCommanderFolder();
            final File commanderFolder = new File(pathname);
            commanderFolder.mkdirs();
            final File shipConfigurationsFile = new File(pathname + OsConstants.OS_SLASH + AppConstants.SHIP_LEGACY_MODULES_FILE);
            try (final FileOutputStream fileOutputStream = new FileOutputStream(shipConfigurationsFile)) {
                fileOutputStream.write(shipLegacyModulesJson.getBytes(StandardCharsets.UTF_8));
            }
        } catch (final JsonProcessingException e) {
            log.error("Failed to save ships legacy modules", e);
        } catch (final IOException e) {
            log.error("Failed to save ships legacy modules to legacy modules file", e);
        }
    }
}
