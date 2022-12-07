package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.domain.Loadout;
import nl.jixxed.eliteodysseymaterials.domain.LoadoutSet;
import nl.jixxed.eliteodysseymaterials.domain.SelectedModification;
import nl.jixxed.eliteodysseymaterials.enums.Suit;
import nl.jixxed.eliteodysseymaterials.enums.SuitModification;
import nl.jixxed.eliteodysseymaterials.enums.Weapon;
import nl.jixxed.eliteodysseymaterials.enums.WeaponModification;
import nl.jixxed.eliteodysseymaterials.journalevents.SuitLoadout.SuitLoadout;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.LoadoutEvent;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SuitLoadoutMessageProcessor implements MessageProcessor<SuitLoadout> {
    private final Pattern pattern = Pattern.compile(".*(\\d+)");//ends with digit

    @Override
    public void process(final SuitLoadout event) {

        LoadoutSet.CURRENT.getLoadouts().clear();
        final String suitName = event.getSuitName();
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
        addSuitMods(selectedModificationSuit, event.getSuitMods());
        LoadoutSet.CURRENT.addLoadout(new Loadout(suit, selectedModificationSuit, currentLevel, targetlevel));

        event.getModules().forEach(weaponModule -> {
            final String weaponName = weaponModule.getModuleName();
            final int currentLevelWeapon = weaponModule.getClass_().intValue();
            final Weapon weapon = Weapon.forFdevName(weaponName);
            final SelectedModification[] selectedModificationWeapon = new SelectedModification[4];
            addWeaponMods(selectedModificationWeapon, weaponModule.getWeaponMods(), weapon);
            LoadoutSet.CURRENT.addLoadout(new Loadout(weapon, selectedModificationWeapon, currentLevelWeapon, 5));
        });
        EventService.publish(new LoadoutEvent(LoadoutSet.CURRENT));
    }

    @Override
    public Class<SuitLoadout> getMessageClass() {
        return SuitLoadout.class;
    }

    private void addSuitMods(final SelectedModification[] selectedModifications, final List<String> mods) {
        final AtomicInteger atomicIndex = new AtomicInteger(0);
        for (int i = 0; i < selectedModifications.length; i++) {
            selectedModifications[i] = new SelectedModification(null, false);
        }
        mods.forEach(mod -> {
            final int index = atomicIndex.getAndIncrement();
            selectedModifications[index].setModification(SuitModification.forFdevName(mod));
            selectedModifications[index].setPresent(true);
        });

    }

    private void addWeaponMods(final SelectedModification[] selectedModifications, final List<String> mods, final Weapon weapon) {
        final AtomicInteger atomicIndex = new AtomicInteger(0);
        for (int i = 0; i < selectedModifications.length; i++) {
            selectedModifications[i] = new SelectedModification(null, false);
        }
        mods.forEach(mod -> {
            final int index = atomicIndex.getAndIncrement();
            selectedModifications[index].setModification(WeaponModification.forFdevName(mod, weapon));
            selectedModifications[index].setPresent(true);
        });
    }
}