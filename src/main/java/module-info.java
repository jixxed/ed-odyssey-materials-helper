module nl.jixxed.eliteodysseymaterials {
    requires jdk.crypto.ec;
    requires javafx.fxml;
    requires javafx.controls;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    opens nl.jixxed.eliteodysseymaterials to javafx.graphics;
    opens nl.jixxed.eliteodysseymaterials.templates to javafx.fxml;
}