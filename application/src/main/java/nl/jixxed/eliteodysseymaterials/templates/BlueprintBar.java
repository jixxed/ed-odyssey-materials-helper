package nl.jixxed.eliteodysseymaterials.templates;

import javafx.application.Application;
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
import nl.jixxed.eliteodysseymaterials.constants.BlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.Blueprint;
import nl.jixxed.eliteodysseymaterials.enums.BlueprintCategory;
import nl.jixxed.eliteodysseymaterials.enums.BlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.Craftability;
import nl.jixxed.eliteodysseymaterials.helper.BlueprintHelper;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.*;

import java.util.Comparator;
import java.util.EnumMap;
import java.util.Map;

@Slf4j
class BlueprintBar extends Accordion {
    private About about;
    private TitledPane[] categoryTitledPanes;
    private TitledPane aboutTitledPane;
    private final Application application;

    BlueprintBar(final Application application) {
        this.application = application;
        initComponents();

    }

    private void initComponents() {
        this.getStyleClass().add("recipe");
        this.categoryTitledPanes = BlueprintConstants.RECIPES.entrySet().stream()
                .sorted(Comparator.comparing(recipeCategoryMapEntry -> recipeCategoryMapEntry.getKey().toString()))
                .map(this::createCategoryTitledPane)
                .toArray(TitledPane[]::new);
        initAboutTitledPane();
        this.getPanes().addAll(this.categoryTitledPanes);
        this.getPanes().add(this.aboutTitledPane);
        this.setExpandedPane(this.aboutTitledPane);
    }

    private void initAboutTitledPane() {
        this.about = new About(this.application);
        this.aboutTitledPane = TitledPaneBuilder.builder().withContent(this.about).withText(LocaleService.getStringBinding("menu.about")).build();
    }

    private TitledPane createCategoryTitledPane(final Map.Entry<BlueprintCategory, Map<BlueprintName, ? extends Blueprint>> recipesEntry) {
        final ScrollPane scroll = new ScrollPane();
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setPannable(true);
        scroll.setFitToHeight(true);
        scroll.setFitToWidth(true);

        final ComboBox<BlueprintName> recipes = ComboBoxBuilder.builder(BlueprintName.class)
                .withStyleClass("recipes-list")
                .withItemsProperty(LocaleService.getListBinding(recipesEntry.getValue().keySet().stream().sorted(Comparator.comparing(recipeName -> LocaleService.getLocalizedStringForCurrentLocale(recipeName.getLocalizationKey()))).toArray(BlueprintName[]::new)))
                .asLocalized()
                .build();
        recipes.setVisibleRowCount(recipes.getItems().size());

        final HBox hBox = BoxBuilder.builder().withNode(recipes).buildHBox();
        HBox.setHgrow(recipes, Priority.ALWAYS);

        final VBox content = BoxBuilder.builder()
                .withStyleClass("recipe-titled-pane-content")
                .withNodes(hBox, scroll)
                .buildVBox();
        VBox.setVgrow(scroll, Priority.ALWAYS);

        final TitledPane categoryTitledPane = TitledPaneBuilder.builder()
                .withStyleClass("category-title-pane")
                .withText(LocaleService.getStringBinding(recipesEntry.getKey().getLocalizationKey()))
                .withContent(content)
                .build();

        final Map<BlueprintName, Node> recipeContent = createRecipeContent(recipesEntry, recipes, categoryTitledPane);
        recipes.valueProperty().addListener((obs, oldValue, newValue) -> scroll.setContent(recipeContent.get(newValue)));
        recipes.setCellFactory(getCellFactory());
        recipes.getSelectionModel().select(recipes.getItems().get(0));
        recipes.setButtonCell(new ListCell<>() {
            @SuppressWarnings("java:S1068")
            private final EventListener<StorageEvent> storageEventEventListener = EventService.addListener(BlueprintBar.this, StorageEvent.class, event -> {
                updateStyle(getItem());
                updateText(getItem(), this.emptyProperty().get());
            });
            @SuppressWarnings("java:S1068")
            private final EventListener<EngineerEvent> engineerEventEventListener = EventService.addListener(BlueprintBar.this, EngineerEvent.class, event -> {
                updateStyle(getItem());
                updateText(getItem(), this.emptyProperty().get());
            });

            @Override
            protected void updateItem(final BlueprintName item, final boolean empty) {
                super.updateItem(item, empty);
                updateText(item, empty);
                updateStyle(item);
            }


            private void updateText(final BlueprintName item, final boolean empty) {
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item.toString() + (BlueprintHelper.isCompletedEngineerRecipe(item) ? " \u2714" : ""));
                }
            }


            private void updateStyle(final BlueprintName item) {
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

    private Callback<ListView<BlueprintName>, ListCell<BlueprintName>> getCellFactory() {
        return listView -> new ListCell<>() {

            @SuppressWarnings("java:S1068")
            private final EventListener<StorageEvent> storageEventEventListener = EventService.addListener(BlueprintBar.this, StorageEvent.class, event -> {
                updateStyle(getItem());
                updateText(getItem(), this.emptyProperty().get());
            });
            @SuppressWarnings("java:S1068")
            private final EventListener<EngineerEvent> engineerEventEventListener = EventService.addListener(BlueprintBar.this, EngineerEvent.class, event -> {
                updateStyle(getItem());
                updateText(getItem(), this.emptyProperty().get());
            });


            @Override
            protected void updateItem(final BlueprintName item, final boolean empty) {
                super.updateItem(item, empty);
                updateText(item, empty);
                updateStyle(item);
            }

            private void updateText(final BlueprintName item, final boolean empty) {
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item + (BlueprintHelper.isCompletedEngineerRecipe(item) ? " \u2714" : ""));
                }
            }

            private void updateStyle(final BlueprintName item) {
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

    private Map<BlueprintName, Node> createRecipeContent(final Map.Entry<BlueprintCategory, Map<BlueprintName, ? extends Blueprint>> recipesEntry, final ComboBox<BlueprintName> comboBox, final TitledPane categoryTitledPane) {
        final Map<BlueprintName, Node> contents = new EnumMap<>(BlueprintName.class);
        recipesEntry.getValue().forEach((key, value) -> {
            EventService.addListener(this, BlueprintClickEvent.class, blueprintClickEvent -> {
                if (blueprintClickEvent.getBlueprintName().equals(key)) {
                    comboBox.getSelectionModel().select(key);
                    this.setExpandedPane(categoryTitledPane);
                }
            });
            final BlueprintContent blueprintContent = new BlueprintContent(value);
            contents.put(key, blueprintContent);
        });
        return contents;
    }
}
