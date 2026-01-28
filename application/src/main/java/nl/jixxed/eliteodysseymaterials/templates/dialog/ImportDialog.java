package nl.jixxed.eliteodysseymaterials.templates.dialog;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import nl.jixxed.eliteodysseymaterials.builder.*;
import nl.jixxed.eliteodysseymaterials.enums.ImportType;
import nl.jixxed.eliteodysseymaterials.helper.AnchorPaneHelper;
import nl.jixxed.eliteodysseymaterials.helper.ScalingHelper;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;
import nl.jixxed.eliteodysseymaterials.templates.dialog.importdialog.CoriolisHorizonsWishlist;
import nl.jixxed.eliteodysseymaterials.templates.dialog.importdialog.EdsyHorizonsWishlist;
import nl.jixxed.eliteodysseymaterials.templates.dialog.importdialog.HorizonsWishlist;
import nl.jixxed.eliteodysseymaterials.templates.dialog.importdialog.OdysseyWishlist;

public class ImportDialog extends DestroyableVBox implements DestroyableTemplate {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
    }

    private final Stage stage;
    private final Runnable importAction;
    private final ImportType type;
    private final String json;

    public ImportDialog(ImportType type, String json, final Stage stage, final Runnable importAction) {
        this.type = type;
        this.json = json;
        this.stage = stage;
        this.importAction = importAction;
        initComponents();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("import-dialog");
        //labels
        final DestroyableLabel explain = LabelBuilder.builder()
                .withStyleClass("explain-text")
                .withText("dialog.import.text", LocaleService.LocalizationKey.of(type.getLocalizationKey()))
                .build();

        var content = switch (type) {
            case HORIZONSWISHLIST -> new HorizonsWishlist(json);
            case WISHLIST -> new OdysseyWishlist(json);
            case LOADOUT -> null;
            case SHIP -> null;
            case EDSY -> new EdsyHorizonsWishlist(json);
            case CORIOLIS -> new CoriolisHorizonsWishlist(json);
            case PINCONFIG -> null;
            case CAPI -> null;//skipped earlier
            case UNKNOWN -> null;//skipped earlier
        };
//        DestroyableVBox destroyableVBox = BoxBuilder.builder().withStyleClass("vertical-box").withNodes(content, new GrowingRegion()).buildVBox();
        DestroyableScrollPane scrollPane = ScrollPaneBuilder.builder()
                .withContent(content)
                .withStyleClass("import-dialog-scrollpane")
                .build();
        //buttons
        final DestroyableHBox buttons = BoxBuilder.builder()
                .withStyleClass("buttons")
                .withNodes(
                        ButtonBuilder.builder()
                                .withText("dialog.import.no")
                                .withOnAction(event -> this.stage.close())
                                .build(),
                        new GrowingRegion(),
                        ButtonBuilder.builder()
                                .withText("dialog.import.yes")
                                .withOnAction(event -> {
                                    importAction.run();
                                    this.stage.close();
                                })
                                .build()
                ).buildHBox();

        DestroyableAnchorPane contents = AnchorPaneBuilder.builder().withNodes(scrollPane).build();
        AnchorPaneHelper.setAnchor(scrollPane, 0.0, 0.0, 0.0, 0.0);
        VBox.setVgrow(contents, Priority.ALWAYS);
        this.getNodes().addAll(explain, contents, buttons);
    }
}
