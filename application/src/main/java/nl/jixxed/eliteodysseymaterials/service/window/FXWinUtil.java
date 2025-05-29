package nl.jixxed.eliteodysseymaterials.service.window;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef;
import javafx.stage.Stage;
import lombok.val;
import nl.jixxed.eliteodysseymaterials.constants.AppConstants;
import nl.jixxed.eliteodysseymaterials.service.ar.WindowInfoUtil;

public class FXWinUtil {

    public static void setDarkMode(Stage stage, boolean darkMode) {
        val hwnd = new WinDef.HWND(Pointer.createConstant(WindowInfoUtil.findWindow(windowTitle -> windowTitle.equals(AppConstants.APP_TITLE))));
        val dwmapi = Dwmapi.INSTANCE;
        WinDef.BOOLByReference darkModeRef = new WinDef.BOOLByReference(new WinDef.BOOL(darkMode));

        dwmapi.DwmSetWindowAttribute(hwnd, 20, darkModeRef, Native.getNativeSize(WinDef.BOOLByReference.class));
        forceRedrawOfWindowTitleBar(stage);
    }

    private static void forceRedrawOfWindowTitleBar(Stage stage) {
        val maximized = stage.isMaximized();
        stage.setMaximized(!maximized);
        stage.setMaximized(maximized);
    }

}