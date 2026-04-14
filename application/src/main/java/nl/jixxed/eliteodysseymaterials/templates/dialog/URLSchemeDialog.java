/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.templates.dialog;

import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ButtonBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.RegistryService;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

@Slf4j
public class URLSchemeDialog extends DestroyableVBox implements DestroyableTemplate {
    private final Stage stage;

    public URLSchemeDialog(final Stage stage) {
        super();
        this.stage = stage;
        this.initComponents();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("urlscheme-dialog");
        PreferencesService.setPreference(PreferenceConstants.URL_SCHEME, true);

        //labels
        final DestroyableLabel explain = LabelBuilder.builder()
                .withStyleClass("explain-text")
                .withText(LocaleService.getStringBinding("url.scheme.registration.text"))
                .build();
        final DestroyableLabel explain2 = LabelBuilder.builder()
                .withStyleClass("explain-text")
                .withText(LocaleService.getStringBinding("url.scheme.registration.text2"))
                .build();

        //buttons
        final DestroyableHBox buttons = BoxBuilder.builder()
                .withStyleClass("buttons")
                .withNodes(new GrowingRegion(), registerButton(), noThanksButton())
                .buildHBox();

        this.getNodes().addAll(explain, explain2, new GrowingRegion(), buttons);
    }

    private DestroyableButton noThanksButton() {
        return ButtonBuilder.builder()
                .withText("dialog.urlscheme.nothanks")
                .withOnAction(event -> this.stage.close())
                .build();
    }

    private DestroyableButton registerButton() {
        return ButtonBuilder.builder()
                .withText("dialog.urlscheme.register")
                .withOnAction(event -> {
                    RegistryService.registerApplication();
                    this.stage.close();
                })
                .build();
    }

}
