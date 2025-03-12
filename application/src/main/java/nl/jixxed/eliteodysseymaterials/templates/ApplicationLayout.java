package nl.jixxed.eliteodysseymaterials.templates;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Side;
import nl.jixxed.eliteodysseymaterials.builder.TabBuilder;
import nl.jixxed.eliteodysseymaterials.builder.TabPaneBuilder;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.enums.ImportResult;
import nl.jixxed.eliteodysseymaterials.helper.AnchorPaneHelper;
import nl.jixxed.eliteodysseymaterials.helper.ScalingHelper;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.AfterFontSizeSetEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.FontSizeEvent;
import nl.jixxed.eliteodysseymaterials.service.event.ImportResultEvent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableAnchorPane;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTab;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTabPane;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;
import nl.jixxed.eliteodysseymaterials.templates.horizons.HorizonsContentArea;
import nl.jixxed.eliteodysseymaterials.templates.odyssey.OdysseyContentArea;
import nl.jixxed.eliteodysseymaterials.templates.settings.SettingsTab;

public class ApplicationLayout extends DestroyableAnchorPane implements DestroyableTemplate {
    private BottomBar bottomBar;
    private OdysseyContentArea odysseyContentArea;
    private HorizonsContentArea horizonsContentArea;

    private DestroyableTabPane tabsMain;
    private DestroyableTab odyssey;
    private DestroyableTab horizons;
    private SettingsTab settingsTab;
    private final IntegerProperty fontSize = new SimpleIntegerProperty(14);


    public ApplicationLayout() {
        initComponents();
        initEventHandling();
    }

    public void initEventHandling() {
        register(EventService.addListener(true, this, AfterFontSizeSetEvent.class, fontSizeEvent -> this.fontSize.set(fontSizeEvent.getFontSize())));
        register(EventService.addListener(true, this, FontSizeEvent.class, applicationLifeCycleEvent -> AnchorPaneHelper.setAnchor(this.tabsMain, 0.0, ScalingHelper.getPixelDoubleFromEm(2D), 0.0, 0.0)));
        register(EventService.addListener(true, this, ImportResultEvent.class, importResultEvent -> {
            if (importResultEvent.getResult().getResultType().equals(ImportResult.ResultType.SUCCESS_HORIZONS_WISHLIST) || importResultEvent.getResult().getResultType().equals(ImportResult.ResultType.SUCCESS_EDSY_WISHLIST) || importResultEvent.getResult().getResultType().equals(ImportResult.ResultType.SUCCESS_CORIOLIS_WISHLIST) || importResultEvent.getResult().getResultType().equals(ImportResult.ResultType.SUCCESS_HORIZONS_SHIP)) {
                this.tabsMain.getSelectionModel().select(this.horizons);
            } else if (importResultEvent.getResult().getResultType().equals(ImportResult.ResultType.SUCCESS_ODYSSEY_WISHLIST) || importResultEvent.getResult().getResultType().equals(ImportResult.ResultType.SUCCESS_LOADOUT)) {
                this.tabsMain.getSelectionModel().select(this.odyssey);
            }
        }));
    }

    public void initComponents() {
        this.getStyleClass().add("app");
        this.bottomBar = new BottomBar();
        this.bottomBar.heightProperty().addListener(observable -> AnchorPaneHelper.setAnchor(this.odysseyContentArea, 0.0, this.bottomBar.getHeight(), 0.0, 0.0));
        this.settingsTab = new SettingsTab();
        this.settingsTab.setClosable(false);
        AnchorPaneHelper.setAnchor(this.bottomBar, null, 0.0, 0.0, 0.0);
        this.odysseyContentArea = new OdysseyContentArea();
        this.horizonsContentArea = new HorizonsContentArea();
        this.odyssey = TabBuilder.builder()
                .withText(LocaleService.getStringBinding("tabs.onfoot"))
                .withClosable(false)
                .withContent(this.odysseyContentArea)
                .build();
        this.horizons = TabBuilder.builder()
                .withText(LocaleService.getStringBinding("tabs.ships"))
                .withClosable(false)
                .withContent(this.horizonsContentArea)
                .build();
        this.tabsMain = TabPaneBuilder.builder()
                .withTabs(this.odyssey, this.horizons, this.settingsTab)
                .withStyleClass("tab-main")
                .withSide(Side.LEFT)
                .withMinWidthProperty(this.tabsMain.heightProperty().divide(3).subtract(this.fontSize.multiply(5.14)))
                .withMaxWidthProperty(this.tabsMain.heightProperty().divide(3).subtract(this.fontSize.multiply(5.14)))
                .withSelectedTab(PreferencesService.getPreference(PreferenceConstants.SELECTED_TAB_MAIN, 0))
                .withSelectedItemListener((_, _, newValue) -> PreferencesService.setPreference(PreferenceConstants.SELECTED_TAB_MAIN, this.tabsMain.getTabs().indexOf(newValue)))
                .build();
        AnchorPaneHelper.setAnchor(this.tabsMain, 0.0, ScalingHelper.getPixelDoubleFromEm(2D), 0.0, 0.0);
//        this.tabsMain.getStyleClass().add("tab-main");
//        this.tabsMain.setSide(Side.LEFT);
//        this.tabsMain.tabMaxWidthProperty().bind(this.tabsMain.heightProperty().divide(3).subtract(this.fontSize.multiply(5.14)));
//        this.tabsMain.tabMinWidthProperty().bind(this.tabsMain.heightProperty().divide(3).subtract(this.fontSize.multiply(5.14)));
//        this.odyssey.setContent(this.odysseyContentArea);
//        this.horizons.setContent(this.horizonsContentArea);
//        this.tabsMain.getSelectionModel().select(Math.min(PreferencesService.getPreference(PreferenceConstants.SELECTED_TAB_MAIN, 0), this.tabsMain.getTabs().size()-1));
//        this.tabsMain.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//            PreferencesService.setPreference(PreferenceConstants.SELECTED_TAB_MAIN, this.tabsMain.getTabs().indexOf(newValue));
//        });
        this.getChildren().addAll(this.tabsMain, this.bottomBar);
    }
}
