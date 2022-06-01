package nl.jixxed.eliteodysseymaterials.service.ar;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.awt.image.BufferedImage;

public class ImageTransformHelper {
    private static double midRows;
    private static double midCols;
    private static int col_count;
    private static int row_count;
    private static double dx;
    private static double dy;
    private static Size size;

    private static final double EXTRA_PIXELS = 10.0;
    private static Point[][] sourceRows;
    private static Point[][] reverseSourceRows;

    public static void init(final DownloadMenu downloadMenu, final double scaling) {
        midRows = downloadMenu.getMenu().getHeight() / 2;
        midCols = downloadMenu.getMenu().getWidth() / 2;
        col_count = (int) (downloadMenu.getMenu().getWidth() / 50);
        row_count = (int) (downloadMenu.getMenu().getHeight() / 50);
        dx = downloadMenu.getMenu().getWidth() / (col_count - 1);
        dy = downloadMenu.getMenu().getHeight() / (row_count - 1);
        size = new Size(dx + EXTRA_PIXELS * 2, dy + EXTRA_PIXELS * 2);
        sourceRows = new Point[row_count][];
        reverseSourceRows = new Point[row_count][];
        for (int row = 0; row < row_count; row++) {
            final Point[] sourceRow = new Point[col_count];
            final Point[] reverseSourceRow = new Point[col_count];
            for (int col = 0; col < col_count; col++) {
                final double x = (int) (dx * col) - EXTRA_PIXELS;
                final double ellipse_offset = 120 * scaling;
                final int a = Math.abs((int) (ellipse_offset * (row * dy - midRows) / midRows));
                final int b = (int) downloadMenu.getMenu().getWidth();
                final double y;
                final double yReverse;
                if (row * dy > midRows) {
                    y = ((row * dy - Math.sqrt(Math.pow(a, 2) * (1 - Math.pow(x - midCols, 2) / Math.pow(b, 2)))) + ellipse_offset * (row * dy - midRows) / midRows) - EXTRA_PIXELS;
                    yReverse = ((row * dy + Math.sqrt(Math.pow(a, 2) * (1 - Math.pow(x - midCols, 2) / Math.pow(b, 2)))) - ellipse_offset * (row * dy - midRows) / midRows) - EXTRA_PIXELS;

                } else {
                    y = ((row * dy + Math.sqrt(Math.pow(a, 2) * (1 - Math.pow(x - midCols, 2) / Math.pow(b, 2)))) + ellipse_offset * (row * dy - midRows) / midRows) - EXTRA_PIXELS;
                    yReverse = ((row * dy - Math.sqrt(Math.pow(a, 2) * (1 - Math.pow(x - midCols, 2) / Math.pow(b, 2)))) - ellipse_offset * (row * dy - midRows) / midRows) - EXTRA_PIXELS;
                }
                sourceRow[col] = new Point(x, y);
                reverseSourceRow[col] = new Point(x, yReverse);
            }
            sourceRows[row] = sourceRow;
            reverseSourceRows[row] = reverseSourceRow;
        }
    }

    static BufferedImage transform(final BufferedImage bufferedImage, final DownloadMenu downloadMenu) {
        final Mat mat = CvHelper.convertToMat(bufferedImage, null);
        final Mat overlay = Mat.zeros(downloadMenu.getContentHeight(), downloadMenu.getContentWidth(), CvType.CV_32FC4);

        for (int row = 0; row < row_count - 1; row++) {
            for (int col = 0; col < col_count - 1; col++) {
                final Mat submat = mat.submat(
                        (int) Math.max(0, Math.min(reverseSourceRows[row][col].y - EXTRA_PIXELS, reverseSourceRows[row + 1][col].y + EXTRA_PIXELS)),
                        (int) Math.min(downloadMenu.getContentHeight(), Math.max(reverseSourceRows[row][col + 1].y - EXTRA_PIXELS, reverseSourceRows[row + 1][col + 1].y + EXTRA_PIXELS)),
                        (int) Math.max(0, reverseSourceRows[row][col].x - EXTRA_PIXELS),
                        (int) Math.min(downloadMenu.getContentWidth(), reverseSourceRows[row + 1][col + 1].x + EXTRA_PIXELS));
                Imgproc.cvtColor(submat, submat, Imgproc.COLOR_BGRA2GRAY);
                if (Core.countNonZero(submat) == 0) {
                    continue;
                }
                final Mat fromPoints = new Mat(1, 4, CvType.CV_32FC2);
                final Mat toPoints = new Mat(1, 4, CvType.CV_32FC2);
                fromPoints.put(0, 0, reverseSourceRows[row][col].x - EXTRA_PIXELS, reverseSourceRows[row][col].y - EXTRA_PIXELS);
                fromPoints.put(0, 1, reverseSourceRows[row][col + 1].x + EXTRA_PIXELS, reverseSourceRows[row][col + 1].y - EXTRA_PIXELS);
                fromPoints.put(0, 2, reverseSourceRows[row + 1][col].x - EXTRA_PIXELS, reverseSourceRows[row + 1][col].y + EXTRA_PIXELS);
                fromPoints.put(0, 3, reverseSourceRows[row + 1][col + 1].x + EXTRA_PIXELS, reverseSourceRows[row + 1][col + 1].y + EXTRA_PIXELS);
                toPoints.put(0, 0, 0 - EXTRA_PIXELS, 0 - EXTRA_PIXELS);
                toPoints.put(0, 1, dx + EXTRA_PIXELS, 0 - EXTRA_PIXELS);
                toPoints.put(0, 2, 0 - EXTRA_PIXELS, dy + EXTRA_PIXELS);
                toPoints.put(0, 3, dx + EXTRA_PIXELS, dy + EXTRA_PIXELS);
                final Mat perspectiveTransform = Imgproc.getPerspectiveTransform(fromPoints, toPoints, Core.DECOMP_LU);
                final Mat dst = new Mat();
                dst.create(size, mat.type());
                Imgproc.warpPerspective(mat, dst, perspectiveTransform, size, Imgproc.INTER_LANCZOS4);

                CvHelper.overlayImage(dst, overlay, new Point((dx * col) + downloadMenu.getMenu().getX(), (dy * row) + downloadMenu.getMenu().getY()), EXTRA_PIXELS);
                perspectiveTransform.release();
                fromPoints.release();
                toPoints.release();
                dst.release();
            }
        }
        final BufferedImage bufferedImage1 = CvHelper.createBufferedImage(overlay);
        mat.release();
        overlay.release();
        return bufferedImage1;
    }


    public static BufferedImage transformForSelection(final BufferedImage downloadMenuCapture, final java.awt.Rectangle requestedArea) {
        final Mat src = CvHelper.convertToMat(downloadMenuCapture, null);
        final Mat result = Mat.zeros(src.rows(), src.cols(), CvType.CV_32FC4);
        for (int row = 0; row < row_count - 1; row++) {
            for (int col = 0; col < col_count - 1; col++) {

                final java.awt.Rectangle section = new java.awt.Rectangle((int) (dx * col), (int) (dy * row), (int) size.width, (int) size.height);

                if (!section.intersects(requestedArea)) {
                    continue;
                }
                final Mat fromPoints = new Mat(1, 4, CvType.CV_32FC2);
                final Mat toPoints = new Mat(1, 4, CvType.CV_32FC2);
                fromPoints.put(0, 0, sourceRows[row][col].x - EXTRA_PIXELS, sourceRows[row][col].y - EXTRA_PIXELS);
                fromPoints.put(0, 1, sourceRows[row][col + 1].x + EXTRA_PIXELS, sourceRows[row][col + 1].y - EXTRA_PIXELS);
                fromPoints.put(0, 2, sourceRows[row + 1][col].x - EXTRA_PIXELS, sourceRows[row + 1][col].y + EXTRA_PIXELS);
                fromPoints.put(0, 3, sourceRows[row + 1][col + 1].x + EXTRA_PIXELS, sourceRows[row + 1][col + 1].y + EXTRA_PIXELS);
                toPoints.put(0, 0, 0 - EXTRA_PIXELS, 0 - EXTRA_PIXELS);
                toPoints.put(0, 1, dx + EXTRA_PIXELS, 0 - EXTRA_PIXELS);
                toPoints.put(0, 2, 0 - EXTRA_PIXELS, dy + EXTRA_PIXELS);
                toPoints.put(0, 3, dx + EXTRA_PIXELS, dy + EXTRA_PIXELS);
                final Mat perspectiveTransform = Imgproc.getPerspectiveTransform(fromPoints, toPoints, Core.DECOMP_LU);
                final Mat dst = new Mat();
                dst.create(size, src.type());

                Imgproc.warpPerspective(src, dst, perspectiveTransform, size, Imgproc.INTER_LANCZOS4);
                CvHelper.overlayImage(dst, result, new Point((dx * col), (dy * row)), EXTRA_PIXELS);
                perspectiveTransform.release();
                fromPoints.release();
                toPoints.release();
                dst.release();
            }
        }
        final BufferedImage bufferedImage = CvHelper.createBufferedImage(result);
        src.release();
        result.release();
        return bufferedImage;
    }
}
