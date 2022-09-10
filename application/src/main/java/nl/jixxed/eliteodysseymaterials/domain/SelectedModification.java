package nl.jixxed.eliteodysseymaterials.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nl.jixxed.eliteodysseymaterials.enums.Modification;

@Getter
@Setter
@AllArgsConstructor
public class SelectedModification {
    private Modification modification;
    private boolean present;

    @JsonIgnore
    public String getImage() {
        if (this.modification == null) {
            return "/images/modification/empty.png";
        }
        return this.modification.getImage(isPresent());
    }

    @JsonIgnore
    public boolean isNotPresent() {
        return !this.present;
    }
}
