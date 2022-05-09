package nl.jixxed.eliteodysseymaterials.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClipboardHorizonsWishlist {
    private String event;
    private Integer version;
    @JsonIgnoreProperties({"wishlist.items.visible"})
    private HorizonsWishlist wishlist;
}
