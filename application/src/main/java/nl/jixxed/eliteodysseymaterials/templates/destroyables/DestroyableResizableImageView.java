/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.templates.destroyables;

import javafx.beans.property.BooleanProperty;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import lombok.Getter;

@Getter
public class DestroyableResizableImageView extends Pane implements DestroyableParent {
//    ObservableListOverride override = new ObservableListOverride(DestroyableResizableImageView.this, super.getChildren());
//
//    @Override
//    public ObservableListOverride getNodes() {
//        return override;
//    }

    private final DestroyableImageView iv;

    public DestroyableResizableImageView() {
        this.iv = new DestroyableImageView();
        this.iv.setSmooth(true);
        getNodes().add(this.iv);
    }

    public final void setImage(final Image image) {
        this.iv.setImage(image);
        this.iv.addBinding(this.iv.fitWidthProperty(), widthProperty());
        this.iv.addBinding(this.iv.fitHeightProperty(), heightProperty());
    }

    public final void setPreserveRatio(final boolean preserveRatio) {
        this.iv.setPreserveRatio(preserveRatio);
    }

    public final boolean isPreserveRatio() {
        return this.iv.isPreserveRatio();
    }

    public final BooleanProperty preserveRatioProperty() {
        return this.iv.preserveRatioProperty();
    }

    public final Image getImage() {
        return this.iv.getImage();
    }

    @Override
    public void destroyInternal() {
        this.iv.setImage(null);
        this.iv.destroy();
        DestroyableParent.super.destroyInternal();
//        this.iv.fitWidthProperty().unbind();
//        this.iv.fitHeightProperty().unbind();
    }
}