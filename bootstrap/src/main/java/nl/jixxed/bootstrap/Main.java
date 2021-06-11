package nl.jixxed.bootstrap;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.ini4j.Ini;
import org.ini4j.IniPreferences;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.Iterator;
import java.util.concurrent.Executors;
import java.util.stream.StreamSupport;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main extends Application {
    private JsonNode response;
    private File appFolder;

    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) {


        primaryStage.setTitle("Elite Dangerous Odyssey Materials Helper");


        final String currentDir = System.getProperty("user.dir") + "\\";
        final StackPane root = new StackPane();
        root.setAlignment(Pos.CENTER);
        final Label label = new Label("Checking for updates...");
        final Image pacmanEating = new Image(getClass().getResourceAsStream("/images/pacman.gif"));
        final ImageView animation = new ImageView(pacmanEating);
        animation.setFitWidth(150);
        animation.setFitHeight(150);
        final VBox vbox = new VBox(label, animation);
        vbox.setAlignment(Pos.CENTER);
        root.getChildren().add(vbox);

        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();

        final Runnable r = () -> {
            this.appFolder = new File(currentDir + "program");
            try {
                final String latestVersion = getLatestVersion();
                if ("ERROR".equals(latestVersion)) {
                    error(label, animation, "Unable to determine latest version. Trying to launch application...", true);
                } else {
                    if (!getCurrentVersion(this.appFolder).equals(latestVersion)) {
                        //update
                        Platform.runLater(() -> label.setText("Downloading update..."));
                        //download zip
                        final String latestUpdateUrl = getLatestUpdateUrl();
                        if (!latestUpdateUrl.endsWith("zip")) {
                            error(label, animation, "Update file is invalid. Trying to launch application...", true);
                        }
                        final URL url = new URL(latestUpdateUrl);
                        final ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
                        final File updateFile = new File(currentDir + "update.zip");
                        try (final FileOutputStream fileOutputStream = new FileOutputStream(updateFile)) {
                            final FileChannel fileChannel = fileOutputStream.getChannel();
                            fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
                        }
                        //remove old
                        Platform.runLater(() -> label.setText("Cleaning old files..."));
                        try {
                            FileUtils.deleteDirectory(this.appFolder);

                        } catch (final IOException ex) {
                            Platform.runLater(() -> label.setText("Terminating running application..."));
                            Runtime.getRuntime().exec("taskkill /F /IM \"Elite Dangerous Odyssey Materials Helper.exe\"");
                            Thread.sleep(3000);
                            Platform.runLater(() -> label.setText("Cleaning old files..."));
                            try {
                                FileUtils.deleteDirectory(this.appFolder);
                            } catch (final IOException exAgain) {
                                error(label, animation, "Unable to clean old files. Files in use.", false);
                            }
                        }
                        //extract new
                        Platform.runLater(() -> label.setText("Installing the update..."));
                        try {
                            unzipFile(updateFile, this.appFolder);
                        } catch (final IOException ex) {
                            error(label, animation, "Failed to install the update.", false);
                        }
                        //remove zip
                        Platform.runLater(() -> label.setText("Wiping the evidence..."));

                        if (!updateFile.delete()) {
                            error(label, animation, "Failed to get rid of the evidence. Trying to launch app...", true);
                        }
                    }
                    //launch app
                    Platform.runLater(() -> label.setText("Launching the application..."));
                    Runtime.getRuntime().exec("cmd /c \"" + this.appFolder + "\\Elite Dangerous Odyssey Materials Helper.exe\"");
                    //close this launcher
                    System.exit(0);
                }
            } catch (final IOException | InterruptedException e) {
                e.printStackTrace();
            }

        };
        Executors.newSingleThreadExecutor().execute(r);


    }

    private void error(final Label label, final ImageView animation, final String errorMessage, final boolean launch) throws InterruptedException, IOException {
        Platform.runLater(() -> {
            label.setText(errorMessage);
            animation.setImage(new Image(getClass().getResourceAsStream("/images/ghost.png")));
        });
        Thread.sleep(3000);
        if (launch) {
            Runtime.getRuntime().exec("cmd /c \"" + this.appFolder + "\\Elite Dangerous Odyssey Materials Helper.exe\"");
            //close this launcher

        }
        System.exit(0);
    }


    public void unzipFile(final File updateFile, final File destDir) throws IOException {

        final byte[] buffer = new byte[1024];
        try (final ZipInputStream zis = new ZipInputStream(new FileInputStream(updateFile))) {
            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                final File newFile = newFile(destDir, zipEntry);
                if (zipEntry.isDirectory()) {
                    if (!newFile.isDirectory() && !newFile.mkdirs()) {
                        throw new IOException("Failed to create directory " + newFile);
                    }
                } else {
                    // fix for Windows-created archives
                    final File parent = newFile.getParentFile();
                    if (!parent.isDirectory() && !parent.mkdirs()) {
                        throw new IOException("Failed to create directory " + parent);
                    }

                    // write file content
                    final FileOutputStream fos = new FileOutputStream(newFile);
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                    fos.close();
                }
                zipEntry = zis.getNextEntry();
            }
            zis.closeEntry();
        }

    }

    public static File newFile(final File destinationDir, final ZipEntry zipEntry) throws IOException {
        final File destFile = new File(destinationDir, zipEntry.getName());

        final String destDirPath = destinationDir.getCanonicalPath();
        final String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
    }

    private String getLatestVersion() {
        final JsonNode response;
        try {
            response = getLatest();
            return response.get("tag_name").asText();
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return "ERROR";
    }

    private String getCurrentVersion(final File appFolder) {
        try {
            final File config = new File(appFolder.getAbsolutePath() + "\\app\\Elite Dangerous Odyssey Materials Helper.cfg");
            try (final FileInputStream input = new FileInputStream(config)) {
                final Ini ini = new Ini(input);
                final IniPreferences prefs = new IniPreferences(ini);
                return prefs.node("Application").get("app.version", "");
            }
        } catch (final IOException ex) {
            return "0";
        }

    }

    private String getLatestUpdateUrl() throws IOException {
        final JsonNode response = getLatest();
        try {
            final Iterator<JsonNode> assets = response.get("assets").elements();
            final Iterable<JsonNode> iterable = () -> assets;
            return StreamSupport.stream(iterable.spliterator(), false)
                    .map(node -> node.get("browser_download_url").asText())
                    .filter(url -> url.endsWith("portable.zip"))
                    .findFirst()
                    .orElse("ERROR");
        } catch (final NullPointerException ex) {
            return "ERROR";
        }
    }

    private JsonNode getLatest() throws IOException {
        if (this.response == null) {
            final URL url = new URL("https://api.github.com/repos/jixxed/ed-odyssey-materials-helper/releases/latest");
            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("accept", "application/json");
            final InputStream responseStream = connection.getInputStream();
            final ObjectMapper objectMapper = new ObjectMapper();
            this.response = objectMapper.readTree(responseStream);
        }
        return this.response;
    }
}
