package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import com.fasterxml.jackson.databind.JsonNode;
import nl.jixxed.eliteodysseymaterials.domain.Loadout;
import nl.jixxed.eliteodysseymaterials.domain.LoadoutSet;
import nl.jixxed.eliteodysseymaterials.domain.SelectedModification;
import nl.jixxed.eliteodysseymaterials.enums.Suit;
import nl.jixxed.eliteodysseymaterials.enums.SuitModification;
import nl.jixxed.eliteodysseymaterials.enums.Weapon;
import nl.jixxed.eliteodysseymaterials.enums.WeaponModification;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.LoadoutEvent;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SuitLoadoutMessageProcessor implements MessageProcessor {
    private final Pattern pattern = Pattern.compile(".*(\\d+)");//ends with digit

    @Override
    @SuppressWarnings("java:S1192")
    public void process(final JsonNode journalMessage) {

        LoadoutSet.CURRENT.getLoadouts().clear();
        final String suitName = journalMessage.get("SuitName").asText();
        final Suit suit = Suit.forFdevName(suitName);
        final Matcher matcher = this.pattern.matcher(suitName);
        int currentLevel = 1;
        int targetlevel = 5;
        if (matcher.matches()) {
            final String group = matcher.group(1);
            currentLevel = Integer.parseInt(group);
        } else {//flightsuit
            targetlevel = 1;
        }
        final SelectedModification[] selectedModificationSuit = new SelectedModification[4];
        final Iterator<JsonNode> suitMods = journalMessage.get("SuitMods").elements();
        addSuitMods(selectedModificationSuit, suitMods);
        LoadoutSet.CURRENT.addLoadout(new Loadout(suit, selectedModificationSuit, currentLevel, targetlevel));

        final Iterator<JsonNode> weapons = journalMessage.get("Modules").elements();
        weapons.forEachRemaining(weaponModule -> {
            final String weaponName = weaponModule.get("ModuleName").asText();
            final int currentLevelWeapon = weaponModule.get("Class").asInt();
            final Weapon weapon = Weapon.forFdevName(weaponName);
            final Iterator<JsonNode> weaponMods = weaponModule.get("WeaponMods").elements();
            final SelectedModification[] selectedModificationWeapon = new SelectedModification[4];
            addWeaponMods(selectedModificationWeapon, weaponMods, weapon);
            LoadoutSet.CURRENT.addLoadout(new Loadout(weapon, selectedModificationWeapon, currentLevelWeapon, 5));
        });
        EventService.publish(new LoadoutEvent(LoadoutSet.CURRENT));
    }

    private void addSuitMods(final SelectedModification[] selectedModifications, final Iterator<JsonNode> mods) {
        final AtomicInteger atomicIndex = new AtomicInteger(0);
        for (int i = 0; i < selectedModifications.length; i++) {
            selectedModifications[i] = new SelectedModification(null, false);
        }
        mods.forEachRemaining(mod -> {
            final int index = atomicIndex.getAndIncrement();
            selectedModifications[index].setModification(SuitModification.forFdevName(mod.asText()));
            selectedModifications[index].setPresent(true);
        });

    }

    private void addWeaponMods(final SelectedModification[] selectedModifications, final Iterator<JsonNode> mods, final Weapon weapon) {
        final AtomicInteger atomicIndex = new AtomicInteger(0);
        for (int i = 0; i < selectedModifications.length; i++) {
            selectedModifications[i] = new SelectedModification(null, false);
        }
        mods.forEachRemaining(mod -> {
            final int index = atomicIndex.getAndIncrement();
            selectedModifications[index].setModification(WeaponModification.forFdevName(mod.asText(), weapon));
            selectedModifications[index].setPresent(true);
        });
    }
}