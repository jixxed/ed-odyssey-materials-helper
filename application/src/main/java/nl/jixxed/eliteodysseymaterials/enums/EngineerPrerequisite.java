package nl.jixxed.eliteodysseymaterials.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;

import java.util.function.Supplier;

@RequiredArgsConstructor
public enum EngineerPrerequisite {
    A1(RecipeName.ENGINEER_A1, "recipe.description.engineer_a1", () -> ApplicationState.getInstance().isEngineerUnlocked(Engineer.DOMINO_GREEN)),
    A2(RecipeName.ENGINEER_A2, "recipe.description.engineer_a2", () -> ApplicationState.getInstance().isEngineerKnown(Engineer.KIT_FOWLER)),
    A3(RecipeName.ENGINEER_A3, "recipe.description.engineer_a3", () -> ApplicationState.getInstance().isEngineerUnlocked(Engineer.KIT_FOWLER)),
    A4(RecipeName.ENGINEER_A4, "recipe.description.engineer_a4", () -> ApplicationState.getInstance().isEngineerKnown(Engineer.YARDEN_BOND)),
    A5(RecipeName.ENGINEER_A5, "recipe.description.engineer_a5", () -> ApplicationState.getInstance().isEngineerUnlocked(Engineer.YARDEN_BOND)),
    B1(RecipeName.ENGINEER_B1, "recipe.description.engineer_b1", () -> ApplicationState.getInstance().isEngineerUnlocked(Engineer.HERO_FERRARI)),
    B2(RecipeName.ENGINEER_B2, "recipe.description.engineer_b2", () -> ApplicationState.getInstance().isEngineerKnown(Engineer.WELLINGTON_BECK)),
    B3(RecipeName.ENGINEER_B3, "recipe.description.engineer_b3", () -> ApplicationState.getInstance().isEngineerUnlocked(Engineer.WELLINGTON_BECK)),
    B4(RecipeName.ENGINEER_B4, "recipe.description.engineer_b4", () -> ApplicationState.getInstance().isEngineerKnown(Engineer.UMA_LASZLO)),
    B5(RecipeName.ENGINEER_B5, "recipe.description.engineer_b5", () -> ApplicationState.getInstance().isEngineerUnlocked(Engineer.UMA_LASZLO)),
    C1(RecipeName.ENGINEER_C1, "recipe.description.engineer_c1", () -> ApplicationState.getInstance().isEngineerUnlocked(Engineer.JUDE_NAVARRO)),
    C2(RecipeName.ENGINEER_C2, "recipe.description.engineer_c2", () -> ApplicationState.getInstance().isEngineerKnown(Engineer.TERRA_VELASQUEZ)),
    C3(RecipeName.ENGINEER_C3, "recipe.description.engineer_c3", () -> ApplicationState.getInstance().isEngineerUnlocked(Engineer.TERRA_VELASQUEZ)),
    C4(RecipeName.ENGINEER_C4, "recipe.description.engineer_c4", () -> ApplicationState.getInstance().isEngineerKnown(Engineer.ODEN_GEIGER)),
    C5(RecipeName.ENGINEER_C5, "recipe.description.engineer_c5", () -> ApplicationState.getInstance().isEngineerUnlocked(Engineer.ODEN_GEIGER));

    @Getter
    private final RecipeName recipeName;
    @Getter
    private final String localisationKey;
    private final Supplier<Boolean> isCompletedSupplier;

    public Boolean isCompleted() {
        return this.isCompletedSupplier.get();
    }
}
