package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import javafx.css.PseudoClass;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.helper.ScalingHelper;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;

@Slf4j
public class ModuleDetailsProperty extends DestroyableVBox implements DestroyableTemplate {
    private final String propertyNameKey;
    private final String valueKey;
    private final String valueText;
    private final int index;
    private final int size;

    public ModuleDetailsProperty(String propertyNameKey, String valueKey, String valueText, int index, int size) {
        this.propertyNameKey = propertyNameKey;
        this.valueKey = valueKey;
        this.valueText = valueText;
        this.index = index;
        this.size = size;
        initComponents();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("property");
        this.pseudoClassStateChanged(PseudoClass.getPseudoClass("first-row"), ModuleDetailsHelper.isFirstRow(index));
        this.pseudoClassStateChanged(PseudoClass.getPseudoClass("last-row"), ModuleDetailsHelper.isLastRow(index, size));
        this.pseudoClassStateChanged(PseudoClass.getPseudoClass("left-column"), ModuleDetailsHelper.isLeftColumn(index));
        this.pseudoClassStateChanged(PseudoClass.getPseudoClass("right-column"), ModuleDetailsHelper.isRightColumn(index));
        var value = LabelBuilder.builder()
                .withStyleClass("value")
                .withText(valueKey, valueText)
                .build();
        value.setTextAlignment(TextAlignment.RIGHT);

        var name = LabelBuilder.builder()
                .withStyleClass("name")
                .withText(propertyNameKey)
                .build();
        double keyWidth = computeTextWidth(name);
        double valueWidth = computeTextWidth(value) + ScalingHelper.getPixelDoubleFromEm(1D);
        double maxWidth = 21 * name.getFont().getSize();
        if (keyWidth + valueWidth <= maxWidth) {
            // They fit on the same line
            var line = BoxBuilder.builder().withNodes(name, new GrowingRegion(), value).buildHBox();
            this.getNodes().add(line);
        } else {
            // Key on first line
            var keyline = BoxBuilder.builder().withNodes(name).buildHBox();
            var valueline = BoxBuilder.builder().withNodes(new GrowingRegion(), value).buildHBox();
            this.getNodes().addAll(keyline, valueline);
        }
    }


    private double computeTextWidth(Label label) {
        Text tempText = new Text(label.getText());
        tempText.setFont(label.getFont());
        return tempText.getLayoutBounds().getWidth();
    }
}
