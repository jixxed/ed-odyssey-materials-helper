package nl.jixxed.eliteodysseymaterials.service.ar;

import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinGDI;
import com.sun.jna.platform.win32.WinNT;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.awt.Rectangle;
import java.awt.image.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JNAScreenshotService implements ScreenshotService {
    private final static JNAScreenshotService INSTANCE = new JNAScreenshotService();

    public static ScreenshotService getInstance() {
        return INSTANCE;
    }

    @Override
    public BufferedImage getScreenshot(final Rectangle bounds, final BufferedImage output) {
        final WinDef.HDC windowDC = GDI.GetDC(USER.GetDesktopWindow());
        final WinDef.HBITMAP outputBitmap = GDI.CreateCompatibleBitmap(windowDC, bounds.width, bounds.height);
        try {
            final WinDef.HDC blitDC = GDI.CreateCompatibleDC(windowDC);
            try {
                final WinNT.HANDLE oldBitmap = GDI.SelectObject(blitDC, outputBitmap);
                try {
                    GDI.BitBlt(blitDC, 0, 0, bounds.width, bounds.height, windowDC, bounds.x, bounds.y, GDI32.SRCCOPY);
                } finally {
                    GDI.SelectObject(blitDC, oldBitmap);
                }
                final WinGDI.BITMAPINFO bi = new WinGDI.BITMAPINFO(40);
                bi.bmiHeader.biSize = 40;
                final boolean ok = GDI.GetDIBits(blitDC, outputBitmap, 0, bounds.height, (byte[]) null, bi, WinGDI.DIB_RGB_COLORS);
                if (ok) {
                    final WinGDI.BITMAPINFOHEADER bih = bi.bmiHeader;
                    bih.biHeight = -Math.abs(bih.biHeight);
                    bi.bmiHeader.biCompression = 0;
                    return bufferedImageFromBitmap(blitDC, outputBitmap, bi, output);
                } else {
                    return null;
                }
            } finally {
                GDI.DeleteObject(blitDC);
            }
        } finally {
            GDI.DeleteObject(outputBitmap);
        }
    }

    private static BufferedImage bufferedImageFromBitmap(final WinDef.HDC blitDC, final WinDef.HBITMAP outputBitmap, final WinGDI.BITMAPINFO bi, final BufferedImage output) {
        final WinGDI.BITMAPINFOHEADER bih = bi.bmiHeader;
        final int height = Math.abs(bih.biHeight);
        final ColorModel cm;
        final DataBuffer buffer;
        final WritableRaster raster;
        final int strideBits = (bih.biWidth * bih.biBitCount);
        final int strideBytesAligned = (((strideBits - 1) | 0x1F) + 1) >> 3;
        final int strideElementsAligned;
        switch (bih.biBitCount) {
            case 16:
                strideElementsAligned = strideBytesAligned / 2;
                cm = new DirectColorModel(16, 0x7C00, 0x3E0, 0x1F);
                buffer = new DataBufferUShort(strideElementsAligned * height);
                raster = Raster.createPackedRaster(buffer, bih.biWidth, height, strideElementsAligned, ((DirectColorModel) cm).getMasks(), null);
                break;
            case 32:
                strideElementsAligned = strideBytesAligned / 4;
                cm = new DirectColorModel(32, 0xFF0000, 0xFF00, 0xFF);
                buffer = new DataBufferInt(strideElementsAligned * height);
                raster = Raster.createPackedRaster(buffer, bih.biWidth, height, strideElementsAligned, ((DirectColorModel) cm).getMasks(), null);
                break;
            default:
                throw new IllegalArgumentException("Unsupported bit count: " + bih.biBitCount);
        }
        final boolean ok;
        switch (buffer.getDataType()) {
            case DataBuffer.TYPE_INT: {
                final int[] pixels = ((DataBufferInt) buffer).getData();
                ok = GDI.GetDIBits(blitDC, outputBitmap, 0, raster.getHeight(), pixels, bi, 0);
            }
            break;
            case DataBuffer.TYPE_USHORT: {
                final short[] pixels = ((DataBufferUShort) buffer).getData();
                ok = GDI.GetDIBits(blitDC, outputBitmap, 0, raster.getHeight(), pixels, bi, 0);
            }
            break;
            default:
                throw new AssertionError("Unexpected buffer element type: " + buffer.getDataType());
        }
        if (ok) {
            if (output != null) {
                output.setData(raster);
                return output;
            } else {
                return new BufferedImage(cm, raster, false, null);
            }
        } else {
            return null;
        }
    }

    private static final User32 USER = User32.INSTANCE;

    private static final GDI32 GDI = GDI32.INSTANCE;

}
