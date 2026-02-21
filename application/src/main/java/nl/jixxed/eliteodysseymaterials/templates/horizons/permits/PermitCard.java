package nl.jixxed.eliteodysseymaterials.templates.horizons.permits;

import javafx.css.PseudoClass;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.EdAwesomeIconViewPaneBuilder;
import nl.jixxed.eliteodysseymaterials.builder.FlowPaneBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.enums.Permit;
import nl.jixxed.eliteodysseymaterials.enums.PermitType;
import nl.jixxed.eliteodysseymaterials.service.PermitService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.PermitEvent;
import nl.jixxed.eliteodysseymaterials.templates.components.EdAwesomeIconViewPane;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.components.edfont.EdAwesomeIcon;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableHBox;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;
import nl.jixxed.eliteodysseymaterials.templates.generic.CopyableLocation;

import java.util.Arrays;
import java.util.List;

@Getter
public class PermitCard extends DestroyableVBox implements DestroyableTemplate {
    private final Permit permit;
    private EdAwesomeIconViewPane icon;
    private List<CopyableLocation> locations;

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
                .withText(permit.getLocalizationKey())
                .build();
        //copyable location
        locations = Arrays.stream(permit.getSystems()).map(CopyableLocation::new).toList();

        //allegiance
//        var allegiance = LabelBuilder.builder()
//                .withStyleClass("permit-allegiance")
//                .withText(permit.getAllegiance().getLocalizationKey())
//                .build();
        var description = LabelBuilder.builder()
                .withStyleClass("permit-description")
                .withText(permit.getDescriptionLocalizationKey())
                .build();
        var labels = BoxBuilder.builder()
                .withStyleClass("permit-labels")
                .withNode(title)
                .withNodes(description)
//                .withNode(allegiance)
                .buildVBox();
        DestroyableHBox content = BoxBuilder.builder()
                .withStyleClass("permit-content")
                .withNodes(icon, labels)
                .buildHBox();

        this.getNodes().addAll(content, new GrowingRegion(), FlowPaneBuilder.builder().withStyleClass("locations").withNodes(locations).build());
        if(!PermitType.FREE.equals(permit.getType())) {
            icon.addEventBinding(icon.onMouseClickedProperty(), _ -> togglePermit());
        }

        if(permit.getPermitLocation() != null) {
            var locationTitle = LabelBuilder.builder()
                    .withStyleClass("permit-location-title")
                    .withText("tab.permit.permitlocation")
                    .build();
            CopyableLocation permitLocation = new CopyableLocation(permit.getPermitLocation().getStarSystem(), permit.getPermitLocation().getStation());
            permitLocation.getStyleClass().add("permit-location");
            this.getNodes().addAll(locationTitle, permitLocation);
        }
    }

    public void togglePermit() {
        PermitService.togglePermit(permit);
        this.pseudoClassStateChanged(PseudoClass.getPseudoClass("obtained"), PermitService.havePermit(permit));
        EventService.publish(new PermitEvent(permit));
    }
}
