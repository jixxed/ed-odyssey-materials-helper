package nl.jixxed.eliteodysseymaterials.templates.components.edfont;

import de.jensd.fx.glyphs.GlyphIcons;
import nl.jixxed.ed.awesome.api.FontLoader;
import nl.jixxed.ed.awesome.api.NoOpFontLoader;

import java.util.ServiceLoader;

public enum EdAwesomeIcon implements GlyphIcons {
    GRADE_1("\uE20A"),
    GRADE_2("\uE20B"),
    GRADE_3("\uE20C"),
    GRADE_4("\uE20D"),
    GRADE_5("\uE20E"),
    WISHLIST("\uE408");

    private static FontLoader fontLoader;

    static {
        ServiceLoader<FontLoader> loader = ServiceLoader.load(FontLoader.class);
        fontLoader = loader.stream().map(ServiceLoader.Provider::get)
                .filter(f -> !(f instanceof NoOpFontLoader))
                .findFirst()
                .orElseGet(NoOpFontLoader::new);
    }

    private final String unicode;

    private EdAwesomeIcon(String unicode) {
        this.unicode = unicode;
    }

    @Override
    public String unicode() {
        return fontLoader.mapCodepoint(unicode);
    }

    @Override
    public String fontFamily() {
        return fontLoader.fontFamily();
    }
}