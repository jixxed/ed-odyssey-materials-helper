package nl.jixxed.eliteodysseymaterials.templates.components;

import javafx.beans.binding.StringBinding;
import javafx.css.PseudoClass;
import nl.jixxed.eliteodysseymaterials.builder.EdAwesomeIconViewPaneBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.StackPaneBuilder;
import nl.jixxed.eliteodysseymaterials.templates.components.edfont.EdAwesomeIcon;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableStackPane;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;

public class InfoNode extends DestroyableVBox implements DestroyableTemplate {
    private EdAwesomeIcon[] icons;
    private StringBinding valueText;
    private StringBinding titleText;
    private boolean enabled;
    public InfoNode(StringBinding title, StringBinding text, boolean enabled, EdAwesomeIcon ... icons) {
        this.titleText = title;
        this.icons = icons;
        this.valueText = text;
        this.enabled = enabled;
        this.initComponents();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("info-node");
        this.pseudoClassStateChanged(PseudoClass.getPseudoClass("unavailable"), !enabled);
        var title = LabelBuilder.builder()
                .withStyleClass("info-node-title")
                .withText(titleText)
                .build();
        var iconPane = EdAwesomeIconViewPaneBuilder.builder()
                .withStyleClass("info-node-icon")
                .withIcons(icons)
                .build();
        DestroyableStackPane iconAndTitle = StackPaneBuilder.builder()
                .withStyleClass("info-node-icon-title")
                .withNodes(title, iconPane)
                .build();
        title.addBinding(title.visibleProperty(), iconAndTitle.hoverProperty());
        iconPane.addBinding(iconPane.visibleProperty(), iconAndTitle.hoverProperty().not());
        var value = LabelBuilder.builder()
                .withStyleClass("info-node-value")
                .withText(valueText)
                .build();
        this.getNodes().addAll(iconAndTitle, new GrowingRegion(), value);
    }
}
