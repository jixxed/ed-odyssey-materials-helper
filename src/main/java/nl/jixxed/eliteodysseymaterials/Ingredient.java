package nl.jixxed.eliteodysseymaterials;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

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
        this.amountAvailable.setText(amountAvailable);
        this.amountInt = Integer.valueOf(amount);
        this.amountAvailableInt =Integer.valueOf(amountAvailable);

        update();
    }
//    public void setName(String name) {
//        this.name.setText(name);
//    }
//    public void setAmount(String amount){
//        this.amount.setText(amount);
//    }
    public void setAmountAvailable(String amountAvailable){
        this.amountAvailableInt = Integer.valueOf(amountAvailable);
    }

    public Type getType() {
        return type;
    }

    public String getCode() {
        return code;
    }

    public void update() {
        this.amountAvailable.setText(this.amountAvailableInt.toString());
        if (this.amountAvailableInt >= this.amountInt) {
            this.setStyle("-fx-border-color: black; -fx-background-color: #89d07f;");
        } else {
            this.setStyle("-fx-border-color: black; -fx-background-color: #ab3f3f;");
        }
    }

    public String getName(){
        return this.name.getText();
    }
}
