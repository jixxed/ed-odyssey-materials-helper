package nl.jixxed.eliteodysseymaterials.templates;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ButtonBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ScrollPaneBuilder;
import nl.jixxed.eliteodysseymaterials.domain.*;
import nl.jixxed.eliteodysseymaterials.enums.ImportResult;
import nl.jixxed.eliteodysseymaterials.enums.Tabs;
import nl.jixxed.eliteodysseymaterials.helper.WishlistHelper;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.ImportResultEvent;

import java.util.Base64;

@Slf4j
public class ImportTab extends EDOTab {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    public static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private ScrollPane scrollPane;
    private TextArea textAreaWishlist;
    private Button buttonWishlist;
    private Label importLabelWishlist;
    private Label errorLabelWishlist;
    private Label noteWishlist;
    private TextArea textAreaLoadout;
    private Button buttonLoadout;
    private Label importLabelLoadout;
    private Label errorLabelLoadout;
    private Label noteLoadout;

    ImportTab() {
        initComponents();
        initEventHandling();
    }

    private void initEventHandling() {
        EventService.addListener(this, ImportResultEvent.class, importResultEvent -> {
            if (ImportResult.ERROR_WISHLIST.equals(importResultEvent.getResult())) {
                this.errorLabelWishlist.setVisible(true);
            }
            if (ImportResult.SUCCESS_WISHLIST.equals(importResultEvent.getResult())) {
                this.textAreaWishlist.setText("");
            }
            if (ImportResult.ERROR_LOADOUT.equals(importResultEvent.getResult())) {
                this.errorLabelWishlist.setVisible(true);
            }
            if (ImportResult.SUCCESS_LOADOUT.equals(importResultEvent.getResult())) {
                this.textAreaWishlist.setText("");
            }
        });
    }

    private void initComponents() {
        this.importLabelWishlist = LabelBuilder.builder()
                .withStyleClass("import-header")
                .withText(LocaleService.getStringBinding("tab.import.header.wishlist"))
                .build();
        this.errorLabelWishlist = LabelBuilder.builder()
                .withStyleClass("import-error")
                .withText(LocaleService.getStringBinding("tab.import.status.invalid.wishlist"))
                .withVisibility(false)
                .build();
        this.noteWishlist = LabelBuilder.builder()
                .withText(LocaleService.getStringBinding("tab.import.text.wishlist", LocaleService.LocalizationKey.of("tab.wishlist.copy")))
                .build();
        this.textProperty().bind(LocaleService.getStringBinding("tabs.import"));
        this.textAreaWishlist = new TextArea();
        this.textAreaWishlist.getStyleClass().add("import-input");
        this.textAreaWishlist.setWrapText(true);
        this.buttonWishlist = ButtonBuilder.builder().withStyleClass("import-button").withText(LocaleService.getStringBinding("tab.import.button.import.wishlist")).withOnAction(event -> {

            this.errorLabelWishlist.setVisible(false);
            try {
                if (this.textAreaWishlist.getText().isEmpty()) {
                    throw new IllegalArgumentException("import string is empty");
                }
                final String decoded = new String(Base64.getDecoder().decode(this.textAreaWishlist.getText().trim()));

                if (decoded.isEmpty()) {
                    throw new IllegalArgumentException("import string is empty");
                }
                final Wishlist wishlist = new Wishlist();
                wishlist.setName("Imported wishlist");
                wishlist.setItems(WishlistHelper.convertWishlist(decoded));

                final Commander commander = APPLICATION_STATE.getPreferredCommander().orElseThrow(IllegalArgumentException::new);
                final Wishlists wishlists = APPLICATION_STATE.getWishlists(commander.getFid());
                wishlists.addWishlist(wishlist);
                wishlists.setSelectedWishlistUUID(wishlist.getUuid());
                APPLICATION_STATE.saveWishlists(commander.getFid(), wishlists);
                EventService.publish(new ImportResultEvent(ImportResult.SUCCESS_WISHLIST));
            } catch (final RuntimeException ex) {
                log.error("failed to import wishlist", ex);
                EventService.publish(new ImportResultEvent(ImportResult.ERROR_WISHLIST));
            }
        }).build();
        final HBox importButtonAndLabelWishlist = BoxBuilder.builder().withNodes(this.buttonWishlist, this.errorLabelWishlist).buildHBox();
        //loadouts
        this.importLabelLoadout = LabelBuilder.builder()
                .withStyleClass("import-header")
                .withText(LocaleService.getStringBinding("tab.import.header.loadout"))
                .build();
        this.errorLabelLoadout = LabelBuilder.builder()
                .withStyleClass("import-error")
                .withText(LocaleService.getStringBinding("tab.import.status.invalid.loadout"))
                .withVisibility(false)
                .build();
        this.noteLoadout = LabelBuilder.builder()
                .withText(LocaleService.getStringBinding("tab.import.text.loadout", LocaleService.LocalizationKey.of("tab.loadout.copy")))
                .build();
        this.textAreaLoadout = new TextArea();
        this.textAreaLoadout.getStyleClass().add("import-input");
        this.textAreaLoadout.setWrapText(true);
        this.buttonLoadout = ButtonBuilder.builder().withStyleClass("import-button").withText(LocaleService.getStringBinding("tab.import.button.import.loadout")).withOnAction(event -> {

            this.errorLabelLoadout.setVisible(false);
            try {
                if (this.textAreaLoadout.getText().isEmpty()) {
                    throw new IllegalArgumentException("import string is empty");
                }
                final String decoded = new String(Base64.getDecoder().decode(this.textAreaLoadout.getText().trim()));

                if (decoded.isEmpty()) {
                    throw new IllegalArgumentException("import string is empty");
                }
                final LoadoutSet loadoutSet = OBJECT_MAPPER.readValue(decoded, LoadoutSet.class);

                final Commander commander = APPLICATION_STATE.getPreferredCommander().orElseThrow(IllegalArgumentException::new);
                final LoadoutSetList loadoutSetList = APPLICATION_STATE.getLoadoutSetList(commander.getFid());
                loadoutSetList.addLoadoutSet(loadoutSet);
                loadoutSetList.setSelectedLoadoutSetUUID(loadoutSet.getUuid());
                APPLICATION_STATE.saveLoadoutSetList(commander.getFid(), loadoutSetList);
                EventService.publish(new ImportResultEvent(ImportResult.SUCCESS_LOADOUT));
            } catch (final RuntimeException | JsonProcessingException ex) {
                log.error("failed to import loadout", ex);
                EventService.publish(new ImportResultEvent(ImportResult.ERROR_LOADOUT));
            }
        }).build();
        final HBox importButtonAndLabelLoadout = BoxBuilder.builder().withNodes(this.buttonLoadout, this.errorLabelLoadout).buildHBox();


        final VBox content = BoxBuilder.builder().withNodes(this.importLabelWishlist, this.noteWishlist, this.textAreaWishlist, importButtonAndLabelWishlist, this.importLabelLoadout, this.noteLoadout, this.textAreaLoadout, importButtonAndLabelLoadout).withStyleClass("import-content").buildVBox();
        this.scrollPane = ScrollPaneBuilder.builder()
                .withContent(content)
                .build();
        this.setContent(this.scrollPane);
    }

    @Override
    public Tabs getTabType() {
        return Tabs.ENGINEERS;
    }
}
