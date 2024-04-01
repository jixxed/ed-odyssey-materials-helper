module nl.jixxed.bootstrap {
    requires javafx.fxml;
    requires javafx.controls;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires org.apache.commons.io;
    requires org.slf4j;
    requires java.prefs;
    requires ch.qos.logback.classic;
    requires ch.qos.logback.core;
    requires java.naming;
    requires maven.artifact;
    requires com.sun.jna;
    requires com.sun.jna.platform;
    requires static lombok;
    opens nl.jixxed.bootstrap to javafx.graphics;
    exports nl.jixxed.bootstrap.log to ch.qos.logback.core;
}