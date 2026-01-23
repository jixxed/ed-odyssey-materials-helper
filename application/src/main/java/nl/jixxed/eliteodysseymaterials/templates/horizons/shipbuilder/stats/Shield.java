package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats;

import javafx.css.PseudoClass;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.EdAwesomeIconViewPaneBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.helper.Formatters;
import nl.jixxed.eliteodysseymaterials.templates.components.EdAwesomeIconViewPane;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.components.edfont.EdAwesomeIcon;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

import java.text.NumberFormat;

public class Shield extends DestroyableStackPane implements DestroyableTemplate {

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
    private final String color;
    private final String symbol;
    private final String type;

    private EdAwesomeIconViewPane shieldImage;

    public Shield(String symbol, String color, String type) {
        this.color = color;
        this.symbol = symbol;
        this.type = type;
        initComponents();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("shield-component-" + this.color);
        shieldImage = "armour".equals(type)
                ? EdAwesomeIconViewPaneBuilder.builder()
                .withStyleClasses("shield-"+ type, this.color)
                .withIcons(EdAwesomeIcon.SHIPS_ARMOUR_1, EdAwesomeIcon.SHIPS_ARMOUR_2)
                .build()
                : EdAwesomeIconViewPaneBuilder.builder()
                .withStyleClasses("shield-"+ type, this.color)
                .withIcons(EdAwesomeIcon.SHIPS_SHIELDS_1, EdAwesomeIcon.SHIPS_SHIELDS_2)
                .build();
        shieldImage.pseudoClassStateChanged(PseudoClass.getPseudoClass(this.color), true);

        final DestroyableVBox shieldBox = BoxBuilder.builder()
                .withNodes(BoxBuilder.builder()
                        .withNodes(new GrowingRegion(), shieldImage, new GrowingRegion())
                        .buildHBox(), new GrowingRegion())
                .buildVBox();
        this.getNodes().add(shieldBox);
        final DestroyableVBox titleBox = BoxBuilder.builder()
                .withNodes( BoxBuilder.builder()
                        .withNodes(new GrowingRegion(),LabelBuilder.builder()
                                .withStyleClasses("shield-"+ type+"-label-" + color, "shield-label-symbol")
                                .withNonLocalizedText(symbol)
                                .build(), new GrowingRegion())
                        .buildHBox(), new GrowingRegion())
                .buildVBox();
        this.getNodes().add(titleBox);

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
            DestroyableLabel rawTitleLabel = LabelBuilder.builder()
                    .withStyleClass("shield-label-" + color)
                    .withText("ship.stats.shield.raw")
                    .build();
            rawLabel = LabelBuilder.builder()
                    .withNonLocalizedText("0")
                    .build();
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

//    @Override
//    public void initEventHandling() {
//        register(EventService.addListener(true, this, AfterFontSizeSetEvent.class, fontSizeEvent -> shieldImage.setImage(ImageService.getImage("/images/ships/icons/shield2_" + color + ".png"))));
//    }

    public void updateValues(double raw, double kinetic, double thermal, double caustic, double explosive) {
        this.raw = raw;
        this.kinetic = kinetic;
        this.thermal = thermal;
        this.caustic = caustic;
        this.explosive = explosive;
        update();
    }

    public void update() {
        if (!this.color.equals("blue")) {
            rawLabel.setText(Formatters.NUMBER_FORMAT_2_NO_GROUP.format(raw));
        }
        kineticLabel.setText(Formatters.NUMBER_FORMAT_2_NO_GROUP.format(kinetic));
        thermalLabel.setText(Formatters.NUMBER_FORMAT_2_NO_GROUP.format(thermal));
        causticLabel.setText(Formatters.NUMBER_FORMAT_2_NO_GROUP.format(caustic));
        explosiveLabel.setText(Formatters.NUMBER_FORMAT_2_NO_GROUP.format(explosive));
    }
}
