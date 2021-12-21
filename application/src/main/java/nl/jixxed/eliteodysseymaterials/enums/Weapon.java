package nl.jixxed.eliteodysseymaterials.enums;

public enum Weapon implements Equipment {
    KINEMATIC_AR50, KINEMATIC_C44, KINEMATIC_L6, KINEMATIC_P15, TAKADA_APHELION, TAKADA_ECLIPSE, TAKADA_ZENITH, MANTICORE_EXECUTIONER, MANTICORE_OPPRESSOR, MANTICORE_SHOTGUN, MANTICORE_TORMENTOR;

    public boolean isKinetic() {
        return KINEMATIC_AR50.equals(this) || KINEMATIC_C44.equals(this) || KINEMATIC_L6.equals(this) || KINEMATIC_P15.equals(this);
    }

    public boolean isLaser() {
        return TAKADA_APHELION.equals(this) || TAKADA_ECLIPSE.equals(this) || TAKADA_ZENITH.equals(this);
    }

    public boolean isPlasma() {
        return MANTICORE_EXECUTIONER.equals(this) || MANTICORE_OPPRESSOR.equals(this) || MANTICORE_SHOTGUN.equals(this) || MANTICORE_TORMENTOR.equals(this);
    }

    public String getImage() {
        return "/images/weapon/" + name().toLowerCase() + ".png";
    }
}
