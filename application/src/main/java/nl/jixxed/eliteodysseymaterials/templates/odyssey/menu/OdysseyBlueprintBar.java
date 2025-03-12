package nl.jixxed.eliteodysseymaterials.templates.odyssey.menu;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ComboBoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.TitledPaneBuilder;
import nl.jixxed.eliteodysseymaterials.constants.OdysseyBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.OdysseyBlueprint;
import nl.jixxed.eliteodysseymaterials.enums.BlueprintCategory;
import nl.jixxed.eliteodysseymaterials.enums.Craftability;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyBlueprintName;
import nl.jixxed.eliteodysseymaterials.helper.BlueprintHelper;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;
import nl.jixxed.eliteodysseymaterials.templates.generic.About;

import java.util.Comparator;
import java.util.EnumMap;
import java.util.Map;

@Slf4j
public
class OdysseyBlueprintBar extends Accordion implements DestroyableTemplate {
    private About about;
    private TitledPane[] categoryTitledPanes;
    private TitledPane aboutTitledPane;


    public OdysseyBlueprintBar() {
        initComponents();

    }

    private void initComponents() {
        this.getStyleClass().add("blueprint-bar");
        this.categoryTitledPanes = OdysseyBlueprintConstants.RECIPES.entrySet().stream()
                .sorted(Comparator.comparing(recipeCategoryMapEntry -> recipeCategoryMapEntry.getKey().toString()))
                .map(this::createCategoryTitledPane)
                .toArray(TitledPane[]::new);
        initAboutTitledPane();
        this.getPanes().addAll(this.categoryTitledPanes);
        this.getPanes().add(this.aboutTitledPane);
        this.setExpandedPane(this.aboutTitledPane);
    }

    private void initAboutTitledPane() {
        this.about = new About();
        this.aboutTitledPane = TitledPaneBuilder.builder().withContent(this.about).withText(LocaleService.getStringBinding("menu.about")).build();
    }

    private TitledPane createCategoryTitledPane(final Map.Entry<BlueprintCategory, Map<OdysseyBlueprintName, ? extends OdysseyBlueprint>> recipesEntry) {
        final ScrollPane scroll = new ScrollPane();
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setPannable(true);
        scroll.setFitToHeight(true);
        scroll.setFitToWidth(true);

        final ComboBox<OdysseyBlueprintName> recipes = ComboBoxBuilder.builder(OdysseyBlueprintName.class)
                .withStyleClass("blueprint-list")
                .withItemsProperty(LocaleService.getListBinding(recipesEntry.getValue().keySet().stream().sorted(Comparator.comparing(recipeName -> LocaleService.getLocalizedStringForCurrentLocale(recipeName.getLocalizationKey()))).toArray(OdysseyBlueprintName[]::new)))
                .asLocalized()
                .build();
        recipes.setVisibleRowCount(recipes.getItems().size());

        final HBox hBox = BoxBuilder.builder().withNode(recipes).buildHBox();
        HBox.setHgrow(recipes, Priority.ALWAYS);

        final VBox content = BoxBuilder.builder()
                .withStyleClass("blueprint-titled-pane-content")
                .withNodes(hBox, scroll)
                .buildVBox();
        VBox.setVgrow(scroll, Priority.ALWAYS);

        final TitledPane categoryTitledPane = TitledPaneBuilder.builder()
                .withStyleClass("category-title-pane")
                .withText(LocaleService.getStringBinding(recipesEntry.getKey().getLocalizationKey()))
                .withContent(content)
                .build();

        final Map<OdysseyBlueprintName, Node> recipeContent = createRecipeContent(recipesEntry, recipes, categoryTitledPane);
        recipes.valueProperty().addListener((obs, oldValue, newValue) -> scroll.setContent(recipeContent.get(newValue)));
        recipes.setCellFactory(getCellFactory());
        recipes.getSelectionModel().select(recipes.getItems().get(0));
        recipes.setButtonCell(new ListCell<>() {
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
                    setText(item.toString() + (BlueprintHelper.isCompletedEngineerRecipe(item) ? " \u2714" : ""));
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

        });
        return categoryTitledPane;
    }

    private Callback<ListView<OdysseyBlueprintName>, ListCell<OdysseyBlueprintName>> getCellFactory() {
        return listView -> new ListCell<>() {

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
                    setText(item + (BlueprintHelper.isCompletedEngineerRecipe(item) ? " \u2714" : ""));
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
    }

    private Map<OdysseyBlueprintName, Node> createRecipeContent(final Map.Entry<BlueprintCategory, Map<OdysseyBlueprintName, ? extends OdysseyBlueprint>> recipesEntry, final ComboBox<OdysseyBlueprintName> comboBox, final TitledPane categoryTitledPane) {
        final Map<OdysseyBlueprintName, Node> contents = new EnumMap<>(OdysseyBlueprintName.class);
        recipesEntry.getValue().forEach((key, value) -> {
            this.eventListeners.add(EventService.addListener(true, this, BlueprintClickEvent.class, blueprintClickEvent -> {
                if (blueprintClickEvent.getBlueprintName().equals(key)) {
                    comboBox.getSelectionModel().select(key);
                    this.setExpandedPane(categoryTitledPane);
                }
            }));
            final OdysseyBlueprintContent odysseyBlueprintContent = new OdysseyBlueprintContent(value);
            contents.put(key, odysseyBlueprintContent);
        });
        return contents;
    }
}
