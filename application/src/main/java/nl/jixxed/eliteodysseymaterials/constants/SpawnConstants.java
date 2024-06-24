package nl.jixxed.eliteodysseymaterials.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.enums.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static nl.jixxed.eliteodysseymaterials.enums.DataPortType.*;
import static nl.jixxed.eliteodysseymaterials.enums.HorizonsMaterialSpawnLocation.*;
import static nl.jixxed.eliteodysseymaterials.enums.PlanetSignalSourceType.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SpawnConstants {
    private static final Map<Data, List<DataPortType>> DATA_PORTS = new EnumMap<>(Data.class);

    static {
        DATA_PORTS.put(Data.ACCIDENTLOGS, List.of(AGR, CMD, EXT, HAB, IND, LAB, MED, PWR, SEC));
        DATA_PORTS.put(Data.AIRQUALITYREPORTS, List.of(CMD, HAB, IND, LAB, PWR, SEC));
        DATA_PORTS.put(Data.ATMOSPHERICDATA, List.of(CMD));
        DATA_PORTS.put(Data.AUDIOLOGS, List.of(CMD, MED, SEC));
        DATA_PORTS.put(Data.AXCOMBATLOGS, List.of(SEC));
        DATA_PORTS.put(Data.BALLISTICSDATA, List.of(CMD, SEC));
        DATA_PORTS.put(Data.BIOLOGICALWEAPONDATA, List.of());
        DATA_PORTS.put(Data.BIOMETRICDATA, List.of(LAB, MED, SEC));
        DATA_PORTS.put(Data.BLACKLISTDATA, List.of(SEC));
        DATA_PORTS.put(Data.BLOODTESTRESULTS, List.of(MED, SEC));
        DATA_PORTS.put(Data.CAMPAIGNPLANS, List.of(CMD, HAB, SEC));
        DATA_PORTS.put(Data.CATMEDIA, List.of(CMD, HAB));
        DATA_PORTS.put(Data.CENSUSDATA, List.of(CMD, HAB, SEC));
        DATA_PORTS.put(Data.CHEMICALEXPERIMENTDATA, List.of(IND, LAB, MED));
        DATA_PORTS.put(Data.CHEMICALFORMULAE, List.of(IND));
        DATA_PORTS.put(Data.CHEMICALINVENTORY, List.of(IND, LAB, MED));
        DATA_PORTS.put(Data.CHEMICALPATENTS, List.of(CMD, IND, LAB, MED));
        DATA_PORTS.put(Data.CHEMICALWEAPONDATA, List.of(SEC));
        DATA_PORTS.put(Data.CLASSICENTERTAINMENT, List.of(CMD, HAB));
        DATA_PORTS.put(Data.COCKTAILRECIPES, List.of(HAB));
        DATA_PORTS.put(Data.COMBATANTPERFORMANCE, List.of(HAB, SEC));
        DATA_PORTS.put(Data.COMBATTRAININGMATERIAL, List.of(SEC));
        DATA_PORTS.put(Data.CONFLICTHISTORY, List.of(SEC));
        DATA_PORTS.put(Data.CRIMINALRECORDS, List.of(SEC));
        DATA_PORTS.put(Data.CROPYIELDANALYSIS, List.of());
        DATA_PORTS.put(Data.CULINARYRECIPES, List.of(HAB));
        DATA_PORTS.put(Data.DIGITALDESIGNS, List.of(CMD, HAB, LAB));
        DATA_PORTS.put(Data.DUTYROTA, List.of(CMD, HAB, IND, LAB, MED, PWR, SEC));
        DATA_PORTS.put(Data.EMPLOYEEDIRECTORY, List.of(CMD, HAB, SEC));
        DATA_PORTS.put(Data.EMPLOYEEEXPENSES, List.of(CMD, HAB));
        DATA_PORTS.put(Data.EMPLOYEEGENETICDATA, List.of(LAB, MED, SEC));
        DATA_PORTS.put(Data.EMPLOYMENTHISTORY, List.of(CMD, HAB));
        DATA_PORTS.put(Data.ENHANCEDINTERROGATIONRECORDINGS, List.of());
        DATA_PORTS.put(Data.ESPIONAGEMATERIAL, List.of(CMD));
        DATA_PORTS.put(Data.EVACUATIONPROTOCOLS, List.of(EXT, HAB, LAB, MED, PWR, SEC));
        DATA_PORTS.put(Data.EXPLORATIONJOURNALS, List.of(CMD, HAB, IND));
        DATA_PORTS.put(Data.EXTRACTIONYIELDDATA, List.of(EXT));
        DATA_PORTS.put(Data.FACTIONASSOCIATES, List.of(CMD, HAB));
        DATA_PORTS.put(Data.FACTIONDONATORLIST, List.of(CMD, HAB, SEC));
        DATA_PORTS.put(Data.FACTIONNEWS, List.of(CMD, HAB, IND, SEC));
        DATA_PORTS.put(Data.FINANCIALPROJECTIONS, List.of(CMD, EXT, HAB, IND));
        DATA_PORTS.put(Data.FLEETREGISTRY, List.of(SEC));
        DATA_PORTS.put(Data.GENESEQUENCINGDATA, List.of(LAB, MED));
        DATA_PORTS.put(Data.GENETICRESEARCH, List.of(MED));
        DATA_PORTS.put(Data.GEOLOGICALDATA, List.of(IND, LAB));
        DATA_PORTS.put(Data.HYDROPONICDATA, List.of(IND, LAB));
        DATA_PORTS.put(Data.INCIDENTLOGS, List.of(CMD, SEC));
        DATA_PORTS.put(Data.INFLUENCEPROJECTIONS, List.of(CMD, HAB));
        DATA_PORTS.put(Data.INTERNALCORRESPONDENCE, List.of(CMD, HAB, SEC));
        DATA_PORTS.put(Data.INTERROGATIONRECORDINGS, List.of(SEC));
        DATA_PORTS.put(Data.INTERVIEWRECORDINGS, List.of(CMD, MED, SEC));
        DATA_PORTS.put(Data.JOBAPPLICATIONS, List.of(CMD, HAB));
        DATA_PORTS.put(Data.KOMPROMAT, List.of());
        DATA_PORTS.put(Data.LITERARYFICTION, List.of(HAB));
        DATA_PORTS.put(Data.MAINTENANCELOGS, List.of(CMD, EXT, IND, LAB, MED, PWR));
        DATA_PORTS.put(Data.MANUFACTURINGINSTRUCTIONS, List.of(CMD, IND, LAB));
        DATA_PORTS.put(Data.MEDICALRECORDS, List.of(MED, SEC));
        DATA_PORTS.put(Data.MEDICALTRIALRECORDS, List.of(LAB));
        DATA_PORTS.put(Data.MEETINGMINUTES, List.of(CMD, HAB, IND, LAB));
        DATA_PORTS.put(Data.MINERALSURVEY, List.of(IND, LAB));
        DATA_PORTS.put(Data.MININGANALYTICS, List.of(EXT));
        DATA_PORTS.put(Data.MULTIMEDIAENTERTAINMENT, List.of(CMD, HAB));
        DATA_PORTS.put(Data.NETWORKACCESSHISTORY, List.of(CMD, HAB, SEC));
        DATA_PORTS.put(Data.NETWORKSECURITYPROTOCOLS, List.of(SEC));
        DATA_PORTS.put(Data.NEXTOFKINRECORDS, List.of(CMD, HAB, MED));
        DATA_PORTS.put(Data.NOCDATA, List.of(SEC));
        DATA_PORTS.put(Data.OPERATIONALMANUAL, List.of(IND, LAB, MED));
        DATA_PORTS.put(Data.OPINIONPOLLS, List.of(CMD, HAB));
        DATA_PORTS.put(Data.PATIENTHISTORY, List.of(IND, LAB, MED));
        DATA_PORTS.put(Data.PATROLROUTES, List.of(CMD, SEC));
        DATA_PORTS.put(Data.PAYROLLINFORMATION, List.of(CMD, HAB));
        DATA_PORTS.put(Data.PERSONALLOGS, List.of(HAB));
        DATA_PORTS.put(Data.PHARMACEUTICALPATENTS, List.of(CMD, MED));
        DATA_PORTS.put(Data.PHOTOALBUMS, List.of(HAB));
        DATA_PORTS.put(Data.PLANTGROWTHCHARTS, List.of(IND, LAB));
        DATA_PORTS.put(Data.POLITICALAFFILIATIONS, List.of(CMD, HAB));
        DATA_PORTS.put(Data.PRISONERLOGS, List.of(SEC));
        DATA_PORTS.put(Data.PRODUCTIONREPORTS, List.of(IND, LAB));
        DATA_PORTS.put(Data.PRODUCTIONSCHEDULE, List.of(IND, LAB));
        DATA_PORTS.put(Data.PROPAGANDA, List.of(CMD, HAB, SEC));
        DATA_PORTS.put(Data.PURCHASERECORDS, List.of(CMD, HAB, IND, MED));
        DATA_PORTS.put(Data.PURCHASEREQUESTS, List.of(CMD, EXT, HAB, IND, LAB, MED));
        DATA_PORTS.put(Data.RADIOACTIVITYDATA, List.of(CMD, LAB, MED, PWR));
        DATA_PORTS.put(Data.REACTOROUTPUTREVIEW, List.of(CMD, IND, LAB, PWR));
        DATA_PORTS.put(Data.RECYCLINGLOGS, List.of(CMD, HAB));
        DATA_PORTS.put(Data.RESIDENTIALDIRECTORY, List.of(CMD, HAB, MED, SEC));
        DATA_PORTS.put(Data.RISKASSESSMENTS, List.of(CMD, IND, LAB, SEC));
        DATA_PORTS.put(Data.SALESRECORDS, List.of(CMD, EXT, HAB));
        DATA_PORTS.put(Data.SECURITYEXPENSES, List.of(CMD, SEC));
        DATA_PORTS.put(Data.SEEDGENEAOLOGY, List.of(LAB));
        DATA_PORTS.put(Data.SETTLEMENTASSAULTPLANS, List.of(SEC));
        DATA_PORTS.put(Data.SETTLEMENTDEFENCEPLANS, List.of(SEC));
        DATA_PORTS.put(Data.SHAREHOLDERINFORMATION, List.of(CMD, HAB));
        DATA_PORTS.put(Data.SLUSHFUNDLOGS, List.of());
        DATA_PORTS.put(Data.SMEARCAMPAIGNPLANS, List.of());
        DATA_PORTS.put(Data.SPECTRALANALYSISDATA, List.of(IND));
        DATA_PORTS.put(Data.SPYWARE, List.of());
        DATA_PORTS.put(Data.STELLARACTIVITYLOGS, List.of(IND, LAB));
        DATA_PORTS.put(Data.SURVEILLEANCELOGS, List.of());
        DATA_PORTS.put(Data.TACTICALPLANS, List.of(SEC));
        DATA_PORTS.put(Data.TAXRECORDS, List.of(HAB, SEC));
        DATA_PORTS.put(Data.TOPOGRAPHICALSURVEYS, List.of(LAB));
        DATA_PORTS.put(Data.TRAVELPERMITS, List.of(CMD, HAB, SEC));
        DATA_PORTS.put(Data.TROOPDEPLOYMENTRECORDS, List.of(SEC));
        DATA_PORTS.put(Data.UNIONMEMBERSHIP, List.of(CMD, HAB, IND, PWR));
        DATA_PORTS.put(Data.VACCINATIONRECORDS, List.of(CMD, IND, LAB, MED));
        DATA_PORTS.put(Data.VACCINERESEARCH, List.of(IND, MED));
        DATA_PORTS.put(Data.VIPSECURITYDETAIL, List.of(SEC));
        DATA_PORTS.put(Data.VIROLOGYDATA, List.of(IND, LAB, MED));
        DATA_PORTS.put(Data.VIRUS, List.of());
        DATA_PORTS.put(Data.VISITORREGISTER, List.of(CMD, HAB, MED, SEC));
        DATA_PORTS.put(Data.WEAPONINVENTORY, List.of(CMD, SEC));
        DATA_PORTS.put(Data.WEAPONTESTDATA, List.of(CMD, SEC));
        DATA_PORTS.put(Data.XENODEFENCEPROTOCOLS, List.of(SEC));
    }

    private static final Map<Data, List<PlanetSignalSourceType>> DATA_PLANET_PORTS = Map.ofEntries(
            Map.entry(Data.ATMOSPHERICDATA, List.of(LARGE_SAT)),
            Map.entry(Data.BALLISTICSDATA, List.of(LARGE_SAT, SKIMMER)),
            Map.entry(Data.BLACKLISTDATA, List.of(SMALL_SAT, SKIMMER)),
            Map.entry(Data.CENSUSDATA, List.of(SMALL_SAT)),
            Map.entry(Data.CONFLICTHISTORY, List.of(SKIMMER)),
            Map.entry(Data.FACTIONNEWS, List.of(SMALL_SAT)),
            Map.entry(Data.INTERNALCORRESPONDENCE, List.of(SMALL_SAT)),
            Map.entry(Data.MAINTENANCELOGS, List.of(SKIMMER)),
            Map.entry(Data.MANUFACTURINGINSTRUCTIONS, List.of(SMALL_SAT, LARGE_SAT, SKIMMER)),
            Map.entry(Data.MINERALSURVEY, List.of(LARGE_SAT)),
            Map.entry(Data.NOCDATA, List.of(SMALL_SAT)),
            Map.entry(Data.OPERATIONALMANUAL, List.of(SMALL_SAT, LARGE_SAT, SKIMMER)),
            Map.entry(Data.PATROLROUTES, List.of(SKIMMER)),
            Map.entry(Data.PROPAGANDA, List.of(SMALL_SAT)),
            Map.entry(Data.RADIOACTIVITYDATA, List.of(LARGE_SAT)),
            Map.entry(Data.SETTLEMENTDEFENCEPLANS, List.of(SKIMMER)),
            Map.entry(Data.STELLARACTIVITYLOGS, List.of(LARGE_SAT)),
            Map.entry(Data.SURVEILLEANCELOGS, List.of(SMALL_SAT, SKIMMER)),
            Map.entry(Data.TOPOGRAPHICALSURVEYS, List.of(LARGE_SAT))
    );

    private static final Map<Data, List<AlternateLocationType>> DATA_OTHER = Map.ofEntries(
            Map.entry(Data.SPYWARE, List.of(AlternateLocationType.MISSION_OBJECTIVE)),
            Map.entry(Data.VIRUS, List.of(AlternateLocationType.MISSION_OBJECTIVE)),
            Map.entry(Data.AIRQUALITYREPORTS, List.of(AlternateLocationType.MISSION_REWARD)),
            Map.entry(Data.ATMOSPHERICDATA, List.of(AlternateLocationType.MISSION_REWARD)),
            Map.entry(Data.AUDIOLOGS, List.of(AlternateLocationType.MISSION_REWARD)),
            Map.entry(Data.BALLISTICSDATA, List.of(AlternateLocationType.MISSION_REWARD)),
            Map.entry(Data.BIOMETRICDATA, List.of(AlternateLocationType.MISSION_REWARD)),
            Map.entry(Data.BLOODTESTRESULTS, List.of(AlternateLocationType.MISSION_REWARD)),
            Map.entry(Data.CHEMICALEXPERIMENTDATA, List.of(AlternateLocationType.MISSION_REWARD)),
            Map.entry(Data.CHEMICALFORMULAE, List.of(AlternateLocationType.MISSION_REWARD)),
            Map.entry(Data.CHEMICALINVENTORY, List.of(AlternateLocationType.MISSION_REWARD)),
            Map.entry(Data.CHEMICALPATENTS, List.of(AlternateLocationType.MISSION_REWARD)),
            Map.entry(Data.COMBATANTPERFORMANCE, List.of(AlternateLocationType.MISSION_REWARD)),
            Map.entry(Data.COMBATTRAININGMATERIAL, List.of(AlternateLocationType.MISSION_REWARD)),
            Map.entry(Data.DIGITALDESIGNS, List.of(AlternateLocationType.MISSION_REWARD)),
            Map.entry(Data.EVACUATIONPROTOCOLS, List.of(AlternateLocationType.MISSION_REWARD)),
            Map.entry(Data.EXTRACTIONYIELDDATA, List.of(AlternateLocationType.MISSION_REWARD)),
            Map.entry(Data.GENESEQUENCINGDATA, List.of(AlternateLocationType.MISSION_REWARD)),
            Map.entry(Data.GENETICRESEARCH, List.of(AlternateLocationType.MISSION_REWARD)),
            Map.entry(Data.MAINTENANCELOGS, List.of(AlternateLocationType.MISSION_REWARD)),
            Map.entry(Data.MANUFACTURINGINSTRUCTIONS, List.of(AlternateLocationType.MISSION_REWARD)),
            Map.entry(Data.MEDICALRECORDS, List.of(AlternateLocationType.MISSION_REWARD)),
            Map.entry(Data.MEDICALTRIALRECORDS, List.of(AlternateLocationType.MISSION_REWARD)),
            Map.entry(Data.MINERALSURVEY, List.of(AlternateLocationType.MISSION_REWARD)),
            Map.entry(Data.MININGANALYTICS, List.of(AlternateLocationType.MISSION_REWARD)),
            Map.entry(Data.NOCDATA, List.of(AlternateLocationType.MISSION_REWARD)),
            Map.entry(Data.OPERATIONALMANUAL, List.of(AlternateLocationType.MISSION_REWARD)),
            Map.entry(Data.PATROLROUTES, List.of(AlternateLocationType.MISSION_REWARD)),
            Map.entry(Data.PHARMACEUTICALPATENTS, List.of(AlternateLocationType.MISSION_REWARD)),
            Map.entry(Data.PRODUCTIONREPORTS, List.of(AlternateLocationType.MISSION_REWARD)),
            Map.entry(Data.PRODUCTIONSCHEDULE, List.of(AlternateLocationType.MISSION_REWARD)),
            Map.entry(Data.RADIOACTIVITYDATA, List.of(AlternateLocationType.MISSION_REWARD)),
            Map.entry(Data.REACTOROUTPUTREVIEW, List.of(AlternateLocationType.MISSION_REWARD)),
            Map.entry(Data.RECYCLINGLOGS, List.of(AlternateLocationType.MISSION_REWARD)),
            Map.entry(Data.RISKASSESSMENTS, List.of(AlternateLocationType.MISSION_REWARD)),
            Map.entry(Data.SECURITYEXPENSES, List.of(AlternateLocationType.MISSION_REWARD)),
            Map.entry(Data.SETTLEMENTASSAULTPLANS, List.of(AlternateLocationType.MISSION_REWARD)),
            Map.entry(Data.SPECTRALANALYSISDATA, List.of(AlternateLocationType.MISSION_REWARD)),
            Map.entry(Data.STELLARACTIVITYLOGS, List.of(AlternateLocationType.MISSION_REWARD)),
            Map.entry(Data.SURVEILLEANCELOGS, List.of(AlternateLocationType.MISSION_REWARD)),
            Map.entry(Data.TACTICALPLANS, List.of(AlternateLocationType.MISSION_REWARD)),
            Map.entry(Data.TOPOGRAPHICALSURVEYS, List.of(AlternateLocationType.MISSION_REWARD)),
            Map.entry(Data.TROOPDEPLOYMENTRECORDS, List.of(AlternateLocationType.MISSION_REWARD)),
            Map.entry(Data.WEAPONINVENTORY, List.of(AlternateLocationType.MISSION_REWARD)),
            Map.entry(Data.WEAPONTESTDATA, List.of(AlternateLocationType.MISSION_REWARD))
    );
    private static final Map<Asset, List<LockerType>> ASSET_LOCKERS = Map.ofEntries(
            Map.entry(Asset.AEROGEL, List.of(LockerType.LAB, LockerType.PROC)),
            Map.entry(Asset.CHEMICALCATALYST, List.of(LockerType.LAB, LockerType.MED)),
            Map.entry(Asset.CHEMICALSUPERBASE, List.of(LockerType.LAB, LockerType.MED)),
            Map.entry(Asset.EPINEPHRINE, List.of(LockerType.LAB, LockerType.MED)),
            Map.entry(Asset.EPOXYADHESIVE, List.of(LockerType.LAB, LockerType.MED)),
            Map.entry(Asset.GRAPHENE, List.of(LockerType.AGR, LockerType.HAB, LockerType.LAB, LockerType.SEC)),
            Map.entry(Asset.OXYGENICBACTERIA, List.of(LockerType.LAB, LockerType.MED)),
            Map.entry(Asset.PHNEUTRALISER, List.of(LockerType.LAB, LockerType.MED)),
            Map.entry(Asset.RDX, List.of(LockerType.LAB, LockerType.MED)),
            Map.entry(Asset.VISCOELASTICPOLYMER, List.of(LockerType.LAB, LockerType.MED)),
            Map.entry(Asset.CIRCUITBOARD, List.of(LockerType.AGR, LockerType.CMD, LockerType.EXT, LockerType.HAB, LockerType.IND, LockerType.LAB, LockerType.MED, LockerType.PWR, LockerType.STO, LockerType.SEC, LockerType.OUT)),
            Map.entry(Asset.CIRCUITSWITCH, List.of(LockerType.DORM, LockerType.LAB, LockerType.OPR, LockerType.PROC, LockerType.SEC, LockerType.STO)),
            Map.entry(Asset.ELECTRICALFUSE, List.of(LockerType.AGR, LockerType.CMD, LockerType.EXT, LockerType.HAB, LockerType.IND, LockerType.LAB, LockerType.MED, LockerType.PWR, LockerType.STO, LockerType.SEC, LockerType.OUT)),
            Map.entry(Asset.ELECTRICALWIRING, List.of(LockerType.AGR, LockerType.CMD, LockerType.EXT, LockerType.HAB, LockerType.IND, LockerType.LAB, LockerType.MED, LockerType.PWR, LockerType.STO, LockerType.SEC, LockerType.OUT)),
            Map.entry(Asset.ELECTROMAGNET, List.of(LockerType.AGR, LockerType.CMD, LockerType.EXT, LockerType.HAB, LockerType.IND, LockerType.LAB, LockerType.MED, LockerType.PWR, LockerType.STO, LockerType.SEC, LockerType.OUT)),
            Map.entry(Asset.IONBATTERY, List.of(LockerType.AGR, LockerType.CMD, LockerType.EXT, LockerType.HAB, LockerType.IND, LockerType.LAB, LockerType.MED, LockerType.PWR, LockerType.STO, LockerType.SEC, LockerType.OUT)),
            Map.entry(Asset.METALCOIL, List.of(LockerType.AGR, LockerType.CMD, LockerType.EXT, LockerType.HAB, LockerType.IND, LockerType.LAB, LockerType.MED, LockerType.PWR, LockerType.STO, LockerType.SEC, LockerType.OUT)),
            Map.entry(Asset.MICROELECTRODE, List.of(LockerType.AGR, LockerType.CMD, LockerType.EXT, LockerType.HAB, LockerType.IND, LockerType.LAB, LockerType.MED, LockerType.PWR, LockerType.STO, LockerType.SEC, LockerType.OUT)),
            Map.entry(Asset.MICROSUPERCAPACITOR, List.of(LockerType.AGR, LockerType.CMD, LockerType.EXT, LockerType.HAB, LockerType.IND, LockerType.LAB, LockerType.MED, LockerType.PWR, LockerType.STO, LockerType.SEC, LockerType.OUT)),
            Map.entry(Asset.MICROTRANSFORMER, List.of(LockerType.AGR, LockerType.CMD, LockerType.EXT, LockerType.HAB, LockerType.IND, LockerType.LAB, LockerType.MED, LockerType.PWR, LockerType.STO, LockerType.SEC, LockerType.OUT)),
            Map.entry(Asset.MOTOR, List.of(LockerType.AGR, LockerType.CMD, LockerType.EXT, LockerType.HAB, LockerType.IND, LockerType.LAB, LockerType.MED, LockerType.PWR, LockerType.STO, LockerType.SEC, LockerType.OUT)),
            Map.entry(Asset.OPTICALFIBRE, List.of(LockerType.AGR, LockerType.CMD, LockerType.EXT, LockerType.HAB, LockerType.IND, LockerType.LAB, LockerType.MED, LockerType.PWR, LockerType.STO, LockerType.SEC, LockerType.OUT)),
            Map.entry(Asset.CARBONFIBREPLATING, List.of(LockerType.AGR, LockerType.CMD, LockerType.EXT, LockerType.HAB, LockerType.IND, LockerType.LAB, LockerType.MED, LockerType.PWR, LockerType.STO, LockerType.SEC, LockerType.OUT)),
            Map.entry(Asset.ENCRYPTEDMEMORYCHIP, List.of(LockerType.AGR, LockerType.CMD, LockerType.EXT, LockerType.HAB, LockerType.IND, LockerType.LAB, LockerType.MED, LockerType.PWR, LockerType.STO, LockerType.SEC, LockerType.OUT)),
            Map.entry(Asset.MICROHYDRAULICS, List.of(LockerType.AGR, LockerType.CMD, LockerType.EXT, LockerType.HAB, LockerType.IND, LockerType.LAB, LockerType.MED, LockerType.PWR, LockerType.STO, LockerType.SEC, LockerType.OUT)),
            Map.entry(Asset.MICROTHRUSTERS, List.of(LockerType.AGR, LockerType.CMD, LockerType.EXT, LockerType.HAB, LockerType.IND, LockerType.LAB, LockerType.MED, LockerType.PWR, LockerType.STO, LockerType.SEC, LockerType.OUT)),
            Map.entry(Asset.MEMORYCHIP, List.of(LockerType.AGR, LockerType.CMD, LockerType.EXT, LockerType.HAB, LockerType.IND, LockerType.LAB, LockerType.MED, LockerType.PWR, LockerType.STO, LockerType.SEC, LockerType.OUT)),
            Map.entry(Asset.OPTICALLENS, List.of(LockerType.AGR, LockerType.CMD, LockerType.EXT, LockerType.HAB, LockerType.IND, LockerType.LAB, LockerType.MED, LockerType.PWR, LockerType.STO, LockerType.SEC, LockerType.OUT)),
            Map.entry(Asset.SCRAMBLER, List.of(LockerType.AGR, LockerType.CMD, LockerType.EXT, LockerType.HAB, LockerType.IND, LockerType.LAB, LockerType.MED, LockerType.PWR, LockerType.STO, LockerType.SEC, LockerType.OUT)),
            Map.entry(Asset.TITANIUMPLATING, List.of(LockerType.PROC, LockerType.PWR)),
            Map.entry(Asset.TRANSMITTER, List.of(LockerType.AGR, LockerType.CMD, LockerType.EXT, LockerType.HAB, LockerType.IND, LockerType.LAB, LockerType.MED, LockerType.PWR, LockerType.STO, LockerType.SEC, LockerType.OUT)),
            Map.entry(Asset.TUNGSTENCARBIDE, List.of(LockerType.AGR, LockerType.CMD, LockerType.EXT, LockerType.HAB, LockerType.IND, LockerType.LAB, LockerType.MED, LockerType.PWR, LockerType.STO, LockerType.SEC, LockerType.OUT)),
            Map.entry(Asset.WEAPONCOMPONENT, List.of(LockerType.AGR, LockerType.CMD, LockerType.EXT, LockerType.HAB, LockerType.IND, LockerType.LAB, LockerType.MED, LockerType.PWR, LockerType.STO, LockerType.SEC, LockerType.OUT))
    );

    private static final Map<Asset, List<PlanetSignalSourceType>> ASSET_PLANET = Map.ofEntries(
            Map.entry(Asset.CIRCUITBOARD, List.of(LARGE_SAT, SMALL_SAT, CRASHED, SRV)),
            Map.entry(Asset.CIRCUITSWITCH, List.of(LARGE_SAT, SMALL_SAT, CRASHED, SRV)),
            Map.entry(Asset.ELECTRICALFUSE, List.of(LARGE_SAT, SMALL_SAT, CRASHED, SRV)),
            Map.entry(Asset.ELECTRICALWIRING, List.of(LARGE_SAT, SMALL_SAT, CRASHED, SRV)),
            Map.entry(Asset.ELECTROMAGNET, List.of(LARGE_SAT, SMALL_SAT, CRASHED, SRV)),
            Map.entry(Asset.IONBATTERY, List.of(LARGE_SAT, SMALL_SAT, CRASHED, SRV)),
            Map.entry(Asset.METALCOIL, List.of(LARGE_SAT, SMALL_SAT, CRASHED, SRV)),
            Map.entry(Asset.MICROELECTRODE, List.of(LARGE_SAT, SMALL_SAT, CRASHED, SRV)),
            Map.entry(Asset.MICROSUPERCAPACITOR, List.of(LARGE_SAT, SMALL_SAT, CRASHED, SRV)),
            Map.entry(Asset.MICROTRANSFORMER, List.of(LARGE_SAT, SMALL_SAT, CRASHED, SRV)),
            Map.entry(Asset.MOTOR, List.of(LARGE_SAT, SMALL_SAT, CRASHED, SRV)),
            Map.entry(Asset.OPTICALFIBRE, List.of(LARGE_SAT, SMALL_SAT, CRASHED, SRV))
    );
    private static final Map<Good, List<LockerType>> GOOD_LOCKERS = Map.ofEntries(
            Map.entry(Good.AGRICULTURALPROCESSSAMPLE, List.of(LockerType.AGR)),
            Map.entry(Good.BIOCHEMICALAGENT, List.of(LockerType.IND)),
            Map.entry(Good.BIOMECHANICALCOMPONENT, List.of()),
            Map.entry(Good.BUILDINGSCHEMATIC, List.of(LockerType.AGR, LockerType.STO)),
            Map.entry(Good.CALIFORNIUM, List.of(LockerType.CMD, LockerType.PWR)),
            Map.entry(Good.CASTFOSSIL, List.of(LockerType.EXT, LockerType.STO)),
            Map.entry(Good.CHEMICALPROCESSSAMPLE, List.of(LockerType.EXT, LockerType.STO)),
            Map.entry(Good.CHEMICALSAMPLE, List.of(LockerType.IND)),
            Map.entry(Good.COMPACTLIBRARY, List.of(LockerType.IND, LockerType.MED, LockerType.PWR)),
            Map.entry(Good.COMPRESSIONLIQUEFIEDGAS, List.of(LockerType.CMD, LockerType.HAB, LockerType.LAB, LockerType.MED)),
            Map.entry(Good.DEEPMANTLESAMPLE, List.of(LockerType.IND, LockerType.LAB, LockerType.PWR)),
            Map.entry(Good.DEGRADEDPOWERREGULATOR, List.of(LockerType.EXT, LockerType.IND, LockerType.LAB, LockerType.PWR, LockerType.STO)),
            Map.entry(Good.GENETICREPAIRMEDS, List.of(LockerType.LAB, LockerType.PWR)),
            Map.entry(Good.GENETICSAMPLE, List.of(LockerType.CMD, LockerType.EXT, LockerType.HAB, LockerType.IND, LockerType.LAB, LockerType.MED, LockerType.SEC)),
            Map.entry(Good.GMEDS, List.of(LockerType.EXT, LockerType.IND, LockerType.MED)),
            Map.entry(Good.HEALTHMONITOR, List.of(LockerType.CMD, LockerType.HAB, LockerType.MED)),
            Map.entry(Good.HUSH, List.of()),
            Map.entry(Good.INERTIACANISTER, List.of(LockerType.AGR, LockerType.EXT, LockerType.IND, LockerType.LAB, LockerType.MED, LockerType.PWR, LockerType.STO)),
            Map.entry(Good.INFINITY, List.of(LockerType.CMD, LockerType.HAB)),
            Map.entry(Good.INORGANICCONTAMINANT, List.of(LockerType.IND)),
            Map.entry(Good.INSIGHT, List.of(LockerType.CMD, LockerType.HAB, LockerType.LAB, LockerType.SEC)),
            Map.entry(Good.INSIGHTDATABANK, List.of(LockerType.CMD, LockerType.HAB, LockerType.LAB, LockerType.SEC)),
            Map.entry(Good.INSIGHTENTERTAINMENTSUITE, List.of(LockerType.CMD, LockerType.HAB)),
            Map.entry(Good.IONISEDGAS, List.of(LockerType.EXT, LockerType.IND, LockerType.LAB, LockerType.PWR)),
            Map.entry(Good.LARGECAPACITYPOWERREGULATOR, List.of()),
            Map.entry(Good.LAZARUS, List.of(LockerType.AGR, LockerType.IND, LockerType.LAB, LockerType.MED)),
            Map.entry(Good.MICROBIALINHIBITOR, List.of()),
            Map.entry(Good.MUTAGENICCATALYST, List.of(LockerType.AGR, LockerType.CMD, LockerType.HAB, LockerType.MED, LockerType.STO)),
            Map.entry(Good.NUTRITIONALCONCENTRATE, List.of(LockerType.CMD, LockerType.HAB, LockerType.LAB, LockerType.MED, LockerType.SEC)),
            Map.entry(Good.PERSONALCOMPUTER, List.of(LockerType.CMD, LockerType.HAB, LockerType.MED, LockerType.SEC)),
            Map.entry(Good.PERSONALDOCUMENTS, List.of(LockerType.STO)),
            Map.entry(Good.PETRIFIEDFOSSIL, List.of(LockerType.PWR)),
            Map.entry(Good.PUSH, List.of()),
            Map.entry(Good.PYROLYTICCATALYST, List.of(LockerType.IND)),
            Map.entry(Good.REFINEMENTPROCESSSAMPLE, List.of(LockerType.EXT)),
            Map.entry(Good.SHIPSCHEMATIC, List.of(LockerType.CMD)),
            Map.entry(Good.SUITSCHEMATIC, List.of(LockerType.CMD, LockerType.PWR)),
            Map.entry(Good.SURVEILLANCEEQUIPMENT, List.of(LockerType.CMD, LockerType.MED, LockerType.SEC)),
            Map.entry(Good.SYNTHETICGENOME, List.of(LockerType.AGR, LockerType.STO)),
            Map.entry(Good.SYNTHETICPATHOGEN, List.of()),
            Map.entry(Good.TRUEFORMFOSSIL, List.of(LockerType.EXT, LockerType.STO)),
            Map.entry(Good.UNIVERSALTRANSLATOR, List.of(LockerType.CMD, LockerType.HAB, LockerType.SEC)),
            Map.entry(Good.VEHICLESCHEMATIC, List.of(LockerType.CMD)),
            Map.entry(Good.WEAPONSCHEMATIC, List.of(LockerType.CMD, LockerType.PWR))
    );

    private static final Map<Good, List<DeviceType>> GOOD_DEVICES = Map.ofEntries(
            Map.entry(Good.LARGECAPACITYPOWERREGULATOR, List.of(DeviceType.PWR, DeviceType.SRV))
    );
    private static final Map<Good, List<AlternateLocationType>> GOOD_OTHER = Map.ofEntries(
            Map.entry(Good.COMPRESSIONLIQUEFIEDGAS, List.of(AlternateLocationType.MISSION_REWARD)),
            Map.entry(Good.GMEDS, List.of(AlternateLocationType.MISSION_REWARD)),
            Map.entry(Good.HEALTHMONITOR, List.of(AlternateLocationType.MISSION_REWARD)),
            Map.entry(Good.IONISEDGAS, List.of(AlternateLocationType.MISSION_REWARD)),
            Map.entry(Good.LARGECAPACITYPOWERREGULATOR, List.of(AlternateLocationType.MISSION_REWARD)),
            Map.entry(Good.SUITSCHEMATIC, List.of(AlternateLocationType.MISSION_REWARD)),
            Map.entry(Good.SURVEILLANCEEQUIPMENT, List.of(AlternateLocationType.MISSION_REWARD)),
            Map.entry(Good.WEAPONSCHEMATIC, List.of(AlternateLocationType.MISSION_REWARD))
    );
    private static final Map<Good, List<PlanetSignalSourceType>> GOOD_PLANET = Map.ofEntries(
            Map.entry(Good.LARGECAPACITYPOWERREGULATOR, List.of(LARGE_SAT)),
            Map.entry(Good.PUSH, List.of(SKIMMER, SRV)),
            Map.entry(Good.HUSH, List.of(SKIMMER, SRV))
    );
    public static final Map<Data, Map<DataPortType, Double>> DATA_SPAWN_CHANCE = new HashMap<>();

    static {
        final String[] headers = {"Data", "AGR", "CMD", "EXT", "HAB", "IND", "LAB", "MED", "POI", "PWR", "SEC"};
        try (final InputStream is = SpawnConstants.class.getResourceAsStream("/dataspawn/data.spawnchance.csv")){
            final Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withHeader(headers)
                    .withFirstRecordAsHeader()
                    .parse(new InputStreamReader(is));
            for (final CSVRecord csvRecord : records) {
                final String dataName = csvRecord.get("Data");
                final String agr = csvRecord.get("AGR");
                final String cmd = csvRecord.get("CMD");
                final String ext = csvRecord.get("EXT");
                final String hab = csvRecord.get("HAB");
                final String ind = csvRecord.get("IND");
                final String lab = csvRecord.get("LAB");
                final String med = csvRecord.get("MED");
                final String poi = csvRecord.get("POI");
                final String pwr = csvRecord.get("PWR");
                final String sec = csvRecord.get("SEC");
                DATA_SPAWN_CHANCE.put(Data.forName(dataName), Map.of(
                        AGR,Double.valueOf(agr),
                        CMD,Double.valueOf(cmd),
                        EXT,Double.valueOf(ext),
                        HAB,Double.valueOf(hab),
                        IND,Double.valueOf(ind),
                        LAB,Double.valueOf(lab),
                        MED,Double.valueOf(med),
                        POI,Double.valueOf(poi),
                        PWR,Double.valueOf(pwr),
                        SEC,Double.valueOf(sec)
                ));
            }
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static final Map<HorizonsMaterial, List<HorizonsMaterialSpawnLocation>> HORIZONSMATERIAL_LOCATION = new HashMap<>();

    static {
        HORIZONSMATERIAL_LOCATION.put(Raw.ANTIMONY, List.of(CRYSTAL_SHARDS_ANTIMONY, CRASHED_SHIP, SURFACE_PROSPECTING));
        HORIZONSMATERIAL_LOCATION.put(Raw.ARSENIC, List.of(SURFACE_PROSPECTING, RING_ALL));
        HORIZONSMATERIAL_LOCATION.put(Raw.BORON, List.of(SURFACE_PROSPECTING, RING_ALL, ASTEROIDS));
        HORIZONSMATERIAL_LOCATION.put(Raw.CADMIUM, List.of(SURFACE_PROSPECTING));
        HORIZONSMATERIAL_LOCATION.put(Raw.CARBON, List.of(SURFACE_PROSPECTING, RING_ALL, ASTEROIDS));
        HORIZONSMATERIAL_LOCATION.put(Raw.CHROMIUM, List.of(SURFACE_PROSPECTING, RING_ICE));
        HORIZONSMATERIAL_LOCATION.put(Raw.GERMANIUM, List.of(SURFACE_PROSPECTING, RING_ALL));
        HORIZONSMATERIAL_LOCATION.put(Raw.IRON, List.of(SURFACE_PROSPECTING, RING_ALL, ASTEROIDS));
        HORIZONSMATERIAL_LOCATION.put(Raw.LEAD, List.of(SURFACE_PROSPECTING, RING_ALL, ASTEROIDS));
        HORIZONSMATERIAL_LOCATION.put(Raw.MANGANESE, List.of(SURFACE_PROSPECTING, RING_ALL));
        HORIZONSMATERIAL_LOCATION.put(Raw.MERCURY, List.of(SURFACE_PROSPECTING));
        HORIZONSMATERIAL_LOCATION.put(Raw.MOLYBDENUM, List.of(SURFACE_PROSPECTING));
        HORIZONSMATERIAL_LOCATION.put(Raw.NICKEL, List.of(SURFACE_PROSPECTING, RING_ALL, ASTEROIDS));
        HORIZONSMATERIAL_LOCATION.put(Raw.NIOBIUM, List.of(SURFACE_PROSPECTING));
        HORIZONSMATERIAL_LOCATION.put(Raw.PHOSPHORUS, List.of(SURFACE_PROSPECTING, RING_ALL, ASTEROIDS));
        HORIZONSMATERIAL_LOCATION.put(Raw.POLONIUM, List.of(CRYSTAL_SHARDS_POLONIUM, SURFACE_PROSPECTING));
        HORIZONSMATERIAL_LOCATION.put(Raw.RHENIUM, List.of(SURFACE_PROSPECTING, RING_ALL, ASTEROIDS));
        HORIZONSMATERIAL_LOCATION.put(Raw.RUTHENIUM, List.of(CRYSTAL_SHARDS_RUTHENIUM_A, CRYSTAL_SHARDS_RUTHENIUM_B, CRASHED_SHIP, SURFACE_PROSPECTING));
        HORIZONSMATERIAL_LOCATION.put(Raw.SELENIUM, List.of(BRAIN_TREES, SURFACE_PROSPECTING));
        HORIZONSMATERIAL_LOCATION.put(Raw.SULPHUR, List.of(SURFACE_PROSPECTING, RING_ALL, ASTEROIDS));
        HORIZONSMATERIAL_LOCATION.put(Raw.TECHNETIUM, List.of(CRYSTAL_SHARDS_TECHNETIUM, SURFACE_PROSPECTING));
        HORIZONSMATERIAL_LOCATION.put(Raw.TELLURIUM, List.of(CRYSTAL_SHARDS_TELLURIUM, CRASHED_SHIP, SURFACE_PROSPECTING));
        HORIZONSMATERIAL_LOCATION.put(Raw.TIN, List.of(SURFACE_PROSPECTING, RING_ALL));
        HORIZONSMATERIAL_LOCATION.put(Raw.TUNGSTEN, List.of(CRASHED_SHIP, SURFACE_PROSPECTING));
        HORIZONSMATERIAL_LOCATION.put(Raw.VANADIUM, List.of(SURFACE_PROSPECTING, RING_ICE));
        HORIZONSMATERIAL_LOCATION.put(Raw.YTTRIUM, List.of(CRYSTAL_SHARDS_YTTRIUM, SURFACE_PROSPECTING));
        HORIZONSMATERIAL_LOCATION.put(Raw.ZINC, List.of(SURFACE_PROSPECTING, RING_PRISTINE));
        HORIZONSMATERIAL_LOCATION.put(Raw.ZIRCONIUM, List.of(CRASHED_SHIP, SURFACE_PROSPECTING, RING_ALL));

        HORIZONSMATERIAL_LOCATION.put(Encoded.SHIELDPATTERNANALYSIS, List.of(BASIC_SCAN_COMBAT_PIRATE, MEGA_SHIP_HACKING));
        HORIZONSMATERIAL_LOCATION.put(Encoded.COMPACTEMISSIONSDATA, List.of(BASIC_SCAN_COMBAT_PIRATE, BASIC_SCAN_AUTHORITY_MILITARY, ENCODED_EMISSIONS_DATA_BEACON));
        HORIZONSMATERIAL_LOCATION.put(Encoded.ADAPTIVEENCRYPTORS, List.of(JAMESON_CRASH_SITE, SCAN_MISSIONS));
        HORIZONSMATERIAL_LOCATION.put(Encoded.BULKSCANDATA, List.of(MISSION_REWARD, BASIC_SCAN_COMBAT_PIRATE, BASIC_SCAN_HAULAGE, MEGA_SHIP_HACKING));
        HORIZONSMATERIAL_LOCATION.put(Encoded.FSDTELEMETRY, List.of(MISSION_REWARD, WAKE_SCAN_HIGH, ENCODED_EMISSIONS_DATA_BEACON));
        HORIZONSMATERIAL_LOCATION.put(Encoded.DISRUPTEDWAKEECHOES, List.of(MISSION_REWARD, WAKE_SCAN_HIGH, ENCODED_EMISSIONS_DATA_BEACON));
        HORIZONSMATERIAL_LOCATION.put(Encoded.ENCRYPTIONARCHIVES, List.of(JAMESON_CRASH_SITE, SCAN_MISSIONS));
        HORIZONSMATERIAL_LOCATION.put(Encoded.SCANDATABANKS, List.of(MISSION_REWARD, BASIC_SCAN_HAULAGE, SETTLEMENT_DATAPOINT, MEGA_SHIP_HACKING));
        HORIZONSMATERIAL_LOCATION.put(Encoded.CLASSIFIEDSCANDATA, List.of(BASIC_SCAN_AUTHORITY_MILITARY, SETTLEMENT_DATAPOINT, MEGA_SHIP_HACKING));
        HORIZONSMATERIAL_LOCATION.put(Encoded.INDUSTRIALFIRMWARE, List.of(MISSION_REWARD, SETTLEMENT_DATAPOINT, ENCODED_EMISSIONS_DATA_BEACON, MEGA_SHIP_HACKING));
        HORIZONSMATERIAL_LOCATION.put(Encoded.DATAMINEDWAKE, List.of(WAKE_SCAN_HIGH, ENCODED_EMISSIONS_DATA_BEACON));
        HORIZONSMATERIAL_LOCATION.put(Encoded.DECODEDEMISSIONDATA, List.of(BASIC_SCAN_COMBAT_PIRATE, BASIC_SCAN_AUTHORITY_MILITARY, SETTLEMENT_DATAPOINT, ENCODED_EMISSIONS_DATA_BEACON));
        HORIZONSMATERIAL_LOCATION.put(Encoded.SHIELDCYCLERECORDINGS, List.of(BASIC_SCAN_AUTHORITY_MILITARY, BASIC_SCAN_HAULAGE, MEGA_SHIP_HACKING));
        HORIZONSMATERIAL_LOCATION.put(Encoded.ENCODEDSCANDATA, List.of(MISSION_REWARD, BASIC_SCAN_AUTHORITY_MILITARY, SETTLEMENT_DATAPOINT, ENCODED_EMISSIONS_DATA_BEACON, MEGA_SHIP_HACKING));
        HORIZONSMATERIAL_LOCATION.put(Encoded.HYPERSPACETRAJECTORIES, List.of(WAKE_SCAN_HIGH));
        HORIZONSMATERIAL_LOCATION.put(Encoded.SCRAMBLEDEMISSIONDATA, List.of(MISSION_REWARD, ENCODED_EMISSIONS_DATA_BEACON));
        HORIZONSMATERIAL_LOCATION.put(Encoded.SHIELDSOAKANALYSIS, List.of(BASIC_SCAN_AUTHORITY_MILITARY, BASIC_SCAN_HAULAGE, MEGA_SHIP_HACKING));
        HORIZONSMATERIAL_LOCATION.put(Encoded.ARCHIVEDEMISSIONDATA, List.of(MISSION_REWARD, ENCODED_EMISSIONS_DATA_BEACON));
        HORIZONSMATERIAL_LOCATION.put(Encoded.CONSUMERFIRMWARE, List.of(MISSION_REWARD, SETTLEMENT_DATAPOINT, ENCODED_EMISSIONS_DATA_BEACON));
        HORIZONSMATERIAL_LOCATION.put(Encoded.EMBEDDEDFIRMWARE, List.of(MISSION_REWARD, SETTLEMENT_DATAPOINT, MEGA_SHIP_HACKING));
        HORIZONSMATERIAL_LOCATION.put(Encoded.SYMMETRICKEYS, List.of(SETTLEMENT_DATAPOINT, ENCODED_EMISSIONS_DATA_BEACON, MEGA_SHIP_HACKING));
        HORIZONSMATERIAL_LOCATION.put(Encoded.SHIELDFREQUENCYDATA, List.of(BASIC_SCAN_COMBAT_PIRATE, MEGA_SHIP_HACKING));
        HORIZONSMATERIAL_LOCATION.put(Encoded.SECURITYFIRMWARE, List.of(MISSION_REWARD, SETTLEMENT_DATAPOINT, MEGA_SHIP_HACKING));
        HORIZONSMATERIAL_LOCATION.put(Encoded.LEGACYFIRMWARE, List.of(MISSION_REWARD, SETTLEMENT_DATAPOINT, DEGRADED_EMISSIONS_DATA_BEACON, ENCODED_EMISSIONS_DATA_BEACON));
        HORIZONSMATERIAL_LOCATION.put(Encoded.WAKESOLUTIONS, List.of(MISSION_REWARD, WAKE_SCAN_HIGH, ENCODED_EMISSIONS_DATA_BEACON));
        HORIZONSMATERIAL_LOCATION.put(Encoded.ENCRYPTIONCODES, List.of(SETTLEMENT_DATAPOINT, ENCODED_EMISSIONS_DATA_BEACON,MEGA_SHIP_HACKING));
        HORIZONSMATERIAL_LOCATION.put(Encoded.EMISSIONDATA, List.of(MISSION_REWARD, BASIC_SCAN_COMBAT_PIRATE, BASIC_SCAN_AUTHORITY_MILITARY, ENCODED_EMISSIONS_DATA_BEACON));
        HORIZONSMATERIAL_LOCATION.put(Encoded.SCANARCHIVES, List.of(BASIC_SCAN_HAULAGE, MEGA_SHIP_HACKING));
        HORIZONSMATERIAL_LOCATION.put(Encoded.SHIELDDENSITYREPORTS, List.of(BASIC_SCAN_COMBAT_PIRATE, BASIC_SCAN_AUTHORITY_MILITARY, MEGA_SHIP_HACKING));
        HORIZONSMATERIAL_LOCATION.put(Encoded.ENCRYPTEDFILES, List.of(SETTLEMENT_DATAPOINT, ENCODED_EMISSIONS_DATA_BEACON, MEGA_SHIP_HACKING));
        HORIZONSMATERIAL_LOCATION.put(Encoded.TG_SHIPFLIGHTDATA, List.of(THARGOID_INTERCEPTOR_XENOSCAN, THARGOID_SCOUT_XENOSCAN));
        HORIZONSMATERIAL_LOCATION.put(Encoded.TG_SHIPSYSTEMSDATA, List.of(THARGOID_INTERCEPTOR_XENOSCAN, THARGOID_SCOUT_XENOSCAN));
        HORIZONSMATERIAL_LOCATION.put(Encoded.TG_COMPOSITIONDATA, List.of(THARGOID_UPLINK_DEVICE_SCAN));
        HORIZONSMATERIAL_LOCATION.put(Encoded.TG_RESIDUEDATA, List.of(THARGOID_UPLINK_DEVICE_SCAN));
        HORIZONSMATERIAL_LOCATION.put(Encoded.UNKNOWNSHIPSIGNATURE, List.of(THARGOID_SCAVENGER_SCAN, THARGOID_INTERCEPTOR_XENOSCAN));
        HORIZONSMATERIAL_LOCATION.put(Encoded.TG_STRUCTURALDATA, List.of(THARGOID_UPLINK_DEVICE_SCAN));
        HORIZONSMATERIAL_LOCATION.put(Encoded.UNKNOWNWAKEDATA, List.of(THARGOID_SCAVENGER_SCAN, THARGOID_WAKE_SCAN));
        HORIZONSMATERIAL_LOCATION.put(Encoded.GUARDIAN_MODULEBLUEPRINT, List.of(GUARDIAN_DATA_TERMINAL));
        HORIZONSMATERIAL_LOCATION.put(Encoded.GUARDIAN_VESSELBLUEPRINT, List.of(GUARDIAN_DATA_TERMINAL));
        HORIZONSMATERIAL_LOCATION.put(Encoded.GUARDIAN_WEAPONBLUEPRINT, List.of(GUARDIAN_DATA_TERMINAL));
        HORIZONSMATERIAL_LOCATION.put(Encoded.ANCIENTBIOLOGICALDATA, List.of(GUARDIAN_OBELISK));
        HORIZONSMATERIAL_LOCATION.put(Encoded.ANCIENTCULTURALDATA, List.of(GUARDIAN_OBELISK));
        HORIZONSMATERIAL_LOCATION.put(Encoded.ANCIENTLANGUAGEDATA, List.of(GUARDIAN_OBELISK));
        HORIZONSMATERIAL_LOCATION.put(Encoded.ANCIENTTECHNOLOGICALDATA, List.of(GUARDIAN_OBELISK));
        HORIZONSMATERIAL_LOCATION.put(Encoded.ANCIENTHISTORICALDATA, List.of(GUARDIAN_OBELISK));

        HORIZONSMATERIAL_LOCATION.put(Manufactured.BASICCONDUCTORS, List.of(DAVS_HOPE, RES_HAULAGE));
        HORIZONSMATERIAL_LOCATION.put(Manufactured.BIOTECHCONDUCTORS, List.of(MISSION_REWARD));
        HORIZONSMATERIAL_LOCATION.put(Manufactured.CHEMICALDISTILLERY, List.of(DAVS_HOPE, HOT_JUPITER, COMBAT_AFTERMATH, ENCODED_EMISSIONS_HIGH_MED, RES_HAULAGE, RES_COMBAT));
        HORIZONSMATERIAL_LOCATION.put(Manufactured.CHEMICALMANIPULATORS, List.of(DAVS_HOPE, HOT_JUPITER, COMBAT_AFTERMATH, ENCODED_EMISSIONS_HIGH_MED, RES_HAULAGE));
        HORIZONSMATERIAL_LOCATION.put(Manufactured.CHEMICALPROCESSORS, List.of(DAVS_HOPE, HOT_JUPITER, COMBAT_AFTERMATH, ENCODED_EMISSIONS_HIGH_MED, DEGRADED_EMISSIONS, RES_HAULAGE, RES_COMBAT));
        HORIZONSMATERIAL_LOCATION.put(Manufactured.CHEMICALSTORAGEUNITS, List.of(RES_HAULAGE));
        HORIZONSMATERIAL_LOCATION.put(Manufactured.COMPACTCOMPOSITES, List.of(RES_COMBAT));
        HORIZONSMATERIAL_LOCATION.put(Manufactured.COMPOUNDSHIELDING, List.of(DAVS_HOPE, HOT_JUPITER, ENCODED_EMISSIONS_HIGH_MED, RES_COMBAT));
        HORIZONSMATERIAL_LOCATION.put(Manufactured.CONDUCTIVECERAMICS, List.of(DAVS_HOPE, HOT_JUPITER, MISSION_REWARD, COMBAT_AFTERMATH, ENCODED_EMISSIONS_ANARCHY, RES_HAULAGE, CZ_AUTHORITY));
        HORIZONSMATERIAL_LOCATION.put(Manufactured.CONDUCTIVECOMPONENTS, List.of(DAVS_HOPE, HOT_JUPITER, MISSION_REWARD, COMBAT_AFTERMATH, ENCODED_EMISSIONS_ANARCHY, DEGRADED_EMISSIONS, RES_HAULAGE, CZ_AUTHORITY));
        HORIZONSMATERIAL_LOCATION.put(Manufactured.CONDUCTIVEPOLYMERS, List.of(DAVS_HOPE, HOT_JUPITER, MISSION_REWARD, COMBAT_AFTERMATH, ENCODED_EMISSIONS_ANARCHY, RES_HAULAGE));
        HORIZONSMATERIAL_LOCATION.put(Manufactured.CONFIGURABLECOMPONENTS, List.of(DAVS_HOPE, HOT_JUPITER, COMBAT_AFTERMATH, ENCODED_EMISSIONS_LOW, RES_HAULAGE));
        HORIZONSMATERIAL_LOCATION.put(Manufactured.FEDCORECOMPOSITES, List.of(HGE_FEDERATION));
        HORIZONSMATERIAL_LOCATION.put(Manufactured.CRYSTALSHARDS, List.of(DAVS_HOPE, MISSION_REWARD, DEGRADED_EMISSIONS, RES_COMBAT));
        HORIZONSMATERIAL_LOCATION.put(Manufactured.ELECTROCHEMICALARRAYS, List.of(DAVS_HOPE, MISSION_REWARD, DEGRADED_EMISSIONS, CZ_AUTHORITY));
        HORIZONSMATERIAL_LOCATION.put(Manufactured.EXQUISITEFOCUSCRYSTALS, List.of(MISSION_REWARD));
        HORIZONSMATERIAL_LOCATION.put(Manufactured.FILAMENTCOMPOSITES, List.of(RES_COMBAT));
        HORIZONSMATERIAL_LOCATION.put(Manufactured.UNCUTFOCUSCRYSTALS, List.of(DAVS_HOPE, MISSION_REWARD, ENCODED_EMISSIONS_HIGH_MED));
        HORIZONSMATERIAL_LOCATION.put(Manufactured.FOCUSCRYSTALS, List.of(DAVS_HOPE, MISSION_REWARD, ENCODED_EMISSIONS_HIGH_MED, RES_COMBAT));
        HORIZONSMATERIAL_LOCATION.put(Manufactured.GALVANISINGALLOYS, List.of(DAVS_HOPE, ENCODED_EMISSIONS_LOW, RES_COMBAT));
        HORIZONSMATERIAL_LOCATION.put(Manufactured.GRIDRESISTORS, List.of(DAVS_HOPE, MISSION_REWARD, ENCODED_EMISSIONS_LOW, CZ_AUTHORITY));
        HORIZONSMATERIAL_LOCATION.put(Manufactured.HEATCONDUCTIONWIRING, List.of(DAVS_HOPE, COMBAT_AFTERMATH, ENCODED_EMISSIONS_ANARCHY, DEGRADED_EMISSIONS, RES_HAULAGE));
        HORIZONSMATERIAL_LOCATION.put(Manufactured.HEATDISPERSIONPLATE, List.of(DAVS_HOPE, HOT_JUPITER, COMBAT_AFTERMATH, ENCODED_EMISSIONS_ANARCHY, DEGRADED_EMISSIONS, RES_HAULAGE));
        HORIZONSMATERIAL_LOCATION.put(Manufactured.HEATEXCHANGERS, List.of(DAVS_HOPE, HOT_JUPITER, COMBAT_AFTERMATH, ENCODED_EMISSIONS_ANARCHY, RES_HAULAGE));
        HORIZONSMATERIAL_LOCATION.put(Manufactured.HEATRESISTANTCERAMICS, List.of(RES_COMBAT));
        HORIZONSMATERIAL_LOCATION.put(Manufactured.HEATVANES, List.of(DAVS_HOPE, HOT_JUPITER, COMBAT_AFTERMATH, ENCODED_EMISSIONS_ANARCHY, RES_HAULAGE));
        HORIZONSMATERIAL_LOCATION.put(Manufactured.HIGHDENSITYCOMPOSITES, List.of(DAVS_HOPE, ENCODED_EMISSIONS_LOW, CZ_AUTHORITY));
        HORIZONSMATERIAL_LOCATION.put(Manufactured.HYBRIDCAPACITORS, List.of(DAVS_HOPE, MISSION_REWARD, CZ_AUTHORITY, ENCODED_EMISSIONS_ANARCHY));
        HORIZONSMATERIAL_LOCATION.put(Manufactured.IMPERIALSHIELDING, List.of(HGE_EMPIRE));
        HORIZONSMATERIAL_LOCATION.put(Manufactured.IMPROVISEDCOMPONENTS, List.of(HGE_CIVIL_UNREST));
        HORIZONSMATERIAL_LOCATION.put(Manufactured.MECHANICALCOMPONENTS, List.of(DAVS_HOPE, HOT_JUPITER, MISSION_REWARD, COMBAT_AFTERMATH, ENCODED_EMISSIONS_LOW, RES_HAULAGE));
        HORIZONSMATERIAL_LOCATION.put(Manufactured.MECHANICALEQUIPMENT, List.of(DAVS_HOPE, HOT_JUPITER, MISSION_REWARD, COMBAT_AFTERMATH, ENCODED_EMISSIONS_LOW, DEGRADED_EMISSIONS, RES_HAULAGE));
        HORIZONSMATERIAL_LOCATION.put(Manufactured.MECHANICALSCRAP, List.of(DAVS_HOPE, MISSION_REWARD, COMBAT_AFTERMATH, ENCODED_EMISSIONS_LOW, RES_HAULAGE));
        HORIZONSMATERIAL_LOCATION.put(Manufactured.MILITARYGRADEALLOYS, List.of(HGE_WAR_CIVIL_WAR));
        HORIZONSMATERIAL_LOCATION.put(Manufactured.MILITARYSUPERCAPACITORS, List.of(HGE_WAR_CIVIL_WAR));
        HORIZONSMATERIAL_LOCATION.put(Manufactured.PHARMACEUTICALISOLATORS, List.of(HGE_OUTBREAK));
        HORIZONSMATERIAL_LOCATION.put(Manufactured.PHASEALLOYS, List.of(DAVS_HOPE, ENCODED_EMISSIONS_LOW, RES_COMBAT));
        HORIZONSMATERIAL_LOCATION.put(Manufactured.POLYMERCAPACITORS, List.of(DAVS_HOPE, CZ_AUTHORITY, ENCODED_EMISSIONS_ANARCHY));
        HORIZONSMATERIAL_LOCATION.put(Manufactured.PRECIPITATEDALLOYS, List.of(MISSION_REWARD, RES_COMBAT, CZ_AUTHORITY));
        HORIZONSMATERIAL_LOCATION.put(Manufactured.FEDPROPRIETARYCOMPOSITES, List.of(HGE_FEDERATION));
        HORIZONSMATERIAL_LOCATION.put(Manufactured.PROTOHEATRADIATORS, List.of(MISSION_REWARD, HGE_BOOM_EXPANSION));
        HORIZONSMATERIAL_LOCATION.put(Manufactured.PROTOLIGHTALLOYS, List.of(HGE_BOOM_EXPANSION));
        HORIZONSMATERIAL_LOCATION.put(Manufactured.PROTORADIOLICALLOYS, List.of(HGE_BOOM_EXPANSION));
        HORIZONSMATERIAL_LOCATION.put(Manufactured.REFINEDFOCUSCRYSTALS, List.of(DAVS_HOPE, MISSION_REWARD, ENCODED_EMISSIONS_HIGH_MED, RES_COMBAT));
        HORIZONSMATERIAL_LOCATION.put(Manufactured.SALVAGEDALLOYS, List.of(DAVS_HOPE, ENCODED_EMISSIONS_LOW, DEGRADED_EMISSIONS, RES_COMBAT));
        HORIZONSMATERIAL_LOCATION.put(Manufactured.SHIELDEMITTERS, List.of(DAVS_HOPE, HOT_JUPITER, ENCODED_EMISSIONS_HIGH_MED, RES_COMBAT));
        HORIZONSMATERIAL_LOCATION.put(Manufactured.SHIELDINGSENSORS, List.of(DAVS_HOPE, HOT_JUPITER, ENCODED_EMISSIONS_HIGH_MED, RES_COMBAT));
        HORIZONSMATERIAL_LOCATION.put(Manufactured.TEMPEREDALLOYS, List.of(RES_COMBAT));
        HORIZONSMATERIAL_LOCATION.put(Manufactured.THERMICALLOYS, List.of(MISSION_REWARD, CZ_AUTHORITY));
        HORIZONSMATERIAL_LOCATION.put(Manufactured.WORNSHIELDEMITTERS, List.of(DAVS_HOPE, ENCODED_EMISSIONS_LOW, RES_COMBAT));

        HORIZONSMATERIAL_LOCATION.put(Manufactured.UNKNOWNCARAPACE, List.of(THARGOID_SCAVENGER));
        HORIZONSMATERIAL_LOCATION.put(Manufactured.UNKNOWNENERGYCELL, List.of(THARGOID_SCAVENGER));
        HORIZONSMATERIAL_LOCATION.put(Manufactured.UNKNOWNENERGYSOURCE, List.of(THARGOID_SCAVENGER, THARGOID_SENSOR));
        HORIZONSMATERIAL_LOCATION.put(Manufactured.UNKNOWNORGANICCIRCUITRY, List.of(THARGOID_SCAVENGER));
        HORIZONSMATERIAL_LOCATION.put(Manufactured.UNKNOWNTECHNOLOGYCOMPONENTS, List.of(THARGOID_SCAVENGER));
        HORIZONSMATERIAL_LOCATION.put(Manufactured.TG_WEAPONPARTS, List.of(THARGOID_SHIP));
        HORIZONSMATERIAL_LOCATION.put(Manufactured.TG_WRECKAGECOMPONENTS, List.of(THARGOID_SHIP));
        HORIZONSMATERIAL_LOCATION.put(Manufactured.TG_PROPULSIONELEMENT, List.of(THARGOID_SHIP));
        HORIZONSMATERIAL_LOCATION.put(Manufactured.TG_BIOMECHANICALCONDUITS, List.of(THARGOID_SHIP));

        HORIZONSMATERIAL_LOCATION.put(Manufactured.GUARDIAN_POWERCELL, List.of(GUARDIAN_PANELS));
        HORIZONSMATERIAL_LOCATION.put(Manufactured.GUARDIAN_POWERCONDUIT, List.of(GUARDIAN_PANELS));
        HORIZONSMATERIAL_LOCATION.put(Manufactured.GUARDIAN_SENTINEL_WEAPONPARTS, List.of(GUARDIAN_SENTINELS));
        HORIZONSMATERIAL_LOCATION.put(Manufactured.GUARDIAN_SENTINEL_WRECKAGECOMPONENTS, List.of(GUARDIAN_SENTINELS));
        HORIZONSMATERIAL_LOCATION.put(Manufactured.GUARDIAN_TECHCOMPONENT, List.of(GUARDIAN_PANELS));
    }

    @SuppressWarnings("java:S1452")
    public static Map<SpawnLocationType, List<? extends SpawnLocation>> getSpawnLocations(final OdysseyMaterial odysseyMaterial) {
        final Map<SpawnLocationType, List<? extends SpawnLocation>> spawnLocations = new EnumMap<>(SpawnLocationType.class);
        if (odysseyMaterial instanceof Data) {
            spawnLocations.put(SpawnLocationType.DATAPORT, DATA_PORTS.getOrDefault(odysseyMaterial, List.of()));
            spawnLocations.put(SpawnLocationType.PLANETARY, DATA_PLANET_PORTS.getOrDefault(odysseyMaterial, List.of()));
            spawnLocations.put(SpawnLocationType.OTHER, DATA_OTHER.getOrDefault(odysseyMaterial, List.of()));
        }
        if (odysseyMaterial instanceof Asset) {
            spawnLocations.put(SpawnLocationType.OPENLOCKER, ASSET_LOCKERS.getOrDefault(odysseyMaterial, List.of()));
            spawnLocations.put(SpawnLocationType.PLANETARY, ASSET_PLANET.getOrDefault(odysseyMaterial, List.of()));
        }
        if (odysseyMaterial instanceof Good) {
            spawnLocations.put(SpawnLocationType.CLOSEDLOCKER, GOOD_LOCKERS.getOrDefault(odysseyMaterial, List.of()));
            spawnLocations.put(SpawnLocationType.PLANETARY, GOOD_PLANET.getOrDefault(odysseyMaterial, List.of()));
            spawnLocations.put(SpawnLocationType.DEVICE, GOOD_DEVICES.getOrDefault(odysseyMaterial, List.of()));
            spawnLocations.put(SpawnLocationType.OTHER, GOOD_OTHER.getOrDefault(odysseyMaterial, List.of()));
        }
        return spawnLocations;
    }

}
