package nl.jixxed.eliteodysseymaterials.templates.odyssey.menu;

import javafx.css.PseudoClass;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
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
import nl.jixxed.eliteodysseymaterials.service.event.BlueprintClickEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EngineerEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.StorageEvent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;
import nl.jixxed.eliteodysseymaterials.templates.generic.menu.About;

import java.util.Comparator;
import java.util.Map;

@Slf4j
public class OdysseyBlueprintBar extends DestroyableAccordion implements DestroyableEventTemplate {
    private About about;
    private DestroyableTitledPane[] categoryTitledPanes;
    private DestroyableTitledPane aboutTitledPane;

    private Callback<ListView<OdysseyBlueprintName>, ListCell<OdysseyBlueprintName>> createDestroyableCellFactory(Destroyable destroyable) {
        return _ -> new DestroyableListCell<>() {
            {
                destroyable.register(this);
                register(EventService.addListener(true, destroyable, StorageEvent.class, _ -> {
                    updateText(getItem(), this.emptyProperty().get());
                }));
                register(EventService.addListener(true, destroyable, EngineerEvent.class, _ -> {
                    updateText(getItem(), this.emptyProperty().get());
                }));
            }

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
                    this.pseudoClassStateChanged(PseudoClass.getPseudoClass("complete"), true);
                    this.pseudoClassStateChanged(PseudoClass.getPseudoClass("trade"), false);
                } else if (BlueprintHelper.getCraftabilityForModuleOrUpgradeRecipe(item).equals(Craftability.CRAFTABLE_WITH_TRADE)) {
                    this.pseudoClassStateChanged(PseudoClass.getPseudoClass("complete"), false);
                    this.pseudoClassStateChanged(PseudoClass.getPseudoClass("trade"), true);
                } else {
                    this.pseudoClassStateChanged(PseudoClass.getPseudoClass("complete"), false);
                    this.pseudoClassStateChanged(PseudoClass.getPseudoClass("trade"), false);
                }
            }
        };
    }

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
        this.getPanes().add(register(this.aboutTitledPane));
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

        final DestroyableComboBox<OdysseyBlueprintName> blueprints = ComboBoxBuilder.builder(OdysseyBlueprintName.class)
                .withStyleClass("blueprint-list")
                .withItemsProperty(LocaleService.getListBinding(recipesEntry.getValue().keySet().stream().sorted(Comparator.comparing(recipeName -> LocaleService.getLocalizedStringForCurrentLocale(recipeName.getLocalizationKey()))).toArray(OdysseyBlueprintName[]::new)))
                .asLocalized()
                .build();
        blueprints.setVisibleRowCount(Math.min(blueprints.getItems().size(), 10));

        final DestroyableHBox hBox = BoxBuilder.builder()
                .withNode(blueprints)
                .buildHBox();
        HBox.setHgrow(blueprints, Priority.ALWAYS);

        final DestroyableVBox content = BoxBuilder.builder()
                .withStyleClass("blueprint-titled-pane-content")
                .withNodes(hBox, scroll)
                .buildVBox();
        VBox.setVgrow(scroll, Priority.ALWAYS);

        final DestroyableTitledPane categoryTitledPane = register(TitledPaneBuilder.builder()
                .withStyleClass("blueprint-title-pane2")
                .withText(recipesEntry.getKey().getLocalizationKey())
                .withContent(content)
                .build());

        blueprints.addChangeListener(blueprints.valueProperty(), (_, _, newValue) -> {
            if (scroll.getContent() instanceof DestroyableTemplate destroyableContent) {
                scroll.deregister(destroyableContent);
                destroyableContent.destroyTemplate();
            }
            final OdysseyBlueprintContent recipeContent = createRecipeContent(OdysseyBlueprintConstants.getRecipe(newValue));
            scroll.setContentNode(recipeContent);
        });
        var destroyableCellFactory = createDestroyableCellFactory(blueprints);
        blueprints.setCellFactory(destroyableCellFactory);
        blueprints.getSelectionModel().select(blueprints.getItems().getFirst());
        blueprints.setButtonCell(destroyableCellFactory.call(null));

        register(EventService.addListener(true, this, BlueprintClickEvent.class, blueprintClickEvent -> {
            if (blueprintClickEvent.getBlueprintName() instanceof OdysseyBlueprintName odysseyBlueprintName && blueprints.getItems().contains(odysseyBlueprintName)) {
                blueprints.getSelectionModel().select(odysseyBlueprintName);
                this.setExpandedPane(categoryTitledPane);
            }
        }));

        return categoryTitledPane;
    }

    private OdysseyBlueprintContent createRecipeContent(OdysseyBlueprint odysseyBlueprint) {
        return new OdysseyBlueprintContent(odysseyBlueprint);
    }
}
