/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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