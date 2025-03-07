package nl.jixxed.eliteodysseymaterials.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.enums.ColonisationBuildable;
import nl.jixxed.eliteodysseymaterials.enums.Commodity;
import nl.jixxed.eliteodysseymaterials.service.ColonisationService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
public class ColonisationItem {

    @JsonIgnore
    public static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    @JsonIgnore
    public static final ColonisationItem ALL = new ColonisationItem("0", "All colonization projects", null, new HashMap<>());
    @EqualsAndHashCode.Include
    private String uuid = UUID.randomUUID().toString();
    private String name;
    private ColonisationBuildable colonisationBuildable;
    private Map<Commodity, Integer> constructionRequirements;

    public ColonisationItem(String name, ColonisationBuildable colonisationBuildable, Map<Commodity, Integer> constructionRequirements) {
        this.name = name;
        this.colonisationBuildable = colonisationBuildable;
        this.constructionRequirements = new HashMap<>(constructionRequirements);
    }

    public Map<Commodity, Integer> getConstructionRequirements() {
        if (this == ALL) {
            return APPLICATION_STATE.getPreferredCommander()
                    .map(commander -> (Map<Commodity, Integer>) ColonisationService.getColonisationItems(commander).getAllColonisationItems().stream()
                            .filter(colonisationItem -> colonisationItem != ColonisationItem.ALL)
                            .flatMap(colonisationItem -> colonisationItem.getConstructionRequirements().entrySet().stream())
                            .collect(HashMap<Commodity, Integer>::new, (m, v) -> m.merge(v.getKey(), v.getValue(), Integer::sum), HashMap::putAll))
                    .orElse(constructionRequirements);
        }
        return constructionRequirements;
    }

    public void updateAmount(Commodity commodity, Integer value) {
        constructionRequirements.put(commodity, value);

    }
}
