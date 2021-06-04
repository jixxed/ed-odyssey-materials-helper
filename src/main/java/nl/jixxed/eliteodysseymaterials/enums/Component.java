package nl.jixxed.eliteodysseymaterials.enums;

public enum Component {
    AEROGEL("Aerogel", ComponentType.CHEMICAL),
    CHEMICALCATALYST("Chemical Catalyst", ComponentType.CHEMICAL),
    CHEMICALSUPERBASE("Chemical Superbase", ComponentType.CHEMICAL),
    EPINEPHRINE("Epinephrine", ComponentType.CHEMICAL),
    EPOXYADHESIVE("Epoxy Adhesive", ComponentType.CHEMICAL),
    GRAPHENE("Graphene", ComponentType.CHEMICAL),
    OXYGENICBACTERIA("Oxygenic Bacteria", ComponentType.CHEMICAL),
    PHNEUTRALISER("PH Neutraliser", ComponentType.CHEMICAL),
    RDX("RDX", ComponentType.CHEMICAL),
    VISCOELASTICPOLYMER("Viscoelastic Polymer", ComponentType.CHEMICAL),

    CIRCUITBOARD("Circuit Board",ComponentType.CIRCUIT),
    CIRCUITSWITCH("Circuit Switch",ComponentType.CIRCUIT),
    ELECTRICALFUSE("Electrical Fuse",ComponentType.CIRCUIT),
    ELECTRICALWIRING("Electrical Wiring",ComponentType.CIRCUIT),
    ELECTROMAGNET("Electromagnet",ComponentType.CIRCUIT),
    IONBATTERY("Ion Battery",ComponentType.CIRCUIT),
    METALCOIL("Metal Coil",ComponentType.CIRCUIT),
    MICROELECTRODE("Microelectrode",ComponentType.CIRCUIT),
    MICROSUPERCAPACITOR("Micro Supercapacitor",ComponentType.CIRCUIT),
    MICROTRANSFORMER("Micro Transformer",ComponentType.CIRCUIT),
    MOTOR("Motor",ComponentType.CIRCUIT),
    OPTICALFIBRE("Optical Fibre",ComponentType.CIRCUIT),

    CARBONFIBREPLATING("Carbon Fibre Plating",ComponentType.TECH),
    ENCRYPTEDMEMORYCHIP("Encrypted Memory Chip",ComponentType.TECH),
    MICROHYDRAULICS("Micro Hydraulics",ComponentType.TECH),
    MICROTHRUSTERS("Micro Thrusters",ComponentType.TECH),
    MEMORYCHIP("Memory Chip",ComponentType.TECH),
    OPTICALLENS("Optical Lens",ComponentType.TECH),
    SCRAMBLER("Scrambler",ComponentType.TECH),
    TITANIUMPLATING("Titanium Plating",ComponentType.TECH),
    TRANSMITTER("Transmitter",ComponentType.TECH),
    TUNGSTENCARBIDE("Tungsten Carbide",ComponentType.TECH),
    WEAPONCOMPONENT("Weapon Component",ComponentType.TECH),

    UNKNOWN("UNKNOWN",ComponentType.NONE);
    String name;
    ComponentType type;
    private Component(String name, ComponentType type) {
        this.name = name;
        this.type = type;
    }

    public static Component forName(String name) {
        try {
            return Component.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException ex) {
            return Component.UNKNOWN;
        }
    }

    public String friendlyName() {
        return this.name;
    }

    public ComponentType getType() {
        return type;
    }
}
