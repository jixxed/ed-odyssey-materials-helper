module nl.jixxed.bootstrap {
    requires jdk.crypto.ec;
    requires javafx.fxml;
    requires javafx.controls;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires org.apache.commons.io;
    requires java.prefs;
    opens nl.jixxed.bootstrap to javafx.graphics;
}