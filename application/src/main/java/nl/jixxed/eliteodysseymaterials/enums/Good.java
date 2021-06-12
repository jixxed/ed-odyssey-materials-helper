package nl.jixxed.eliteodysseymaterials.enums;

public enum Good implements Material {
    AGRICULTURALPROCESSSAMPLE("Agricultural Process Sample"),
    BIOCHEMICALAGENT("Biochemical Agent"),
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
    GENETICSAMPLE("Biological Sample"),
    GMEDS("G-Meds"),
    HEALTHCANISTER("Health Canister"),
    HEALTHMONITOR("Health Monitor"),
    HUSH("Hush"),
    INERTIACANISTER("Inertia Canister"),
    INFINITY("Infinity"),
    INORGANICCONTAMINANT("Inorganic Contaminant"),
    INSIGHT("InSight"),
    INSIGHTDATABANK("InSight Data Bank"),
    INSIGHTENTERTAINMENTSUITE("InSight Entertainment Suite"),
    IONISEDGAS("Ionised Gas"),
    LARGECAPACITYPOWERREGULATOR("Power Regulator"),
    LAZARUS("Lazarus"),
    MICROBIALINHIBITOR("Microbial Inhibitor"),
    MUTAGENICCATALYST("Mutagenic Catalyst"),
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

    Good(final String name) {
        this.name = name;
    }

    public static Good forName(final String name) {
        try {
            return Good.valueOf(name.toUpperCase());
        } catch (final IllegalArgumentException ex) {
            return Good.UNKNOWN;
        }
    }

    @Override
    public String friendlyName() {
        return this.name;
    }
}
