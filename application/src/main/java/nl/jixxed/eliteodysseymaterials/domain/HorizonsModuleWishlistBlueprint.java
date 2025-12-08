package nl.jixxed.eliteodysseymaterials.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;

import java.util.Map;
import java.util.stream.Collectors;
@Slf4j
@NoArgsConstructor
//@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public final class HorizonsModuleWishlistBlueprint extends HorizonsWishlistBlueprint {
    private HorizonsBlueprintType blueprintType;
    private HorizonsBlueprintType experimentalEffect;
    //no longer persisting the data
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Map<HorizonsBlueprintGrade, Integer> blueprintGradeRolls;
    private Map<HorizonsBlueprintGrade, Double> percentageToComplete;

    public Map<HorizonsBlueprintGrade, Double> getPercentageToComplete() {
        return percentageToComplete != null ? percentageToComplete : blueprintGradeRolls.entrySet().stream().map(entry -> Map.entry(entry.getKey(), entry.getValue() > 0 ? 1D : 0D)).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public HorizonsModuleWishlistBlueprint(HorizonsBlueprintType blueprintType, Map<HorizonsBlueprintGrade, Double> percentageToComplete) {
        this.blueprintType = blueprintType;
        this.percentageToComplete = percentageToComplete;
        bugfix(getRecipeName(), experimentalEffect);
    }

    public void setBlueprintGradePercentageToComplete(final HorizonsBlueprintGrade grade, final Double percentage) {
        if (this.percentageToComplete != null && percentage.equals(0D)) {
            this.percentageToComplete.remove(grade);
            return;
        } else if (this.percentageToComplete != null && percentage > 0D) {
            this.percentageToComplete.put(grade, percentage);
            return;
        }
        throw new IllegalArgumentException("Cannot set percentage to complete for grade: " + grade.name() + "/" + percentage);
    }

    public void setRecipeName(HorizonsBlueprintName recipeName) {
        super.setRecipeName(recipeName);
        bugfix(recipeName, this.experimentalEffect);
    }

    private void bugfix(HorizonsBlueprintName recipeName, HorizonsBlueprintType experimentalEffect) {
        if (HorizonsBlueprintName.MISSILE_RACK.equals(recipeName)) {
            log.debug("HorizonsModuleWishlistBlueprint: Bugfixing recipe name for MISSILE_RACK to DUMBFIRE_MISSILE_RACK");
            super.setRecipeName(HorizonsBlueprintName.DUMBFIRE_MISSILE_RACK);
        }
        if (HorizonsBlueprintName.DUMBFIRE_MISSILE_RACK.equals(getRecipeName()) && HorizonsBlueprintType.DRAG_MUNITION.equals(experimentalEffect)) {
            log.debug("HorizonsModuleWishlistBlueprint: Bugfixing recipe name for DUMBFIRE_MISSILE_RACK to SEEKER_MISSILE_RACK");
            super.setRecipeName(HorizonsBlueprintName.SEEKER_MISSILE_RACK);
        }
    }

    public void setBlueprintType(HorizonsBlueprintType blueprintType) {
        this.blueprintType = blueprintType;
        bugfix(getRecipeName(), experimentalEffect);
    }

    public void setExperimentalEffect(HorizonsBlueprintType experimentalEffect) {
        this.experimentalEffect = experimentalEffect;
        bugfix(getRecipeName(), experimentalEffect);
    }
}
