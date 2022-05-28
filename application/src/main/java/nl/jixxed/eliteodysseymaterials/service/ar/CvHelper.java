package nl.jixxed.eliteodysseymaterials.service.ar;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import lombok.extern.slf4j.Slf4j;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;

@Slf4j
public class CvHelper {

    /**
     * Converts a BufferedImage to an OpenCV Mat object.
     */
    public static Mat convertToMat(final Image fxImg) {
        final BufferedImage buffImg = SwingFXUtils.fromFXImage(fxImg, null);

        return convertToMat(buffImg, null);
    }

    public static Mat convertToMat(final BufferedImage buffImg, Mat targetMat) {
        final BufferedImage convertedImg;
        if (buffImg.getType() == BufferedImage.TYPE_4BYTE_ABGR) {
            convertedImg = buffImg;
        } else {
            convertedImg = new BufferedImage(buffImg.getWidth(), buffImg.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
            convertedImg.getGraphics().drawImage(buffImg, 0, 0, null);
        }
        if (targetMat == null) {
            targetMat = Mat.zeros(buffImg.getHeight(), buffImg.getWidth(), CvType.CV_8UC4);
        }
        if (targetMat.cols() != buffImg.getWidth() || targetMat.rows() != buffImg.getHeight()) {
            targetMat = Mat.zeros(buffImg.getHeight(), buffImg.getWidth(), CvType.CV_8UC4);
        }

        final byte[] pixels = ((DataBufferByte) convertedImg.getRaster().getDataBuffer()).getData();

        targetMat.put(0, 0, pixels);

        return targetMat;
    }


    static Image createFxImage(final Mat matrix) {
        final BufferedImage image = createBufferedImage(matrix);
        return SwingFXUtils.toFXImage(image, null);
    }

    public static BufferedImage createBufferedImage(final Mat matrix) {
        if (matrix.type() == CvType.CV_8UC1) {
            Imgproc.cvtColor(matrix, matrix, Imgproc.COLOR_GRAY2RGBA);
        }
        final BufferedImage image = new BufferedImage(matrix.width(), matrix.height(), BufferedImage.TYPE_4BYTE_ABGR_PRE);
        final byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        if (matrix.type() == CvType.CV_32FC4) {
            final Mat cvtmatrix = new Mat();
            matrix.convertTo(cvtmatrix, CvType.CV_8UC4);
            cvtmatrix.get(0, 0, data);
            cvtmatrix.release();
        } else {
            matrix.get(0, 0, data);
        }
        return image;
    }

    static Image mat2Image(final Mat frame) {
        // create a temporary buffer
        final MatOfByte buffer = new MatOfByte();
        // encode the frame in the buffer, according to the PNG format
        Imgcodecs.imencode(".png", frame, buffer);
        // build and return an Image created from the image encoded in the
        // buffer
        return new Image(new ByteArrayInputStream(buffer.toArray()));
    }

    static Mat binaryBufferedImageToMat(final BufferedImage img) {

        final Mat mat = new Mat(img.getHeight(), img.getWidth(), CvType.CV_8UC1);

        final byte[] white = new byte[]{(byte) 255};
        final byte[] black = new byte[]{(byte) 0};

        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                if (img.getRGB(x, y) == Color.BLACK.getRGB()) {
                    mat.put(y, x, black);
                } else {
                    mat.put(y, x, white);
                }
            }
        }
        return mat;
    }

    public static BufferedImage mat2Img(final Mat in) {
        final BufferedImage out;
        final byte[] data = new byte[in.cols() * in.rows() * (int) in.elemSize()];
        final int type;
        in.get(0, 0, data);

        if (in.channels() == 1) {
            type = BufferedImage.TYPE_BYTE_GRAY;
        } else if (in.channels() == 3) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        } else {
            type = BufferedImage.TYPE_4BYTE_ABGR_PRE;
        }

        out = new BufferedImage(in.cols(), in.rows(), type);

        out.getRaster().setDataElements(0, 0, in.cols(), in.rows(), data);
        return out;
    }
}