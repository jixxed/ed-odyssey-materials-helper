package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats;

import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.service.ImageService;
import nl.jixxed.eliteodysseymaterials.service.event.AfterFontSizeSetEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

import java.text.NumberFormat;

public class Shield extends DestroyableStackPane implements DestroyableEventTemplate {

    private static final NumberFormat NUMBER_FORMAT_2 = NumberFormat.getNumberInstance();

    static {
        NUMBER_FORMAT_2.setMaximumFractionDigits(2);
        NUMBER_FORMAT_2.setMinimumFractionDigits(2);
        NUMBER_FORMAT_2.setGroupingUsed(false);
    }

    private double raw;
    private double kinetic;
    private double thermal;
    private double caustic;
    private double explosive;
    private DestroyableLabel rawLabel;
    private DestroyableLabel kineticLabel;
    private DestroyableLabel thermalLabel;
    private DestroyableLabel causticLabel;
    private DestroyableLabel explosiveLabel;
    private final String title;
    private final String color;
    private final String symbol;

    private DestroyableResizableImageView shieldImage;

    public Shield(String title, String symbol, String color) {
        this.title = title;
        this.color = color;
        this.symbol = symbol;
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("shield-component-" + this.color);
        shieldImage = ResizableImageViewBuilder.builder()
                .withStyleClass("shield-image")
                .withImage(ImageService.getImage("/images/ships/icons/shield2_" + color + ".png"))
                .build();
        final DestroyableVBox shieldBox = BoxBuilder.builder()
                .withNodes(BoxBuilder.builder()
                        .withNodes(new GrowingRegion(), shieldImage, new GrowingRegion())
                        .buildHBox(), new GrowingRegion())
                .buildVBox();
        this.getNodes().add(shieldBox);
        final DestroyableVBox titleBox = BoxBuilder.builder()
                .withNodes(BoxBuilder.builder()
                        .withNodes(new GrowingRegion(), LabelBuilder.builder()
                                .withStyleClasses("shield-label-" + color, "shield-label-symbol")
                                .withNonLocalizedText(symbol)
                                .build(), new GrowingRegion())
                        .buildHBox(), BoxBuilder.builder()
                        .withNodes(new GrowingRegion(), LabelBuilder.builder()
                                .withStyleClasses("shield-label-" + color, "shield-label-title")
                                .withNonLocalizedText(title)
                                .build(), new GrowingRegion())
                        .buildHBox(), new GrowingRegion())
                .buildVBox();
        this.getNodes().add(titleBox);
        DestroyableLabel rawTitleLabel = LabelBuilder.builder()
                .withStyleClass("shield-label-" + color)
                .withText("ship.stats.shield.raw")
                .build();
        DestroyableLabel kineticTitleLabel = LabelBuilder.builder()
                .withStyleClass("shield-label-" + color)
                .withText("ship.stats.shield.kinetic")
                .build();
        DestroyableLabel thermalTitleLabel = LabelBuilder.builder()
                .withStyleClass("shield-label-" + color)
                .withText("ship.stats.shield.thermal")
                .build();
        DestroyableLabel causticTitleLabel = LabelBuilder.builder()
                .withStyleClass("shield-label-" + color)
                .withText("ship.stats.shield.caustic")
                .build();
        DestroyableLabel explosiveTitleLabel = LabelBuilder.builder()
                .withStyleClass("shield-label-" + color)
                .withText("ship.stats.shield.explosive")
                .build();
        rawLabel = LabelBuilder.builder()
                .withNonLocalizedText("0")
                .build();
        kineticLabel = LabelBuilder.builder()
                .withNonLocalizedText("0")
                .build();
        thermalLabel = LabelBuilder.builder()
                .withNonLocalizedText("0")
                .build();
        causticLabel = LabelBuilder.builder()
                .withNonLocalizedText("0")
                .build();
        explosiveLabel = LabelBuilder.builder()
                .withNonLocalizedText("0")
                .build();
        if (this.color.equals("blue")) {
            final DestroyableVBox valuesBox = BoxBuilder.builder()
                    .withStyleClass("shield-values")
                    .withNodes(
                            BoxBuilder.builder()
                                    .withNodes(causticTitleLabel, new GrowingRegion(), kineticTitleLabel)
                                    .buildHBox(),
                            BoxBuilder.builder()
                                    .withNodes(causticLabel, new GrowingRegion(), kineticLabel)
                                    .buildHBox(),
                            new GrowingRegion(),
                            BoxBuilder.builder()
                                    .withNodes(thermalLabel, new GrowingRegion(), explosiveLabel)
                                    .buildHBox(),
                            BoxBuilder.builder()
                                    .withNodes(thermalTitleLabel, new GrowingRegion(), explosiveTitleLabel)
                                    .buildHBox()
                    )
                    .buildVBox();
            this.getNodes().add(valuesBox);
        } else {

            final DestroyableVBox valuesBox = BoxBuilder.builder()
                    .withStyleClass("shield-values")
                    .withNodes(
                            BoxBuilder.builder()
                                    .withNodes(causticTitleLabel, new GrowingRegion(), kineticTitleLabel)
                                    .buildHBox(),
                            BoxBuilder.builder()
                                    .withNodes(causticLabel, new GrowingRegion(), kineticLabel)
                                    .buildHBox(),
                            new GrowingRegion(),
                            BoxBuilder.builder()
                                    .withNodes(thermalLabel, new GrowingRegion(), explosiveLabel)
                                    .buildHBox(),
                            BoxBuilder.builder()
                                    .withNodes(thermalTitleLabel, new GrowingRegion(), explosiveTitleLabel)
                                    .buildHBox(),
                            new GrowingRegion(),
                            BoxBuilder.builder()
                                    .withNodes(new GrowingRegion(), rawLabel, new GrowingRegion())
                                    .buildHBox(),
                            BoxBuilder.builder()
                                    .withNodes(new GrowingRegion(), rawTitleLabel, new GrowingRegion())
                                    .buildHBox()
                    ).buildVBox();
            this.getNodes().add(valuesBox);
        }
    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, AfterFontSizeSetEvent.class, fontSizeEvent -> shieldImage.setImage(ImageService.getImage("/images/ships/icons/shield2_" + color + ".png"))));
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
