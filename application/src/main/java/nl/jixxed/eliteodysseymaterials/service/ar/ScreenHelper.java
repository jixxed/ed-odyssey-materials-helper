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
