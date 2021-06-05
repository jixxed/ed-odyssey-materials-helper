package nl.jixxed.eliteodysseymaterials;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import nl.jixxed.eliteodysseymaterials.enums.Type;

import java.io.IOException;

public class Ingredient extends HBox {
    private final Type type;
    private final String code;

    private Integer amountInt;

    private Integer amountAvailableInt;
    @FXML
    private HBox box;
    @FXML
    private Label name;
    @FXML
    private Label amount;
    @FXML
    private Label amountAvailable;
    public Ingredient(Type type, String code, String name, String amount, String amountAvailable) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Ingredient.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);


        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.name.setText(name);
        this.type = type;
        this.code = code;
        this.amount.setText(amount);
        this.amountInt = Integer.valueOf(amount);
        this.amountAvailableInt = Integer.valueOf(amountAvailable);

        update();
    }

    public void setAmountAvailable(Integer amountAvailable) {
        this.amountAvailableInt = amountAvailable;
    }

    public Type getType() {
        return type;
    }

    public String getCode() {
        return code;
    }

    public void update() {
        if (Type.OTHER.equals(type)) {
            this.amountAvailable.setText("");
            this.amount.setText("");
            this.setStyle("-fx-border-color: black; -fx-background-color: #fff;");
            name.setStyle("-fx-pref-width: 300;-fx-label-padding: 5;");
            amount.setStyle("-fx-pref-width: 0;");
            amountAvailable.setStyle("-fx-pref-width: 0;");
        } else if (this.amountAvailableInt >= this.amountInt) {
            this.amountAvailable.setText(this.amountAvailableInt.toString());
            this.setStyle("-fx-border-color: black; -fx-background-color: #89d07f;");
        } else {
            this.amountAvailable.setText(this.amountAvailableInt.toString());
            this.setStyle("-fx-border-color: black; -fx-background-color: #ff7c7c;");
        }
    }

    public String getName(){
        return this.name.getText();
    }
}
