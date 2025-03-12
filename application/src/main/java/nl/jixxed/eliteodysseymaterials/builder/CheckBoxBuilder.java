package nl.jixxed.eliteodysseymaterials.builder;

import javafx.beans.value.ChangeListener;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableCheckBox;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CheckBoxBuilder extends AbstractButtonBaseBuilder<CheckBoxBuilder> {
    private ChangeListener<Boolean> changeListener;
    private boolean selected = false;

    public static CheckBoxBuilder builder() {
        return new CheckBoxBuilder();
    }


    public CheckBoxBuilder withSelected(final boolean selected) {
        this.selected = selected;
        return this;
    }

    public CheckBoxBuilder withSelectedProperty(final ChangeListener<Boolean> changeListener) {
        this.changeListener = changeListener;
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public DestroyableCheckBox build() {
        DestroyableCheckBox checkBox = new DestroyableCheckBox();
        super.build(checkBox);

        if (this.changeListener != null) {
            checkBox.addChangeListener(checkBox.selectedProperty(), this.changeListener);
        }

        checkBox.setSelected(this.selected);
        return checkBox;
    }
}
