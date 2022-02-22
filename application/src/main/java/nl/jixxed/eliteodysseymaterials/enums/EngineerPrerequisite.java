package nl.jixxed.eliteodysseymaterials.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;

import java.util.function.Supplier;

@RequiredArgsConstructor
public enum EngineerPrerequisite {
    A1(BlueprintName.ENGINEER_A1, "blueprint.description.engineer_a1", () -> ApplicationState.getInstance().isEngineerUnlocked(Engineer.DOMINO_GREEN)),
    A2(BlueprintName.ENGINEER_A2, "blueprint.description.engineer_a2", () -> ApplicationState.getInstance().isEngineerKnown(Engineer.KIT_FOWLER)),
    A3(BlueprintName.ENGINEER_A3, "blueprint.description.engineer_a3", () -> ApplicationState.getInstance().isEngineerUnlocked(Engineer.KIT_FOWLER)),
    A4(BlueprintName.ENGINEER_A4, "blueprint.description.engineer_a4", () -> ApplicationState.getInstance().isEngineerKnown(Engineer.YARDEN_BOND)),
    A5(BlueprintName.ENGINEER_A5, "blueprint.description.engineer_a5", () -> ApplicationState.getInstance().isEngineerUnlocked(Engineer.YARDEN_BOND)),
    B1(BlueprintName.ENGINEER_B1, "blueprint.description.engineer_b1", () -> ApplicationState.getInstance().isEngineerUnlocked(Engineer.HERO_FERRARI)),
    B2(BlueprintName.ENGINEER_B2, "blueprint.description.engineer_b2", () -> ApplicationState.getInstance().isEngineerKnown(Engineer.WELLINGTON_BECK)),
    B3(BlueprintName.ENGINEER_B3, "blueprint.description.engineer_b3", () -> ApplicationState.getInstance().isEngineerUnlocked(Engineer.WELLINGTON_BECK)),
    B4(BlueprintName.ENGINEER_B4, "blueprint.description.engineer_b4", () -> ApplicationState.getInstance().isEngineerKnown(Engineer.UMA_LASZLO)),
    B5(BlueprintName.ENGINEER_B5, "blueprint.description.engineer_b5", () -> ApplicationState.getInstance().isEngineerUnlocked(Engineer.UMA_LASZLO)),
    C1(BlueprintName.ENGINEER_C1, "blueprint.description.engineer_c1", () -> ApplicationState.getInstance().isEngineerUnlocked(Engineer.JUDE_NAVARRO)),
    C2(BlueprintName.ENGINEER_C2, "blueprint.description.engineer_c2", () -> ApplicationState.getInstance().isEngineerKnown(Engineer.TERRA_VELASQUEZ)),
    C3(BlueprintName.ENGINEER_C3, "blueprint.description.engineer_c3", () -> ApplicationState.getInstance().isEngineerUnlocked(Engineer.TERRA_VELASQUEZ)),
    C4(BlueprintName.ENGINEER_C4, "blueprint.description.engineer_c4", () -> ApplicationState.getInstance().isEngineerKnown(Engineer.ODEN_GEIGER)),
    C5(BlueprintName.ENGINEER_C5, "blueprint.description.engineer_c5", () -> ApplicationState.getInstance().isEngineerUnlocked(Engineer.ODEN_GEIGER)),
    D1_1(BlueprintName.ENGINEER_D1_1, "blueprint.description.engineer_d1_1", () -> ApplicationState.getInstance().isEngineerUnlocked(Engineer.BALTANOS)),
    D1_2(BlueprintName.ENGINEER_D1_2, "blueprint.description.engineer_d1_2", () -> ApplicationState.getInstance().isEngineerUnlocked(Engineer.ROSA_DAYETTE)),
    D1_3(BlueprintName.ENGINEER_D1_3, "blueprint.description.engineer_d1_3", () -> ApplicationState.getInstance().isEngineerUnlocked(Engineer.ELEANOR_BRESA)),
    D2(BlueprintName.ENGINEER_D2, "blueprint.description.engineer_d2", () -> ApplicationState.getInstance().isEngineerKnown(Engineer.YI_SHEN)),
    D3(BlueprintName.ENGINEER_D3, "blueprint.description.engineer_d3", () -> ApplicationState.getInstance().isEngineerUnlocked(Engineer.YI_SHEN));

    @Getter
    private final BlueprintName blueprintName;
    @Getter
    private final String localisationKey;
    private final Supplier<Boolean> isCompletedSupplier;

    public Boolean isCompleted() {
        return this.isCompletedSupplier.get();
    }
}
