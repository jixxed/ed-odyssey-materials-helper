module nl.jixxed.eliteodysseymaterials {
    requires jdk.crypto.ec;
    requires javafx.fxml;
    requires javafx.controls;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires io.reactivex.rxjava3;
    requires org.jfxtras.styles.jmetro;
    requires org.slf4j;
    requires com.google.common;
    requires static lombok;
    opens nl.jixxed.eliteodysseymaterials to javafx.graphics;
    opens nl.jixxed.eliteodysseymaterials.templates to javafx.fxml;
    exports nl.jixxed.eliteodysseymaterials;
    exports nl.jixxed.eliteodysseymaterials.enums;
    exports nl.jixxed.eliteodysseymaterials.domain;
    opens nl.jixxed.eliteodysseymaterials.domain to com.fasterxml.jackson.databind;
}