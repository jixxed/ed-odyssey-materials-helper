package nl.jixxed.eliteodysseymaterials.templates.horizons.menu;

import javafx.css.PseudoClass;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.domain.Location;
import nl.jixxed.eliteodysseymaterials.enums.GuardianStructure;
import nl.jixxed.eliteodysseymaterials.enums.GuardianStructureLayout;
import nl.jixxed.eliteodysseymaterials.service.GuardianStructureService;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.LocationService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.LocationChangedEvent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableEventTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;
import nl.jixxed.eliteodysseymaterials.templates.generic.CopyableLocation;

import java.util.List;

public class HorizonsNearestGuardianStructure extends DestroyableVBox implements DestroyableEventTemplate {

    private final List<GuardianStructureLayout> guardianStructureLayouts;
    private DestroyableLabel title;
    private CopyableLocation copyableLocation;


    HorizonsNearestGuardianStructure(final List<GuardianStructureLayout> guardianStructureLayouts) {
        this.guardianStructureLayouts = guardianStructureLayouts;
        initComponents();
        initEventHandling();
    }
    HorizonsNearestGuardianStructure(final GuardianStructureLayout guardianStructureLayout) {
        this.guardianStructureLayouts = List.of(guardianStructureLayout);
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("nearest-guardian-structure");
        this.title = LabelBuilder.builder()
                .withStyleClass("title")
                .withNonLocalizedText("")
                .build();
        this.getNodes().add(this.title);
        update();
    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, LocationChangedEvent.class, _ -> update()));
    }

    private void update() {
        try {
            final Location currentLocation = LocationService.getCurrentLocation();
            final GuardianStructure closestGuardianStructure = GuardianStructureService.findClosest(currentLocation.getStarSystem(), this.guardianStructureLayouts);
            if(GuardianStructureLayout.BEACON.equals(closestGuardianStructure.getLayout())) {
                this.title.addBinding(this.title.textProperty(), LocaleService.getStringBinding("guardian.structure.title.beacon"));
            }else {
                this.title.addBinding(this.title.textProperty(), LocaleService.getStringBinding("guardian.structure.title", LocaleService.LocalizationKey.of(closestGuardianStructure.getLayout().getType().getLocalizationKey()), LocaleService.LocalizationKey.of(closestGuardianStructure.getLayout().getLocalizationKey())));
            }
            //replace system if null or changed
            if (copyableLocation == null || !closestGuardianStructure.getStarSystem().equals(copyableLocation.getStarSystem())) {
                this.getNodes().remove(copyableLocation);
                this.copyableLocation = new CopyableLocation(closestGuardianStructure.getStarSystem(), closestGuardianStructure.getBody());
                this.getNodes().add(copyableLocation);
            }
            this.pseudoClassStateChanged(PseudoClass.getPseudoClass("hidden"), false);

        } catch (final IllegalArgumentException ex) {
            this.pseudoClassStateChanged(PseudoClass.getPseudoClass("hidden"), true);
        }
    }

}
