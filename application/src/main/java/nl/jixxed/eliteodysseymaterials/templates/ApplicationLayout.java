package nl.jixxed.eliteodysseymaterials.templates;

import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Side;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.enums.ImportResult;
import nl.jixxed.eliteodysseymaterials.helper.AnchorPaneHelper;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.horizons.HorizonsContentArea;
import nl.jixxed.eliteodysseymaterials.templates.odyssey.OdysseyContentArea;
import nl.jixxed.eliteodysseymaterials.templates.settings.SettingsTab;

import java.util.ArrayList;
import java.util.List;

public class ApplicationLayout extends AnchorPane {
    private BottomBar bottomBar;
    private OdysseyContentArea odysseyContentArea;
    private HorizonsContentArea horizonsContentArea;

    private TabPane tabsMain;
    private Tab odyssey;
    private Tab horizons;
    private SettingsTab settingsTab;
    private final IntegerProperty fontSize = new SimpleIntegerProperty(14);
    private final List<EventListener<?>> eventListeners = new ArrayList<>();

    public ApplicationLayout(final Application application) {
        initComponents(application);
        initEventHandling();
    }

    private void initEventHandling() {
        this.eventListeners.add(EventService.addListener(this, AfterFontSizeSetEvent.class, fontSizeEvent -> this.fontSize.set(fontSizeEvent.getFontSize())));
        this.eventListeners.add(EventService.addListener(this, ApplicationLifeCycleEvent.class, applicationLifeCycleEvent -> AnchorPaneHelper.setAnchor(this.tabsMain, 0.0, this.bottomBar.getHeight(), 0.0, 0.0)));
        this.bottomBar.heightProperty().addListener(observable -> AnchorPaneHelper.setAnchor(this.odysseyContentArea, 0.0, this.bottomBar.getHeight(), 0.0, 0.0));
        this.eventListeners.add(EventService.addListener(this, ImportResultEvent.class, importResultEvent -> {
            if (importResultEvent.getResult().getResultType().equals(ImportResult.ResultType.SUCCESS_HORIZONS_WISHLIST) || importResultEvent.getResult().getResultType().equals(ImportResult.ResultType.SUCCESS_EDSY_WISHLIST) || importResultEvent.getResult().getResultType().equals(ImportResult.ResultType.SUCCESS_CORIOLIS_WISHLIST) || importResultEvent.getResult().getResultType().equals(ImportResult.ResultType.SUCCESS_HORIZONS_SHIP)) {
                this.tabsMain.getSelectionModel().select(this.horizons);
            } else if (importResultEvent.getResult().getResultType().equals(ImportResult.ResultType.SUCCESS_ODYSSEY_WISHLIST) || importResultEvent.getResult().getResultType().equals(ImportResult.ResultType.SUCCESS_LOADOUT)) {
                this.tabsMain.getSelectionModel().select(this.odyssey);
            }
        }));
    }

    private void initComponents(final Application application) {
        this.getStyleClass().add("app");
        this.bottomBar = new BottomBar();
        this.settingsTab = new SettingsTab(application);
        this.settingsTab.setClosable(false);
        AnchorPaneHelper.setAnchor(this.bottomBar, null, 0.0, 0.0, 0.0);
        this.odysseyContentArea = new OdysseyContentArea(application);
        this.horizonsContentArea = new HorizonsContentArea(application);
        this.odyssey = new Tab();
        this.odyssey.textProperty().bind(LocaleService.getStringBinding("tabs.onfoot"));
        this.odyssey.setClosable(false);
        this.horizons = new Tab();
        this.horizons.textProperty().bind(LocaleService.getStringBinding("tabs.ships"));
        this.horizons.setClosable(false);
        this.tabsMain = new TabPane(this.odyssey, this.horizons, this.settingsTab);
        this.tabsMain.getStyleClass().add("tab-main");
        this.tabsMain.setSide(Side.LEFT);
        this.tabsMain.tabMaxWidthProperty().bind(this.tabsMain.heightProperty().divide(3).subtract(this.fontSize.multiply(5.14)));
        this.tabsMain.tabMinWidthProperty().bind(this.tabsMain.heightProperty().divide(3).subtract(this.fontSize.multiply(5.14)));
        this.odyssey.setContent(this.odysseyContentArea);
        this.horizons.setContent(this.horizonsContentArea);
        this.tabsMain.getSelectionModel().select(Math.min(PreferencesService.getPreference(PreferenceConstants.SELECTED_TAB_MAIN, 0), this.tabsMain.getTabs().size()-1));
        this.tabsMain.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            PreferencesService.setPreference(PreferenceConstants.SELECTED_TAB_MAIN, this.tabsMain.getTabs().indexOf(newValue));
        });
        this.getChildren().addAll(this.tabsMain, this.bottomBar);
    }
}
