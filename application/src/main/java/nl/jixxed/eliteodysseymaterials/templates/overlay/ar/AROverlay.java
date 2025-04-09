package nl.jixxed.eliteodysseymaterials.templates.overlay.ar;

import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.helper.AnchorPaneHelper;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableAnchorPane;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableResizableImageView;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;

public class AROverlay extends DestroyableAnchorPane implements DestroyableTemplate {
    @Getter
    private DestroyableResizableImageView resizableImageView;

    public AROverlay() {
        super();
        initComponents();
    }

    @Override
    public void initComponents() {
        this.resizableImageView = ResizableImageViewBuilder.builder()
                .build();
        AnchorPaneHelper.setAnchor(this.resizableImageView, 0D, 0D, 0D, 0D);
        this.getNodes().add(this.resizableImageView);
    }
}
