package nl.jixxed.eliteodysseymaterials.templates;

import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import jfxtras.styles.jmetro.JMetroStyleClass;

public class BottomBar extends HBox {

    final Label watchedFileLabel = new Label();
    final Label lastTimeStampLabel = new Label();

    public BottomBar() {
        super();
        this.getChildren().addAll(this.watchedFileLabel, new Separator(Orientation.VERTICAL), this.lastTimeStampLabel);
        this.getStyleClass().add("bottom-bar");
        this.getStyleClass().add(JMetroStyleClass.BACKGROUND);
    }

    public void setWatchedFileLabel(final String watchedFileLabel) {
        this.watchedFileLabel.setText(watchedFileLabel);
    }

    public void setLastTimeStampLabel(final String lastTimeStampLabel) {
        this.lastTimeStampLabel.setText(lastTimeStampLabel);
    }
}
