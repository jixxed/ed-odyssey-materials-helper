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

import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.win32.StdCallLibrary;

public interface User32 extends StdCallLibrary {
    User32 INSTANCE = Native.load("user32", User32.class);

    boolean EnumWindows(WndEnumProc wndenumproc, int lParam);

    boolean IsWindowVisible(int hWnd);

    boolean IsWindow(int hWnd);

    boolean SetWindowPos(int hWnd, int hWndInsertAfter, int x, int y, int cx, int cy, int flags);

    int GetWindowRect(int hWnd, RECT r);

    int GetClientRect(int hWnd, RECT r);

    boolean AdjustWindowRect(RECT r, int dwStyle, boolean menu);

    void GetWindowTextA(int hWnd, byte[] buffer, int buflen);

    int GetTopWindow(int hWnd);

    int GetWindow(int hWnd, int flag);

    boolean ShowWindow(int hWnd);

    boolean BringWindowToTop(int hWnd);

    WinDef.HWND GetDesktopWindow();

    int GetForegroundWindow();

    int GetActiveWindow();

    boolean SetForegroundWindow(int hWnd);

    int FindWindowA(String winClass, String title);

    long SendMessageA(int hWnd, int msg, int num1, int num2);

    int GetSystemMetrics(int metric);

    long GetWindowLongA(int hWnd, int index);

    void ClientToScreen(int hwnd, WinDef.POINT getPos);

    void ScreenToClient(int hwnd, WinDef.POINT getPos);

    void PhysicalToLogicalPointForPerMonitorDPI(int hwnd, WinDef.POINT getPos);

    void LogicalToPhysicalPointForPerMonitorDPI(int hwnd, WinDef.POINT getPos);

    void SetWindowLongA(int hwnd, int nIndex, long dwNewLong);

}