package nl.jixxed.eliteodysseymaterials;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import nl.jixxed.eliteodysseymaterials.enums.Component;
import nl.jixxed.eliteodysseymaterials.enums.Data;
import nl.jixxed.eliteodysseymaterials.enums.Goods;

import java.io.IOException;

public class MaterialCard extends HBox {
    @FXML
    private Label name;
    @FXML
    private Label amount;
    public MaterialCard(String name, String amount) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Material.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);


        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.name.setText(name);
        this.amount.setText(amount);
    }

    public MaterialCard(Goods key, String name, String amount) {
        this(name, amount);
        this.name.setTooltip(new Tooltip(name + "\n" + "Used in recipes:\n" + RecipeConstants.findRecipesContaining(key)));
    }

    public MaterialCard(Data key, String name, String amount) {
        this(name, amount);
        this.name.setTooltip(new Tooltip(name + "\n" + "Used in recipes:\n" + RecipeConstants.findRecipesContaining(key)));
    }

    public MaterialCard(Component key, String name, String amount) {
        this(name, amount);
        this.name.setTooltip(new Tooltip(name + "\n" + "Used in recipes:\n" + RecipeConstants.findRecipesContaining(key)));
    }
//
//    public MaterialCard(Component component, String amount) {
//    }
//
//    public void setName(String name) {
//        this.name.setText(name);
//    }
//    public void setAmount(String amount){
//        this.amount.setText(amount);
//    }
//    public String getAmount(){
//       return this.amount.getText();
//    }

    public Label getName() {
        return name;
    }
}
