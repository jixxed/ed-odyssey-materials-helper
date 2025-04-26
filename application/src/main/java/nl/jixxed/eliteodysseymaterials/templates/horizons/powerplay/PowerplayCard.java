package nl.jixxed.eliteodysseymaterials.templates.horizons.powerplay;

import javafx.geometry.Orientation;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ships.ModuleSize;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.Power;
import nl.jixxed.eliteodysseymaterials.service.ImageService;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.PowerplayEvent;
import nl.jixxed.eliteodysseymaterials.service.event.PowerplayLeaveEvent;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;
import nl.jixxed.eliteodysseymaterials.templates.generic.CopyableLocation;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PowerplayCard extends DestroyableVBox implements DestroyableEventTemplate {
    @Getter
    private final Power power;


    protected DestroyableResizableImageView image;

    protected DestroyableLabel name;
    protected DestroyableLabel rankLabel;
    protected DestroyableLabel meritsLabel;
    private DestroyableLabel powerLocation;
    private DestroyableLabel powerDistance;
    protected DestroyableFlowPane location;

    private DestroyableLabel unlockablesTitle;
    private List<PowerplayUnlockableModule> unlockablesLabels;
    private DestroyableSeparator unlockablesSeparator;
    private Long merits;
    private Long rank;
    private DestroyableHBox rankAndMeritsBox;

    public PowerplayCard(Power power) {
        this.power = power;
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("powerplay-card");
        this.image = getPowerImage();
        this.name = getPowerName();
        DestroyableVBox textBox = BoxBuilder.builder()
                .withStyleClass("text-box")
                .withNodes(this.name)
                .buildVBox();
        this.getNodes().addAll(
                this.image,
                textBox
        );
        if (!Power.ALL.equals(this.power)) {
            this.location = new CopyableLocation(power.getStarSystem());
            rankLabel = LabelBuilder.builder()
                    .withStyleClass("rank")
                    .withText("tab.powerplay.rank", ApplicationState.getInstance().getPowerRank())
                    .build();
            meritsLabel = LabelBuilder.builder()
                    .withStyleClass("merits")
                    .withText("tab.powerplay.merits", ApplicationState.getInstance().getPowerMerits(), Power.getMeritsRequiredForRank(ApplicationState.getInstance().getPowerRank() + 1))
                    .build();
            rankAndMeritsBox = BoxBuilder.builder()
                    .withNodes(rankLabel, new GrowingRegion(), meritsLabel)
                    .buildHBox();
            textBox.getNodes().addAll(this.location, rankAndMeritsBox);
        }
        final List<PowerplayPerk> powerplayPerks = this.power.getPerks().entrySet().stream()
                .map(entry -> new PowerplayPerk(this.power, entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparing(powerplayPerk -> LocaleService.getLocalizedStringForCurrentLocale(powerplayPerk.getPerk().getLocalizationTitleKey())))
                .toList();
        textBox.getNodes().addAll(powerplayPerks);
        if (!Power.ALL.equals(this.power)) {
            this.unlockablesTitle = getUnlockablesTitle();
            this.unlockablesLabels = getUnlockables();
            this.unlockablesSeparator = new DestroyableSeparator(Orientation.HORIZONTAL);
            textBox.getNodes().addAll(
                    this.unlockablesSeparator,
                    new GrowingRegion(),
                    this.unlockablesTitle
            );
            textBox.getNodes().addAll(this.unlockablesLabels);
        }
        VBox.setVgrow(textBox, Priority.ALWAYS);
        this.update(ApplicationState.getInstance().getPower(), Optional.of(ApplicationState.getInstance().getPowerMerits()), Optional.of(ApplicationState.getInstance().getPowerRank()));
    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, PowerplayEvent.class, powerplayEvent ->
                this.update(powerplayEvent.getPower(), powerplayEvent.getMerits(), powerplayEvent.getRank())));
        register(EventService.addListener(true, this, PowerplayLeaveEvent.class, powerplayLeaveEvent ->
                this.update(powerplayLeaveEvent.getPower(), Optional.empty(), Optional.empty())));

    }

    private void update(Power power, Optional<Long> meritsOpt, Optional<Long> rankOpt) {
        meritsOpt.ifPresent(merits -> this.merits = merits);
        rankOpt.ifPresent(rank -> this.rank = rank);

        final boolean isThisPower = this.power.equals(power);
        if (isThisPower) {
            rankLabel.addBinding(rankLabel.textProperty(), LocaleService.getStringBinding("tab.powerplay.rank", this.rank));
            meritsLabel.addBinding(meritsLabel.textProperty(), LocaleService.getStringBinding("tab.powerplay.merits", this.merits, Power.getMeritsRequiredForRank(this.rank + 1)));
        }
        if (rankAndMeritsBox != null) {
            rankAndMeritsBox.setVisible(isThisPower);
            rankAndMeritsBox.setManaged(isThisPower);

        }
    }


    protected List<PowerplayUnlockableModule> getUnlockables() {
        return this.power.getUnlockables().entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .filter(Predicate.not(entry ->
                        entry.getKey().getName().equals(HorizonsBlueprintName.PRISMATIC_SHIELD_GENERATOR)
                                && entry.getKey().getModuleSize().isLower(ModuleSize.SIZE_8)))
                .map(unlockable -> new PowerplayUnlockableModule(this.power, unlockable.getValue(), unlockable.getKey()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private DestroyableLabel getUnlockablesTitle() {
        return LabelBuilder.builder()
                .withStyleClass("category")
                .withText("tab.powerplay.unlockables")
                .build();
    }

    private DestroyableLabel getPowerName() {
        return LabelBuilder.builder()
                .withStyleClass("name")
                .withText(this.power.getLocalizationKey())
                .build();
    }

    private DestroyableResizableImageView getPowerImage() {
        return ResizableImageViewBuilder.builder()
                .withStyleClass("photo")
                .withPreserveRatio(true)
                .withImage(ImageService.getImage("/images/power/" + power.name().toLowerCase() + ".png"))
                .build();

    }
}
