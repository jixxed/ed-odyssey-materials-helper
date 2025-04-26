package nl.jixxed.eliteodysseymaterials.templates.destroyables;

import org.controlsfx.control.SegmentedBar;

public class DestroyableSegmentedBar<T extends SegmentedBar.Segment & Destroyable> extends SegmentedBar<T> implements DestroyableComponent {

    @Override
    public void destroyInternal() {
        DestroyableComponent.super.destroyInternal();
        //reset to defaults
//        this.setSegmentViewFactory(SegmentView::new);
//        this.setInfoNodeFactory(segment -> {
//            Label label = new Label("Value: " + segment.getValue());
//            label.setPadding(new Insets(4.0F));
//            return label;
//        });
        if (this.getSkin() != null) {
            this.getSkin().dispose();
        }

    }
}
