package nl.jixxed.eliteodysseymaterials.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.lept4j.util.LeptUtils;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract1;
import net.sourceforge.tess4j.TesseractException;
import nl.jixxed.eliteodysseymaterials.constants.OsConstants;
import nl.jixxed.eliteodysseymaterials.service.event.ARLocaleChangeEvent;
import nl.jixxed.eliteodysseymaterials.service.event.ARWhitelistChangeEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class OCRService {
    private static final ITesseract instance;  // JNA Interface Mapping
    public static final String TESS4J_DIR = new File(OsConstants.TESS4J).getPath();

    static {

//        System.setProperty("java.library.path", tmpFolder.getPath());
        extractLibResources();
        final File tessData = extractTessDataResources();
        instance = new Tesseract1();
        instance.setDatapath(tessData.getPath());
        instance.setLanguage("eng");
        instance.setVariable("tessedit_char_whitelist", "ABCDEFGHIJKLMNOPQRSTUVWXYZ- ");
        EventService.addStaticListener(ARWhitelistChangeEvent.class, arWhitelistChangeEvent ->
                instance.setVariable("tessedit_char_whitelist", arWhitelistChangeEvent.getWhitelist())
        );
        EventService.addStaticListener(ARLocaleChangeEvent.class, arLocaleChangeEvent ->
                instance.setLanguage(arLocaleChangeEvent.getLocale().getIso6392B())
        );
    }

    static synchronized String imageToString(final BufferedImage image) throws TesseractException {
        return instance.doOCR(image);
    }

    private static File extractTessDataResources() {
        final File targetPath = new File(TESS4J_DIR, "tessdata");
        final File configsPath = new File(TESS4J_DIR, "tessdata/configs");
        targetPath.mkdirs();
        configsPath.mkdirs();
        extractResource(Path.of(targetPath.getPath(), "eng.traineddata"), "/tessdata/eng.traineddata");
        extractResource(Path.of(targetPath.getPath(), "osd.traineddata"), "/tessdata/osd.traineddata");
        extractResource(Path.of(targetPath.getPath(), "pdf.ttf"), "/tessdata/pdf.ttf");
        extractResource(Path.of(configsPath.getPath(), "alto"), "/tessdata/configs/alto");
        extractResource(Path.of(configsPath.getPath(), "api_config"), "/tessdata/configs/api_config");
        extractResource(Path.of(configsPath.getPath(), "bazaar"), "/tessdata/configs/bazaar");
        extractResource(Path.of(configsPath.getPath(), "digits"), "/tessdata/configs/digits");
        extractResource(Path.of(configsPath.getPath(), "hocr"), "/tessdata/configs/hocr");
        extractResource(Path.of(configsPath.getPath(), "lstmbox"), "/tessdata/configs/lstmbox");
        extractResource(Path.of(configsPath.getPath(), "pdf"), "/tessdata/configs/pdf");
        extractResource(Path.of(configsPath.getPath(), "quiet"), "/tessdata/configs/quiet");
        extractResource(Path.of(configsPath.getPath(), "tsv"), "/tessdata/configs/tsv");
        extractResource(Path.of(configsPath.getPath(), "txt"), "/tessdata/configs/txt");
        extractResource(Path.of(configsPath.getPath(), "unlv"), "/tessdata/configs/unlv");
        extractResource(Path.of(configsPath.getPath(), "wordstrbox"), "/tessdata/configs/wordstrbox");

        return targetPath;
    }

    private static File extractLibResources() {
//        ,"win32-x86-64"
        final File targetPath = new File(TESS4J_DIR, "win32-x86-64");
        targetPath.mkdirs();
        extractResource(Path.of(targetPath.getPath(), "libtesseract510.dll"), "/win32-x86-64/libtesseract510.dll");
        extractLeptResource(Path.of(targetPath.getPath(), "liblept1820.dll"), "/win32-x86-64/liblept1820.dll");

        return targetPath;
    }

    private static void extractResource(final Path targetPath, final String resourcePath) {
        try (final OutputStream outStream = Files.newOutputStream(targetPath);
             final InputStream resourceAsStream = Tesseract1.class.getResourceAsStream(resourcePath)) {
            outStream.write(resourceAsStream.readAllBytes());
        } catch (final IOException e) {
            log.warn(e.getMessage(), e);
        }
    }

    private static void extractLeptResource(final Path targetPath, final String resourcePath) {
        try (final OutputStream outStream = Files.newOutputStream(targetPath);
             final InputStream resourceAsStream = LeptUtils.class.getResourceAsStream(resourcePath)) {
            outStream.write(resourceAsStream.readAllBytes());
        } catch (final IOException e) {
            log.warn(e.getMessage(), e);
        }
    }


    public static void setDataPath(final String dataPath) {
        instance.setDatapath(dataPath);
    }
}
