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
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
public class Settings extends VBox {


    @FXML
    Label version;
    @FXML
    private
    Label disclaimer1;
    @FXML
    private
    Label disclaimer2;
    @FXML
    private
    Label beer;
    @FXML
    private
    Label versionLabel;
    @FXML
    private
    Hyperlink link;
    @FXML
    private
    Hyperlink bugs;
    @FXML
    private
    Hyperlink donate;
    private ImageView donateImage = new ImageView();

    public Settings(final Application application) {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Settings.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (final IOException exception) {
            throw new RuntimeException(exception);
        }
        this.disclaimer1.textProperty().bind(LocaleService.getStringBinding("menu.about.disclaimer.1"));
        this.disclaimer2.textProperty().bind(LocaleService.getStringBinding("menu.about.disclaimer.2"));
        this.beer.textProperty().bind(LocaleService.getStringBinding("menu.about.beer"));
        final String buildVersion = getBuildVersion();
        String latestVersion = "";
        try {
            latestVersion = getLatestVersion();
        } catch (final IOException e) {
            log.error("Error retrieving latest version", e);
        }
        if (getBuildVersion() == null) {
            this.versionLabel.textProperty().bind(LocaleService.getStringBinding("menu.about.version", "dev"));
            this.link.textProperty().bind(LocaleService.getStringBinding("menu.about.download"));
            this.link.setOnAction((actionEvent) ->
                    application.getHostServices().showDocument("https://github.com/jixxed/ed-odyssey-materials-helper/releases"));
        } else if (buildVersion.equals(latestVersion)) {
            this.versionLabel.textProperty().bind(LocaleService.getStringBinding("menu.about.version", buildVersion));
            this.link.setVisible(false);
        } else {
            this.versionLabel.textProperty().bind(LocaleService.getStringBinding("menu.about.version.new", buildVersion, latestVersion));

            this.link.textProperty().bind(LocaleService.getStringBinding("menu.about.download"));
            this.link.setOnAction((actionEvent) ->
                    application.getHostServices().showDocument("https://github.com/jixxed/ed-odyssey-materials-helper/releases"));

        }
        this.donateImage.setImage(new Image(getClass().getResourceAsStream("/images/donate.png")));
        this.donate.setGraphic(this.donateImage);
        this.donate.setOnAction((actionEvent) ->
                application.getHostServices().showDocument("https://www.paypal.com/donate?business=4LB2HUSB7NDAS&item_name=Odyssey+Materials+Helper"));

        this.bugs.textProperty().bind(LocaleService.getStringBinding("menu.about.report"));
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

    private static String getBuildVersion() {
        return System.getProperty("app.version");
    }
}
