package nl.jixxed.eliteodysseymaterials.templates.components.edfont;

import de.jensd.fx.glyphs.GlyphIcon;
import javafx.scene.text.Font;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.ed.awesome.api.FontLoader;
import nl.jixxed.ed.awesome.api.NoOpFontLoader;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableComponent;

import java.io.IOException;
import java.util.ServiceLoader;

@Slf4j
public class EdAwesomeIconView extends GlyphIcon<EdAwesomeIcon> implements DestroyableComponent {

    private static FontLoader fontLoader;

    static {
        ServiceLoader<FontLoader> loader = ServiceLoader.load(FontLoader.class);
        fontLoader = loader.stream().map(ServiceLoader.Provider::get)
                .filter(f -> !(f instanceof NoOpFontLoader))
                .findFirst()
                .orElseGet(NoOpFontLoader::new);
        fontLoader.load();
    }

    public EdAwesomeIconView(EdAwesomeIcon icon, String iconSize) {
        super(EdAwesomeIcon.class);
        setIcon(icon);
        setStyle(String.format("-fx-font-family: %s; -fx-font-size: %s;", fontLoader.fontFamily(), iconSize));
        this.getStyleClass().add(icon.name().toLowerCase().replaceAll("_", "-"));
    }

    public EdAwesomeIconView(EdAwesomeIcon icon) {
        this(icon, "1em");
    }

    public EdAwesomeIconView() {
        this(EdAwesomeIcon.MATERIALS_GRADE_1);
    }

    @Override
    public EdAwesomeIcon getDefaultGlyph() {
        return EdAwesomeIcon.MATERIALS_GRADE_1;
    }
}
