module nl.jixxed.eliteodysseymaterials {
    requires javafx.fxml;
    requires javafx.controls;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    opens nl.jixxed.eliteodysseymaterials to javafx.graphics, javafx.fxml;
}