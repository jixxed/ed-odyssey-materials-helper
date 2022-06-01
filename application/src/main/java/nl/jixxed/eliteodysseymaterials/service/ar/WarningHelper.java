package nl.jixxed.eliteodysseymaterials.service.ar;

import javafx.scene.image.Image;
import nl.jixxed.eliteodysseymaterials.service.ARService;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.awt.image.BufferedImage;

public class WarningHelper {
    private static final int MATCH_METHOD = Imgproc.TM_CCOEFF_NORMED;
    private static final Mat alertTemplate;
    private static final Mat alertTemplateScaled;
    private static Mat menuWarningResult;
    private static Mat alertCaptureMat;
    private static Mat alertCaptureMatGray;

    static {
        alertTemplate = CvHelper.convertToMat(new Image(ARService.class.getResourceAsStream("/images/opencv/cv_template_alert_small.png")));
        alertTemplateScaled = CvHelper.convertToMat(new Image(ARService.class.getResourceAsStream("/images/opencv/cv_template_alert_small.png")));
        Imgproc.cvtColor(alertTemplate, alertTemplate, Imgproc.COLOR_BGRA2GRAY);
        Imgproc.cvtColor(alertTemplateScaled, alertTemplateScaled, Imgproc.COLOR_BGRA2GRAY);

    }

    public static boolean menuHasWarning(final BufferedImage downloadMenuCapture, final double scaling, final double threshold) {
        if (downloadMenuCapture == null) {
            return false;
        }
        final BufferedImage alertCapture = downloadMenuCapture.getSubimage(
                (int) (48 * scaling),
                (int) (270 * scaling),
                (int) (140 * scaling),
                (int) (60 * scaling));

        alertCaptureMat = CvHelper.convertToMat(alertCapture, alertCaptureMat);
        final int result_cols = alertCaptureMat.cols() - alertTemplateScaled.cols() + 1;
        final int result_rows = alertCaptureMat.rows() - alertTemplateScaled.rows() + 1;
        if (menuWarningResult == null || menuWarningResult.cols() != result_cols || menuWarningResult.rows() != result_rows) {
            if (menuWarningResult != null) {
                menuWarningResult.release();
            }
            menuWarningResult = new Mat(result_rows, result_cols, CvType.CV_32FC1);
        }
        if (alertCaptureMatGray == null || alertCaptureMat.cols() != alertCaptureMatGray.cols() || alertCaptureMat.rows() != alertCaptureMatGray.rows()) {
            if (alertCaptureMatGray != null) {
                alertCaptureMatGray.release();
            }
            alertCaptureMatGray = new Mat();
        }

        //grayscale capture
        Imgproc.cvtColor(alertCaptureMat, alertCaptureMatGray, Imgproc.COLOR_BGRA2GRAY);

        //matching
        Imgproc.matchTemplate(alertCaptureMatGray, alertTemplateScaled, menuWarningResult, MATCH_METHOD);
        final Core.MinMaxLocResult mmr = Core.minMaxLoc(menuWarningResult);

        return mmr.maxVal > threshold;
    }

    public static void updateScale(final double scaling) {

        Imgproc.resize(alertTemplate, alertTemplateScaled, new Size(), scaling, scaling, Imgproc.INTER_AREA);
    }
}
