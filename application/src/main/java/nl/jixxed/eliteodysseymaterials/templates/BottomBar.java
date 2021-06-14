package nl.jixxed.eliteodysseymaterials.templates;

import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;

public class BottomBar extends HBox {

    final Label watchedFileLabel = new Label();
    final Label lastTimeStampLabel = new Label();

    public BottomBar() {
        super();
        this.getChildren().addAll(this.watchedFileLabel, new Separator(Orientation.VERTICAL), this.lastTimeStampLabel);
        this.getStyleClass().add("bottom-bar");
    }

    public void setWatchedFileLabel(final String watchedFileLabel) {
        this.watchedFileLabel.setText(watchedFileLabel);
    }

    public void setLastTimeStampLabel(final String lastTimeStampLabel) {
        this.lastTimeStampLabel.setText(lastTimeStampLabel);
    }
}
