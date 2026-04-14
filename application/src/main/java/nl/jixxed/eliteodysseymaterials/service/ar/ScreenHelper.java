/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.service.ar;

import com.sun.glass.ui.Screen;

import java.util.List;
import java.util.Optional;

public class ScreenHelper {

    public static Rectangle getScaledRectForRealRect(final int x, final int y, final int maxX, final int maxY) {
        final java.util.List<Screen> screens = (List<Screen>) com.sun.javafx.tk.Toolkit.getToolkit().getScreens();
        final Optional<Screen> first = screens.stream().filter((Screen screen) ->
                x + ((maxX - x) / 2) >= screen.getPlatformX() && x + ((maxX - x) / 2) < screen.getPlatformX() + screen.getPlatformWidth() &&
                        y + ((maxY - y) / 2) >= screen.getPlatformY() && y + ((maxY - y) / 2) < screen.getPlatformY() + screen.getPlatformHeight()
        ).findFirst();
        return first.map(screen -> new Rectangle(
                screen.getX() + ((x - screen.getPlatformX()) / screen.getPlatformScaleX()),
                screen.getY() + ((y - screen.getPlatformY()) / screen.getPlatformScaleY()),
                screen.getX() + ((maxX - screen.getPlatformX()) / screen.getPlatformScaleX()),
                screen.getY() + ((maxY - screen.getPlatformY()) / screen.getPlatformScaleY()))).orElse(null);
    }
}
