package nl.jixxed.eliteodysseymaterials.service.ar;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WindowInfoUtil {
    private static List<WindowInfo> getAllWindows() {
        final List<WindowInfo> inflList = new ArrayList<>();
        final List<Integer> order = new ArrayList<>();
        int top = User32.INSTANCE.GetTopWindow(0);
        while (top != 0) {
            order.add(top);
            top = User32.INSTANCE.GetWindow(top, WinUser.GW_HWNDNEXT);
        }

        User32.INSTANCE.EnumWindows((hWnd, lParam) -> {
            final WindowInfo info = getWindowInfo(new WindowInfo(hWnd, new RECT(), "", 0));
            inflList.add(info);
            return true;
        }, 0);

        inflList.sort(Comparator.comparingInt(o -> order.indexOf(o.hwnd)));

        return inflList;
    }

    public static WindowInfo getWindowInfo(final WindowInfo windowInfo) {

        User32.INSTANCE.GetClientRect(windowInfo.hwnd, windowInfo.rect);
        final byte[] buffer = new byte[1024];
        final WinDef.POINT getPos = new WinDef.POINT();
        User32.INSTANCE.ClientToScreen(windowInfo.hwnd, getPos);
        windowInfo.rect.left += getPos.x;
        windowInfo.rect.top += getPos.y;
        windowInfo.rect.right += getPos.x;
        windowInfo.rect.bottom += getPos.y;
        User32.INSTANCE.GetWindowTextA(windowInfo.hwnd, buffer, buffer.length);
        windowInfo.title = Native.toString(buffer);
        windowInfo.styles = User32.INSTANCE.GetWindowLongA(windowInfo.hwnd, WinUser.GWL_STYLE);
        return windowInfo;
    }

    public static int findWindow(final Predicate<String> nameMatcher) {
        final List<WindowInfo> allWindows = getAllWindows();
        return allWindows.stream()
                .filter(windowInfo -> nameMatcher.test(windowInfo.title))
                .findFirst()
                .map(windowInfo -> windowInfo.hwnd)
                .orElse(0);
    }

    public static boolean isWindow(final int hWnd) {
        return User32.INSTANCE.IsWindow(hWnd);
    }

    public static Borders getWindowBorders(final int hWnd) {

        final RECT r2 = new RECT();
        r2.top = 0;
        r2.left = 0;
        r2.right = 1000;
        r2.bottom = 1000;
        final long windowStyles = User32.INSTANCE.GetWindowLongA(hWnd, WinUser.GWL_STYLE);
        User32.INSTANCE.AdjustWindowRect(r2, (int) windowStyles, false);
        final int xDiff = r2.right - r2.left - 1000;
        final int yDiff = r2.bottom - r2.top - 1000;
        return new Borders(xDiff, yDiff);
    }

    public static void setZOrder(final int targetHandle, final int arOverlayHandle) {
        User32.INSTANCE.SetWindowPos(targetHandle, arOverlayHandle, 0, 0, 100, 100, WinUser.SWP_NOSIZE | WinUser.SWP_NOMOVE);
    }
}
