package nl.jixxed.eliteodysseymaterials.helper;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AnchorPaneHelper {

    public static void setAnchor(final Node child, final Double topValue, final Double bottomValue, final Double leftValue, final Double rightValue) {
        AnchorPane.setTopAnchor(child, topValue);
        AnchorPane.setBottomAnchor(child, bottomValue);
        AnchorPane.setLeftAnchor(child, leftValue);
        AnchorPane.setRightAnchor(child, rightValue);
    }
}
