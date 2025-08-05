package nl.jixxed.eliteodysseymaterials.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import nl.jixxed.eliteodysseymaterials.constants.OsConstants;
import nl.jixxed.eliteodysseymaterials.enums.GameVersion;

import java.time.LocalDateTime;
import java.util.Locale;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class Commander {
    private final String name;
    private final String fid;
    private final GameVersion gameVersion;
    private final LocalDateTime timestamp;
    @Setter
    private boolean duplicateName = false;

    @Override
    public String toString() {
        return this.name + (this.duplicateName ? " - " + this.fid : "") + " (" + this.gameVersion.name() + ")";
    }

    public String getCommanderFolder() {
        return OsConstants.getConfigDirectory() + OsConstants.getOsSlash() + this.fid.toLowerCase(Locale.ENGLISH) + (this.gameVersion.equals(GameVersion.LEGACY) ? ".legacy" : "");
    }

    public String getLiveCommanderFolder() {
        return OsConstants.getConfigDirectory() + OsConstants.getOsSlash() + this.fid.toLowerCase(Locale.ENGLISH);
    }

}
