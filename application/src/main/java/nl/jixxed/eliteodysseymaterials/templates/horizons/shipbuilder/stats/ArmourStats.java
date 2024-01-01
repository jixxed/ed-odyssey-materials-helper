package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats;

import javafx.geometry.Orientation;
import javafx.scene.control.Separator;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.templates.Template;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
@Slf4j
public class ArmourStats extends Stats implements Template {
    private DestroyableLabel resistanceRaw;
    private DestroyableLabel resistanceKinetic;
    private DestroyableLabel resistanceThermal;
    private DestroyableLabel resistanceExplosive;
    private DestroyableLabel resistanceCaustic;
    private DestroyableLabel integrityRaw;
    private DestroyableLabel integrityKinetic;
    private DestroyableLabel integrityThermal;
    private DestroyableLabel integrityExplosive;
    private DestroyableLabel integrityCaustic;

    public ArmourStats() {
        super();
        initComponents();
    }

    @Override
    public void initComponents() {
        this.getChildren().add(BoxBuilder.builder().withNodes(new GrowingRegion(), createTitle("ship.stats.armour"), new GrowingRegion()).buildHBox());
        this.getChildren().add(new Separator(Orientation.HORIZONTAL));

        this.resistanceRaw = createValueLabel(String.format("%.2f", calculateResistanceRaw()));
        this.resistanceKinetic = createValueLabel(String.format("%.2f", calculateResistanceKinetic()));
        this.resistanceThermal = createValueLabel(String.format("%.2f", calculateResistanceThermal()));
        this.resistanceExplosive = createValueLabel(String.format("%.2f", calculateResistanceExplosive()));
        this.resistanceCaustic = createValueLabel(String.format("%.2f", calculateResistanceCaustic()));
        this.integrityRaw = createValueLabel(String.format("%.2f", calculateIntegrityRaw()));
        this.integrityKinetic = createValueLabel(String.format("%.2f", calculateIntegrityKinetic()));
        this.integrityThermal = createValueLabel(String.format("%.2f", calculateIntegrityThermal()));
        this.integrityExplosive = createValueLabel(String.format("%.2f", calculateIntegrityExplosive()));
        this.integrityCaustic = createValueLabel(String.format("%.2f", calculateIntegrityCaustic()));
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.armour.resistanceraw"), new GrowingRegion(), this.resistanceRaw).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.armour.resistancekinetic"), new GrowingRegion(), this.resistanceKinetic).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.armour.resistancethermal"), new GrowingRegion(), this.resistanceThermal).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.armour.resistanceexplosive"), new GrowingRegion(), this.resistanceExplosive).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.armour.resistancecaustic"), new GrowingRegion(), this.resistanceCaustic).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.armour.integrityraw"), new GrowingRegion(), this.integrityRaw).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.armour.integritykinetic"), new GrowingRegion(), this.integrityKinetic).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.armour.integritythermal"), new GrowingRegion(), this.integrityThermal).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.armour.integrityexplosive"), new GrowingRegion(), this.integrityExplosive).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.armour.integritycaustic"), new GrowingRegion(), this.integrityCaustic).buildHBox());
    }
    public double calculateResistanceRaw(){return 0d;}
    public double calculateResistanceKinetic(){return 0d;}
    public double calculateResistanceThermal(){return 0d;}
    public double calculateResistanceExplosive(){return 0d;}
    public double calculateResistanceCaustic(){return 0d;}
    public double calculateIntegrityRaw(){return 0d;}
    public double calculateIntegrityKinetic(){return 0d;}
    public double calculateIntegrityThermal(){return 0d;}
    public double calculateIntegrityExplosive(){return 0d;}
    public double calculateIntegrityCaustic(){return 0d;}
    @Override
    protected void update() {
        log.debug("update armour: " + this.getShip().isPresent());
        this.getShip().ifPresent(ship1 ->  log.debug("type: " + ship1.getShipType()));
        this.resistanceRaw.setText(String.format("%.2f", calculateResistanceRaw()));
        this.resistanceKinetic.setText(String.format("%.2f", calculateResistanceKinetic()));
        this.resistanceThermal.setText(String.format("%.2f", calculateResistanceThermal()));
        this.resistanceExplosive.setText(String.format("%.2f", calculateResistanceExplosive()));
        this.resistanceCaustic.setText(String.format("%.2f", calculateResistanceCaustic()));
        this.integrityRaw.setText(String.format("%.2f", calculateIntegrityRaw()));
        this.integrityKinetic.setText(String.format("%.2f", calculateIntegrityKinetic()));
        this.integrityThermal.setText(String.format("%.2f", calculateIntegrityThermal()));
        this.integrityExplosive.setText(String.format("%.2f", calculateIntegrityExplosive()));
        this.integrityCaustic.setText(String.format("%.2f", calculateIntegrityCaustic()));
    }
}
