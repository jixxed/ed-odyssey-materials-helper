package nl.jixxed.eliteodysseymaterials.enums;

import nl.jixxed.eliteodysseymaterials.service.LocaleService;

public enum Data implements OdysseyMaterial {
    ACCIDENTLOGS(false, 3),
    AIRQUALITYREPORTS(false, 3),
    ATMOSPHERICDATA(false, 5),
    AUDIOLOGS(false, 15),
    AXCOMBATLOGS(false, 20),
    BALLISTICSDATA(false, 10),
    BIOLOGICALWEAPONDATA(true, 30),
    BIOMETRICDATA(false, 20),
    BLACKLISTDATA(false, 10),
    BLOODTESTRESULTS(false, 15),
    CAMPAIGNPLANS(false, 10),
    CATMEDIA(false, 3),
    CENSUSDATA(false, 5),
    CHEMICALEXPERIMENTDATA(false, 10),
    CHEMICALFORMULAE(false, 5),
    CHEMICALINVENTORY(false, 5),
    CHEMICALPATENTS(false, 15),
    CHEMICALWEAPONDATA(true, 30),
    CLASSICENTERTAINMENT(false, 5),
    COCKTAILRECIPES(false, 3),
    COMBATANTPERFORMANCE(false, 5),
    COMBATTRAININGMATERIAL(false, 5),
    CONFLICTHISTORY(false, 10),
    CRIMINALRECORDS(false, 20),
    CROPYIELDANALYSIS(false, 3),
    CULINARYRECIPES(false, 3),
    DIGITALDESIGNS(false, 10),
    DUTYROTA(false, 5),
    EMPLOYEEDIRECTORY(false, 3),
    EMPLOYEEEXPENSES(false, 3),
    EMPLOYEEGENETICDATA(false, 15),
    EMPLOYMENTHISTORY(false, 3),
    ENHANCEDINTERROGATIONRECORDINGS(true, 30),
    ESPIONAGEMATERIAL(true, 30),
    EVACUATIONPROTOCOLS(false, 5),
    EXPLORATIONJOURNALS(false, 5),
    EXTRACTIONYIELDDATA(false, 3),
    FACTIONASSOCIATES(false, 10),
    FACTIONDONATORLIST(false, 10),
    FACTIONNEWS(false, 3),
    FINANCIALPROJECTIONS(false, 3),
    FLEETREGISTRY(false, 15),
    GENESEQUENCINGDATA(false, 15),
    GENETICRESEARCH(false, 15),
    GEOLOGICALDATA(false, 5),
    HYDROPONICDATA(false, 10),
    INCIDENTLOGS(false, 5),
    INFLUENCEPROJECTIONS(false, 3),
    INTERNALCORRESPONDENCE(false, 15),
    INTERROGATIONRECORDINGS(false, 20),
    INTERVIEWRECORDINGS(false, 10),
    JOBAPPLICATIONS(false, 3),
    KOMPROMAT(true, 30),
    LITERARYFICTION(false, 3),
    MAINTENANCELOGS(false, 3),
    MANUFACTURINGINSTRUCTIONS(false, 5),
    MEDICALRECORDS(false, 15),
    MEDICALTRIALRECORDS(false, 20),
    MEETINGMINUTES(false, 3),
    MINERALSURVEY(false, 3),
    MININGANALYTICS(false, 5),
    MULTIMEDIAENTERTAINMENT(false, 3),
    NETWORKACCESSHISTORY(false, 3),
    NETWORKSECURITYPROTOCOLS(false, 20),
    NEXTOFKINRECORDS(false, 10),
    NOCDATA(false, 90),
    OPERATIONALMANUAL(false, 5),
    OPINIONPOLLS(false, 3),
    PATIENTHISTORY(false, 5),
    PATROLROUTES(false, 10),
    PAYROLLINFORMATION(false, 3),
    PERSONALLOGS(false, 3),
    PHARMACEUTICALPATENTS(false, 15),
    PHOTOALBUMS(false, 3),
    PLANTGROWTHCHARTS(false, 3),
    POLITICALAFFILIATIONS(false, 5),
    PRISONERLOGS(false, 15),
    PRODUCTIONREPORTS(false, 5),
    PRODUCTIONSCHEDULE(false, 5),
    PROPAGANDA(false, 15),
    PURCHASERECORDS(false, 5),
    PURCHASEREQUESTS(false, 3),
    RADIOACTIVITYDATA(false, 10),
    REACTOROUTPUTREVIEW(false, 5),
    RECYCLINGLOGS(false, 3),
    RESIDENTIALDIRECTORY(false, 5),
    RISKASSESSMENTS(false, 3),
    SALESRECORDS(false, 5),
    SECURITYEXPENSES(false, 10),
    SEEDGENEAOLOGY(false, 5),
    SETTLEMENTASSAULTPLANS(false, 15),
    SETTLEMENTDEFENCEPLANS(false, 20),
    SHAREHOLDERINFORMATION(false, 15),
    SLUSHFUNDLOGS(true, 30),
    SMEARCAMPAIGNPLANS(true, 20),
    SPECTRALANALYSISDATA(false, 10),
    SPYWARE(true, 60, true),
    STELLARACTIVITYLOGS(false, 5),
    SURVEILLEANCELOGS(false, 15),
    TACTICALPLANS(false, 15),
    TAXRECORDS(false, 10),
    TOPOGRAPHICALSURVEYS(false, 10),
    TRAVELPERMITS(false, 3),
    TROOPDEPLOYMENTRECORDS(false, 10),
    UNIONMEMBERSHIP(false, 3),
    VACCINATIONRECORDS(false, 3),
    VACCINERESEARCH(false, 10),
    VIPSECURITYDETAIL(false, 15),
    VIROLOGYDATA(false, 10),
    VIRUS(true, 60, true),
    VISITORREGISTER(false, 3),
    WEAPONINVENTORY(false, 5),
    WEAPONTESTDATA(false, 10),
    XENODEFENCEPROTOCOLS(false, 20),
    POWERPREPARATIONSPYWARE(true, 60, true, true),
    POWERSPYWARE(true, 60, true, true),
    POWERMEGASHIPDATA(false, 0, false, true),//TODO time + DataParser
    POWERRESEARCHDATA(false, 20, false, true),
    POWEREMPLOYEEDATA(false, 15, false, true),
    POWERFINANCIALRECORDS(false, 15, false, true),
    POWERPROPAGANDADATA(false, 10, false, true),
    POWERCLASSIFIEDDATA(false, 30, false, true),
    UNKNOWN(false, 0);
    private final boolean illegal;
    private final int transferTime;
    private boolean upload = false;
    private boolean powerplay = false;

    Data(final boolean illegal, final int transferTime) {
        this.illegal = illegal;
        this.transferTime = transferTime;
    }

    Data(final boolean illegal, final int transferTime, final boolean upload) {
        this(illegal, transferTime);
        this.upload = upload;
    }

    Data(final boolean illegal, final int transferTime, final boolean upload, final boolean powerplay) {
        this(illegal, transferTime, upload);
        this.powerplay = powerplay;
    }

    public static Data forName(final String name) {
        try {
            return Data.valueOf(name.toUpperCase());
        } catch (final IllegalArgumentException ex) {
            return Data.UNKNOWN;
        }
    }

    @Override
    public OdysseyStorageType getStorageType() {
        return OdysseyStorageType.DATA;
    }


    @Override
    public String getLocalizationKey() {
        return "material.data." + this.name().toLowerCase();
    }

    @Override
    public boolean isUnknown() {
        return this == Data.UNKNOWN;
    }

    @Override
    public boolean isIllegal() {
        return this.illegal;
    }

    @Override
    public boolean isPowerplay() {
        return this.powerplay;
    }

    @Override
    public String getTypeNameLocalized() {
        return LocaleService.getLocalizedStringForCurrentLocale("material.asset.type.data");
    }

    public int getTransferTime() {
        return this.transferTime;
    }

    public boolean isUpload() {
        return this.upload;
    }

    @Override
    public String toString() {
        return LocaleService.getLocalizedStringForCurrentLocale(getLocalizationKey());
    }

}
