package nl.jixxed.eliteodysseymaterials.templates.components.edfont;

import de.jensd.fx.glyphs.GlyphIcon;
import javafx.scene.text.Font;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableComponent;

import java.io.IOException;
@Slf4j
public class EdAwesomeIconView extends GlyphIcon<EdAwesomeIcon> implements DestroyableComponent {

    public final static String TTF_PATH = "/fonts/EdAwesome.ttf";

    static {
        try {
            Font.loadFont(EdAwesomeIconView.class.getResource(TTF_PATH).openStream(), 10.0d);
        } catch (IOException ex) {
            log.error("Failed to load font", ex);
        }
    }

    public EdAwesomeIconView(EdAwesomeIcon icon, String iconSize) {
        super(EdAwesomeIcon.class);
        setIcon(icon);
        setStyle(String.format("-fx-font-family: %s; -fx-font-size: %s;", icon.fontFamily(), iconSize));
    }

    public EdAwesomeIconView(EdAwesomeIcon icon) {
        this(icon, "1em");
    }

    public EdAwesomeIconView() {
        this(EdAwesomeIcon.GRADE_1);
    }

    @Override
    public EdAwesomeIcon getDefaultGlyph() {
        return EdAwesomeIcon.GRADE_1;
    }
}
