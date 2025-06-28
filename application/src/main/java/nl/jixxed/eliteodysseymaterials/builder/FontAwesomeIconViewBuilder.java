package nl.jixxed.eliteodysseymaterials.builder;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableFontAwesomeIconView;

public class FontAwesomeIconViewBuilder extends AbstractShapeBuilder<FontAwesomeIconViewBuilder> {
    private FontAwesomeIcon icon;

    public static FontAwesomeIconViewBuilder builder() {
        return new FontAwesomeIconViewBuilder();
    }

    public FontAwesomeIconViewBuilder withIcon(final FontAwesomeIcon icon) {
        this.icon = icon;
        return this;
    }

    public DestroyableFontAwesomeIconView build() {
        final DestroyableFontAwesomeIconView fontAwesomeIconView = new DestroyableFontAwesomeIconView(this.icon);
        fontAwesomeIconView.setStyle(String.format("-fx-font-family: %s;", icon.fontFamily()));
        super.build(fontAwesomeIconView);
        return fontAwesomeIconView;
    }
}
