/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.templates.overlay.ar;

import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.builder.AnchorPaneBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.helper.AnchorPaneHelper;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableResizableImageView;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;

@Getter
public class ARDebugOverlay extends DestroyableVBox implements DestroyableTemplate {
    private DestroyableResizableImageView resizableImageView1;
    private DestroyableResizableImageView resizableImageView2;
    private DestroyableResizableImageView resizableImageView3;
    private DestroyableResizableImageView resizableImageView4;
    private DestroyableResizableImageView resizableImageView5;
    private DestroyableResizableImageView resizableImageView6;

    public ARDebugOverlay() {
        super();
        initComponents();
    }

    @Override
    public void initComponents() {
        this.resizableImageView1 = ResizableImageViewBuilder.builder()
                .build();
        this.resizableImageView2 = ResizableImageViewBuilder.builder()
                .build();
        this.resizableImageView3 = ResizableImageViewBuilder.builder()
                .build();
        this.resizableImageView4 = ResizableImageViewBuilder.builder()
                .build();
        this.resizableImageView5 = ResizableImageViewBuilder.builder()
                .build();
        this.resizableImageView6 = ResizableImageViewBuilder.builder()
                .build();
        AnchorPaneHelper.setAnchor(this.resizableImageView1, 0D, 0D, 0D, 0D);
        AnchorPaneHelper.setAnchor(this.resizableImageView2, 0D, 0D, 0D, 0D);
        AnchorPaneHelper.setAnchor(this.resizableImageView3, 0D, 0D, 0D, 0D);
        AnchorPaneHelper.setAnchor(this.resizableImageView4, 0D, 0D, 0D, 0D);
        AnchorPaneHelper.setAnchor(this.resizableImageView5, 0D, 0D, 0D, 0D);
        AnchorPaneHelper.setAnchor(this.resizableImageView6, 0D, 0D, 0D, 0D);
        this.getNodes().addAll(
                AnchorPaneBuilder.builder()
                        .withNodes(this.resizableImageView1)
                        .build(),
                AnchorPaneBuilder.builder()
                        .withNodes(this.resizableImageView2)
                        .build(),
                AnchorPaneBuilder.builder()
                        .withNodes(this.resizableImageView3)
                        .build(),
                AnchorPaneBuilder.builder()
                        .withNodes(this.resizableImageView4)
                        .build(),
                AnchorPaneBuilder.builder()
                        .withNodes(this.resizableImageView5)
                        .build(),
                AnchorPaneBuilder.builder()
                        .withNodes(this.resizableImageView6)
                        .build()
        );
    }

    public DestroyableResizableImageView getResizableImageView(final int index) {
        return switch (index) {
            case 1:
                yield this.resizableImageView1;
            case 2:
                yield this.resizableImageView2;
            case 3:
                yield this.resizableImageView3;
            case 4:
                yield this.resizableImageView4;
            case 5:
                yield this.resizableImageView5;
            case 6:
                yield this.resizableImageView6;
            default:
                throw new IllegalStateException("Unexpected value: " + index);
        };
    }
}
