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
    COMMODITIES_METALS("\uE00A"),
    COMMODITIES_MINERALS("\uE00B"),
    COMMODITIES_SALVAGE("\uE00C"),
    COMMODITIES_SLAVERY("\uE00D"),
    COMMODITIES_TECHNOLOGY("\uE00E"),
    COMMODITIES_TEXTILES("\uE00F"),
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
    COMPANIES_SUPRATECH_3("\uE10A"),
    COMPANIES_TAKADA("\uE10B"),
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
    MATERIALS_GRADE_1("\uE20A"),
    MATERIALS_GRADE_2("\uE20B"),
    MATERIALS_GRADE_3("\uE20C"),
    MATERIALS_GRADE_4("\uE20D"),
    MATERIALS_GRADE_5("\uE20E"),
    MATERIALS_TECH("\uE20F"),
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
    OTHER_COLONIA("\uE403"),
    OTHER_COMMANDER("\uE404"),
    OTHER_COMMUNITYGOAL("\uE405"),
    OTHER_ELITELOGO("\uE406"),
    OTHER_MINING("\uE407"),
    OTHER_PERMIT("\uE408"),
    OTHER_POWERPLAY("\uE409"),
    OTHER_POWERPLAY_OPEN("\uE40A"),
    OTHER_STOCK_BRACKET_HIGH("\uE40B"),
    OTHER_STOCK_BRACKET_LOW("\uE40C"),
    OTHER_WISHLIST("\uE40D"),
    SHIPS_ADVANCED("\uE500"),
    SHIPS_ARMOUR("\uE501"),
    SHIPS_ARMOUR_1("\uE502"),
    SHIPS_ARMOUR_2("\uE503"),
    SHIPS_BOOSTSPEED("\uE504"),
    SHIPS_BOOSTSPEED_1("\uE505"),
    SHIPS_BOOSTSPEED_2("\uE506"),
    SHIPS_BOOSTSPEED_3("\uE507"),
    SHIPS_CARGOCAP("\uE508"),
    SHIPS_CARGOCAP_1("\uE509"),
    SHIPS_CARGOCAP_2("\uE50A"),
    SHIPS_DUMBFIRE("\uE50B"),
    SHIPS_ENGINEER("\uE50C"),
    SHIPS_ENHANCED("\uE50D"),
    SHIPS_EXPERIMENTAL_EFFECT("\uE50E"),
    SHIPS_EXPLOSIVE("\uE50F"),
    SHIPS_FIGHTER("\uE510"),
    SHIPS_FIGHTER_1("\uE511"),
    SHIPS_FIGHTER_2("\uE512"),
    SHIPS_FIXED("\uE513"),
    SHIPS_FSDLADEN("\uE514"),
    SHIPS_FSDLADEN_1("\uE515"),
    SHIPS_FSDLADEN_2("\uE516"),
    SHIPS_FSDUNLADEN("\uE517"),
    SHIPS_FSDUNLADEN_1("\uE518"),
    SHIPS_FSDUNLADEN_2("\uE519"),
    SHIPS_GIMBALLED("\uE51A"),
    SHIPS_GUARDIAN("\uE51B"),
    SHIPS_GUARDIAN_1("\uE51C"),
    SHIPS_GUARDIAN_2("\uE51D"),
    SHIPS_HEIGHT("\uE51E"),
    SHIPS_HEIGHT_1("\uE51F"),
    SHIPS_HEIGHT_2("\uE520"),
    SHIPS_KINETIC("\uE521"),
    SHIPS_LANDING_PAD("\uE522"),
    SHIPS_LANDING_PAD_1("\uE523"),
    SHIPS_LANDING_PAD_2("\uE524"),
    SHIPS_LEGACY("\uE525"),
    SHIPS_LENGTH("\uE526"),
    SHIPS_LENGTH_1("\uE527"),
    SHIPS_LENGTH_2("\uE528"),
    SHIPS_MANOEUVRABILITY("\uE529"),
    SHIPS_MASS("\uE52A"),
    SHIPS_MULTICREW("\uE52B"),
    SHIPS_MULTICREW_1("\uE52C"),
    SHIPS_MULTICREW_2("\uE52D"),
    SHIPS_MULTICREW_RADAR("\uE52E"),
    SHIPS_PREENGINEERED("\uE52F"),
    SHIPS_SEEKER("\uE530"),
    SHIPS_SHIELDS("\uE531"),
    SHIPS_SHIELDS_1("\uE532"),
    SHIPS_SHIELDS_2("\uE533"),
    SHIPS_SHIP("\uE534"),
    SHIPS_SHIP_1("\uE535"),
    SHIPS_SHIP_2("\uE536"),
    SHIPS_SRV("\uE537"),
    SHIPS_SYNTHESIS_BASIC("\uE538"),
    SHIPS_SYNTHESIS_PREMIUM("\uE539"),
    SHIPS_SYNTHESIS_STANDARD("\uE53A"),
    SHIPS_THERMAL("\uE53B"),
    SHIPS_TOPSPEED("\uE53C"),
    SHIPS_TOPSPEED_1("\uE53D"),
    SHIPS_TOPSPEED_2("\uE53E"),
    SHIPS_TOPSPEED_3("\uE53F"),
    SHIPS_TURRETED("\uE540"),
    SHIPS_WIDTH("\uE541"),
    SHIPS_WIDTH_1("\uE542"),
    SHIPS_WIDTH_2("\uE543"),
    SQUADRON_BANK("\uE600"),
    SQUADRON_CARRIER("\uE601"),
    STATION_CORIOLIS("\uE700");

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