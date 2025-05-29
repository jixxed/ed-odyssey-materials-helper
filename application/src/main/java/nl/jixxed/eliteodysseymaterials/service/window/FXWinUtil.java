package nl.jixxed.eliteodysseymaterials.service.window;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.val;

public class FXWinUtil {

    public static WinDef.HWND getNativeHandleForStage(Stage stage) {
        try {
            val getPeer = Window.class.getDeclaredMethod("getPeer");
            getPeer.setAccessible(true);
            val tkStage = getPeer.invoke(stage);

            if (tkStage == null) {
                throw new IllegalStateException("Unable to retrieve toolkit stage.");
            }

            val getRawHandle = tkStage.getClass().getMethod("getRawHandle");
            getRawHandle.setAccessible(true);
            val rawHandle = getRawHandle.invoke(tkStage);

            if (rawHandle == null) {
                throw new IllegalStateException("Raw handle is null.");
            }

            val pointer = new Pointer((Long) rawHandle);
            return new WinDef.HWND(pointer);
        } catch (NoSuchMethodException | IllegalAccessException | IllegalStateException ex) {
            System.err.println("Reflection error: " + ex.getMessage());
        } catch (Exception ex) {
            System.err.println("Unexpected error: " + ex.getMessage());
        }
        return null;
    }

    public static void setDarkMode(Stage stage, boolean darkMode) {
        val hwnd = FXWinUtil.getNativeHandleForStage(stage);
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