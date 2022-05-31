package nl.jixxed.eliteodysseymaterials.service.ar;

import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinGDI;
import com.sun.jna.platform.win32.WinNT;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.awt.Rectangle;
import java.awt.*;
import java.awt.image.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JNAScreenshotService implements ScreenshotService {
    private final static JNAScreenshotService INSTANCE = new JNAScreenshotService();
    private static WinGDI.BITMAPINFOHEADER bih;
    private static int height;
    private static ColorModel cm;
    private static DataBuffer buffer;
    private static WritableRaster raster;
    private static int strideBits;
    private static int strideBytesAligned;
    private static int strideElementsAligned;
    private static boolean ok;
    private WinGDI.BITMAPINFOHEADER bih1;
    private WinGDI.BITMAPINFO bi;
    private WinDef.HDC blitDC;
    private WinDef.HDC windowDC;
    private WinDef.HBITMAP outputBitmap;
    private WinNT.HANDLE oldBitmap;

    public static ScreenshotService getInstance() {
        return INSTANCE;
    }

    @Override
    public BufferedImage getScreenshot(final Point offset, final Rectangle bounds, final BufferedImage output) {
        this.windowDC = GDI.GetDC(USER.GetDesktopWindow());
        this.outputBitmap = GDI.CreateCompatibleBitmap(this.windowDC, bounds.width, bounds.height);
        try {
            this.blitDC = GDI.CreateCompatibleDC(this.windowDC);
            try {
                this.oldBitmap = GDI.SelectObject(this.blitDC, this.outputBitmap);
                try {
                    GDI.BitBlt(this.blitDC, 0, 0, bounds.width, bounds.height, this.windowDC, offset.x + bounds.x, offset.y + bounds.y, GDI32.SRCCOPY);
                } finally {
                    GDI.SelectObject(this.blitDC, this.oldBitmap);
                }
                this.bi = new WinGDI.BITMAPINFO(40);
                this.bi.bmiHeader.biSize = 40;
                if (GDI.GetDIBits(this.blitDC, this.outputBitmap, 0, bounds.height, (byte[]) null, this.bi, WinGDI.DIB_RGB_COLORS)) {
                    this.bih1 = this.bi.bmiHeader;
                    this.bih1.biHeight = -Math.abs(this.bih1.biHeight);
                    this.bi.bmiHeader.biCompression = 0;
                    return bufferedImageFromBitmap(this.blitDC, this.outputBitmap, this.bi, output);
                } else {
                    return null;
                }
            } finally {
                GDI.DeleteObject(this.blitDC);
            }
        } finally {
            GDI.DeleteObject(this.outputBitmap);
        }
    }

    private static BufferedImage bufferedImageFromBitmap(final WinDef.HDC blitDC, final WinDef.HBITMAP outputBitmap, final WinGDI.BITMAPINFO bi, final BufferedImage output) {
        bih = bi.bmiHeader;
        height = Math.abs(bih.biHeight);
        strideBits = (bih.biWidth * bih.biBitCount);
        strideBytesAligned = (((strideBits - 1) | 0x1F) + 1) >> 3;
        if (output == null || ((output.getData().getDataBuffer() instanceof DataBufferUShort) ? 16 : 32) != bih.biBitCount) {
            switch (bih.biBitCount) {
                case 16 -> {
                    strideElementsAligned = strideBytesAligned / 2;
                    cm = new DirectColorModel(16, 0x7C00, 0x3E0, 0x1F);
                    buffer = new DataBufferUShort(strideElementsAligned * height);
                }
                case 32 -> {
                    strideElementsAligned = strideBytesAligned / 4;
                    cm = new DirectColorModel(32, 0xFF0000, 0xFF00, 0xFF);
                    buffer = new DataBufferInt(strideElementsAligned * height);
                }
                default -> throw new IllegalArgumentException("Unsupported bit count: " + bih.biBitCount);
            }
        } else {
            strideElementsAligned = strideBytesAligned / 2;
            cm = output.getColorModel();
            buffer = output.getData().getDataBuffer();
        }
        raster = Raster.createPackedRaster(buffer, bih.biWidth, height, strideElementsAligned, ((DirectColorModel) cm).getMasks(), null);
        switch (buffer.getDataType()) {
            case DataBuffer.TYPE_INT: {
                ok = GDI.GetDIBits(blitDC, outputBitmap, 0, raster.getHeight(), ((DataBufferInt) buffer).getData(), bi, 0);
            }
            break;
            case DataBuffer.TYPE_USHORT: {
                ok = GDI.GetDIBits(blitDC, outputBitmap, 0, raster.getHeight(), ((DataBufferUShort) buffer).getData(), bi, 0);
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
