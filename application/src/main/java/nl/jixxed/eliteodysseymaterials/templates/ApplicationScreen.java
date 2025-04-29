package nl.jixxed.eliteodysseymaterials.templates;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Side;
import nl.jixxed.eliteodysseymaterials.builder.TabBuilder;
import nl.jixxed.eliteodysseymaterials.builder.TabPaneBuilder;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
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
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableEventTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTab;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTabPane;
import nl.jixxed.eliteodysseymaterials.templates.horizons.HorizonsContentArea;
import nl.jixxed.eliteodysseymaterials.templates.odyssey.OdysseyContentArea;
import nl.jixxed.eliteodysseymaterials.templates.settings.SettingsTab;

import java.util.Set;

public class ApplicationScreen extends DestroyableAnchorPane implements DestroyableEventTemplate {

    private DestroyableTabPane tabsMain;
    private DestroyableTab odyssey;
    private DestroyableTab horizons;

    private IntegerProperty fontSize = new SimpleIntegerProperty(14);


    public ApplicationScreen() {
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
        this.getStyleClass().add("application-screen");
        BottomBar bottomBar = new BottomBar();
        OdysseyContentArea odysseyContentArea = new OdysseyContentArea();
        HorizonsContentArea horizonsContentArea = new HorizonsContentArea();

        addChangeListener(bottomBar.heightProperty(), (_, _, _) -> AnchorPaneHelper.setAnchor(odysseyContentArea, 0.0, bottomBar.getHeight(), 0.0, 0.0));
        SettingsTab settingsTab = new SettingsTab();
        settingsTab.setClosable(false);
        AnchorPaneHelper.setAnchor(bottomBar, null, 0.0, 0.0, 0.0);
        this.odyssey = TabBuilder.builder()
                .withStyleClass("odyssey-tab")
                .withText(LocaleService.getStringBinding("tabs.onfoot"))
                .withClosable(false)
                .withContent(odysseyContentArea)
                .withDisableProperty(ApplicationState.getInstance().getCommandersProperty().map(Set::isEmpty))
                .build();
        this.horizons = TabBuilder.builder()
                .withStyleClass("horizons-tab")
                .withText(LocaleService.getStringBinding("tabs.ships"))
                .withClosable(false)
                .withContent(horizonsContentArea)
                .withDisableProperty(ApplicationState.getInstance().getCommandersProperty().map(Set::isEmpty))
                .build();
        this.tabsMain = TabPaneBuilder.builder()
                .withTabs(this.odyssey, this.horizons, settingsTab)
                .withStyleClass("tab-main")
                .withSide(Side.LEFT)
                .withSelectedTab(PreferencesService.getPreference(PreferenceConstants.SELECTED_TAB_MAIN, 2))
                .withSelectedItemListener((_, _, newValue) -> PreferencesService.setPreference(PreferenceConstants.SELECTED_TAB_MAIN, this.tabsMain.getTabs().indexOf(newValue)))
                .build();
        this.tabsMain.addBinding(this.tabsMain.tabMinWidthProperty(), this.tabsMain.heightProperty().divide(3).subtract(this.fontSize.multiply(5.14)));
        this.tabsMain.addBinding(this.tabsMain.tabMaxWidthProperty(), this.tabsMain.heightProperty().divide(3).subtract(this.fontSize.multiply(5.14)));
        AnchorPaneHelper.setAnchor(this.tabsMain, 0.0, ScalingHelper.getPixelDoubleFromEm(2D), 0.0, 0.0);
        this.getNodes().addAll(this.tabsMain, bottomBar);
    }

    @Override
    public void destroyInternal() {
        super.destroyInternal();

        this.tabsMain.getTabs().clear();
        this.horizons.setContent(null);
        this.odyssey.setContent(null);
        this.fontSize = null;
        this.tabsMain = null;
        this.horizons = null;
        this.odyssey = null;
    }
}
