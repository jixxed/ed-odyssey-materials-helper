package nl.jixxed.eliteodysseymaterials.builder;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Side;
import javafx.scene.control.Tab;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTab;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTabPane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TabPaneBuilder extends AbstractControlBuilder<TabPaneBuilder> {
    private final List<DestroyableTab> tabs = new ArrayList<>();
    private Side side;
    private DoubleBinding maxWidthProperty;
    private DoubleBinding minWidthProperty;
    private ChangeListener<Tab> selectedItemListener;
    private Integer selectedTab;

    public static TabPaneBuilder builder() {
        return new TabPaneBuilder();
    }

    public TabPaneBuilder withTabs(final DestroyableTab... tabs) {
        this.tabs.addAll(Arrays.stream(tabs).toList());
        return this;
    }

    public TabPaneBuilder withSelectedTab(final Integer tab) {
        this.selectedTab = tab;
        return this;
    }

    public TabPaneBuilder withSide(final Side side) {
        this.side = side;
        return this;
    }

    public TabPaneBuilder withMaxWidthProperty(final DoubleBinding maxWidthProperty) {
        this.maxWidthProperty = maxWidthProperty;
        return this;
    }

    public TabPaneBuilder withMinWidthProperty(final DoubleBinding minWidthProperty) {
        this.minWidthProperty = minWidthProperty;
        return this;
    }

    public TabPaneBuilder withSelectedItemListener(final ChangeListener<Tab> selectedItemListener) {
        this.selectedItemListener = selectedItemListener;
        return this;
    }

    @SuppressWarnings("unchecked")
    public DestroyableTabPane build() {
        final DestroyableTabPane tabPane = new DestroyableTabPane();
        super.build(tabPane);

        tabPane.getTabs().addAll(tabs);
        tabPane.registerAll(tabs);
        if(side != null) {
            tabPane.setSide(side);
        }
        if (minWidthProperty != null) {
            tabPane.addBinding(tabPane.tabMinWidthProperty(), minWidthProperty);
        }
        if (maxWidthProperty != null) {
            tabPane.addBinding(tabPane.tabMaxWidthProperty(), maxWidthProperty);
        }
        if (selectedTab != null) {
            tabPane.getSelectionModel().select(Math.min(selectedTab, tabPane.getTabs().size()-1));
        }
        if (selectedItemListener != null) {
            tabPane.addChangeListener(tabPane.getSelectionModel().selectedItemProperty(), selectedItemListener);
        }

        return tabPane;
    }
}
