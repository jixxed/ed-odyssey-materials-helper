module nl.jixxed.eliteodysseymaterials {
    requires jdk.crypto.ec;
    requires javafx.fxml;
    requires transitive javafx.controls;
    requires transitive javafx.media;
    requires javafx.swing;
    requires org.controlsfx.controls;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires io.reactivex.rxjava3;
    requires java.net.http;
    requires org.jfxtras.styles.jmetro;
    requires org.slf4j;
    requires com.google.common;
    requires java.naming;
    requires jdk.naming.dns;
    requires static lombok;
    requires org.apache.commons.csv;
    requires scribejava.core;
    requires org.openpnp;
    requires net.java.dev.jna;
    requires com.sun.jna.platform;

//    requires com.asprise;
    requires net.sourceforge.tess4j;
    requires net.sourceforge.lept4j;

    requires java.desktop;
    opens nl.jixxed.eliteodysseymaterials to javafx.graphics;
    opens nl.jixxed.eliteodysseymaterials.service.message to com.fasterxml.jackson.databind;
    opens nl.jixxed.eliteodysseymaterials.trade.message.outbound to com.fasterxml.jackson.databind;
    opens nl.jixxed.eliteodysseymaterials.trade.message.outbound.payload to com.fasterxml.jackson.databind;
    opens nl.jixxed.eliteodysseymaterials.trade.message.common to com.fasterxml.jackson.databind;
    opens nl.jixxed.eliteodysseymaterials.trade.message.inbound to com.fasterxml.jackson.databind;
    opens nl.jixxed.eliteodysseymaterials.templates to javafx.fxml, org.controlsfx.controls;
    opens nl.jixxed.eliteodysseymaterials.templates.destroyables to javafx.fxml, org.controlsfx.controls;
    exports nl.jixxed.eliteodysseymaterials;
    exports nl.jixxed.eliteodysseymaterials.enums;
    exports nl.jixxed.eliteodysseymaterials.domain;
    exports nl.jixxed.eliteodysseymaterials.templates;
    exports nl.jixxed.eliteodysseymaterials.service;
    opens nl.jixxed.eliteodysseymaterials.domain to com.fasterxml.jackson.databind;
    opens nl.jixxed.eliteodysseymaterials.enums to com.fasterxml.jackson.databind;
    opens nl.jixxed.eliteodysseymaterials.templates.segmentbar to javafx.fxml, org.controlsfx.controls;
    exports nl.jixxed.eliteodysseymaterials.service.ar;
}