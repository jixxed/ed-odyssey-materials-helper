package nl.jixxed.eliteodysseymaterials.templates.dialog;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import nl.jixxed.eliteodysseymaterials.builder.*;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.enums.FontSize;
import nl.jixxed.eliteodysseymaterials.enums.ImportType;
import nl.jixxed.eliteodysseymaterials.helper.AnchorPaneHelper;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;
import nl.jixxed.eliteodysseymaterials.templates.dialog.importdialog.*;

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
        this.styleProperty().set("-fx-font-size: " + FontSize.valueOf(PreferencesService.getPreference(PreferenceConstants.TEXTSIZE, "NORMAL")).getSize() + "px");
        //labels
        final DestroyableLabel explain = LabelBuilder.builder()
                .withStyleClass("explain-text")
                .withText("dialog.import.text", LocaleService.LocalizationKey.of(type.getLocalizationKey()))
                .build();

        var content = switch (type) {
            case HORIZONSWISHLIST -> new HorizonsWishlist(json);
            case WISHLIST -> new OdysseyWishlist(json);
            case LOADOUT -> new OdysseyLoadout(json);
            case SHIP -> new HorizonsShip(json);
            case EDSY -> new EdsyHorizonsWishlist(json);
            case CORIOLIS -> new CoriolisHorizonsWishlist(json);
            case PINCONFIG -> new HorizonsPinConfig(json);
            case CAPI -> null;//skipped earlier
            case UNKNOWN -> null;//skipped earlier
        };
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
