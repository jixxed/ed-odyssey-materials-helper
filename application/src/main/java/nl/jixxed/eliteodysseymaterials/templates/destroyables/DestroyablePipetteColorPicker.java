package nl.jixxed.eliteodysseymaterials.templates.destroyables;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.builder.ButtonBuilder;
import nl.jixxed.eliteodysseymaterials.builder.FontAwesomeIconViewBuilder;
import nl.jixxed.eliteodysseymaterials.templates.components.PipetteOverlay;

@Getter
public class DestroyablePipetteColorPicker extends DestroyableHBox implements DestroyableTemplate {
    private DestroyableColorPicker colorPicker;
    private DestroyableButton pipetteButton;

    public DestroyablePipetteColorPicker() {
        initComponents();
    }

    @Override
    public void initComponents() {
        this.colorPicker = new DestroyableColorPicker();
        this.pipetteButton = ButtonBuilder.builder()
                .withStyleClass("pipette-button")
                .withGraphic(FontAwesomeIconViewBuilder.builder()
                        .withIcon(FontAwesomeIcon.EYEDROPPER)
                        .withOnMouseClicked(e -> PipetteOverlay.start(color -> colorPicker.setValue(color)))
                        .build())
                .build();
        this.getNodes().addAll(this.colorPicker, this.pipetteButton);

    }
}
