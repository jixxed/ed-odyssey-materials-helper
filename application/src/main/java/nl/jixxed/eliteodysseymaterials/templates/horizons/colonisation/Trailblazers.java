package nl.jixxed.eliteodysseymaterials.templates.horizons.colonisation;

import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.service.TrailblazerService;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;
import nl.jixxed.eliteodysseymaterials.templates.generic.CopyableLocation;

import java.util.List;

public class Trailblazers extends DestroyableVBox implements DestroyableTemplate {

    public Trailblazers() {
        initComponents();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("trailblazers");
        final DestroyableLabel title = LabelBuilder.builder()
                .withStyleClass("title")
                .withText("tab.colonisation.trailblazers.title")
                .build();
        this.getNodes().add(title);
        final List<CopyableLocation> locations = TrailblazerService.getAllTrailblazers().stream()
                .map(trailblazer ->
                        new CopyableLocation(
                                trailblazer.getStarSystem(),
                                trailblazer.getName(),
                                trailblazer.getDistanceFromStar(),
                                trailblazer.getDistanceFromStarVariance()
                        ))
                .toList();
        this.getNodes().addAll(locations);
    }
}
