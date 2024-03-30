package nl.jixxed.eliteodysseymaterials.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ShipLegacyModules {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ShipLegacyModule> legacyModules = new ArrayList<>();

}
