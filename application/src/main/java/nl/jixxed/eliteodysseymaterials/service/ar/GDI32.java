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