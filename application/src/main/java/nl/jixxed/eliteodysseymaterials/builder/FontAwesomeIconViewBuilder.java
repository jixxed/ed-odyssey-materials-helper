/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
