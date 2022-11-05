package nl.jixxed.eliteodysseymaterials.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CoriolisWishlistItem {
    private String item;
    private String blueprint;
    private Integer grade;
    private Double highestGradePercentage;
}
