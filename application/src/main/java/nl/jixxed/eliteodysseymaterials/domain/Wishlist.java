package nl.jixxed.eliteodysseymaterials.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Wishlist {
    @EqualsAndHashCode.Include
    private String uuid = UUID.randomUUID().toString();
    private String name;
    private List<WishlistRecipe> items = new ArrayList<>();

    @Override
    public String toString() {
        return this.name;
    }
}
