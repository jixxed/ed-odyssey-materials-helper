package nl.jixxed.eliteodysseymaterials.templates;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Settings extends VBox {


    @FXML
    Label version;
    @FXML
    Hyperlink link;
    @FXML
    Hyperlink bugs;
    @FXML
    Hyperlink donate;
    ImageView donateImage = new ImageView();

    public Settings(final Application application) {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Settings.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (final IOException exception) {
            throw new RuntimeException(exception);
        }

        final String buildVersion = getBuildVersion();
        String latestVersion = "";
        try {
            latestVersion = getLatestVersion();
        } catch (final IOException e) {
            e.printStackTrace();
        }
        if (getBuildVersion() == null) {
            this.version.setText("Version: dev");
            this.link.setText("Download");
            this.link.setOnAction((actionEvent) ->
                    application.getHostServices().showDocument("https://github.com/jixxed/ed-odyssey-materials-helper/releases"));
        } else if (getBuildVersion().equals(latestVersion)) {
            this.version.setText("Version: " + latestVersion);
            this.link.setVisible(false);
        } else {
            this.version.setText("Version: " + buildVersion + " (" + latestVersion + " available!)");

            this.link.setText("Download");
            this.link.setOnAction((actionEvent) ->
                    application.getHostServices().showDocument("https://github.com/jixxed/ed-odyssey-materials-helper/releases"));

        }
        this.donateImage.setImage(new Image(getClass().getResourceAsStream("/images/donate.png")));
        this.donate.setGraphic(this.donateImage);
        this.donate.setOnAction((actionEvent) ->
                application.getHostServices().showDocument("https://www.paypal.com/donate?business=4LB2HUSB7NDAS&item_name=Odyssey+Materials+Helper"));

        this.bugs.setText("Report a bug");
        this.bugs.setOnAction((actionEvent) ->
                application.getHostServices().showDocument("https://github.com/jixxed/ed-odyssey-materials-helper/issues"));


    }


    private String getLatestVersion() throws IOException {
        final URL url = new URL("https://api.github.com/repos/jixxed/ed-odyssey-materials-helper/releases/latest");
        final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("accept", "application/json");
        final InputStream responseStream = connection.getInputStream();
        final ObjectMapper objectMapper = new ObjectMapper();
        final JsonNode response = objectMapper.readTree(responseStream);
        return response.get("tag_name").asText();
    }

    public static String getBuildVersion() {
        return System.getProperty("app.version");
    }
}
