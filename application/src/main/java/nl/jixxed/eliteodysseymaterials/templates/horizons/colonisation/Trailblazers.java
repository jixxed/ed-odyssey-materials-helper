/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.templates.horizons.colonisation;

import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.service.TrailblazerService;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;
import nl.jixxed.eliteodysseymaterials.templates.generic.CopyableLocation;

import java.util.List;

public class Trailblazers extends DestroyableVBox implements DestroyableTemplate {

    public Trailblazers() {
        initComponents();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("trailblazers");
        final DestroyableLabel title = LabelBuilder.builder()
                .withStyleClass("title")
                .withText("tab.colonisation.trailblazers.title")
                .build();
        this.getNodes().add(title);
        final List<CopyableLocation> locations = TrailblazerService.getAllTrailblazers().stream()
                .map(trailblazer ->
                        new CopyableLocation(
                                trailblazer.getStarSystem(),
                                trailblazer.getName(),
                                trailblazer.getDistanceFromStar(),
                                trailblazer.getDistanceFromStarVariance()
                        ))
                .toList();
        this.getNodes().addAll(locations);
    }
}
