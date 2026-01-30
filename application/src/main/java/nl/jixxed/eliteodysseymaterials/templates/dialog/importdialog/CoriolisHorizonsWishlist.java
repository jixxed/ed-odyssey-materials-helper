package nl.jixxed.eliteodysseymaterials.templates.dialog.importdialog;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.FlowPaneBuilder;
import nl.jixxed.eliteodysseymaterials.constants.HorizonsBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.*;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.NotificationType;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.NotificationService;
import nl.jixxed.eliteodysseymaterials.service.exception.CoriolisDeeplinkException;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableFlowPane;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;

import java.util.EnumMap;
import java.util.List;
import java.util.Objects;

@Slf4j
public class CoriolisHorizonsWishlist extends DestroyableVBox implements DestroyableTemplate {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
    }

    private String json;

    public CoriolisHorizonsWishlist(String json) {
        this.json = json;
        initComponents();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("wishlist");
        try {
            final CoriolisWishlist coriolisWishlist = OBJECT_MAPPER.readValue(json, CoriolisWishlist.class);
            final List<WishlistBlueprint<HorizonsBlueprintName>> wishlistBlueprintList = coriolisWishlist.getItems().stream().map(coriolisWishlistItem -> {
                try {
                    final HorizonsBlueprintGrade horizonsBlueprintGrade = coriolisWishlistItem.getGrade() != null ? HorizonsBlueprintGrade.valueOf("GRADE_" + coriolisWishlistItem.getGrade().toString()) : null;
                    final HorizonsBlueprint blueprint = (HorizonsBlueprint) HorizonsBlueprintConstants.getRecipeByInternalName(coriolisWishlistItem.getItem(), coriolisWishlistItem.getBlueprint(), horizonsBlueprintGrade);
                    final HorizonsWishlistBlueprint bp;
                    if (blueprint.isPreEngineered()) {
                        return null;
                    }
                    if (blueprint instanceof HorizonsModuleBlueprint horizonsModuleBlueprint) {
                        bp = new HorizonsModuleWishlistBlueprint(horizonsModuleBlueprint.getHorizonsBlueprintType(), createGradeMap(horizonsModuleBlueprint.getHorizonsBlueprintType(), coriolisWishlistItem.getGrade(), coriolisWishlistItem.getHighestGradePercentage()));
                    } else if (blueprint instanceof HorizonsExperimentalEffectBlueprint horizonsExperimentalEffectBlueprint) {
                        bp = new HorizonsExperimentalWishlistBlueprint(horizonsExperimentalEffectBlueprint.getHorizonsBlueprintType());
                    } else {
                        throw new CoriolisDeeplinkException("Failed to parse deeplink", "notification.imported.exception.failed.parse");
                    }
                    bp.setRecipeName((blueprint.getBlueprintName()));
                    bp.setVisible(true);
                    return (WishlistBlueprint<HorizonsBlueprintName>) bp;
                } catch (final IllegalArgumentException ex) {
                    log.error(ex.getMessage());
                    NotificationService.showWarning(NotificationType.IMPORT, LocaleService.LocaleString.of("notification.imported.coriolis.error.title"), LocaleService.LocaleString.of("notification.imported.coriolis.error.text", String.join(":\n", ex.getMessage().split(": "))), true);
                    return null;
                }
            }).filter(Objects::nonNull).toList();
            var list = wishlistBlueprintList.stream().map(Blueprint::new).toList();
            DestroyableFlowPane flowPane = FlowPaneBuilder.builder().withStyleClass("list").withNodes(list).build();
            this.getNodes().add(flowPane);
            HBox.setHgrow(flowPane, Priority.ALWAYS);
        } catch (JsonProcessingException ex) {
            log.error(ex.getMessage());
            throw new CoriolisDeeplinkException("Failed to parse deeplink", "notification.imported.exception.failed.parse");
        }
    }

    private static EnumMap<HorizonsBlueprintGrade, Double> createGradeMap(HorizonsBlueprintType type, Integer grade, final Double fraction) {
        final HorizonsBlueprintGrade topGrade = HorizonsBlueprintGrade.valueOf("GRADE_" + grade--);
        final EnumMap<HorizonsBlueprintGrade, Double> gradeRolls = new EnumMap<>(HorizonsBlueprintGrade.class);
        gradeRolls.put(topGrade, fraction);
        for (int i = grade; i > 0; i--) {
            final HorizonsBlueprintGrade horizonsBlueprintGrade = HorizonsBlueprintGrade.valueOf("GRADE_" + i);
            gradeRolls.put(horizonsBlueprintGrade, 1D);
        }
        return gradeRolls;
    }
}
