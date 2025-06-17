package nl.jixxed.eliteodysseymaterials.templates.generic;

import javafx.css.PseudoClass;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;

public class AmountCircle extends DestroyableLabel implements DestroyableTemplate {
    @Getter
    private final int index;
    @Getter
    private boolean filled = false;

    public AmountCircle(int index) {
        this.index = index;
        initComponents();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().addAll("value", "value-" + index);
        this.setText("\u25CB");
    }

    public void update(int quantity) {
        filled = index <= quantity;
        this.setText(filled ? "\u23FA" : "\u25CB");
        this.pseudoClassStateChanged(PseudoClass.getPseudoClass("filled"), filled);
    }
}
