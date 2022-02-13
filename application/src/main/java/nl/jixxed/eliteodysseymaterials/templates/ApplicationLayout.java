package nl.jixxed.eliteodysseymaterials.templates;

import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Side;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import nl.jixxed.eliteodysseymaterials.helper.AnchorPaneHelper;
import nl.jixxed.eliteodysseymaterials.service.event.AfterFontSizeSetEvent;
import nl.jixxed.eliteodysseymaterials.service.event.ApplicationLifeCycleEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;

public class ApplicationLayout extends AnchorPane {
    private BottomBar bottomBar;
    private ContentArea contentArea;
    private TabPane tabsMain;
    private Tab odyssey;
    private Tab horizons;
    private final IntegerProperty fontSize = new SimpleIntegerProperty(14);

    public ApplicationLayout(final Application application) {
        initComponents(application);
        initEventHandling();
    }

    private void initEventHandling() {
        EventService.addListener(this, AfterFontSizeSetEvent.class, fontSizeEvent -> this.fontSize.set(fontSizeEvent.getFontSize()));
        EventService.addListener(this, ApplicationLifeCycleEvent.class, applicationLifeCycleEvent -> AnchorPaneHelper.setAnchor(this.tabsMain, 0.0, this.bottomBar.getHeight(), 0.0, 0.0));
        this.bottomBar.heightProperty().addListener(observable -> AnchorPaneHelper.setAnchor(this.contentArea, 0.0, this.bottomBar.getHeight(), 0.0, 0.0));
    }

    private void initComponents(final Application application) {
        this.getStyleClass().add("app");
        this.bottomBar = new BottomBar();
        AnchorPaneHelper.setAnchor(this.bottomBar, null, 0.0, 0.0, 0.0);
        this.contentArea = new ContentArea(application);
        this.odyssey = new Tab();
        this.odyssey.setText("Odyssey");
        this.odyssey.setClosable(false);
        this.horizons = new HorizonsMaterialOverviewTab();
        this.tabsMain = new TabPane(this.odyssey, this.horizons);
        this.tabsMain.getStyleClass().add("tab-main");
        this.tabsMain.setSide(Side.LEFT);
        this.tabsMain.tabMaxWidthProperty().bind(this.tabsMain.heightProperty().divide(2).subtract(this.fontSize.multiply(5.14)));
        this.tabsMain.tabMinWidthProperty().bind(this.tabsMain.heightProperty().divide(2).subtract(this.fontSize.multiply(5.14)));
        this.odyssey.setContent(this.contentArea);
        this.getChildren().addAll(this.tabsMain, this.bottomBar);
    }
}
