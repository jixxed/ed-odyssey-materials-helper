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
import com.sun.jna.platform.win32.WinDef.HDC;
import com.sun.jna.platform.win32.WinGDI;

interface GDI32 extends com.sun.jna.platform.win32.GDI32 {
    GDI32 INSTANCE = Native.load("gdi32", GDI32.class);

    @Override
    boolean BitBlt(HDC hdcDest, int nXDest, int nYDest, int nWidth, int nHeight, HDC hdcSrc, int nXSrc, int nYSrc, int dwRop);

    HDC GetDC(WinDef.HWND hWnd);

    boolean GetDIBits(HDC dc, WinDef.HBITMAP bmp, int startScan, int scanLines, byte[] pixels, WinGDI.BITMAPINFO bi, int usage);

    boolean GetDIBits(HDC dc, WinDef.HBITMAP bmp, int startScan, int scanLines, short[] pixels, WinGDI.BITMAPINFO bi, int usage);

    boolean GetDIBits(HDC dc, WinDef.HBITMAP bmp, int startScan, int scanLines, int[] pixels, WinGDI.BITMAPINFO bi, int usage);

    int SRCCOPY = 0xCC0020;
}