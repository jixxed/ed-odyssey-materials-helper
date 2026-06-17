/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.templates.generic.menu;

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.FXApplication;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.HyperlinkBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.PinnedBlueprintService;
import nl.jixxed.eliteodysseymaterials.service.VersionService;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

import java.io.IOException;

@Slf4j
public
class About extends DestroyableVBox implements DestroyableTemplate {
    private final String betaVersion = "2.50-beta11";
    private DestroyableLabel versionLabel;
    private DestroyableHyperlink downloadLink;

    public About() {
        initComponents();
        versionCheck();
    }

    public void initComponents() {
        this.getStyleClass().add("about");

        this.getNodes().addAll(
                version(),
                downloadLink(),
                donateTitle(),
                donateButton(),
                donateLabel(),
                discord(),
                tutorial(),
                pinPlanner()
        );
    }

    private DestroyableHBox pinPlanner() {
        DestroyableHyperlink linkPinPlanner = HyperlinkBuilder.builder()
                .withStyleClass("about-download-link")
                .withText(LocaleService.getStringBinding("menu.about.pinplanner"))
                .withOnAction(_ ->
                        FXApplication.getInstance().getHostServices().showDocument("https://pinplanner.edomh.nl/?config=" + PinnedBlueprintService.getPinConfigForUrl()))
                .build();
        return BoxBuilder.builder().withNodes(new GrowingRegion(), linkPinPlanner, new GrowingRegion()).buildHBox();
    }

    private DestroyableHBox tutorial() {
        DestroyableHyperlink linkTutorial = HyperlinkBuilder.builder()
                .withStyleClass("about-download-link")
                .withText(LocaleService.getStringBinding("menu.about.tutorial"))
                .withOnAction(_ ->
                        FXApplication.getInstance().getHostServices().showDocument("https://www.youtube.com/playlist?list=PLg3S2KmbzGpPvHqMJrP2yFKEyUr8Axn0T"))
                .build();
        return BoxBuilder.builder().withNodes(new GrowingRegion(), linkTutorial, new GrowingRegion()).buildHBox();
    }

    private DestroyableHBox discord() {
        DestroyableResizableImageView discordImage = ResizableImageViewBuilder.builder()
                .withStyleClass("about-discord-image")
                .withImage("/images/other/discord_button.png")
                .build();
        DestroyableHyperlink discord = HyperlinkBuilder.builder()
                .withStyleClass("about-discord")
                .withOnAction(_ ->
                        FXApplication.getInstance().getHostServices().showDocument("https://discord.gg/M8Rgz4AmmA"))
                .withGraphic(discordImage)
                .build();
        return BoxBuilder.builder().withNodes(new GrowingRegion(), discord, new GrowingRegion()).buildHBox();
    }

    private DestroyableHBox downloadLink() {
        this.downloadLink = HyperlinkBuilder.builder()
                .withStyleClass("about-download-link")
                .withText(LocaleService.getStringBinding("menu.about.download"))
                .withOnAction(_ ->
                        FXApplication.getInstance().getHostServices().showDocument("https://github.com/jixxed/ed-odyssey-materials-helper/releases"))
                .build();
        return BoxBuilder.builder().withNodes(new GrowingRegion(), this.downloadLink, new GrowingRegion()).buildHBox();
    }

    private DestroyableHBox version() {
        this.versionLabel = LabelBuilder.builder()
                .withStyleClass("about-version")
                .build();
        return BoxBuilder.builder().withNodes(new GrowingRegion(), this.versionLabel, new GrowingRegion()).buildHBox();
    }

    private DestroyableHBox donateTitle() {
        DestroyableLabel donate = LabelBuilder.builder()
                .withText("menu.about.sponsor.github")
                .build();
        return BoxBuilder.builder()
                .withStyleClass("about-donate")
                .withNodes(
                        new GrowingRegion(),
                        donate,
                        new GrowingRegion()

                ).buildHBox();
    }

    private DestroyableHBox donateLabel() {
        DestroyableLabel commissionGH = LabelBuilder.builder()
                .withStyleClass("about-donate-commission")
                .withText("menu.about.sponsor.github.commission")
                .build();
        return BoxBuilder.builder()
                .withStyleClass("about-donate")
                .withNodes(
                        new GrowingRegion(),
                        commissionGH,
                        new GrowingRegion()
                ).buildHBox();
    }

    private DestroyableHBox donateButton() {
        DestroyableResizableImageView donateImage = ResizableImageViewBuilder.builder()
                .withStyleClass("about-donate-image")
                .withImage("/images/github_sponsors.png")
                .build();
        DestroyableHyperlink donate = HyperlinkBuilder.builder()
                .withStyleClass("about-donate-button")
                .withOnAction(_ ->
                        FXApplication.getInstance().getHostServices().showDocument("https://github.com/sponsors/jixxed"))
                .withGraphic(donateImage)
                .build();
        return BoxBuilder.builder()
                .withStyleClass("about-donate")
                .withNodes(
                        new GrowingRegion(),
                        donate,
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
                this.downloadLink.setVisible(false);
            } else {
                this.versionLabel.addBinding(this.versionLabel.textProperty(), LocaleService.getStringBinding("menu.about.version.new", buildVersion, latestVersion));
            }
        } else {
            this.versionLabel.setText(this.betaVersion);
            this.downloadLink.setVisible(false);
        }
    }
}
