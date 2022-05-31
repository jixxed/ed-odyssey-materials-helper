package nl.jixxed.eliteodysseymaterials.service.ar;

import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.platform.win32.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.awt.Rectangle;
import java.awt.*;
import java.awt.image.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GDIScreenshotService implements ScreenshotService {
    private final static GDIScreenshotService INSTANCE = new GDIScreenshotService();


    private static final DirectColorModel SCREENSHOT_COLOR_MODEL = new DirectColorModel(24, 0x00FF0000, 0xFF00, 0xFF);
    private static final int[] SCREENSHOT_BAND_MASKS = {
            SCREENSHOT_COLOR_MODEL.getRedMask(),
            SCREENSHOT_COLOR_MODEL.getGreenMask(),
            SCREENSHOT_COLOR_MODEL.getBlueMask()
    };

    public static ScreenshotService getInstance() {
        return INSTANCE;
    }

    @Override
    public BufferedImage getScreenshot(final Point offset, final Rectangle bounds, final BufferedImage output) {
//        WinDef.RECT rect = new WinDef.RECT();
//        if (!com.sun.jna.platform.win32.User32.INSTANCE.GetWindowRect(target, rect)) {
//            throw new Win32Exception(Native.getLastError());
//        }
        final Rectangle jRectangle = new Rectangle(offset.x + bounds.x, offset.y + bounds.y, bounds.width, bounds.height);

        if (jRectangle.width == 0 || jRectangle.height == 0) {
            throw new IllegalStateException("Window width and/or height were 0 even though GetWindowRect did not appear to fail.");
        }

        final WinDef.HDC hdcTarget = com.sun.jna.platform.win32.User32.INSTANCE.GetDC(com.sun.jna.platform.win32.User32.INSTANCE.GetDesktopWindow());
        if (hdcTarget == null) {
            throw new Win32Exception(Native.getLastError());
        }

        Win32Exception we = null;

        // device context used for drawing
        WinDef.HDC hdcTargetMem = null;

        // handle to the bitmap to be drawn to
        WinDef.HBITMAP hBitmap = null;

        // original display surface associated with the device context
        WinNT.HANDLE hOriginal = null;

        // final java image structure we're returning.
        BufferedImage image = null;

        try {
            hdcTargetMem = com.sun.jna.platform.win32.GDI32.INSTANCE.CreateCompatibleDC(hdcTarget);
            if (hdcTargetMem == null) {
                throw new Win32Exception(Native.getLastError());
            }

            hBitmap = com.sun.jna.platform.win32.GDI32.INSTANCE.CreateCompatibleBitmap(hdcTarget, jRectangle.width, jRectangle.height);
            if (hBitmap == null) {
                throw new Win32Exception(Native.getLastError());
            }

            hOriginal = com.sun.jna.platform.win32.GDI32.INSTANCE.SelectObject(hdcTargetMem, hBitmap);
            if (hOriginal == null) {
                throw new Win32Exception(Native.getLastError());
            }

            // draw to the bitmap
            if (!com.sun.jna.platform.win32.GDI32.INSTANCE.BitBlt(hdcTargetMem, 0, 0, jRectangle.width, jRectangle.height, hdcTarget, jRectangle.x, jRectangle.y, com.sun.jna.platform.win32.GDI32.SRCCOPY)) {
                throw new Win32Exception(Native.getLastError());
            }

            final WinGDI.BITMAPINFO bmi = new WinGDI.BITMAPINFO();
            bmi.bmiHeader.biWidth = jRectangle.width;
            bmi.bmiHeader.biHeight = -jRectangle.height;
            bmi.bmiHeader.biPlanes = 1;
            bmi.bmiHeader.biBitCount = 32;
            bmi.bmiHeader.biCompression = WinGDI.BI_RGB;

            final Memory buffer = new Memory(jRectangle.width * jRectangle.height * 4);
            final int resultOfDrawing = com.sun.jna.platform.win32.GDI32.INSTANCE.GetDIBits(hdcTarget, hBitmap, 0, jRectangle.height, buffer, bmi,
                    WinGDI.DIB_RGB_COLORS);
            if (resultOfDrawing == 0 || resultOfDrawing == WinError.ERROR_INVALID_PARAMETER) {
                throw new Win32Exception(Native.getLastError());
            }

            final int bufferSize = jRectangle.width * jRectangle.height;
            final DataBuffer dataBuffer = new DataBufferInt(buffer.getIntArray(0, bufferSize), bufferSize);
            final WritableRaster raster = Raster.createPackedRaster(dataBuffer, jRectangle.width, jRectangle.height, jRectangle.width,
                    SCREENSHOT_BAND_MASKS, null);
            image = new BufferedImage(SCREENSHOT_COLOR_MODEL, raster, false, null);

        } catch (final Win32Exception e) {
            we = e;
        } finally {
            if (hOriginal != null) {
                // per MSDN, set the display surface back when done drawing
                final WinNT.HANDLE result = com.sun.jna.platform.win32.GDI32.INSTANCE.SelectObject(hdcTargetMem, hOriginal);
                // failure modes are null or equal to HGDI_ERROR
                if (result == null || WinGDI.HGDI_ERROR.equals(result)) {
                    final Win32Exception ex = new Win32Exception(Native.getLastError());
                    if (we != null) {
//                        ex.addSuppressedReflected(we);
                    }
                    we = ex;
                }
            }

            if (hBitmap != null) {
                if (!com.sun.jna.platform.win32.GDI32.INSTANCE.DeleteObject(hBitmap)) {
                    final Win32Exception ex = new Win32Exception(Native.getLastError());
                    if (we != null) {
//                        ex.addSuppressedReflected(we);
                    }
                    we = ex;
                }
            }

            if (hdcTargetMem != null) {
                // get rid of the device context when done
                if (!com.sun.jna.platform.win32.GDI32.INSTANCE.DeleteDC(hdcTargetMem)) {
                    final Win32Exception ex = new Win32Exception(Native.getLastError());
                    if (we != null) {
//                        ex.addSuppressedReflected(we);
                    }
                    we = ex;
                }
            }

            if (hdcTarget != null) {
                if (0 == com.sun.jna.platform.win32.User32.INSTANCE.ReleaseDC(com.sun.jna.platform.win32.User32.INSTANCE.GetDesktopWindow(), hdcTarget)) {
                    throw new IllegalStateException("Device context did not release properly.");
                }
            }
        }

        if (we != null) {
            throw we;
        }
        return image;
    }

    private static final User32 USER = User32.INSTANCE;

    private static final GDI32 GDI = GDI32.INSTANCE;

}
