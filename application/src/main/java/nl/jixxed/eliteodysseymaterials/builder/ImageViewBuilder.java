/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.builder;

import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.service.ImageService;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableImageView;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageViewBuilder extends AbstractNodeBuilder<ImageViewBuilder> {
    private Image image;
    private boolean preserveRatio = true;
    private ObservableValue<Number> fitHeightBinding;
    private ObservableValue<Number> fitWidthBinding;

    public static ImageViewBuilder builder() {
        return new ImageViewBuilder();
    }

    public ImageViewBuilder withPreserveRatio(final boolean preserveRatio) {
        this.preserveRatio = preserveRatio;
        return this;
    }

    public ImageViewBuilder withImage(final Image image) {
        this.image = image;
        return this;
    }

    public ImageViewBuilder withImage(final String imageResource) {
        this.image = ImageService.getImage(imageResource);
        return this;
    }

    public ImageViewBuilder withFitHeightBinding(final ObservableValue<Number> fitHeightBinding) {
        this.fitHeightBinding = fitHeightBinding;
        return this;
    }

    public ImageViewBuilder withFitWidthBinding(final ObservableValue<Number> fitWidthBinding) {
        this.fitWidthBinding = fitWidthBinding;
        return this;
    }

    public DestroyableImageView build() {
        final DestroyableImageView imageView = new DestroyableImageView();
        super.build(imageView);

        imageView.setImage(this.image);
        imageView.setPreserveRatio(this.preserveRatio);
        if (this.fitHeightBinding != null) {
            imageView.addBinding(imageView.fitHeightProperty(), this.fitHeightBinding);
        }
//        if (this.fitWidthBinding != null) {
//            imageView.addBinding(imageView.fitWidthProperty(), this.fitWidthBinding);
//        }
        return imageView;
    }
}
