package nl.jixxed.eliteodysseymaterials.templates.odyssey.menu;

import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ComboBoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ScrollPaneBuilder;
import nl.jixxed.eliteodysseymaterials.builder.TitledPaneBuilder;
import nl.jixxed.eliteodysseymaterials.constants.OdysseyBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.constants.UTF8Constants;
import nl.jixxed.eliteodysseymaterials.domain.OdysseyBlueprint;
import nl.jixxed.eliteodysseymaterials.enums.BlueprintCategory;
import nl.jixxed.eliteodysseymaterials.enums.Craftability;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyBlueprintName;
import nl.jixxed.eliteodysseymaterials.helper.BlueprintHelper;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;
import nl.jixxed.eliteodysseymaterials.templates.generic.menu.About;

import java.util.Comparator;
import java.util.Map;

@Slf4j
public class OdysseyBlueprintBar extends DestroyableAccordion implements DestroyableEventTemplate {
    private About about;
    private TitledPane[] categoryTitledPanes;
    private TitledPane aboutTitledPane;


    private Callback<ListView<OdysseyBlueprintName>, ListCell<OdysseyBlueprintName>> cellFactory = _ -> new ListCell<>() {

        @SuppressWarnings("java:S1068")
        private final EventListener<StorageEvent> storageEventEventListener = EventService.addListener(true, OdysseyBlueprintBar.this, StorageEvent.class, event -> {
            updateStyle(getItem());
            updateText(getItem(), this.emptyProperty().get());
        });
        @SuppressWarnings("java:S1068")
        private final EventListener<EngineerEvent> engineerEventEventListener = EventService.addListener(true, OdysseyBlueprintBar.this, EngineerEvent.class, event -> {
            updateStyle(getItem());
            updateText(getItem(), this.emptyProperty().get());
        });


        @Override
        protected void updateItem(final OdysseyBlueprintName item, final boolean empty) {
            super.updateItem(item, empty);
            updateText(item, empty);
            updateStyle(item);
        }

        private void updateText(final OdysseyBlueprintName item, final boolean empty) {
            if (empty || item == null) {
                setText(null);
                setGraphic(null);
            } else {
                setText(item + (BlueprintHelper.isCompletedEngineerRecipe(item) ? " " + UTF8Constants.CHECK_TRUE : ""));
            }
        }

        private void updateStyle(final OdysseyBlueprintName item) {
            if (BlueprintHelper.isCompletedEngineerRecipe(item) || BlueprintHelper.getCraftabilityForModuleOrUpgradeRecipe(item).equals(Craftability.CRAFTABLE)) {
                this.setStyle("-fx-text-fill: #89d07f;");
            } else if (BlueprintHelper.getCraftabilityForModuleOrUpgradeRecipe(item).equals(Craftability.CRAFTABLE_WITH_TRADE)) {
                this.setStyle("-fx-text-fill: #dcc030;");
            } else {
                this.setStyle("-fx-text-fill: white;");
            }
        }

    };

    public OdysseyBlueprintBar() {
        super();
        initComponents();

    }

    public void initComponents() {
        this.getStyleClass().add("blueprint-menu");
        this.categoryTitledPanes = OdysseyBlueprintConstants.RECIPES.entrySet().stream()
                .sorted(Comparator.comparing(recipeCategoryMapEntry -> recipeCategoryMapEntry.getKey().toString()))
                .map(this::createCategoryTitledPane)
                .toArray(DestroyableTitledPane[]::new);
        initAboutTitledPane();
        this.getPanes().addAll(this.categoryTitledPanes);
        this.getPanes().add(this.aboutTitledPane);
        this.setExpandedPane(this.aboutTitledPane);
    }

    @Override
    public void initEventHandling() {
        //see createCategoryTitledPane
    }

    private void initAboutTitledPane() {
        this.about = new About();
        this.aboutTitledPane = TitledPaneBuilder.builder()
                .withStyleClass("about-titled-pane")
                .withContent(this.about)
                .withText("menu.about")
                .build();
    }

    private DestroyableTitledPane createCategoryTitledPane(final Map.Entry<BlueprintCategory, Map<OdysseyBlueprintName, ? extends OdysseyBlueprint>> recipesEntry) {
        final DestroyableScrollPane scroll = ScrollPaneBuilder.builder()
                .withVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER)
                .build();

        final DestroyableComboBox<OdysseyBlueprintName> recipes = ComboBoxBuilder.builder(OdysseyBlueprintName.class)
                .withStyleClass("blueprint-list")
                .withItemsProperty(LocaleService.getListBinding(recipesEntry.getValue().keySet().stream().sorted(Comparator.comparing(recipeName -> LocaleService.getLocalizedStringForCurrentLocale(recipeName.getLocalizationKey()))).toArray(OdysseyBlueprintName[]::new)))
                .asLocalized()
                .build();
        recipes.setVisibleRowCount(recipes.getItems().size());

        final DestroyableHBox hBox = BoxBuilder.builder()
                .withNode(recipes)
                .buildHBox();
        HBox.setHgrow(recipes, Priority.ALWAYS);

        final DestroyableVBox content = BoxBuilder.builder()
                .withStyleClass("blueprint-titled-pane-content")
                .withNodes(hBox, scroll)
                .buildVBox();
        VBox.setVgrow(scroll, Priority.ALWAYS);

        final DestroyableTitledPane categoryTitledPane = TitledPaneBuilder.builder()
                .withStyleClass("blueprint-title-pane")
                .withText(recipesEntry.getKey().getLocalizationKey())
                .withContent(content)
                .build();

        recipes.addChangeListener(recipes.valueProperty(), (_, _, newValue) -> {
            if (scroll.getContent() != null) ((DestroyableTemplate) scroll.getContent()).destroyTemplate();
            scroll.setContent(createRecipeContent(OdysseyBlueprintConstants.getRecipe(newValue)));
        });
        recipes.setCellFactory(cellFactory);
        recipes.getSelectionModel().select(recipes.getItems().get(0));
        recipes.setButtonCell(cellFactory.call(null));

        register(EventService.addListener(true, this, BlueprintClickEvent.class, blueprintClickEvent -> {
            if (blueprintClickEvent.getBlueprintName() instanceof OdysseyBlueprintName odysseyBlueprintName && recipes.getItems().contains(odysseyBlueprintName)) {
                recipes.getSelectionModel().select(odysseyBlueprintName);
                this.setExpandedPane(categoryTitledPane);
            }
        }));

        return categoryTitledPane;
    }

    private Node createRecipeContent(OdysseyBlueprint odysseyBlueprint) {
        return new OdysseyBlueprintContent(odysseyBlueprint);
    }
}
