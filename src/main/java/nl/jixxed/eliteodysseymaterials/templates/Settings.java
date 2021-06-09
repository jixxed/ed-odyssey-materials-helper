package nl.jixxed.eliteodysseymaterials.templates;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import nl.jixxed.eliteodysseymaterials.enums.Engineer;
import nl.jixxed.eliteodysseymaterials.enums.EngineerState;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Settings extends VBox {

    @FXML
    CheckBox hideIrrelevant;
    @FXML
    CheckBox hideUnlocked;

    @FXML
    Label version;
    @FXML
    Hyperlink link;
    @FXML
    Hyperlink bugs;
    @FXML
    Hyperlink donate;
    ImageView donateImage = new ImageView();
    private final static Map<Engineer, EngineerState> ENGINEER_STATES = new HashMap<>();

    static {
        ENGINEER_STATES.put(Engineer.DOMINO_GREEN, EngineerState.UNKNOWN);
        ENGINEER_STATES.put(Engineer.HERO_FERRARI, EngineerState.UNKNOWN);
        ENGINEER_STATES.put(Engineer.JUDE_NAVARRO, EngineerState.UNKNOWN);
        ENGINEER_STATES.put(Engineer.KIT_FOWLER, EngineerState.UNKNOWN);
        ENGINEER_STATES.put(Engineer.ODEN_GEIGER, EngineerState.UNKNOWN);
        ENGINEER_STATES.put(Engineer.TERRA_VELASQUEZ, EngineerState.UNKNOWN);
        ENGINEER_STATES.put(Engineer.UMA_LASZLO, EngineerState.UNKNOWN);
        ENGINEER_STATES.put(Engineer.WELLINGTON_BECK, EngineerState.UNKNOWN);
        ENGINEER_STATES.put(Engineer.YARDEN_BOND, EngineerState.UNKNOWN);
    }


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

    public CheckBox getHideIrrelevant() {
        return this.hideIrrelevant;
    }

    public CheckBox getHideUnlocked() {
        return this.hideUnlocked;
    }

    public boolean hideIrrelevant() {
        return this.hideIrrelevant.selectedProperty().get();
    }

    public boolean hideUnlocked() {
        return this.hideUnlocked.selectedProperty().get();
    }

    public static boolean isEngineerKnown(final Engineer engineer) {
        final EngineerState engineerState = ENGINEER_STATES.get(engineer);
        return EngineerState.KNOWN.equals(engineerState) || isEngineerUnlocked(engineer);

    }

    public static boolean isEngineerUnlocked(final Engineer engineer) {
        final EngineerState engineerState = ENGINEER_STATES.get(engineer);
        return EngineerState.INVITED.equals(engineerState) || EngineerState.UNLOCKED.equals(engineerState);
    }

    public static void setEngineerState(final Engineer engineer, final EngineerState engineerState) {
        ENGINEER_STATES.put(engineer, engineerState);
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
