/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.templates.horizons.menu;

import javafx.css.PseudoClass;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.domain.Location;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBrokerType;
import nl.jixxed.eliteodysseymaterials.enums.TechnologyBroker;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.LocationService;
import nl.jixxed.eliteodysseymaterials.service.TechnologyBrokerService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.LocationChangedEvent;
import nl.jixxed.eliteodysseymaterials.service.event.PermitEvent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableEventTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;
import nl.jixxed.eliteodysseymaterials.templates.generic.CopyableLocation;

import java.util.List;

public class HorizonsNearestBroker extends DestroyableVBox implements DestroyableEventTemplate {

    private final List<HorizonsBrokerType> horizonsBrokerTypes;
    private DestroyableLabel title;
    private CopyableLocation copyableLocation;


    HorizonsNearestBroker(final List<HorizonsBrokerType> horizonsBrokerTypes) {
        this.horizonsBrokerTypes = horizonsBrokerTypes;
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("nearest-broker");
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
            final TechnologyBroker closestBroker = TechnologyBrokerService.findClosest(currentLocation.getStarSystem(), this.horizonsBrokerTypes);
            this.title.addBinding(this.title.textProperty(), LocaleService.getStringBinding(closestBroker.getType().getLocalizationKey()));
            //replace system if null or changed
            if (copyableLocation == null || !closestBroker.getStarSystem().equals(copyableLocation.getStarSystem())) {
                this.getNodes().remove(copyableLocation);
                this.copyableLocation = new CopyableLocation(closestBroker.getStarSystem(), closestBroker.getName(), closestBroker.getDistanceFromStar(), closestBroker.getDistanceFromStarVariance());
                this.getNodes().add(copyableLocation);
            }
            this.pseudoClassStateChanged(PseudoClass.getPseudoClass("hidden"), false);

        } catch (final IllegalArgumentException ex) {
            this.pseudoClassStateChanged(PseudoClass.getPseudoClass("hidden"), true);
        }
    }

}
