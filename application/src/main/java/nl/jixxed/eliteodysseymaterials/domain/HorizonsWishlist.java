package nl.jixxed.eliteodysseymaterials.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.WishlistService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class HorizonsWishlist {

    @JsonIgnore
    public static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    @JsonIgnore
    public static final HorizonsWishlist ALL = new HorizonsWishlist("0", "All Wishlists", new ArrayList<>());
    @EqualsAndHashCode.Include
    private String uuid = UUID.randomUUID().toString();
    private String name;
    private List<WishlistBlueprint<HorizonsBlueprintName>> items = new ArrayList<>();

    @Override
    public String toString() {
        return getName();
    }

    public String getName() {

        if (this == ALL) {
            return LocaleService.getLocalizedStringForCurrentLocale("tab.wishlist.option.all");
        }
        return this.name;
    }

    public List<WishlistBlueprint<HorizonsBlueprintName>> getItems() {
        if (this == HorizonsWishlist.ALL) {
            var allItems = APPLICATION_STATE.getPreferredCommander()
                    .map(commander -> WishlistService.getHorizonsWishlists(commander).getAllWishlists().stream()
                            .filter(wishlist -> wishlist != HorizonsWishlist.ALL)
                            .flatMap(wishlist -> wishlist.getItems().stream())
                            .collect(Collectors.toList()))
                    .orElseGet(() -> new ArrayList<>(this.items));
            return aggregateBlueprints(allItems);
        }
        return this.items;
    }

    @JsonIgnore
    private static List<WishlistBlueprint<HorizonsBlueprintName>> aggregateBlueprints(List<WishlistBlueprint<HorizonsBlueprintName>> blueprints) {
        List<WishlistBlueprint<HorizonsBlueprintName>> aggregated = new ArrayList<>();

        for (WishlistBlueprint<HorizonsBlueprintName> item : blueprints) {
            boolean found = false;
            for (WishlistBlueprint<HorizonsBlueprintName> existing : aggregated) {
                if (item instanceof HorizonsExperimentalWishlistBlueprint exwb &&
                        existing instanceof HorizonsExperimentalWishlistBlueprint existingExwb &&
                        exwb.getRecipeName().equals(existingExwb.getRecipeName()) &&
                        exwb.getBlueprintType().equals(existingExwb.getBlueprintType())) {
                    existingExwb.setQuantity(existingExwb.getQuantity() + exwb.getQuantity());
                    found = true;
                    break;
                } else if (item instanceof HorizonsTechBrokerWishlistBlueprint tbwb &&
                        existing instanceof HorizonsTechBrokerWishlistBlueprint existingTbwb &&
                        tbwb.getRecipeName().equals(existingTbwb.getRecipeName()) &&
                        tbwb.getBlueprintType().equals(existingTbwb.getBlueprintType())) {
                    existingTbwb.setQuantity(existingTbwb.getQuantity() + tbwb.getQuantity());
                    found = true;
                    break;
                } else if (item instanceof HorizonsEngineerWishlistBlueprint ewb &&
                        existing instanceof HorizonsEngineerWishlistBlueprint existingEwb &&
                        ewb.getRecipeName().equals(existingEwb.getRecipeName())) {
                    existingEwb.setQuantity(existingEwb.getQuantity() + ewb.getQuantity());
                    found = true;
                    break;
                } else if (item instanceof HorizonsSynthesisWishlistBlueprint swb &&
                        existing instanceof HorizonsSynthesisWishlistBlueprint existingSwb &&
                        swb.getRecipeName().equals(existingSwb.getRecipeName()) &&
                        swb.getBlueprintGrade().equals(existingSwb.getBlueprintGrade())) {
                    existingSwb.setQuantity(existingSwb.getQuantity() + swb.getQuantity());
                    found = true;
                    break;
                } else if (item instanceof HorizonsModuleWishlistBlueprint mwp &&
                        existing instanceof HorizonsModuleWishlistBlueprint existingMwp &&
                        mwp.getRecipeName().equals(existingMwp.getRecipeName()) &&
                        mwp.getBlueprintType().equals(existingMwp.getBlueprintType()) &&
                        Objects.equals(mwp.getExperimentalEffect(), existingMwp.getExperimentalEffect()) &&
                        mwp.getPercentageToComplete().equals(existingMwp.getPercentageToComplete())) {
                    existingMwp.setQuantity(existingMwp.getQuantity() + mwp.getQuantity());
                    found = true;
                    break;
                }
            }
            if (!found) {
                aggregated.add(item);
            }
        }

        return aggregated;
    }

    @JsonIgnore
    public static List<HorizonsWishlistBlueprint> aggregateTenBlueprints(List<HorizonsWishlistBlueprint> blueprints) {
        List<HorizonsWishlistBlueprint> aggregated = new ArrayList<>();

        for (HorizonsWishlistBlueprint item : blueprints) {
            boolean found = false;
            for (WishlistBlueprint<HorizonsBlueprintName> existing : aggregated) {
                if (item instanceof HorizonsExperimentalWishlistBlueprint exwb &&
                        existing instanceof HorizonsExperimentalWishlistBlueprint existingExwb &&
                        exwb.getRecipeName().equals(existingExwb.getRecipeName()) &&
                        exwb.getBlueprintType().equals(existingExwb.getBlueprintType()) &&
                        existingExwb.getQuantity() < 10) {
                    existingExwb.setQuantity(existingExwb.getQuantity() + exwb.getQuantity());
                    found = true;
                    break;
                } else if (item instanceof HorizonsTechBrokerWishlistBlueprint tbwb &&
                        existing instanceof HorizonsTechBrokerWishlistBlueprint existingTbwb &&
                        tbwb.getRecipeName().equals(existingTbwb.getRecipeName()) &&
                        tbwb.getBlueprintType().equals(existingTbwb.getBlueprintType()) &&
                        existingTbwb.getQuantity() < 10) {
                    existingTbwb.setQuantity(existingTbwb.getQuantity() + tbwb.getQuantity());
                    found = true;
                    break;
                } else if (item instanceof HorizonsEngineerWishlistBlueprint ewb &&
                        existing instanceof HorizonsEngineerWishlistBlueprint existingEwb &&
                        ewb.getRecipeName().equals(existingEwb.getRecipeName()) &&
                        existingEwb.getQuantity() < 10) {
                    existingEwb.setQuantity(existingEwb.getQuantity() + ewb.getQuantity());
                    found = true;
                    break;
                } else if (item instanceof HorizonsSynthesisWishlistBlueprint swb &&
                        existing instanceof HorizonsSynthesisWishlistBlueprint existingSwb &&
                        swb.getRecipeName().equals(existingSwb.getRecipeName()) &&
                        swb.getBlueprintGrade().equals(existingSwb.getBlueprintGrade()) &&
                        existingSwb.getQuantity() < 10) {
                    existingSwb.setQuantity(existingSwb.getQuantity() + swb.getQuantity());
                    found = true;
                    break;
                } else if (item instanceof HorizonsModuleWishlistBlueprint mwp &&
                        existing instanceof HorizonsModuleWishlistBlueprint existingMwp &&
                        mwp.getRecipeName().equals(existingMwp.getRecipeName()) &&
                        mwp.getBlueprintType().equals(existingMwp.getBlueprintType()) &&
                        Objects.equals(mwp.getExperimentalEffect(), existingMwp.getExperimentalEffect()) &&
                        mwp.getPercentageToComplete().equals(existingMwp.getPercentageToComplete()) &&
                        existingMwp.getQuantity() < 10) {
                    existingMwp.setQuantity(existingMwp.getQuantity() + mwp.getQuantity());
                    found = true;
                    break;
                }
            }
            if (!found) {
                aggregated.add(item);
            }
        }

        return aggregated;
    }


//    @JsonIgnore
//    private List<WishlistBlueprint<HorizonsBlueprintName>> aggregateBlueprints2(List<WishlistBlueprint<HorizonsBlueprintName>> blueprints) {
//        //loop over all items. if type and fields are the same, increase the quantity field of the first and remove the second
//        //        HorizonsExperimentalWishlistBlueprint name, type
//        //        HorizonsModuleWishlistBlueprint name, type, grades
//        //        HorizonsSynthesisWishlistBlueprint name, grade
//        //        HorizonsTechBrokerWishlistBlueprint name, type
//        //        HorizonsEngineerWishlistBlueprint name
//        for (Iterator<WishlistBlueprint<HorizonsBlueprintName>> it = blueprints.iterator(); it.hasNext(); ) {
//            WishlistBlueprint item = it.next();
//            switch (item) {
//                case HorizonsExperimentalWishlistBlueprint exwb -> {
//                    for (Iterator<WishlistBlueprint<HorizonsBlueprintName>> itOther = blueprints.iterator(); it.hasNext(); ) {
//                        final WishlistBlueprint<HorizonsBlueprintName> other = itOther.next();
//                        if (other instanceof HorizonsExperimentalWishlistBlueprint otherExwb && otherExwb.getRecipeName().equals(exwb.getRecipeName()) && otherExwb.getBlueprintType().equals(exwb.getBlueprintType())) {
//                            exwb.setQuantity(exwb.getQuantity() + otherExwb.getQuantity());
//                            itOther.remove();
//                        }
//                    }
//                }
//                case HorizonsTechBrokerWishlistBlueprint tbwb -> {
//                    for (Iterator<WishlistBlueprint<HorizonsBlueprintName>> itOther = blueprints.iterator(); it.hasNext(); ) {
//                        final WishlistBlueprint<HorizonsBlueprintName> other = itOther.next();
//                        if (other instanceof HorizonsTechBrokerWishlistBlueprint otherTbwb && otherTbwb.getRecipeName().equals(tbwb.getRecipeName()) && otherTbwb.getBlueprintType().equals(tbwb.getBlueprintType())) {
//                            tbwb.setQuantity(tbwb.getQuantity() + otherTbwb.getQuantity());
//                            itOther.remove();
//                        }
//                    }
//                }
//                case HorizonsEngineerWishlistBlueprint ewb -> {
//                    for (Iterator<WishlistBlueprint<HorizonsBlueprintName>> itOther = blueprints.iterator(); it.hasNext(); ) {
//                        final WishlistBlueprint<HorizonsBlueprintName> other = itOther.next();
//                        if (other instanceof HorizonsEngineerWishlistBlueprint otherEwb && otherEwb.getRecipeName().equals(ewb.getRecipeName())) {
//                            ewb.setQuantity(ewb.getQuantity() + otherEwb.getQuantity());
//                            itOther.remove();
//                        }
//                    }
//                }
//                case HorizonsSynthesisWishlistBlueprint swb -> {
//                    for (Iterator<WishlistBlueprint<HorizonsBlueprintName>> itOther = blueprints.iterator(); it.hasNext(); ) {
//                        final WishlistBlueprint<HorizonsBlueprintName> other = itOther.next();
//                        if (other instanceof HorizonsSynthesisWishlistBlueprint otherSwb && otherSwb.getRecipeName().equals(swb.getRecipeName()) && otherSwb.getBlueprintGrade().equals(swb.getBlueprintGrade())) {
//                            swb.setQuantity(swb.getQuantity() + otherSwb.getQuantity());
//                            itOther.remove();
//                        }
//                    }
//                }
//                case HorizonsModuleWishlistBlueprint mwp -> {
//                    for (Iterator<WishlistBlueprint<HorizonsBlueprintName>> itOther = blueprints.iterator(); it.hasNext(); ) {
//                        final WishlistBlueprint<HorizonsBlueprintName> other = itOther.next();
//                        if (other instanceof HorizonsModuleWishlistBlueprint otherMwp && otherMwp.getRecipeName().equals(mwp.getRecipeName()) && otherMwp.getBlueprintType().equals(mwp.getBlueprintType()) && otherMwp.getPercentageToComplete().equals(mwp.getPercentageToComplete())) {
//                            mwp.setQuantity(mwp.getQuantity() + otherMwp.getQuantity());
//                            itOther.remove();
//                        }
//                    }
//                }
//                case null, default -> throw new IllegalStateException("Unexpected value: " + item);
//            }
//        }
//        return blueprints;
//    }

    @JsonIgnore
    public void optimizeUUIDs() {
        for (int i = 0; i < this.items.size(); i++) {
            final WishlistBlueprint<HorizonsBlueprintName> item = this.items.get(i);
            if (item instanceof HorizonsWishlistBlueprint horizonsWishlistBlueprint) {
                horizonsWishlistBlueprint.setUuid(String.valueOf(i));
            }
        }
    }
}
