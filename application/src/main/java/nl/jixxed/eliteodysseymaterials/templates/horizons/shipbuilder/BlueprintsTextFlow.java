package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import javafx.beans.binding.StringBinding;
import javafx.css.PseudoClass;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ships.ExternalModule;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipModule;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableFlowPane;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BlueprintsTextFlow extends DestroyableFlowPane implements DestroyableTemplate {
    List<DestroyableLabel> blueprints = new ArrayList<>();
    DestroyableLabel effects;
    DestroyableLabel separator;

    public BlueprintsTextFlow() {
        initComponents();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("blueprints-flow");
    }

    public void update(final ShipModule shipModule, final ShipModule oldShipModule) {
        this.getNodes().clear();
        setBlueprints(LocaleService.getStringBinding(() -> Optional.ofNullable(shipModule).map(mod ->
                mod.getModifications().stream()
                        .map(modification -> LocaleService.getLocalizedStringForCurrentLocale(modification.getModification().getLocalizationKey(true)))
                        .collect(Collectors.joining(", "))
        ).orElse("")));
        setEffects(LocaleService.getStringBinding(() -> Optional.ofNullable(shipModule).map(mod ->
                mod.getExperimentalEffects().stream()
                        .map(effect -> LocaleService.getLocalizedStringForCurrentLocale(effect.getLocalizationKey(true)))
                        .collect(Collectors.joining(", "))
        ).orElse("")));
        boolean hasEffects = Optional.ofNullable(shipModule).map(mod -> !mod.getExperimentalEffects().isEmpty()).orElse(false);
        boolean hasBlueprints = Optional.ofNullable(shipModule).map(mod -> !mod.getModifications().isEmpty()).orElse(false);
        separator.setVisible(hasEffects && hasBlueprints);
        separator.setManaged(hasEffects && hasBlueprints);
        updateStyle(shipModule, oldShipModule);
    }

    void updateStyle(final ShipModule shipModule, final ShipModule oldShipModule) {
        if (shipModule != null) {
            final boolean sameModule = shipModule.isSameId(oldShipModule);
            final boolean sameModifications = shipModule.isSameModifications(oldShipModule);
            blueprints.forEach(blueprint -> blueprint.pseudoClassStateChanged(PseudoClass.getPseudoClass("same"), sameModule && sameModifications));
            separator.pseudoClassStateChanged(PseudoClass.getPseudoClass("same"), sameModule && sameModifications);
            effects.pseudoClassStateChanged(PseudoClass.getPseudoClass("same"), sameModule && sameModifications && shipModule.isSameExperimentalEffects(oldShipModule));
            effects.pseudoClassStateChanged(PseudoClass.getPseudoClass("missing-blueprints"), shipModule.getModifications().isEmpty());
        }
    }

    public void setBlueprints(final StringBinding blueprintStringBinding) {
        blueprints.clear();
        String[] words = blueprintStringBinding.getValue().splitWithDelimiters(" ", 0);
        for (String word : words) {
            DestroyableLabel blueprint = LabelBuilder.builder()
                    .withStyleClass("blueprint")
                    .withNonLocalizedText(word)
                    .build();
            blueprints.add(blueprint);
            this.getNodes().add(blueprint);
        }
    }

    public void setEffects(final StringBinding effectsBinding) {
        separator = LabelBuilder.builder()
                .withStyleClass("separator-comma")
                .withNonLocalizedText(", ")
                .withVisibility(false)
                .withManaged(false)
                .build();
        effects = LabelBuilder.builder()
                .withStyleClass("effect")
                .withText(effectsBinding)
                .build();
        this.getNodes().addAll(separator, effects);
    }
}
