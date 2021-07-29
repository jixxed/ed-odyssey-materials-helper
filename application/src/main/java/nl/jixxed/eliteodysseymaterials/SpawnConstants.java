package nl.jixxed.eliteodysseymaterials;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.enums.*;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static nl.jixxed.eliteodysseymaterials.enums.DataPortType.*;
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
            Map.entry(Data.SPYWARE, List.of(AlternateLocationType.MISSION))
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
            Map.entry(Good.LARGECAPACITYPOWERREGULATOR, List.of(AlternateLocationType.MISSION))
    );
    private static final Map<Good, List<PlanetSignalSourceType>> GOOD_PLANET = Map.ofEntries(
            Map.entry(Good.LARGECAPACITYPOWERREGULATOR, List.of(LARGE_SAT)),
            Map.entry(Good.PUSH, List.of(SKIMMER, SRV)),
            Map.entry(Good.HUSH, List.of(SKIMMER, SRV))
    );

    public static Map<SpawnLocationType, List<? extends SpawnLocation>> getSpawnLocations(final Material material) {
        final Map<SpawnLocationType, List<? extends SpawnLocation>> spawnLocations = new EnumMap<>(SpawnLocationType.class);
        if (material instanceof Data) {
            spawnLocations.put(SpawnLocationType.DATAPORT, DATA_PORTS.getOrDefault(material, List.of()));
            spawnLocations.put(SpawnLocationType.PLANETARY, DATA_PLANET_PORTS.getOrDefault(material, List.of()));
            spawnLocations.put(SpawnLocationType.OTHER, DATA_OTHER.getOrDefault(material, List.of()));
        }
        if (material instanceof Asset) {
            spawnLocations.put(SpawnLocationType.OPENLOCKER, ASSET_LOCKERS.getOrDefault(material, List.of()));
            spawnLocations.put(SpawnLocationType.PLANETARY, ASSET_PLANET.getOrDefault(material, List.of()));
        }
        if (material instanceof Good) {
            spawnLocations.put(SpawnLocationType.CLOSEDLOCKER, GOOD_LOCKERS.getOrDefault(material, List.of()));
            spawnLocations.put(SpawnLocationType.PLANETARY, GOOD_PLANET.getOrDefault(material, List.of()));
            spawnLocations.put(SpawnLocationType.DEVICE, GOOD_DEVICES.getOrDefault(material, List.of()));
            spawnLocations.put(SpawnLocationType.OTHER, GOOD_OTHER.getOrDefault(material, List.of()));
        }
        return spawnLocations;
    }
}
