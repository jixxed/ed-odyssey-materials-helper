package nl.jixxed.eliteodysseymaterials;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import nl.jixxed.eliteodysseymaterials.enums.Engineer;
import nl.jixxed.eliteodysseymaterials.enums.EngineerState;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Settings extends VBox {

    @FXML
    CheckBox checkBoxIrrelevant;
    @FXML
    CheckBox checkBoxUnlock;

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


    public Settings() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Settings.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);


        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public CheckBox getCheckBoxIrrelevant() {
        return checkBoxIrrelevant;
    }

    public CheckBox getCheckBoxUnlock() {
        return checkBoxUnlock;
    }

    public static boolean isEngineerKnown(Engineer engineer) {
        final EngineerState engineerState = ENGINEER_STATES.get(engineer);
        return EngineerState.KNOWN.equals(engineerState) || EngineerState.INVITED.equals(engineerState) || EngineerState.UNLOCKED.equals(engineerState);

    }

    public static boolean isEngineerUnlocked(Engineer engineer) {
        return EngineerState.UNLOCKED.equals(ENGINEER_STATES.get(engineer));
    }

    public static void setEngineerState(Engineer engineer, EngineerState engineerState) {
        ENGINEER_STATES.put(engineer, engineerState);
    }
}
