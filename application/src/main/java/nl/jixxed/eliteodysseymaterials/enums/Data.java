package nl.jixxed.eliteodysseymaterials.enums;

public enum Data implements Material {
    ACCIDENTLOGS(false),
    AIRQUALITYREPORTS(false),
    ATMOSPHERICDATA(false),
    AUDIOLOGS(false),
    AXCOMBATLOGS(false),
    BALLISTICSDATA(false),
    BIOLOGICALWEAPONDATA(true),
    BIOMETRICDATA(false),
    BLACKLISTDATA(false),
    BLOODTESTRESULTS(false),
    CAMPAIGNPLANS(false),
    CATMEDIA(false),
    CENSUSDATA(false),
    CHEMICALEXPERIMENTDATA(true),
    CHEMICALFORMULAE(false),
    CHEMICALINVENTORY(false),
    CHEMICALPATENTS(false),
    CHEMICALWEAPONDATA(false),
    CLASSICENTERTAINMENT(false),
    COCKTAILRECIPES(false),
    COMBATANTPERFORMANCE(false),
    COMBATTRAININGMATERIAL(false),
    CONFLICTHISTORY(false),
    CRIMINALRECORDS(false),
    CROPYIELDANALYSIS(false),
    CULINARYRECIPES(false),
    DIGITALDESIGNS(false),
    DUTYROTA(false),
    EMPLOYEEDIRECTORY(false),
    EMPLOYEEEXPENSES(false),
    EMPLOYEEGENETICDATA(false),
    EMPLOYMENTHISTORY(false),
    ENHANCEDINTERROGATIONRECORDINGS(true),
    ESPIONAGEMATERIAL(true),
    EVACUATIONPROTOCOLS(false),
    EXPLORATIONJOURNALS(false),
    EXTRACTIONYIELDDATA(false),
    FACTIONASSOCIATES(false),
    FACTIONDONATORLIST(false),
    FACTIONNEWS(false),
    FINANCIALPROJECTIONS(false),
    FLEETREGISTRY(false),
    GENESEQUENCINGDATA(false),
    GENETICRESEARCH(false),
    GEOLOGICALDATA(false),
    HYDROPONICDATA(false),
    INCIDENTLOGS(false),
    INFLUENCEPROJECTIONS(false),
    INTERNALCORRESPONDENCE(false),
    INTERROGATIONRECORDINGS(false),
    INTERVIEWRECORDINGS(false),
    JOBAPPLICATIONS(false),
    KOMPROMAT(true),
    LITERARYFICTION(false),
    MAINTENANCELOGS(false),
    MANUFACTURINGINSTRUCTIONS(false),
    MEDICALRECORDS(false),
    MEDICALTRIALRECORDS(false),
    MEETINGMINUTES(false),
    MINERALSURVEY(false),
    MININGANALYTICS(false),
    MULTIMEDIAENTERTAINMENT(false),
    NETWORKACCESSHISTORY(false),
    NETWORKSECURITYPROTOCOLS(false),
    NEXTOFKINRECORDS(false),
    NOCDATA(false),
    OPERATIONALMANUAL(false),
    OPINIONPOLLS(false),
    PATIENTHISTORY(false),
    PATROLROUTES(false),
    PAYROLLINFORMATION(false),
    PERSONALLOGS(false),
    PHARMACEUTICALPATENTS(false),
    PHOTOALBUMS(false),
    PLANTGROWTHCHARTS(false),
    POLITICALAFFILIATIONS(false),
    PRISONERLOGS(false),
    PRODUCTIONREPORTS(false),
    PRODUCTIONSCHEDULE(false),
    PROPAGANDA(false),
    PURCHASERECORDS(false),
    PURCHASEREQUESTS(false),
    RADIOACTIVITYDATA(false),
    REACTOROUTPUTREVIEW(false),
    RECYCLINGLOGS(false),
    RESIDENTIALDIRECTORY(false),
    RISKASSESSMENTS(false),
    SALESRECORDS(false),
    SECURITYEXPENSES(false),
    SEEDGENEAOLOGY(false),
    SETTLEMENTASSAULTPLANS(false),
    SETTLEMENTDEFENCEPLANS(false),
    SHAREHOLDERINFORMATION(false),
    SLUSHFUNDLOGS(true),
    SMEARCAMPAIGNPLANS(true),
    SPECTRALANALYSISDATA(false),
    SPYWARE(true),
    STELLARACTIVITYLOGS(false),
    SURVEILLEANCELOGS(false),
    TACTICALPLANS(false),
    TAXRECORDS(false),
    TOPOGRAPHICALSURVEYS(false),
    TRAVELPERMITS(false),
    TROOPDEPLOYMENTRECORDS(false),
    UNIONMEMBERSHIP(false),
    VACCINATIONRECORDS(false),
    VACCINERESEARCH(false),
    VIPSECURITYDETAIL(false),
    VIROLOGYDATA(false),
    VIRUS(true),
    VISITORREGISTER(false),
    WEAPONINVENTORY(false),
    WEAPONTESTDATA(false),
    XENODEFENCEPROTOCOLS(false),
    UNKNOWN(false);
    private final boolean illegal;

    Data(final boolean illegal) {
        this.illegal = illegal;
    }

    public static Data forName(final String name) {
        try {
            return Data.valueOf(name.toUpperCase());
        } catch (final IllegalArgumentException ex) {
            return Data.UNKNOWN;
        }
    }

    @Override
    public StorageType getStorageType() {
        return StorageType.DATA;
    }


    @Override
    public String getLocalizationKey() {
        return "material.data." + this.toString().toLowerCase();
    }

    @Override
    public boolean isUnknown() {
        return this == Data.UNKNOWN;
    }

    @Override
    public boolean isIllegal() {
        return this.illegal;
    }
}
