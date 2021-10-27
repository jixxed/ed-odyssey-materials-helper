package nl.jixxed.eliteodysseymaterials.templates;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import nl.jixxed.eliteodysseymaterials.builder.ButtonBuilder;
import nl.jixxed.eliteodysseymaterials.domain.Location;
import nl.jixxed.eliteodysseymaterials.enums.Material;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.trade.XMessageWebSocketEvent;
import nl.jixxed.eliteodysseymaterials.trade.message.common.XMessage;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
abstract class Trade extends HBox {
    private final Application application;
    @EqualsAndHashCode.Include
    private String offerId;
    private Material offerMaterial;
    private Material receiveMaterial;
    private Integer offerAmount;
    private Integer receiveAmount;
    private Boolean online;
    private Button contactButton;
    private String chat = "";

    private Location location;

    Trade(final Application application, final String offerId, final Material offerMaterial, final Integer offerAmount, final Material receiveMaterial, final Integer receiveAmount, final Location location) {
        this.application = application;
        this.offerId = offerId;
        this.offerMaterial = offerMaterial;
        this.receiveMaterial = receiveMaterial;
        this.offerAmount = offerAmount;
        this.receiveAmount = receiveAmount;
        this.location = location;
        this.online = true;
        initComponents();
        initEventHandling();
    }

    private void initEventHandling() {
        EventService.addListener(XMessageWebSocketEvent.class, xMessageWebSocketEvent -> {
            final XMessage message = xMessageWebSocketEvent.getXMessageMessage().getMessage();
            if (message.getOfferId().equals(this.offerId)) {
                this.chat += message.getText() + "\n";
            }
        });
    }

    private void initComponents() {
        this.contactButton = ButtonBuilder.builder()
                .withText(LocaleService.getStringBinding("trade.button.chat"))
                .withOnAction(event -> createModal())
                .build();
        this.contactButton.setVisible(false);
    }

    private void createModal() {
        final Stage stage = new Stage();
        final ChatDialog chatDialog = new ChatDialog(stage, this.chat, this.offerId, getTokenHash());
        final Scene scene = new Scene(chatDialog, 640, 480);
        stage.initModality(Modality.WINDOW_MODAL);
        final JMetro jMetro = new JMetro(Style.DARK);
        jMetro.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("/nl/jixxed/eliteodysseymaterials/style/style.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle(LocaleService.getLocalizedStringForCurrentLocale("trade.chat.title") + ": " + LocaleService.getLocalizedStringForCurrentLocale(this.offerMaterial.getLocalizationKey()) + "(" + this.offerAmount + ") <> " + LocaleService.getLocalizedStringForCurrentLocale(this.receiveMaterial.getLocalizationKey()) + "(" + this.receiveAmount + ")");
        stage.show();
        this.contactButton.setDisable(true);
        stage.setOnCloseRequest(event -> {
            this.contactButton.setDisable(false);
            this.chat = chatDialog.getChat().getText();
        });

    }

    abstract String getTokenHash();

}
