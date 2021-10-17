package nl.jixxed.eliteodysseymaterials.templates;

import javafx.scene.layout.HBox;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import nl.jixxed.eliteodysseymaterials.enums.Material;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
class Trade extends HBox {
    @EqualsAndHashCode.Include
    private String offerId;
    private Material offerMaterial;
    private Material receiveMaterial;
    private Integer offerAmount;
    private Integer receiveAmount;
    private Boolean online;

    Trade(final String offerId, final Material offerMaterial, final Integer offerAmount, final Material receiveMaterial, final Integer receiveAmount) {
        this.offerId = offerId;
        this.offerMaterial = offerMaterial;
        this.receiveMaterial = receiveMaterial;
        this.offerAmount = offerAmount;
        this.receiveAmount = receiveAmount;
        this.online = true;
    }
}
