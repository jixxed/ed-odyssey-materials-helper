package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.service.ImageService;
import nl.jixxed.eliteodysseymaterials.service.event.AfterFontSizeSetEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.templates.Template;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableResizableImageView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class Shield extends StackPane implements Template {

    private static final NumberFormat NUMBER_FORMAT_2 = NumberFormat.getNumberInstance();

    static {
        NUMBER_FORMAT_2.setMaximumFractionDigits(2);
        NUMBER_FORMAT_2.setMinimumFractionDigits(2);
        NUMBER_FORMAT_2.setGroupingUsed(false);
    }

    double raw;
    double kinetic;
    double thermal;
    double caustic;
    double explosive;
    Label rawLabel;
    Label kineticLabel;
    Label thermalLabel;
    Label causticLabel;
    Label explosiveLabel;
    Label rawTitleLabel;
    Label kineticTitleLabel;
    Label thermalTitleLabel;
    Label causticTitleLabel;
    Label explosiveTitleLabel;
    String title;
    String color;
    private final List<EventListener<?>> eventListeners = new ArrayList<>();
    private DestroyableResizableImageView shield;

    public Shield(String title, String color) {
        this.title = title;
        this.color = color;
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("shield-component");
        shield = ResizableImageViewBuilder.builder().withStyleClass("shield-image").withImage(ImageService.getImage("/images/ships/icons/shield2_" + color + ".png")).build();
        final VBox shieldBox = BoxBuilder.builder().withNodes(BoxBuilder.builder().withNodes(new GrowingRegion(), shield, new GrowingRegion()).buildHBox(), new GrowingRegion()).buildVBox();
        this.getChildren().add(shieldBox);
        final VBox titleBox = BoxBuilder.builder().withNodes(new GrowingRegion(), BoxBuilder.builder().withNodes(new GrowingRegion(), LabelBuilder.builder().withStyleClass("shield-label-" + color).withNonLocalizedText(title).build(), new GrowingRegion()).buildHBox(), new GrowingRegion()).buildVBox();
        this.getChildren().add(titleBox);
        rawTitleLabel = LabelBuilder.builder().withStyleClass("shield-label-" + color).withNonLocalizedText("Raw").build();
        kineticTitleLabel = LabelBuilder.builder().withStyleClass("shield-label-" + color).withNonLocalizedText("Kinetic").build();
        thermalTitleLabel = LabelBuilder.builder().withStyleClass("shield-label-" + color).withNonLocalizedText("Thermal").build();
        causticTitleLabel = LabelBuilder.builder().withStyleClass("shield-label-" + color).withNonLocalizedText("Caustic").build();
        explosiveTitleLabel = LabelBuilder.builder().withStyleClass("shield-label-" + color).withNonLocalizedText("Explosive").build();
        rawLabel = LabelBuilder.builder().withNonLocalizedText("0").build();
        kineticLabel = LabelBuilder.builder().withNonLocalizedText("0").build();
        thermalLabel = LabelBuilder.builder().withNonLocalizedText("0").build();
        causticLabel = LabelBuilder.builder().withNonLocalizedText("0").build();
        explosiveLabel = LabelBuilder.builder().withNonLocalizedText("0").build();
        final VBox valuesBox = BoxBuilder.builder().withStyleClass("shield-values").withNodes(
                BoxBuilder.builder().withNodes(rawTitleLabel, new GrowingRegion(), kineticTitleLabel).buildHBox(),
                BoxBuilder.builder().withNodes(rawLabel, new GrowingRegion(), kineticLabel).buildHBox(),
                new GrowingRegion(),
                BoxBuilder.builder().withNodes(thermalLabel, new GrowingRegion(), explosiveLabel).buildHBox(),
                BoxBuilder.builder().withNodes(thermalTitleLabel, new GrowingRegion(), explosiveTitleLabel).buildHBox(),
                new GrowingRegion(),
                BoxBuilder.builder().withNodes(new GrowingRegion(), causticLabel, new GrowingRegion()).buildHBox(),
                BoxBuilder.builder().withNodes(new GrowingRegion(), causticTitleLabel, new GrowingRegion()).buildHBox()
        ).buildVBox();
        this.getChildren().add(valuesBox);
    }

    @Override
    public void initEventHandling() {
        this.eventListeners.add(EventService.addListener(this, AfterFontSizeSetEvent.class, fontSizeEvent -> shield.setImage(ImageService.getImage("/images/ships/icons/shield2_" + color + ".png"))));
    }

    public void updateValues(double raw, double kinetic, double thermal, double caustic, double explosive) {
        this.raw = raw;
        this.kinetic = kinetic;
        this.thermal = thermal;
        this.caustic = caustic;
        this.explosive = explosive;
        update();
    }

    public void update() {
        rawLabel.setText(NUMBER_FORMAT_2.format(raw));
        kineticLabel.setText(NUMBER_FORMAT_2.format(kinetic));
        thermalLabel.setText(NUMBER_FORMAT_2.format(thermal));
        causticLabel.setText(NUMBER_FORMAT_2.format(caustic));
        explosiveLabel.setText(NUMBER_FORMAT_2.format(explosive));
    }
}
