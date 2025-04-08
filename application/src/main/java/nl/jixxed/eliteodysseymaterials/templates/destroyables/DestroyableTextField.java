package nl.jixxed.eliteodysseymaterials.templates.destroyables;

import javafx.scene.control.TextField;
import lombok.Getter;

@Getter
public class DestroyableTextField extends TextField implements DestroyableComponent {

    public DestroyableTextField(String s) {
        super(s);
    }

    public DestroyableTextField() {
        super();
    }
}