package nl.jixxed.eliteodysseymaterials.enums;

public enum Asset implements Material {
    AEROGEL("Aerogel", AssetType.CHEMICAL),
    CHEMICALCATALYST("Chemical Catalyst", AssetType.CHEMICAL),
    CHEMICALSUPERBASE("Chemical Superbase", AssetType.CHEMICAL),
    EPINEPHRINE("Epinephrine", AssetType.CHEMICAL),
    EPOXYADHESIVE("Epoxy Adhesive", AssetType.CHEMICAL),
    GRAPHENE("Graphene", AssetType.CHEMICAL),
    OXYGENICBACTERIA("Oxygenic Bacteria", AssetType.CHEMICAL),
    PHNEUTRALISER("PH Neutraliser", AssetType.CHEMICAL),
    RDX("RDX", AssetType.CHEMICAL),
    VISCOELASTICPOLYMER("Viscoelastic Polymer", AssetType.CHEMICAL),

    CIRCUITBOARD("Circuit Board", AssetType.CIRCUIT),
    CIRCUITSWITCH("Circuit Switch", AssetType.CIRCUIT),
    ELECTRICALFUSE("Electrical Fuse", AssetType.CIRCUIT),
    ELECTRICALWIRING("Electrical Wiring", AssetType.CIRCUIT),
    ELECTROMAGNET("Electromagnet", AssetType.CIRCUIT),
    IONBATTERY("Ion Battery", AssetType.CIRCUIT),
    METALCOIL("Metal Coil", AssetType.CIRCUIT),
    MICROELECTRODE("Microelectrode", AssetType.CIRCUIT),
    MICROSUPERCAPACITOR("Micro Supercapacitor", AssetType.CIRCUIT),
    MICROTRANSFORMER("Micro Transformer", AssetType.CIRCUIT),
    MOTOR("Motor", AssetType.CIRCUIT),
    OPTICALFIBRE("Optical Fibre", AssetType.CIRCUIT),

    CARBONFIBREPLATING("Carbon Fibre Plating", AssetType.TECH),
    ENCRYPTEDMEMORYCHIP("Encrypted Memory Chip", AssetType.TECH),
    MICROHYDRAULICS("Micro Hydraulics", AssetType.TECH),
    MICROTHRUSTERS("Micro Thrusters", AssetType.TECH),
    MEMORYCHIP("Memory Chip", AssetType.TECH),
    OPTICALLENS("Optical Lens", AssetType.TECH),
    SCRAMBLER("Scrambler", AssetType.TECH),
    TITANIUMPLATING("Titanium Plating", AssetType.TECH),
    TRANSMITTER("Transmitter", AssetType.TECH),
    TUNGSTENCARBIDE("Tungsten Carbide", AssetType.TECH),
    WEAPONCOMPONENT("Weapon Component", AssetType.TECH),

    UNKNOWN("UNKNOWN", AssetType.NONE);
    String name;
    AssetType type;

    Asset(final String name, final AssetType type) {
        this.name = name;
        this.type = type;
    }

    public static Asset forName(final String name) {
        try {
            return Asset.valueOf(name.toUpperCase());
        } catch (final IllegalArgumentException ex) {
            return Asset.UNKNOWN;
        }
    }

    @Override
    public String friendlyName() {
        return this.name;
    }

    public AssetType getType() {
        return this.type;
    }
}
