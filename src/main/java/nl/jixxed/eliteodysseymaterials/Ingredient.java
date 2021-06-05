package nl.jixxed.eliteodysseymaterials;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import nl.jixxed.eliteodysseymaterials.enums.StorageType;

import java.io.IOException;

public class Ingredient extends HBox {
    private final StorageType storageType;
    private final String code;

    private final Integer amountRequired;
    private Integer amountAvailable;

    @FXML
    private HBox box;
    @FXML
    private Label nameLabel;
    @FXML
    private Label amountRequiredLabel;
    @FXML
    private Label amountAvailableLabel;

    public Ingredient(final StorageType storageType, final String code, final String name, final String amount, final String amountAvailable) {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Ingredient.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);


        try {
            fxmlLoader.load();
        } catch (final IOException exception) {
            throw new RuntimeException(exception);
        }
        this.nameLabel.setText(name);
        this.storageType = storageType;
        this.code = code;
        this.amountRequiredLabel.setText(amount);
        this.amountRequired = Integer.valueOf(amount);
        this.amountAvailable = Integer.valueOf(amountAvailable);

        update();
    }

    public void setAmountAvailable(final Integer amountAvailable) {
        this.amountAvailable = amountAvailable;
    }

    public StorageType getType() {
        return this.storageType;
    }

    public String getCode() {
        return this.code;
    }

    public void update() {
        if (StorageType.OTHER.equals(this.storageType)) {
            this.amountAvailableLabel.setText("");
            this.amountRequiredLabel.setText("");
            this.setStyle("-fx-border-color: black; -fx-background-color: #fff;");
            this.nameLabel.setStyle("-fx-pref-width: 300;-fx-label-padding: 5;");
            this.amountRequiredLabel.setStyle("-fx-pref-width: 0;");
            this.amountAvailableLabel.setStyle("-fx-pref-width: 0;");
        } else if (this.amountAvailable >= this.amountRequired) {
            this.amountAvailableLabel.setText(this.amountAvailable.toString());
            this.setStyle("-fx-border-color: black; -fx-background-color: #89d07f;");
        } else {
            this.amountAvailableLabel.setText(this.amountAvailable.toString());
            this.setStyle("-fx-border-color: black; -fx-background-color: #ff7c7c;");
        }
    }

    public String getName() {
        return this.nameLabel.getText();
    }
}
