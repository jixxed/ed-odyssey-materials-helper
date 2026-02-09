package nl.jixxed.eliteodysseymaterials.templates.generic;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsWishlistBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.OdysseyWishlistBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.WishlistBlueprint;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.HorizonsWishlistHighlightEvent;
import nl.jixxed.eliteodysseymaterials.service.event.OdysseyWishlistHighlightEvent;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableHBox;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public class QuantitySelect extends DestroyableHBox implements DestroyableTemplate, QuantitySelectable {
    @Getter
    private SimpleIntegerProperty quantity;
    private final List<AmountCircle> values = new ArrayList<>();
    private SimpleBooleanProperty visible;
    private WishlistBlueprint wishlistBlueprint;
    private Disposable subscribe;

    public QuantitySelect(SimpleBooleanProperty visible, WishlistBlueprint wishlistBlueprint) {
        this(1, visible, wishlistBlueprint);
    }

    public QuantitySelect(int quantity, SimpleBooleanProperty visible, WishlistBlueprint wishlistBlueprint) {
        this.quantity = new SimpleIntegerProperty(quantity);
        this.visible = new SimpleBooleanProperty();
        this.visible.bind(visible);
        this.wishlistBlueprint = wishlistBlueprint;
        initComponents();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("quantity-select");
        this.getNodes().add(new GrowingRegion());

        subscribe = Observable.create((ObservableEmitter<AmountCircle> emitter) -> {
                    for (int i = 1; i <= 10; i++) {
                        AmountCircle circle = addValue(i);
                        int quantity2 = i;
                        circle.hoverProperty().addListener((_, _, newValue) -> {
                            if (this.visible.get()) {
                                emitter.onNext(circle);
                            }
                        });
                        this.getNodes().add(circle);
                    }
                })
                .debounce(100, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.computation())
                .subscribe(circle -> {
                    if (circle.isHover() && circle.isFilled()) {
                        if (wishlistBlueprint instanceof HorizonsWishlistBlueprint hwbp) {
                            EventService.publish(new HorizonsWishlistHighlightEvent(hwbp, circle.getIndex(), true));
                        }
                        if (wishlistBlueprint instanceof OdysseyWishlistBlueprint owbp) {
                            EventService.publish(new OdysseyWishlistHighlightEvent(owbp, circle.getIndex(), true));
                        }
                    } else {
                        if (wishlistBlueprint instanceof HorizonsWishlistBlueprint hwbp) {
                            EventService.publish(new HorizonsWishlistHighlightEvent(hwbp, circle.getIndex(), false));
                        }
                        if (wishlistBlueprint instanceof OdysseyWishlistBlueprint owbp) {
                            EventService.publish(new OdysseyWishlistHighlightEvent(owbp, circle.getIndex(), false));
                        }
                    }
                }, t -> log.error(t.getMessage(), t));

        this.getNodes().add(new GrowingRegion());
        update();
    }

    private AmountCircle addValue(int value) {
        final AmountCircle amountCircle = new AmountCircle(value);
        HBox.setHgrow(amountCircle, Priority.ALWAYS);
        values.add(amountCircle);
        return amountCircle;
    }

    public void update() {
        values.forEach(value -> value.update(this.quantity.get()));
        this.quantity.set(this.quantity.get());
    }

    @Override
    public void destroyInternal() {
        super.destroyInternal();
        subscribe.dispose();
    }
}
