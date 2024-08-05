package nl.jixxed.eliteodysseymaterials.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WishlistMaterial {
    private Integer minimum;
    private Integer required;
    private Integer maximum;

    public static WishlistMaterial sum(WishlistMaterial a, WishlistMaterial b){
        var minimumSum = a.minimum + b.minimum;
        var requiredSum = a.required + b.required;
        var maximumSum = a.maximum + b.maximum;
        return new WishlistMaterial(minimumSum, requiredSum, maximumSum);
    }
}
