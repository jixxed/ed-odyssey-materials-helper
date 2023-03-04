package nl.jixxed.eliteodysseymaterials.templates.generic;

import javafx.application.Application;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.HyperlinkBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.VersionService;

import java.io.IOException;

@Slf4j
public
class About extends VBox {
    private final String betaVersion = "1.80-beta13";
    private Label versionLabel;
    private Hyperlink link;
    private Hyperlink linkTutorial;
    private Hyperlink donate;
    private Hyperlink donate2;
    private Hyperlink discord;
    private ImageView donateImage;
    private ImageView donate2Image;
    private ImageView discordImage;

    public About(final Application application) {
        initComponents(application);
        versionCheck();
    }

    private void initComponents(final Application application) {
        this.getStyleClass().add("about");
        this.versionLabel = LabelBuilder.builder().withStyleClass("about-version").build();
        this.link = HyperlinkBuilder.builder().withStyleClass("about-download-link").withText(LocaleService.getStringBinding("menu.about.download")).withAction(actionEvent ->
                application.getHostServices().showDocument("https://github.com/jixxed/ed-odyssey-materials-helper/releases")).build();
        this.linkTutorial = HyperlinkBuilder.builder().withStyleClass("about-download-link").withText(LocaleService.getStringBinding("menu.about.tutorial")).withAction(actionEvent ->
                application.getHostServices().showDocument("https://www.youtube.com/playlist?list=PLg3S2KmbzGpPvHqMJrP2yFKEyUr8Axn0T")).build();
        this.donateImage = ImageViewBuilder.builder().withStyleClass("about-donate-image").withImage("/images/donate.png").build();
        this.donate2Image = ImageViewBuilder.builder().withStyleClass("about-donate-image").withImage("/images/github_sponsors.png").build();
        this.discordImage = ImageViewBuilder.builder().withStyleClass("about-discord-image").withImage("/images/other/discord_button.png").build();
        this.donate = HyperlinkBuilder.builder().withAction(actionEvent ->
                application.getHostServices().showDocument("https://www.paypal.com/donate?business=4LB2HUSB7NDAS&item_name=Odyssey+Materials+Helper")).withGraphic(this.donateImage).build();
        this.donate2 = HyperlinkBuilder.builder().withAction(actionEvent ->
                application.getHostServices().showDocument("https://github.com/sponsors/jixxed")).withGraphic(this.donate2Image).build();
        this.discord = HyperlinkBuilder.builder().withStyleClass("about-discord").withAction(actionEvent ->
                application.getHostServices().showDocument("https://discord.gg/M8Rgz4AmmA")).withGraphic(this.discordImage).build();
        this.getChildren().addAll(this.versionLabel, this.link, BoxBuilder.builder().withStyleClass("about-donate").withNodes(this.donate, this.donate2).buildHBox(), this.discord, this.linkTutorial);

        this.discordImage = ImageViewBuilder.builder().withStyleClass("about-discord-image").withImage("/images/other/discord_button.png").build();
    }

    private void versionCheck() {
        if (!VersionService.isBeta()) {
            final String buildVersion = VersionService.getBuildVersion();
            String latestVersion = "";
            try {
                latestVersion = VersionService.getLatestVersion();
            } catch (final IOException e) {
                log.error("Error retrieving latest version", e);
            }

            if (VersionService.getBuildVersion() == null) {
                this.versionLabel.textProperty().bind(LocaleService.getStringBinding("menu.about.version", "dev"));
            } else if (buildVersion.equals(latestVersion)) {
                this.versionLabel.textProperty().bind(LocaleService.getStringBinding("menu.about.version", buildVersion));
                this.link.setVisible(false);
            } else {
                this.versionLabel.textProperty().bind(LocaleService.getStringBinding("menu.about.version.new", buildVersion, latestVersion));
            }
        } else {
            this.versionLabel.setText(this.betaVersion);
            this.link.setVisible(false);
        }
    }

}
