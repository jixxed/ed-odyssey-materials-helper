package nl.jixxed.eliteodysseymaterials.templates.horizons.colonisation;

import javafx.scene.control.MenuButton;
import nl.jixxed.eliteodysseymaterials.enums.ColonisationBuildable;
import nl.jixxed.eliteodysseymaterials.enums.ColonisationCategory;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.util.function.Consumer;

public class ColonisationButton extends MenuButton {

    public ColonisationButton(final ColonisationCategory colonisationCategory, Consumer<ColonisationBuildable> action) {
        super();
        this.textProperty().bind(LocaleService.getStringBinding(colonisationCategory.getLocalizationKey()));
    }
}
