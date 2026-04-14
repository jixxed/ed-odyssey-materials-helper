/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.templates.components.edfont;

import de.jensd.fx.glyphs.GlyphIcon;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.ed.awesome.api.FontLoader;
import nl.jixxed.ed.awesome.api.NoOpFontLoader;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableComponent;

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
