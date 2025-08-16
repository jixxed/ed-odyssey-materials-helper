package nl.jixxed.eliteodysseymaterials.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.AppConstants;
import nl.jixxed.eliteodysseymaterials.constants.HorizonsBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.constants.OsConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.Commander;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprintJson;
import nl.jixxed.eliteodysseymaterials.enums.Engineer;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.service.event.CommanderSelectedEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EngineerEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class PinnedBlueprintService {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final Map<Engineer, HorizonsBlueprint> pinnedBlueprints = new EnumMap<>(Engineer.class);
    private static final List<EventListener<?>> EVENT_LISTENERS = new ArrayList<>();
    private static LocalDateTime lastChange = LocalDateTime.now();
    static {
        EVENT_LISTENERS.add(EventService.addStaticListener(0, CommanderSelectedEvent.class, event -> {
            load(event.getCommander());
            log.info("pinned blueprints loaded for: " + event.getCommander().getName() + " - " + event.getCommander().getGameVersion());
        }));
//        eventListeners.add(EventService.addStaticListener(0, JournalInitEvent.class, event -> {
//            ApplicationState.getInstance().getPreferredCommander().ifPresent(commander -> {
//                load(commander);
//                log.info("pinned blueprints loaded");
//            });
//        });
        EVENT_LISTENERS.add(EventService.addStaticListener(0, EngineerEvent.class, event -> {
            ApplicationState.getInstance().getPreferredCommander().ifPresent(commander -> {
                load(commander);
                log.info("pinned blueprints loaded");
            });
        }));

    }

    public static void pinBlueprint(final Engineer engineer, final HorizonsBlueprint blueprint) {
        final HorizonsBlueprint highestGradeBlueprint = (HorizonsBlueprint) HorizonsBlueprintConstants.getRecipe(
                blueprint.getHorizonsBlueprintName(),
                blueprint.getHorizonsBlueprintType(),
                HorizonsBlueprintGrade.forDigit(Math.min(HorizonsBlueprintConstants.getEngineerMaxGrade(blueprint, engineer), ApplicationState.getInstance().getEngineerRank(engineer)))
        );
        pinnedBlueprints.put(engineer, highestGradeBlueprint);
        save();
    }

    public static void unpinBlueprint(final Engineer engineer) {
        pinnedBlueprints.remove(engineer);
        save();
    }

    private static void save() {
        lastChange = LocalDateTime.now();
        ApplicationState.getInstance().getPreferredCommander().ifPresent(commander -> {
            try {
                final String wishlistsJson = OBJECT_MAPPER.writeValueAsString(pinnedBlueprints.entrySet().stream()
                        .map(entry -> new Pair<>(entry.getKey(), new HorizonsBlueprintJson(entry.getValue().getHorizonsBlueprintName().name(), entry.getValue().getHorizonsBlueprintType().name())))
                        .collect(Collectors.toMap(Pair::getKey, Pair::getValue)));
                final String pathname = commander.getCommanderFolder();
                final File commanderFolder = new File(pathname);
                commanderFolder.mkdirs();
                final File pinnedBlueprintsFile = new File(pathname + OsConstants.getOsSlash() + AppConstants.HORIZONS_PINNED_BLUEPRINTS_FILE);
                try (final FileOutputStream fileOutputStream = new FileOutputStream(pinnedBlueprintsFile)) {
                    fileOutputStream.write(wishlistsJson.getBytes(StandardCharsets.UTF_8));
                }
            } catch (final JsonProcessingException e) {
                log.error("Failed to save pinned blueprints", e);
            } catch (final IOException e) {
                log.error("Failed to save pinned blueprints to pinned blueprints file", e);
            }
        });
    }

    private static void load(final Commander commander) {
        pinnedBlueprints.clear();
        final String pathname = commander.getCommanderFolder();
        final File commanderFolder = new File(pathname);
        commanderFolder.mkdirs();
        final File pinnedBlueprintsFile = new File(pathname + OsConstants.getOsSlash() + AppConstants.HORIZONS_PINNED_BLUEPRINTS_FILE);
        try {
            if (pinnedBlueprintsFile.exists()) {
                final TypeReference<HashMap<Engineer, HorizonsBlueprintJson>> typeRef = new TypeReference<HashMap<Engineer, HorizonsBlueprintJson>>() {
                };
                final HashMap<Engineer, HorizonsBlueprintJson> pinnedBlueprintsMap = OBJECT_MAPPER.readValue(Files.readString(pinnedBlueprintsFile.toPath()), typeRef);
                pinnedBlueprintsMap.forEach((engineer, horizonsBlueprintJson) -> {
                    final Integer engineerRank = ApplicationState.getInstance().getEngineerRank(engineer);
                    final HorizonsBlueprintName name = HorizonsBlueprintName.forName(horizonsBlueprintJson.getName().equals("MISSILE_RACK") ? "DUMBFIRE_MISSILE_RACK" : horizonsBlueprintJson.getName());
                    final HorizonsBlueprintType horizonsBlueprintType = HorizonsBlueprintType.forName(horizonsBlueprintJson.getType());
                    final int maxBlueprintGrade = HorizonsBlueprintConstants.getBlueprintGrades(name, horizonsBlueprintType).stream().max(Comparator.comparing(HorizonsBlueprintGrade::getGrade)).orElse(HorizonsBlueprintGrade.GRADE_1).getGrade();
                    pinnedBlueprints.put(engineer, (HorizonsBlueprint) HorizonsBlueprintConstants.getRecipe(
                            name,
                            horizonsBlueprintType,
                            HorizonsBlueprintGrade.forDigit(Math.max(Math.min(engineerRank, maxBlueprintGrade), 1))
                    ));
                });
            }

        } catch (final IOException e) {
            log.error("Failed to process pinned blueprints file", e);

        }

    }

    public static boolean isPinned(final Engineer engineer, final HorizonsBlueprint blueprint) {
        final HorizonsBlueprint horizonsModuleBlueprint = pinnedBlueprints.get(engineer);
        if (blueprint.getHorizonsBlueprintName().equals(HorizonsBlueprintName.CAUSTIC_SINK_LAUNCHER)
                || blueprint.getHorizonsBlueprintName().equals(HorizonsBlueprintName.HEAT_SINK_LAUNCHER)
                || (blueprint.getHorizonsBlueprintName().equals(HorizonsBlueprintName.POWER_PLANT) && blueprint.getHorizonsBlueprintType().equals(HorizonsBlueprintType.ANTI_GUARDIAN_ZONE_RESISTANCE))
                || (blueprint.getHorizonsBlueprintName().equals(HorizonsBlueprintName.POWER_DISTRIBUTOR) && blueprint.getHorizonsBlueprintType().equals(HorizonsBlueprintType.ANTI_GUARDIAN_ZONE_RESISTANCE))
                || blueprint.getHorizonsBlueprintName().equals(HorizonsBlueprintName.GUARDIAN_HULL_REINFORCEMENT_PACKAGE)
                || blueprint.getHorizonsBlueprintName().equals(HorizonsBlueprintName.GUARDIAN_MODULE_REINFORCEMENT_PACKAGE)
                || blueprint.getHorizonsBlueprintName().equals(HorizonsBlueprintName.GUARDIAN_SHIELD_REINFORCEMENT_PACKAGE)
                || blueprint.getHorizonsBlueprintName().equals(HorizonsBlueprintName.GUARDIAN_GAUSS_CANNON)
                || blueprint.getHorizonsBlueprintName().equals(HorizonsBlueprintName.GUARDIAN_PLASMA_CHARGER)
                || blueprint.getHorizonsBlueprintName().equals(HorizonsBlueprintName.GUARDIAN_SHARD_CANNON)
                || blueprint.getHorizonsBlueprintName().equals(HorizonsBlueprintName.FRAME_SHIFT_DRIVE_BOOSTER)
        ) {
            return typeMatch(blueprint, horizonsModuleBlueprint);
        }
        return exactMatch(blueprint, horizonsModuleBlueprint);
    }

    private static boolean typeMatch(HorizonsBlueprint blueprint, HorizonsBlueprint horizonsModuleBlueprint) {
        return horizonsModuleBlueprint != null
                && blueprint.getHorizonsBlueprintType().equals(horizonsModuleBlueprint.getHorizonsBlueprintType())
                && blueprint.getHorizonsBlueprintGrade().getGrade() <= horizonsModuleBlueprint.getHorizonsBlueprintGrade().getGrade();
    }

    private static boolean exactMatch(HorizonsBlueprint blueprint, HorizonsBlueprint horizonsModuleBlueprint) {
        return horizonsModuleBlueprint != null
                && blueprint.getHorizonsBlueprintName().equals(horizonsModuleBlueprint.getBlueprintName())
                && blueprint.getHorizonsBlueprintType().equals(horizonsModuleBlueprint.getHorizonsBlueprintType())
                && blueprint.getHorizonsBlueprintGrade().getGrade() <= horizonsModuleBlueprint.getHorizonsBlueprintGrade().getGrade();
    }

    private static HorizonsBlueprintName getBlueprintName(HorizonsBlueprint horizonsModuleBlueprint) {
//        if(horizonsModuleBlueprint)
        return horizonsModuleBlueprint.getBlueprintName();
    }

    public static boolean hasPinnedBlueprint(final Engineer engineer) {
        return pinnedBlueprints.get(engineer) != null;
    }

    public static HorizonsBlueprint getPinnedBlueprint(final Engineer engineer) {
        return pinnedBlueprints.get(engineer);
    }

    public static void init() {
    }

    public static boolean hasChangedSince(LocalDateTime instant) {
        return lastChange.isAfter(instant);
    }
}
