package nl.jixxed.eliteodysseymaterials.templates.horizons.colonisation;

import javafx.scene.control.MenuButton;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import nl.jixxed.eliteodysseymaterials.builder.FlowPaneBuilder;
import nl.jixxed.eliteodysseymaterials.builder.MenuButtonBuilder;
import nl.jixxed.eliteodysseymaterials.enums.ColonisationBuildable;
import nl.jixxed.eliteodysseymaterials.enums.ColonisationCategory;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.templates.Template;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ColonisationView extends VBox implements Template {
    private FlowPane buildables;
    private BillOfMaterials billOfMaterials;

    public ColonisationView() {
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
//        final ColonisationButton[] list = Arrays.stream(ColonisationCategory.values())
//                .map(category -> new ColonisationButton(category, buildable -> billOfMaterials.setBuildable(buildable)))
//                .toArray(ColonisationButton[]::new);
        final MenuButton[] list = Arrays.stream(ColonisationCategory.values())
                .map(category ->
                        MenuButtonBuilder.builder()
                                .withText(LocaleService.getStringBinding(category.getLocalizationKey()))
                                .withMenuItems(Arrays.stream(ColonisationBuildable.values())
                                        .filter(buildable -> buildable.getColonisationCategory().equals(category))
                                        .collect(Collectors.toMap(ColonisationBuildable::getLocalizationKey, c -> event -> billOfMaterials.setBuildable(c))))
                                .build()).toArray(MenuButton[]::new);
        buildables = FlowPaneBuilder.builder().withStyleClass("colonisation-buttons").withNodes(list).build();
        billOfMaterials = new BillOfMaterials();
        this.getChildren().addAll(buildables, billOfMaterials);
    }

    @Override
    public void initEventHandling() {

    }
}
