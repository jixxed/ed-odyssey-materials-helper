package nl.jixxed.eliteodysseymaterials.templates.generic.menu;

import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.FXApplication;
import nl.jixxed.eliteodysseymaterials.builder.*;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.PinnedBlueprintService;
import nl.jixxed.eliteodysseymaterials.service.VersionService;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

import java.io.IOException;

@Slf4j
public
class About extends DestroyableVBox implements DestroyableTemplate {
    private static DestroyableLabel commissionPP;
    private static DestroyableLabel commissionGH;
    private static DestroyableTooltip tooltipPP;
    private static DestroyableTooltip tooltipGH;
    private final String betaVersion = "2.50-beta11";
    private DestroyableLabel versionLabel;
    private DestroyableHyperlink link;
    private DestroyableHyperlink linkTutorial;
    private DestroyableHyperlink linkPinPlanner;
    private DestroyableHyperlink donate;
    private DestroyableHyperlink donate2;
    private DestroyableHyperlink discord;
    private DestroyableResizableImageView donateImage;
    private DestroyableResizableImageView donate2Image;
    private DestroyableResizableImageView discordImage;

    public About() {
        initComponents();
        versionCheck();
    }

    public void initComponents() {
        this.getStyleClass().add("about");
        this.versionLabel = LabelBuilder.builder()
                .withStyleClass("about-version")
                .build();
        this.link = HyperlinkBuilder.builder()
                .withStyleClass("about-download-link")
                .withText(LocaleService.getStringBinding("menu.about.download"))
                .withOnAction(actionEvent ->
                        FXApplication.getInstance().getHostServices().showDocument("https://github.com/jixxed/ed-odyssey-materials-helper/releases"))
                .build();
        this.linkTutorial = HyperlinkBuilder.builder()
                .withStyleClass("about-download-link")
                .withText(LocaleService.getStringBinding("menu.about.tutorial"))
                .withOnAction(actionEvent ->
                        FXApplication.getInstance().getHostServices().showDocument("https://www.youtube.com/playlist?list=PLg3S2KmbzGpPvHqMJrP2yFKEyUr8Axn0T"))
                .build();
        this.linkPinPlanner = HyperlinkBuilder.builder()
                .withStyleClass("about-download-link")
                .withText(LocaleService.getStringBinding("menu.about.pinplanner"))
                .withOnAction(actionEvent ->
                        FXApplication.getInstance().getHostServices().showDocument("https://jixxed.github.io/ed-pin-planner/?config=" + PinnedBlueprintService.getPinConfigForUrl()))
                .build();
        this.donateImage = ResizableImageViewBuilder.builder()
                .withStyleClass("about-donate-image")
                .withImage("/images/paypal.png")
                .build();
        this.donate2Image = ResizableImageViewBuilder.builder()
                .withStyleClass("about-donate-image")
                .withImage("/images/github_sponsors.png")
                .build();
        this.discordImage = ResizableImageViewBuilder.builder()
                .withStyleClass("about-discord-image")
                .withImage("/images/other/discord_button.png")
                .build();
        this.donate = HyperlinkBuilder.builder()
                .withStyleClass("about-donate-button")
                .withOnAction(actionEvent ->
                        FXApplication.getInstance().getHostServices().showDocument("https://www.paypal.com/donate?business=4LB2HUSB7NDAS&item_name=Odyssey+Materials+Helper"))
                .withGraphic(this.donateImage)
                .build();
        this.donate2 = HyperlinkBuilder.builder()
                .withStyleClass("about-donate-button")
                .withOnAction(actionEvent ->
                        FXApplication.getInstance().getHostServices().showDocument("https://github.com/sponsors/jixxed"))
                .withGraphic(this.donate2Image)
                .build();
        this.discord = HyperlinkBuilder.builder()
                .withStyleClass("about-discord")
                .withOnAction(actionEvent ->
                        FXApplication.getInstance().getHostServices().showDocument("https://discord.gg/M8Rgz4AmmA"))
                .withGraphic(this.discordImage)
                .build();

        this.getNodes().addAll(
                BoxBuilder.builder().withNodes(new GrowingRegion(), this.versionLabel, new GrowingRegion()).buildHBox(),
                BoxBuilder.builder().withNodes(new GrowingRegion(), this.link, new GrowingRegion()).buildHBox(),
                donateButtons(),
                donateLabels(),
                BoxBuilder.builder().withNodes(new GrowingRegion(), this.discord, new GrowingRegion()).buildHBox(),
                BoxBuilder.builder().withNodes(new GrowingRegion(), this.linkTutorial, new GrowingRegion()).buildHBox(),
                BoxBuilder.builder().withNodes(new GrowingRegion(), this.linkPinPlanner, new GrowingRegion()).buildHBox()
        );
    }

    private static DestroyableHBox donateLabels() {
        commissionPP = LabelBuilder.builder()
                .withStyleClass("about-donate-commission")
                .withNonLocalizedText("2.89%+39ct")
                .build();
        commissionGH = LabelBuilder.builder()
                .withStyleClass("about-donate-commission")
                .withNonLocalizedText("0%")
                .build();
        tooltipPP = TooltipBuilder.builder()
                .withShowDelay(Duration.ZERO)
                .withText("menu.about.commission")
                .build();
        tooltipGH = TooltipBuilder.builder()
                .withShowDelay(Duration.ZERO)
                .withText("menu.about.commission")
                .build();
        tooltipPP.install(commissionPP);
        tooltipGH.install(commissionGH);
        return BoxBuilder.builder()
                .withStyleClass("about-donate")
                .withNodes(
                        new GrowingRegion(),
                        commissionPP,
                        commissionGH,
                        new GrowingRegion()
                ).buildHBox();
    }

    private DestroyableHBox donateButtons() {
        return BoxBuilder.builder()
                .withStyleClass("about-donate")
                .withNodes(
                        new GrowingRegion(),
                        this.donate,
                        this.donate2,
                        new GrowingRegion()

                ).buildHBox();
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

            if (VersionService.isDev()) {
                this.versionLabel.addBinding(this.versionLabel.textProperty(), LocaleService.getStringBinding("menu.about.version", "dev"));
            } else if (buildVersion.equals(latestVersion)) {
                this.versionLabel.addBinding(this.versionLabel.textProperty(), LocaleService.getStringBinding("menu.about.version", buildVersion));
                this.link.setVisible(false);
            } else {
                this.versionLabel.addBinding(this.versionLabel.textProperty(), LocaleService.getStringBinding("menu.about.version.new", buildVersion, latestVersion));
            }
        } else {
            this.versionLabel.setText(this.betaVersion);
            this.link.setVisible(false);
        }
    }

    @Override
    public void destroyInternal() {
        super.destroyInternal();
//        Tooltip.uninstall(commissionPP, tooltipPP);
//        Tooltip.uninstall(commissionGH, tooltipGH);
    }
}
