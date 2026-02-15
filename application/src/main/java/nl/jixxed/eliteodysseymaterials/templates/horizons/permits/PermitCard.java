package nl.jixxed.eliteodysseymaterials.templates.horizons.permits;

import javafx.css.PseudoClass;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.EdAwesomeIconViewPaneBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.enums.Permit;
import nl.jixxed.eliteodysseymaterials.service.PermitService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.PermitEvent;
import nl.jixxed.eliteodysseymaterials.templates.components.EdAwesomeIconViewPane;
import nl.jixxed.eliteodysseymaterials.templates.components.edfont.EdAwesomeIcon;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableHBox;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;
import nl.jixxed.eliteodysseymaterials.templates.generic.CopyableLocation;

public class PermitCard extends DestroyableVBox implements DestroyableTemplate {

    private final Permit permit;
    private EdAwesomeIconViewPane icon;
    private CopyableLocation location;

    public PermitCard(Permit permit) {
        this.permit = permit;
        initComponents();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("permit-card");
        //image/icon
        icon = EdAwesomeIconViewPaneBuilder.builder()
                .withStyleClass("permit-image")
                .withIcons(EdAwesomeIcon.OTHER_PERMIT)
                .build();
        this.pseudoClassStateChanged(PseudoClass.getPseudoClass("obtained"), PermitService.havePermit(permit));
        //system name as title
        var title = LabelBuilder.builder()
                .withStyleClass("permit-title")
                .withNonLocalizedText(permit.getSystem().getName())
                .build();
        //copyable location
        location = new CopyableLocation(permit.getSystem());
        //allegiance
        var allegiance = LabelBuilder.builder()
                .withStyleClass("permit-allegiance")
                .withText(permit.getAllegiance().getLocalizationKey())
                .build();
        var labels = BoxBuilder.builder()
                .withStyleClass("permit-labels")
                .withNodes(title, location, allegiance)
                .buildVBox();
        DestroyableHBox content = BoxBuilder.builder()
                .withStyleClass("permit-content")
                .withNodes(icon, labels)
                .buildHBox();
        this.getNodes().add(content);

        icon.addEventBinding(icon.onMouseClickedProperty(), _ -> togglePermit());
    }

    public void togglePermit() {
        PermitService.togglePermit(permit);
        this.pseudoClassStateChanged(PseudoClass.getPseudoClass("obtained"), PermitService.havePermit(permit));
        EventService.publish(new PermitEvent(permit));
    }
}
