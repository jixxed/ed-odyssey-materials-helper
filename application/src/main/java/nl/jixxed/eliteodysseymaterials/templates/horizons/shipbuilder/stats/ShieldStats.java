package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats;

import javafx.geometry.Orientation;
import javafx.scene.control.Separator;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.templates.Template;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
@Slf4j
public class ShieldStats extends Stats implements Template {
    private DestroyableLabel resistanceKinetic;
    private DestroyableLabel resistanceThermal;
    private DestroyableLabel resistanceExplosive;
    private DestroyableLabel resistanceCaustic;
    private DestroyableLabel strengthRaw;
    private DestroyableLabel strengthKinetic;
    private DestroyableLabel strengthThermal;
    private DestroyableLabel strengthExplosive;
    private DestroyableLabel strengthCaustic;
    public ShieldStats() {
        super();
        initComponents();
    }

    @Override
    public void initComponents() {
        this.getChildren().add(BoxBuilder.builder().withNodes(new GrowingRegion(), createTitle("ship.stats.shield"), new GrowingRegion()).buildHBox());
        this.getChildren().add(new Separator(Orientation.HORIZONTAL));

        this.resistanceKinetic = createValueLabel(String.format("%.2f", calculateResistanceKinetic()));
        this.resistanceThermal = createValueLabel(String.format("%.2f", calculateResistanceThermal()));
        this.resistanceExplosive = createValueLabel(String.format("%.2f", calculateResistanceExplosive()));
        this.resistanceCaustic = createValueLabel(String.format("%.2f", calculateResistanceCaustic()));
        this.strengthRaw = createValueLabel(String.format("%.2f", calculateStrengthRaw()));
        this.strengthKinetic = createValueLabel(String.format("%.2f", calculateStrengthKinetic()));
        this.strengthThermal = createValueLabel(String.format("%.2f", calculateStrengthThermal()));
        this.strengthExplosive = createValueLabel(String.format("%.2f", calculateStrengthExplosive()));
        this.strengthCaustic = createValueLabel(String.format("%.2f", calculateStrengthCaustic()));
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.shield.resistancekinetic"), new GrowingRegion(), this.resistanceKinetic).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.shield.resistancethermal"), new GrowingRegion(), this.resistanceThermal).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.shield.resistanceexplosive"), new GrowingRegion(), this.resistanceExplosive).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.shield.resistancecaustic"), new GrowingRegion(), this.resistanceCaustic).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.shield.strengthraw"), new GrowingRegion(), this.strengthRaw).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.shield.strengthkinetic"), new GrowingRegion(), this.strengthKinetic).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.shield.strengththermal"), new GrowingRegion(), this.strengthThermal).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.shield.strengthexplosive"), new GrowingRegion(), this.strengthExplosive).buildHBox());
        this.getChildren().add(BoxBuilder.builder().withNodes(createLabel("ship.stats.shield.strengthcaustic"), new GrowingRegion(), this.strengthCaustic).buildHBox());

    }
    public double calculateResistanceKinetic(){return 0d;}
    public double calculateResistanceThermal(){return 0d;}
    public double calculateResistanceExplosive(){return 0d;}
    public double calculateResistanceCaustic(){return 0d;}
    public double calculateStrengthRaw(){return 0d;}
    public double calculateStrengthKinetic(){return 0d;}
    public double calculateStrengthThermal(){return 0d;}
    public double calculateStrengthExplosive(){return 0d;}
    public double calculateStrengthCaustic(){return 0d;}

    @Override
    protected void update() {
        log.debug("update armour: " + this.getShip().isPresent());
        this.getShip().ifPresent(ship1 ->  log.debug("type: " + ship1.getShipType()));
        this.resistanceKinetic.setText(String.format("%.2f", calculateResistanceKinetic()));
        this.resistanceThermal.setText(String.format("%.2f", calculateResistanceThermal()));
        this.resistanceExplosive.setText(String.format("%.2f", calculateResistanceExplosive()));
        this.resistanceCaustic.setText(String.format("%.2f", calculateResistanceCaustic()));
        this.strengthRaw.setText(String.format("%.2f", calculateStrengthRaw()));
        this.strengthKinetic.setText(String.format("%.2f", calculateStrengthKinetic()));
        this.strengthThermal.setText(String.format("%.2f", calculateStrengthThermal()));
        this.strengthExplosive.setText(String.format("%.2f", calculateStrengthExplosive()));
        this.strengthCaustic.setText(String.format("%.2f", calculateStrengthCaustic()));
    }
}
