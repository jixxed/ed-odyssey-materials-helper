package nl.jixxed.eliteodysseymaterials.service.ar;

import lombok.Data;

@Data
public class BartenderMenu {
    private double scale;
    private int contentHeight;
    private int contentWidth;

    public BartenderMenu(final double scale, final int contentWidth, final int contentHeight) {
        this.scale = scale;
        this.contentHeight = contentHeight;
        this.contentWidth = contentWidth;
    }
}
