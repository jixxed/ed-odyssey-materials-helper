package nl.jixxed.eliteodysseymaterials.templates.generic;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.util.Duration;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.*;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsWishlistBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.OdysseyWishlistBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.WishlistBlueprint;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.HorizonsWishlistHighlightEvent;
import nl.jixxed.eliteodysseymaterials.service.event.OdysseyWishlistHighlightEvent;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableHBox;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTooltip;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ControllableQuantitySelect extends DestroyableHBox implements DestroyableTemplate, QuantitySelectable {
    @Getter
    private SimpleIntegerProperty quantity;
    private final List<AmountCircle> values = new ArrayList<>();
    private SimpleBooleanProperty visible;
    private WishlistBlueprint wishlistBlueprint;
    private Disposable subscribe;

    public ControllableQuantitySelect(SimpleBooleanProperty visible, WishlistBlueprint wishlistBlueprint) {
        this(1, visible, wishlistBlueprint);
    }

    public ControllableQuantitySelect(int quantity, SimpleBooleanProperty visible, WishlistBlueprint wishlistBlueprint) {
        this.quantity = new SimpleIntegerProperty(quantity);
        this.visible = new SimpleBooleanProperty();
        this.visible.bind(visible);
        this.wishlistBlueprint = wishlistBlueprint;
        initComponents();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("quantity-select");
//        DestroyableLabel left = LabelBuilder.builder()
//                .withStyleClasses("arrow", "left")
//                .withNonLocalizedText("◀")
//                .withOnMouseClicked(_ -> decrease())
//                .build();
       var left = BoxBuilder.builder()
               .withStyleClasses("modifier-container", "left")
               .withNode(FontAwesomeIconViewBuilder.builder()
                .withStyleClasses("modifier")
                .withIcon(FontAwesomeIcon.MINUS)
                .withOnMouseClicked(_ -> decrease())
                .build()).buildHBox();
        this.getNodes().add(left);
        this.getNodes().add(new GrowingRegion());

        subscribe = Observable.create((ObservableEmitter<AmountCircle> emitter) -> {
                    for (int i = 1; i <= 10; i++) {
                        AmountCircle circle = addValue(i);
                        int quantity2 = i;
                        circle.hoverProperty().addListener((_, _, newValue) -> {
                            if (this.visible.get()) {
                                if (Boolean.TRUE.equals(newValue)) {
                                    if (circle.isFilled()) {
                                        emitter.onNext(circle);
                                    }
                                } else {
                                    if (wishlistBlueprint instanceof HorizonsWishlistBlueprint hwbp) {
                                        EventService.publish(new HorizonsWishlistHighlightEvent(hwbp, quantity2, false));
                                    }
                                    if (wishlistBlueprint instanceof OdysseyWishlistBlueprint owbp) {
                                        EventService.publish(new OdysseyWishlistHighlightEvent(owbp, quantity2, false));
                                    }
                                }
                            }
                        });
                        this.getNodes().add(circle);
                    }
                })
                .delay(250, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io())
                .subscribe(circle -> {
                    if (circle.isHover()) {
                        if (wishlistBlueprint instanceof HorizonsWishlistBlueprint hwbp) {
                            EventService.publish(new HorizonsWishlistHighlightEvent(hwbp, circle.getIndex(), true));
                        }
                        if (wishlistBlueprint instanceof OdysseyWishlistBlueprint owbp) {
                            EventService.publish(new OdysseyWishlistHighlightEvent(owbp, circle.getIndex(), true));
                        }
                    }
                }, t -> log.error(t.getMessage(), t));

        this.getNodes().add(new GrowingRegion());

//        DestroyableLabel right = LabelBuilder.builder()
//                .withStyleClasses("arrow", "right")
//                .withNonLocalizedText("▶")
//                .withOnMouseClicked(_ -> increase())
//                .build();
        var right = BoxBuilder.builder()
                .withStyleClasses("modifier-container", "right")
                .withNode(FontAwesomeIconViewBuilder.builder()
                        .withStyleClasses("modifier")
                        .withIcon(FontAwesomeIcon.PLUS)
                        .withOnMouseClicked(_ -> increase())
                        .build()).buildHBox();

        DestroyableTooltip leftTooltip = TooltipBuilder.builder()
                .withStyleClass("action-tooltip")
                .withShowDelay(Duration.millis(100))
                .withText("wishlist.tooltip.quantity.decrease")
                .build();
        leftTooltip.install(left);

        DestroyableTooltip rightTooltip = TooltipBuilder.builder()
                .withStyleClass("action-tooltip")
                .withShowDelay(Duration.millis(100))
                .withText("wishlist.tooltip.quantity.increase")
                .build();
        rightTooltip.install(right);
//        var right = FontAwesomeIconViewBuilder.builder()
//                .withStyleClasses("arrow", "right")
//                .withIcon(FontAwesomeIcon.PLUS)
//                .withOnMouseClicked(_ -> increase())
//                .build();
        HBox.setHgrow(right, Priority.ALWAYS);
        this.getNodes().add(right);
        update();
    }


    private void decrease() {
        if (this.quantity.get() <= 1) {
            return;
        }
        quantity.set(quantity.get() - 1);
        update();
    }

    private void increase() {
        if (this.quantity.get() >= 10) {
            return;
        }
        quantity.set(quantity.get() + 1);
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
