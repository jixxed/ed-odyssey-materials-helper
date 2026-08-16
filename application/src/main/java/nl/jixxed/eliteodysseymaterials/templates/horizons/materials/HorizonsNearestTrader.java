/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.templates.horizons.materials;

import javafx.css.PseudoClass;
import nl.edomh.ui.shared.builder.LabelBuilder;
import nl.edomh.core.domain.Location;
import nl.edomh.core.enums.HorizonsStorageType;
import nl.edomh.core.enums.MaterialTrader;
import nl.edomh.core.service.LocaleService;
import nl.edomh.core.service.LocationService;
import nl.edomh.core.service.MaterialTraderService;
import nl.edomh.core.service.event.EventService;
import nl.edomh.core.service.event.LocationChangedEvent;
import nl.edomh.ui.shared.service.event.PermitEvent;
import nl.edomh.ui.shared.templates.destroyables.DestroyableEventTemplate;
import nl.edomh.ui.shared.templates.destroyables.DestroyableLabel;
import nl.edomh.ui.shared.templates.destroyables.DestroyableVBox;
import nl.edomh.ui.shared.templates.generic.CopyableLocation;

public class HorizonsNearestTrader extends DestroyableVBox implements DestroyableEventTemplate {

    private final HorizonsStorageType type;
    private DestroyableLabel title;
    private CopyableLocation copyableLocation;


    HorizonsNearestTrader(final HorizonsStorageType type) {
        this.type = type;
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("nearest-trader");
        this.title = LabelBuilder.builder()
                .withStyleClass("title")
                .withNonLocalizedText("")
                .build();
        this.getNodes().add(this.title);
        update();
    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, LocationChangedEvent.class, _ -> update()));
        register(EventService.addListener(true, this, PermitEvent.class, _ -> update()));
    }

    private void update() {
        try {
            final Location currentLocation = LocationService.getCurrentLocation();
            final MaterialTrader closestTrader = MaterialTraderService.findClosest(currentLocation.getStarSystem(), this.type);
            this.title.addBinding(this.title.textProperty(), LocaleService.getStringBinding(closestTrader.getType().getLocalizationKey()));
            //replace system if null or changed
            if (copyableLocation == null || !closestTrader.getStarSystem().equals(copyableLocation.getStarSystem())) {
                this.getNodes().remove(copyableLocation);
                this.copyableLocation = new CopyableLocation(closestTrader.getStarSystem(), closestTrader.getName(), closestTrader.getDistanceFromStar(), closestTrader.getDistanceFromStarVariance());
                this.getNodes().add(copyableLocation);
            }
            this.pseudoClassStateChanged(PseudoClass.getPseudoClass("hidden"), false);

        } catch (final IllegalArgumentException ex) {
            this.pseudoClassStateChanged(PseudoClass.getPseudoClass("hidden"), true);
        }
    }
}
