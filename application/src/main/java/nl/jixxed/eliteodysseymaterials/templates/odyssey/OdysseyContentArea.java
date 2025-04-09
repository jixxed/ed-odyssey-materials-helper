package nl.jixxed.eliteodysseymaterials.templates.odyssey;

import javafx.geometry.Side;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.TabPaneBuilder;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.enums.Action;
import nl.jixxed.eliteodysseymaterials.enums.Expansion;
import nl.jixxed.eliteodysseymaterials.enums.ImportResult;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyTabs;
import nl.jixxed.eliteodysseymaterials.helper.AnchorPaneHelper;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableAnchorPane;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableEventTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTabPane;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;
import nl.jixxed.eliteodysseymaterials.templates.odyssey.bartender.OdysseyBartenderTab;
import nl.jixxed.eliteodysseymaterials.templates.odyssey.engineers.OdysseyEngineersTab;
import nl.jixxed.eliteodysseymaterials.templates.odyssey.loadout.OdysseyLoadoutEditorTab;
import nl.jixxed.eliteodysseymaterials.templates.odyssey.materials.OdysseyMaterialTab;
import nl.jixxed.eliteodysseymaterials.templates.odyssey.menu.OdysseyBlueprintBar;
import nl.jixxed.eliteodysseymaterials.templates.odyssey.wishlist.OdysseyWishlistTab;

@SuppressWarnings("java:S110")
@Slf4j
public
class OdysseyContentArea extends DestroyableAnchorPane implements DestroyableEventTemplate {

    private OdysseySearchBar odysseySearchBar;
    private OdysseyBlueprintBar odysseyBlueprintBar;
    private OdysseyMaterialTab overview;
    private OdysseyWishlistTab wishlistTab;
    private OdysseyEngineersTab odysseyEngineersTab;
    private OdysseyLoadoutEditorTab loadoutEditorTab;
    private DestroyableTabPane tabs;
    private DestroyableVBox body;
    private OdysseyBartenderTab odysseyBartenderTab;


    public OdysseyContentArea() {
        initComponents();
        initEventHandling();
    }

    public void initComponents() {
        this.getStyleClass().add("odyssey-tab-content");
        this.overview = new OdysseyMaterialTab();
        this.wishlistTab = new OdysseyWishlistTab();
        this.loadoutEditorTab = new OdysseyLoadoutEditorTab();
        this.odysseyEngineersTab = new OdysseyEngineersTab();
        this.odysseyBartenderTab = new OdysseyBartenderTab();
        this.overview.setClosable(false);
        this.wishlistTab.setClosable(false);
        this.loadoutEditorTab.setClosable(false);
        this.odysseyEngineersTab.setClosable(false);
        this.odysseyBartenderTab.setClosable(false);

        this.odysseySearchBar = new OdysseySearchBar();
        this.tabs = TabPaneBuilder.builder()
                .withTabs(this.overview, this.wishlistTab, this.loadoutEditorTab, this.odysseyBartenderTab, this.odysseyEngineersTab)
                .withStyleClass("odyssey-tab-pane")
                .withSide(Side.LEFT)
                .withSelectedItemListener((_, _, newValue) -> {
                    if (newValue != null) {
                        final OdysseyTabs tabType = ((OdysseyTab) newValue).getTabType();
                        EventService.publish(new OdysseyTabSelectedEvent(tabType));
                    }
                    PreferencesService.setPreference(PreferenceConstants.SELECTED_TAB_ODYSSEY, this.tabs.getTabs().indexOf(newValue));
                })
                .build();
        this.tabs.getSelectionModel().select(Math.min(PreferencesService.getPreference(PreferenceConstants.SELECTED_TAB_ODYSSEY, 0), this.tabs.getTabs().size() - 1));
        VBox.setVgrow(this.tabs, Priority.ALWAYS);

        this.body = BoxBuilder.builder().withNodes(this.odysseySearchBar, this.tabs).buildVBox();
        HBox.setHgrow(this.body, Priority.ALWAYS);

        this.odysseyBlueprintBar = new OdysseyBlueprintBar();
        addChangeListener(this.odysseyBlueprintBar.visibleProperty(), (_, _, newValue) ->
                setBodyAnchor(newValue, this.odysseyBlueprintBar.getWidth()));
//        addChangeListener(this.odysseyBlueprintBar.widthProperty(), (_, _, newValue) ->
//                setBodyAnchor(isRecipeBarVisible(), newValue.doubleValue()));
        this.odysseyBlueprintBar.setVisible(isRecipeBarVisible());

        AnchorPaneHelper.setAnchor(this.odysseyBlueprintBar, 0.0, 0.0, 0.0, null);
        setBodyAnchor(isRecipeBarVisible(), this.odysseyBlueprintBar.getWidth());
        AnchorPaneHelper.setAnchor(this.tabs, 0.0, 0.0, 0.0, null);

        this.getNodes().addAll(this.odysseyBlueprintBar, this.body);
    }

    public void initEventHandling() {
        register(EventService.addListener(true, this, WishlistBlueprintEvent.class, wishlistEvent -> {
            if (Action.ADDED.equals(wishlistEvent.getAction())) {
                this.tabs.getSelectionModel().select(this.wishlistTab);
            }
        }));
        register(EventService.addListener(true, this, BlueprintClickEvent.class, _ -> {
            this.odysseyBlueprintBar.setVisible(true);
            PreferencesService.setPreference(PreferenceConstants.RECIPES_VISIBLE, true);
        }));
        register(EventService.addListener(true, this, ApplicationLifeCycleEvent.class, _ -> setBodyAnchor(isRecipeBarVisible(), this.odysseyBlueprintBar.getWidth())));
        register(EventService.addListener(true, this, AfterFontSizeSetEvent.class, _ -> setBodyAnchor(isRecipeBarVisible(), this.odysseyBlueprintBar.getWidth())));
        register(EventService.addListener(true, this, MenuButtonClickedEvent.class, event -> {
            if (Expansion.ODYSSEY.equals(event.getExpansion())) {
                final boolean visibility = !this.odysseyBlueprintBar.isVisible();
                this.odysseyBlueprintBar.setVisible(visibility);
                PreferencesService.setPreference(PreferenceConstants.RECIPES_VISIBLE, visibility);
            }
        }));

        register(EventService.addListener(true, this, ImportResultEvent.class, importResultEvent -> {
            final ImportResult.ResultType resultType = importResultEvent.getResult().getResultType();
            if (isWishlistResult(resultType)) {
                this.tabs.getSelectionModel().select(this.wishlistTab);
            } else if (isLoadoutResult(resultType)) {
                this.tabs.getSelectionModel().select(this.loadoutEditorTab);
            }
        }));
    }

    private static boolean isLoadoutResult(ImportResult.ResultType resultType) {
        return resultType.equals(ImportResult.ResultType.SUCCESS_LOADOUT);
    }

    private static boolean isWishlistResult(ImportResult.ResultType resultType) {
        return resultType.equals(ImportResult.ResultType.SUCCESS_ODYSSEY_WISHLIST);
    }

    private boolean isRecipeBarVisible() {
        return PreferencesService.getPreference(PreferenceConstants.RECIPES_VISIBLE, Boolean.TRUE);
    }

    private void setBodyAnchor(final boolean isRecipeBarVisible, final double width) {
        AnchorPaneHelper.setAnchor(this.body, 0.0, 0.0, isRecipeBarVisible ? width : 0.0, 0.0);
    }


}
