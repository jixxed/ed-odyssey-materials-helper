package nl.jixxed.eliteodysseymaterials.templates.horizons.menu;

import javafx.collections.ListChangeListener;
import javafx.css.PseudoClass;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import nl.jixxed.eliteodysseymaterials.builder.MenuItemBuilder;
import nl.jixxed.eliteodysseymaterials.constants.HorizonsBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.*;
import nl.jixxed.eliteodysseymaterials.enums.Action;
import nl.jixxed.eliteodysseymaterials.enums.BlueprintCategory;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.WishlistService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.HorizonsWishlistBlueprintEvent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableEventTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableMenuButton;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableMenuItem;

import java.util.*;

public class AddToWishlistMenuButton extends DestroyableMenuButton implements DestroyableEventTemplate {

    private ListChangeListener<MenuItem> menuItemListChangeListener;

    public AddToWishlistMenuButton() {
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("wishlist-button");
        this.addBinding(this.textProperty(), LocaleService.getStringBinding("blueprint.add.to.wishlist"));
        this.getItems().addAll(new ArrayList<>());
        menuItemListChangeListener = c ->
                this.pseudoClassStateChanged(PseudoClass.getPseudoClass("hidden-menu"), c.getList().size() <= 1);
        this.getItems().addListener(menuItemListChangeListener);
        this.addEventBinding(this.onMouseClickedProperty(), event -> {
            if (this.getItems().size() == 1) {
                this.getItems().getFirst().fire();
                event.consume();
            }
        });
        this.registerEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            if (this.getItems().size() == 1) {
                event.consume();
            }
        });
        this.registerEventFilter(MouseEvent.MOUSE_RELEASED, event -> {
            if (this.getItems().size() == 1) {
                event.consume();
            }
        });
    }

    @Override
    public void initEventHandling() {

    }

    public void loadCommanderWishlists(final Commander commander, HorizonsBlueprint blueprint) {
        final HorizonsWishlists wishlists = WishlistService.getHorizonsWishlists(commander);
        this.clear();
        final List<DestroyableMenuItem> menuItems = wishlists.getAllWishlists().stream().filter(horizonsWishlist -> !horizonsWishlist.equals(HorizonsWishlist.ALL)).sorted(Comparator.comparing(HorizonsWishlist::getName)).flatMap(wishlist -> {
            final List<DestroyableMenuItem> items = new ArrayList<>();
            if (blueprint instanceof HorizonsModuleBlueprint) {
                final DestroyableMenuItem menuItemSingle = createMenuItem(commander, wishlist, blueprint);
                menuItemSingle.addBinding(menuItemSingle.textProperty(), LocaleService.getStringBinding("blueprint.add.to.wishlist.single.grade", wishlist.getName()));
                items.add(menuItemSingle);
                final DestroyableMenuItem menuItemAll = createAllGradeMenuItem(commander, wishlist, blueprint);
                menuItemAll.addBinding(menuItemAll.textProperty(), LocaleService.getStringBinding("blueprint.add.to.wishlist.all.grade", wishlist.getName()));
                items.add(menuItemAll);
            } else {
                final DestroyableMenuItem menuItemSingle = createMenuItem(commander, wishlist, blueprint);
                menuItemSingle.setText(wishlist.getName());
                items.add(menuItemSingle);
            }
            return items.stream();
        }).toList();
        this.addAll(menuItems);
    }


    private DestroyableMenuItem createMenuItem(final Commander commander, final HorizonsWishlist wishlist, HorizonsBlueprint blueprint) {
        return MenuItemBuilder.builder()
                .withOnAction(_ -> {
                    final HorizonsWishlistBlueprint bp;
                    if (blueprint instanceof HorizonsModuleBlueprint horizonsModuleBlueprint) {
                        bp = new HorizonsModuleWishlistBlueprint(horizonsModuleBlueprint.getHorizonsBlueprintType(), new EnumMap<>(Map.of(horizonsModuleBlueprint.getHorizonsBlueprintGrade(), 1D)));
                    } else if (blueprint instanceof HorizonsExperimentalEffectBlueprint horizonsExperimentalEffectBlueprint) {
                        bp = new HorizonsExperimentalWishlistBlueprint(horizonsExperimentalEffectBlueprint.getHorizonsBlueprintType());
                    } else if (BlueprintCategory.SYNTHESIS.equals((blueprint.getBlueprintName()).getBlueprintCategory())) {
                        bp = new HorizonsSynthesisWishlistBlueprint(blueprint.getHorizonsBlueprintGrade());
                    } else if (BlueprintCategory.TECHBROKER.equals((blueprint.getBlueprintName()).getBlueprintCategory())) {
                        bp = new HorizonsTechBrokerWishlistBlueprint(blueprint.getHorizonsBlueprintType());
                    } else {
                        bp = new HorizonsEngineerWishlistBlueprint((blueprint.getBlueprintName()), true);
                    }
                    bp.setRecipeName((blueprint.getBlueprintName()));
                    bp.setVisible(true);
                    EventService.publish(new HorizonsWishlistBlueprintEvent(commander, wishlist.getUuid(), List.of(bp), Action.ADDED));
                })
                .build();
    }

    private DestroyableMenuItem createAllGradeMenuItem(final Commander commander, final HorizonsWishlist wishlist, HorizonsBlueprint blueprint) {
        return MenuItemBuilder.builder()
                .withOnAction(_ -> {
                    final Set<HorizonsBlueprintGrade> blueprintGrades = HorizonsBlueprintConstants.getBlueprintGrades((blueprint.getBlueprintName()), blueprint.getHorizonsBlueprintType());

                    final Map<HorizonsBlueprintGrade, Double> gradePercentages = new EnumMap<>(HorizonsBlueprintGrade.class);
                    blueprintGrades.forEach(horizonsBlueprintGrade -> gradePercentages.put(horizonsBlueprintGrade, 1D));
                    final HorizonsModuleWishlistBlueprint bp = new HorizonsModuleWishlistBlueprint(blueprint.getHorizonsBlueprintType(), gradePercentages);
                    bp.setRecipeName((blueprint.getBlueprintName()));
                    bp.setVisible(true);
                    EventService.publish(new HorizonsWishlistBlueprintEvent(commander, wishlist.getUuid(), List.of(bp), Action.ADDED));
                }).build();
    }

    @Override
    public void destroyInternal() {
        super.destroyInternal();
        this.getItems().removeListener(menuItemListChangeListener);
    }
}
