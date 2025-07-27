module nl.jixxed.bootstrap {
    requires javafx.fxml;
    requires javafx.controls;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires org.apache.commons.io;
    requires java.prefs;
    requires java.naming;
    requires com.sun.jna.platform;
    requires com.sun.jna;
    requires ch.qos.logback.classic;
    requires ch.qos.logback.core;
    requires org.slf4j;
    requires static lombok;
    exports nl.jixxed.bootstrap.log to ch.qos.logback.core;
    opens nl.jixxed.bootstrap to javafx.graphics;
}