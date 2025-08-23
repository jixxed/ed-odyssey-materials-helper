package nl.jixxed.eliteodysseymaterials.templates.components.edfont;

import de.jensd.fx.glyphs.GlyphIcons;
import nl.jixxed.ed.awesome.api.FontLoader;
import nl.jixxed.ed.awesome.api.NoOpFontLoader;

import java.util.ServiceLoader;

public enum EdAwesomeIcon implements GlyphIcons {
    COMMODITIES_CHEMICALS("\uE000"),
    COMMODITIES_CONSUMER_ITEMS("\uE001"),
    COMMODITIES_FOODS("\uE002"),
    COMMODITIES_INDUSTRIAL_MATERIALS("\uE003"),
    COMMODITIES_LEGAL_DRUGS("\uE004"),
    COMMODITIES_LIMPETS("\uE005"),
    COMMODITIES_LIMPETS_1("\uE006"),
    COMMODITIES_LIMPETS_2("\uE007"),
    COMMODITIES_MACHINERY("\uE008"),
    COMMODITIES_MEDICINES("\uE009"),
    COMMODITIES_METALS("\uE00a"),
    COMMODITIES_MINERALS("\uE00b"),
    COMMODITIES_SALVAGE("\uE00c"),
    COMMODITIES_SLAVERY("\uE00d"),
    COMMODITIES_TECHNOLOGY("\uE00e"),
    COMMODITIES_TEXTILES("\uE00f"),
    COMMODITIES_WASTE("\uE010"),
    COMMODITIES_WEAPONS("\uE011"),
    COMPANIES_BREWER("\uE100"),
    COMPANIES_CORE_DYNAMICS("\uE101"),
    COMPANIES_FAULCON_DELACY("\uE102"),
    COMPANIES_GUTAMAYA("\uE103"),
    COMPANIES_KINEMATIC_ARMAMENTS("\uE104"),
    COMPANIES_MANTICORE("\uE105"),
    COMPANIES_REMLOK("\uE106"),
    COMPANIES_SUPRATECH("\uE107"),
    COMPANIES_SUPRATECH_1("\uE108"),
    COMPANIES_SUPRATECH_2("\uE109"),
    COMPANIES_SUPRATECH_3("\uE10a"),
    COMPANIES_TAKADA("\uE10b"),
    MATERIALS_CHEMICAL("\uE200"),
    MATERIALS_CHEMICAL_1("\uE201"),
    MATERIALS_CHEMICAL_2("\uE202"),
    MATERIALS_CIRCUIT("\uE203"),
    MATERIALS_CIRCUIT_1("\uE204"),
    MATERIALS_CIRCUIT_2("\uE205"),
    MATERIALS_DATA("\uE206"),
    MATERIALS_GOOD("\uE207"),
    MATERIALS_GOOD_1("\uE208"),
    MATERIALS_GOOD_2("\uE209"),
    MATERIALS_GRADE_1("\uE20a"),
    MATERIALS_GRADE_2("\uE20b"),
    MATERIALS_GRADE_3("\uE20c"),
    MATERIALS_GRADE_4("\uE20d"),
    MATERIALS_GRADE_5("\uE20e"),
    MATERIALS_TECH("\uE20f"),
    MATERIALS_TECH_1("\uE210"),
    MATERIALS_TECH_2("\uE211"),
    ONFOOT_BACKPACK("\uE300"),
    ONFOOT_BACKPACK_1("\uE301"),
    ONFOOT_BACKPACK_2("\uE302"),
    ONFOOT_DYNAMIC("\uE303"),
    ONFOOT_FORCE("\uE304"),
    ONFOOT_STRATEGIC("\uE305"),
    OTHER_ARX("\uE400"),
    OTHER_CARRIER_EXTENDED("\uE401"),
    OTHER_CARRIER_SIMPLE("\uE402"),
    OTHER_COMMANDER("\uE403"),
    OTHER_COMMUNITYGOAL("\uE404"),
    OTHER_ELITELOGO("\uE405"),
    OTHER_POWERPLAY("\uE406"),
    OTHER_POWERPLAY_OPEN("\uE407"),
    OTHER_WISHLIST("\uE408"),
    SHIPS_ARMOUR("\uE500"),
    SHIPS_ARMOUR_1("\uE501"),
    SHIPS_ARMOUR_2("\uE502"),
    SHIPS_BOOSTSPEED("\uE503"),
    SHIPS_BOOSTSPEED_1("\uE504"),
    SHIPS_BOOSTSPEED_2("\uE505"),
    SHIPS_BOOSTSPEED_3("\uE506"),
    SHIPS_CARGOCAP("\uE507"),
    SHIPS_CARGOCAP_1("\uE508"),
    SHIPS_CARGOCAP_2("\uE509"),
    SHIPS_ENGINEER("\uE50a"),
    SHIPS_EXPERIMENTAL_EFFECT("\uE50b"),
    SHIPS_EXPLOSIVE("\uE50c"),
    SHIPS_FIGHTER("\uE50d"),
    SHIPS_FIGHTER_1("\uE50e"),
    SHIPS_FIGHTER_2("\uE50f"),
    SHIPS_FIXED("\uE510"),
    SHIPS_FSDLADEN("\uE511"),
    SHIPS_FSDLADEN_1("\uE512"),
    SHIPS_FSDLADEN_2("\uE513"),
    SHIPS_FSDUNLADEN("\uE514"),
    SHIPS_FSDUNLADEN_1("\uE515"),
    SHIPS_FSDUNLADEN_2("\uE516"),
    SHIPS_GIMBALLED("\uE517"),
    SHIPS_GUARDIAN("\uE518"),
    SHIPS_GUARDIAN_1("\uE519"),
    SHIPS_GUARDIAN_2("\uE51a"),
    SHIPS_HEIGHT("\uE51b"),
    SHIPS_HEIGHT_1("\uE51c"),
    SHIPS_HEIGHT_2("\uE51d"),
    SHIPS_KINETIC("\uE51e"),
    SHIPS_LANDING_PAD("\uE51f"),
    SHIPS_LANDING_PAD_1("\uE520"),
    SHIPS_LANDING_PAD_2("\uE521"),
    SHIPS_LENGTH("\uE522"),
    SHIPS_LENGTH_1("\uE523"),
    SHIPS_LENGTH_2("\uE524"),
    SHIPS_MANOEUVRABILITY("\uE525"),
    SHIPS_MASS("\uE526"),
    SHIPS_MULTICREW("\uE527"),
    SHIPS_MULTICREW_1("\uE528"),
    SHIPS_MULTICREW_2("\uE529"),
    SHIPS_MULTICREW_RADAR("\uE52a"),
    SHIPS_SHIELDS("\uE52b"),
    SHIPS_SHIELDS_1("\uE52c"),
    SHIPS_SHIELDS_2("\uE52d"),
    SHIPS_SHIP("\uE52e"),
    SHIPS_SHIP_1("\uE52f"),
    SHIPS_SHIP_2("\uE530"),
    SHIPS_SRV("\uE531"),
    SHIPS_SYNTHESIS_BASIC("\uE532"),
    SHIPS_SYNTHESIS_PREMIUM("\uE533"),
    SHIPS_SYNTHESIS_STANDARD("\uE534"),
    SHIPS_THERMAL("\uE535"),
    SHIPS_TOPSPEED("\uE536"),
    SHIPS_TOPSPEED_1("\uE537"),
    SHIPS_TOPSPEED_2("\uE538"),
    SHIPS_TOPSPEED_3("\uE539"),
    SHIPS_TURRETED("\uE53a"),
    SHIPS_WIDTH("\uE53b"),
    SHIPS_WIDTH_1("\uE53c"),
    SHIPS_WIDTH_2("\uE53d"),
    SQUADRON_BANK("\uE600"),
    SQUADRON_CARRIER("\uE601");

    private static FontLoader fontLoader;

    static {
        ServiceLoader<FontLoader> loader = ServiceLoader.load(FontLoader.class);
        fontLoader = loader.stream().map(ServiceLoader.Provider::get)
                .filter(f -> !(f instanceof NoOpFontLoader))
                .findFirst()
                .orElseGet(NoOpFontLoader::new);
    }

    private final String unicode;

    private EdAwesomeIcon(String unicode) {
        this.unicode = unicode;
    }

    @Override
    public String unicode() {
        return fontLoader.mapCodepoint(unicode);
    }

    @Override
    public String fontFamily() {
        return fontLoader.fontFamily();
    }
}