package nl.jixxed.eliteodysseymaterials;

public enum Goods {
    AGRICULTURALPROCESSSAMPLE("AGRICULTURALPROCESSSAMPLE"),
    BIOCHEMICALAGENT("Biochemical Agent"),
    BIOLOGICALSAMPLE("Biological Sample"),
    BUILDINGSCHEMATIC("Building Schematic"),
    CALIFORNIUM("Californium"),
    CASTFOSSIL("Cast Fossil"),
    CHEMICALPROCESSSAMPLE("Chemical Process Sample"),
    CHEMICALSAMPLE("Chemical Sample"),
    COMPACTLIBRARY("Compact Library"),
    COMPRESSIONLIQUEFIEDGAS("Compression-Liquefied Gas"),
    DEEPMANTLESAMPLE("Deep Mantle Sample"),
    DEGRADEDPOWERREGULATOR("Degraded Power Regulator"),
    GENETICREPAIRMEDS("Genetic Repair Meds"),
    GENETICSAMPLE("Genetic Sample"),
    GMEDS("G-Meds"),
    HEALTHCANISTER("Health Canister"),
    HEALTHMONITOR("Health Monitor"),
    HUSH("Hush"),
    INERTIACANISTER("Inertia Canister"),
    INFINITY("Infinity"),
    INORGANICCONTAMINANT("INORGANICCONTAMINANT"),
    INSIGHT("InSight"),
    INSIGHTDATABANK("InSight Data Bank"),
    INSIGHTENTERTAINMENTSUITE("InSight Entertainment Suite"),
    IONISEDGAS("Ionised Gas"),
    LARGECAPACITYPOWERREGULATOR("Power Regulator"),
    LAZARUS("Lazarus"),
    MICROBIALINHIBITOR("Microbial Inhibitor"),
    NUTRITIONALCONCENTRATE("Nutritional Concentrate"),
    PERSONALCOMPUTER("Personal Computer"),
    PERSONALDOCUMENTS("Personal Documents"),
    PETRIFIEDFOSSIL("Petrified Fossil"),
    PUSH("Push"),
    PYROLYTICCATALYST("Pyrolytic Catalyst"),
    REFINEMENTPROCESSSAMPLE("Refinement Process Sample"),
    SHIPSCHEMATIC("Ship Schematic"),
    SUITSCHEMATIC("Suit Schematic"),
    SURVEILLANCEEQUIPMENT("Surveillance Equipment"),
    SYNTHETICGENOME("Synthetic Genome"),
    SYNTHETICPATHOGEN("Synthetic Pathogen"),
    TRUEFORMFOSSIL("True Form Fossil"),
    UNIVERSALTRANSLATOR("Universal Translator"),
    VEHICLESCHEMATIC("Vehicle Schematic"),
    WEAPONSCHEMATIC("Weapon Schematic"),
    UNKNOWN("UNKNOWN");

    String name;

    private Goods(String name) {
        this.name = name;
    }

    public static Goods forName(String name) {
        try {
            return Goods.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException ex) {
            return Goods.UNKNOWN;
        }
    }

    public String friendlyName() {
        return this.name;
    }
}
