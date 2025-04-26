package nl.jixxed.eliteodysseymaterials.builder;

import javafx.scene.Node;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.Destroyable;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyablePopOver;
import org.controlsfx.control.PopOver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class PopOverBuilder {
    private final List<String> styleClasses = new ArrayList<>();

    private boolean destroyOnHide = true;
    private Boolean detachable;
    private Boolean headerAlwaysVisible;
    private Integer arrowSize;
    private Integer arrowIndent;
    private Integer cornerRadius;
    private Node contentNode;
    private PopOver.ArrowLocation arrowLocation;
    private Boolean autoFix;
    private Boolean autoHide;
    private Boolean animated;

    public static PopOverBuilder builder() {
        return new PopOverBuilder();
    }

    public PopOverBuilder withStyleClass(final String styleClass) {
        this.styleClasses.add(styleClass);
        return this;
    }

    public PopOverBuilder withStyleClasses(final String... styleClasses) {
        this.styleClasses.addAll(Arrays.asList(styleClasses));
        return this;
    }

    public PopOverBuilder withDestroyOnHide(boolean destroyOnHide) {
        this.destroyOnHide = destroyOnHide;
        return this;
    }


    public PopOverBuilder withDetachable(boolean detachable) {
        this.detachable = detachable;
        return this;
    }

    public PopOverBuilder withAutoHide(boolean autoHide) {
        this.autoHide = autoHide;
        return this;
    }

    public PopOverBuilder withAnimated(boolean animated) {
        this.animated = animated;
        return this;
    }

    public PopOverBuilder withAutoFix(boolean autoFix) {
        this.autoFix = autoFix;
        return this;
    }

    public PopOverBuilder withHeaderAlwaysVisible(boolean headerAlwaysVisible) {
        this.headerAlwaysVisible = headerAlwaysVisible;
        return this;
    }

    public PopOverBuilder withArrowIndent(int arrowIndent) {
        this.arrowIndent = arrowIndent;
        return this;
    }

    public PopOverBuilder withArrowSize(int arrowSize) {
        this.arrowSize = arrowSize;
        return this;
    }

    public PopOverBuilder withCornerRadius(int cornerRadius) {
        this.cornerRadius = cornerRadius;
        return this;
    }

    public PopOverBuilder withArrowLocation(PopOver.ArrowLocation arrowLocation) {
        this.arrowLocation = arrowLocation;
        return this;
    }

    public <E extends javafx.scene.Node & Destroyable> PopOverBuilder withContent(final E contentNode) {
        this.contentNode = contentNode;
        return this;
    }

    public DestroyablePopOver build() {
        final DestroyablePopOver popOver = new DestroyablePopOver();
        popOver.getStyleClass().addAll(this.styleClasses);

        if (this.detachable != null) {
            popOver.setDetachable(this.detachable);
        }
        if (this.autoHide != null) {
            popOver.setAutoHide(this.autoHide);
        }
        if (this.animated != null) {
            popOver.setAnimated(this.animated);
        }
        if (this.autoFix != null) {
            popOver.setAutoFix(this.autoFix);
        }
        if (this.headerAlwaysVisible != null) {
            popOver.setHeaderAlwaysVisible(this.headerAlwaysVisible);
        }
        if (this.arrowSize != null) {
            popOver.setArrowSize(this.arrowSize);
        }
        if (this.arrowIndent != null) {
            popOver.setArrowIndent(this.arrowIndent);
        }
        if (this.cornerRadius != null) {
            popOver.setCornerRadius(this.cornerRadius);
        }
        if (this.arrowLocation != null) {
            popOver.setArrowLocation(this.arrowLocation);
        }
        if (this.contentNode != null) {
            popOver.setContentNode(popOver.register((javafx.scene.Node & Destroyable) this.contentNode));
        }
        if (destroyOnHide) {
            popOver.addChangeListener(popOver.showingProperty(), (_, _, newValue) -> {
                if (Boolean.FALSE.equals(newValue)) {
                    popOver.destroy();
                }
            });
        }
        return popOver;
    }

}
