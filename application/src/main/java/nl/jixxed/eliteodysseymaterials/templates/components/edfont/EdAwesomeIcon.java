package nl.jixxed.eliteodysseymaterials.templates.components.edfont;

import de.jensd.fx.glyphs.GlyphIcons;

public enum EdAwesomeIcon implements GlyphIcons {
    GRADE_1("\u0041"),
    GRADE_2("\u0042"),
    GRADE_3("\u0043"),
    GRADE_4("\u0044"),
    GRADE_5("\u0045"),
    WISHLIST("\u0047"),
    TIMED_ASSASSIN("\u004c"),
    UNKNOWN_MISSION("\u004d"),
    YURI_GROM("\u004e"),
    ZACHARY_HUDSON("\u004f"),
    THERMAL("\u0050"),
    ZEMINA_TORVAL("\u0051"),
    AISLING_DUVAL("\u0052"),
    ALLIANCE("\u0053"),
    ALLIANCE_NO_RING("\u0054"),
    ARCHON_DELAINE("\u0055"),
    ARISSA_LAVIGNY_DUVAL("\u0056"),
    ASTEROID_STATION_ICON("\u0058"),
    BASE_ASSAULT("\u0059"),
    CHARITY("\u005a"),
    COMBAT("\u0061"),
    COMBAT_BORDER("\u0062"),
    CORIOLIS("\u0063"),
    CORIOLIS_SM("\u0064"),
    COURIER("\u0065"),
    COURIER_NO_SCAN("\u0066"),
    COURIER_PLANET("\u0067"),
    COURIER_TIMED("\u0068"),
    COVERT("\u0069"),
    COVERT_BORDER("\u006a"),
    DATA_DELIVERY("\u006b"),
    DELIVERY("\u006c"),
    DENTON_PATREUS("\u0070"),
    ED_FIXED("\u0071"),
    ED_GIMBALLED("\u0072"),
    EDMUND_MAHON("\u0073"),
    ED_TURRET("\u0074"),
    EMPIRE("\u0075"),
    EXPLOSIVE("\u0076"),
    FEDERATION("\u0077"),
    FELICIA_WINTERS("\u0078"),
    FINANCE("\u0079"),
    FINANCE_BORDER("\u007a"),
    HAULING("\u0030"),
    KINETIC("\u0031"),
    LI_YONG_RUI("\u0032"),
    MEGA_SHIP_ICON("\u0033"),
    MERCENARY("\u0034"),
    MINI_FEDERATION_ICON("\u0035"),
    MINING("\u0036"),
    OCELLUS("\u0037"),
    OCELLUS_SM("\u0038"),
    ORBIS("\u0039"),
    ORBIS_SM("\u0021"),
    OUTPOST("\u005c\u0022"),
    OUTPOST_SM("\u0023"),
    PIRATE("\u0024"),
    PIRATE_TIMED("\u0025"),
    PLANETARY_DATA_UNKNOWN_MISSION("\u0026"),
    PRANAV_ANTAL("\u0027"),
    RESCUE_MISSION("\u0028"),
    SETTLEMENT_PM("\u0029"),
    SETTLEMENT_SM("\u002a"),
    SKIMMER_HUNT("\u002b"),
    SOCIAL("\u002c"),
    SOCIAL_BORDER("\u002d"),
    SURFACE_PORT("\u002e"),
    SURFACE_PORT_PM("\u002f"),
    SURFACE_PORT_SM("\u005b");
 
    private final String unicode;

    private EdAwesomeIcon(String unicode) {
        this.unicode = unicode;
    }

    @Override
    public String unicode() {
        return unicode;
    }

    @Override
    public String fontFamily() {
        return "EdAwesome";
    }
}