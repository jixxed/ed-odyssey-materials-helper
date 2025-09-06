package nl.jixxed.eliteodysseymaterials.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.domain.StarSystem;

@Getter
@RequiredArgsConstructor
public enum RareCommodity implements Commodity {
    ADVERT1(CommodityType.CONSUMER_ITEMS, "Langford Enterprise", new StarSystem("17 Lyrae", -119.90625, 25.375, 58.0625)),
    AERIALEDENAPPLE(CommodityType.FOODS, "Andrade Legacy", new StarSystem("Aerial", 100.75, -102.625, 8.40625)),
    AGANIPPERUSH(CommodityType.MEDICINES, "Julian Market", new StarSystem("Aganippe", -11.5625, 43.8125, 11.625)),
    ALACARAKMOSKINART(CommodityType.CONSUMER_ITEMS, "Weyl Gateway", new StarSystem("Alacarakmo", -32.40625, 169.53125, -49.4375)),
    ALBINOQUECHUAMAMMOTH(CommodityType.FOODS, "Crown Ring", new StarSystem("Quechua", 52.09375, -112.09375, -40.78125)),
    ALIENEGGS(CommodityType.CONSUMER_ITEMS, "Ridley Scott", new StarSystem("Zaonce", 80.75, 48.75, 69.25)),
    ALTAIRIANSKIN(CommodityType.CONSUMER_ITEMS, "Solo Orbiter", new StarSystem("Altair", -12.3125, -2.75, 11.0)),
    ALYABODILYSOAP(CommodityType.MEDICINES, "Malaspina Gateway", new StarSystem("Alya", -77.28125, -57.4375, 30.53125)),
    ANDULIGAFIREWORKS(CommodityType.CONSUMER_ITEMS, "Celsius Estate", new StarSystem("Anduliga", 124.625, 2.5, 61.25)),
    ANIMALEFFIGIES(CommodityType.LEGAL_DRUGS, "Chorel Survey", new StarSystem("Crom", 53.0625, -17.34375, 8.46875)),
    ANYNACOFFEE(CommodityType.FOODS, "Libby Orbital", new StarSystem("Any Na", 125.65625, -1.71875, 14.09375)),
    APAVIETII(CommodityType.LEGAL_DRUGS, "Forester's Choice", new StarSystem("Upaniklis", -151.21875, -25.0, 77.71875)),
    AROUCACONVENTUALSWEETS(CommodityType.FOODS, "Shipton Orbital", new StarSystem("Arouca", 104.96875, -6.53125, -4.40625)),
    AZCANCRIFORMULA42(CommodityType.TECHNOLOGY, "Fisher Station", new StarSystem("AZ Cancri", 19.65625, 26.84375, -38.09375)),
    BAKEDGREEBLES(CommodityType.FOODS, "Bamford Ring", new StarSystem("38 Virginis", 44.96875, 91.53125, 31.53125)),
    BALTAHSINEVACUUMKRILL(CommodityType.FOODS, "Baltha'Sine Station", new StarSystem("Baltah'Sine", 85.15625, -56.3125, 40.34375)),
    BANKIAMPHIBIOUSLEATHER(CommodityType.TEXTILES, "Antonio de Andrade Vista", new StarSystem("Banki", 49.53125, 15.75, -91.71875)),
    BASTSNAKEGIN(CommodityType.LEGAL_DRUGS, "Hart Station", new StarSystem("Bast", -36.46875, 16.15625, -34.9375)),
    BELALANSRAYLEATHER(CommodityType.TEXTILES, "Boscovich Ring", new StarSystem("Belalans", 81.625, -94.875, -58.5625)),
    BLUEMILK(CommodityType.FOODS, "George Lucas", new StarSystem("Leesti", 72.75, 48.75, 68.25)),
    BORASETANIPATHOGENETICS(CommodityType.WEAPONS, "Katzenstein Terminal", new StarSystem("Borasetani", 0.21875, 117.75, -18.03125)),
    BUCKYBALLBEERMATS(CommodityType.CONSUMER_ITEMS, "Rebuy Prospect", new StarSystem("Fullerene C60", -50.5, 19.28125, -203.875)),
    BURNHAMBILEDISTILLATE(CommodityType.LEGAL_DRUGS, "Burnham Beacon", new StarSystem("HIP 59533", 122.96875, -9.0625, 69.0)),
    CD75CATCOFFEE(CommodityType.FOODS, "Kirk Dock", new StarSystem("CD-75 661", 67.875, -21.5, 51.15625)),
    CENTAURIMEGAGIN(CommodityType.LEGAL_DRUGS, "Hutton Orbital", new StarSystem("Alpha Centauri", 3.03125, -0.09375, 3.15625)),
    CEREMONIALHEIKETEA(CommodityType.FOODS, "Brunel City", new StarSystem("Heike", -76.96875, 71.90625, 69.1875)),
    CETIAEPYORNISEGG(CommodityType.FOODS, "Glushko Station", new StarSystem("47 Ceti", -14.125, -116.96875, -32.53125)),
    CETIRABBITS(CommodityType.FOODS, "Kaufmanis Hub", new StarSystem("47 Ceti", -14.125, -116.96875, -32.53125)),
    CHAMELEONCLOTH(CommodityType.TEXTILES, "Smith Reserve", new StarSystem("LDS 883", -28.71875, -35.71875, -61.4375)),
    CHATEAUDEAEGAEON(CommodityType.LEGAL_DRUGS, "Schweickart Station", new StarSystem("Aegaeon", 46.90625, 23.625, -59.75)),
    CHERBONESBLOODCRYSTALS(CommodityType.METALS, "Chalker Landing", new StarSystem("Cherbones", 5.1875, 84.53125, -16.75)),
    CHIERIDANIMARINEPASTE(CommodityType.FOODS, "Steve Masters", new StarSystem("Chi Eridani", 26.28125, -51.75, 4.625)),
    CLASSIFIEDEXPERIMENTALEQUIPMENT(CommodityType.TECHNOLOGY, "Heart of Taurus", new StarSystem("HIP 22460", -41.3125, -58.96875, -354.78125)),
    COQUIMSPONGIFORMVICTUALS(CommodityType.FOODS, "Hirayama Installation", new StarSystem("Coquim", 20.75, -82.25, 33.59375)),
    CRYSTALLINESPHERES(CommodityType.SALVAGE, "Snow Moon", new StarSystem("Bento", 16.46875, -31.0625, 1.46875)),
    DAMNACARAPACES(CommodityType.TEXTILES, "Nemere Market", new StarSystem("Damna", -40.53125, 4.375, -124.25)),
    DELTAPHOENICISPALMS(CommodityType.CHEMICALS, "Trading Post", new StarSystem("Delta Phoenicis", 53.90625, -130.6875, 14.65625)),
    DEURINGASTRUFFLES(CommodityType.FOODS, "Shukor Hub", new StarSystem("Deuringas", 137.3125, 3.84375, -35.90625)),
    DISOMACORN(CommodityType.FOODS, "Shifnalport", new StarSystem("Diso", 72.15625, 48.75, 70.75)),
    DURADRIVES(CommodityType.CONSUMER_ITEMS, "Cowper Dock", new StarSystem("Anima", 57.65625, -61.1875, -36.53125)),
    ELEUTHERMALS(CommodityType.CONSUMER_ITEMS, "Finney Dock", new StarSystem("Eleu", -29.65625, 32.6875, 104.84375)),
    ERANINPEARLWHISKY(CommodityType.LEGAL_DRUGS, "Azeban City", new StarSystem("Eranin", -22.84375, 36.53125, -1.1875)),
    ESHUUMBRELLAS(CommodityType.CONSUMER_ITEMS, "Shajn Terminal", new StarSystem("Eshu", 120.78125, -247.1875, -16.4375)),
    ESUSEKUCAVIAR(CommodityType.FOODS, "Savinykh Orbital", new StarSystem("Esuseku", -107.875, 29.5625, -20.9375)),
    ETHGREZETEABUDS(CommodityType.FOODS, "Bloch Station", new StarSystem("Ethgreze", -30.03125, 72.34375, -23.8125)),
    FUJINTEA(CommodityType.FOODS, "Futen Spaceport", new StarSystem("Fujin", -6.03125, -30.375, -59.03125)),
    GALACTICTRAVELGUIDE(CommodityType.SALVAGE, "Bluford Orbital", new StarSystem("LHS 3447", -43.1875, -5.28125, 56.15625)),
    GEAWENDANCEDUST(CommodityType.LEGAL_DRUGS, "Obruchev Legacy", new StarSystem("Geawen", 22.5, 23.78125, 171.0625)),
    GERASIANGUEUZEBEER(CommodityType.LEGAL_DRUGS, "Yurchikhin Port", new StarSystem("Geras", -7.03125, -12.875, -56.375)),
    GIANTIRUKAMASNAILS(CommodityType.FOODS, "Blaauw City", new StarSystem("Irukama", 140.71875, -96.96875, 67.78125)),
    GIANTVERRIX(CommodityType.MACHINERY, "Greeboski's Outpost", new StarSystem("Phiagre", 44.28125, -82.96875, 52.5)),
    GILYASIGNATUREWEAPONS(CommodityType.WEAPONS, "Bell Orbital", new StarSystem("Gilya", -76.78125, 51.03125, 21.75)),
    GOMANYAUPONCOFFEE(CommodityType.FOODS, "Gustav Sporer Port", new StarSystem("Goman", 151.875, -173.78125, 25.28125)),
    HAIDNEBLACKBREW(CommodityType.FOODS, "Searfoss Enterprise", new StarSystem("Haiden", -142.03125, -13.34375, -43.90625)),
    HARMASILVERSEARUM(CommodityType.LEGAL_DRUGS, "Gabriel Enterprise", new StarSystem("Harma", -99.25, -100.96875, 20.40625)),
    HAVASUPAIDREAMCATCHER(CommodityType.CONSUMER_ITEMS, "Lovelace Port", new StarSystem("Havasupai", -88.75, -76.75, -39.625)),
    HELVETITJPEARLS(CommodityType.METALS, "Friend Orbital", new StarSystem("Helvetitj", -23.1875, 80.03125, 61.84375)),
    HIP10175BUSHMEAT(CommodityType.FOODS, "Stefanyshyn-Piper Station", new StarSystem("HIP 10175", -45.78125, -93.5, -83.90625)),
    HIP118311SWARM(CommodityType.WEAPONS, "Lubbock Market", new StarSystem("HIP 118311", 9.40625, -123.5, 31.59375)),
    HIP41181SQUID(CommodityType.FOODS, "Andersson Station", new StarSystem("HIP 41181", -8.15625, 74.8125, -105.125)),
    HIPORGANOPHOSPHATES(CommodityType.CHEMICALS, "Stasheff Colony", new StarSystem("HIP 80364", -101.59375, 93.78125, 9.90625)),
    HOLVADUELLINGBLADES(CommodityType.WEAPONS, "Kreutz Orbital", new StarSystem("Holva", 58.6875, -170.96875, -41.96875)),
    HONESTYPILLS(CommodityType.MEDICINES, "King Gateway", new StarSystem("LP 375-25", 9.53125, 59.3125, -13.21875)),
    HR7221WHEAT(CommodityType.FOODS, "Veron City", new StarSystem("HR 7221", 58.53125, -55.8125, 91.25)),
    INDIBOURBON(CommodityType.LEGAL_DRUGS, "Mansfield Orbiter", new StarSystem("Epsilon Indi", 3.125, -8.875, 7.125)),
    JAQUESQUINENTIANSTILL(CommodityType.CONSUMER_ITEMS, "Jaques Station", new StarSystem("Colonia", -9530.5, -910.28125, 19808.125)),
    JARADHARREPUZZLEBOX(CommodityType.CONSUMER_ITEMS, "Gohar Station", new StarSystem("Jaradharre", 39.53125, 21.28125, 56.625)),
    JAROUARICE(CommodityType.FOODS, "McCool City", new StarSystem("Jaroua", 157.53125, -110.53125, 28.25)),
    JOTUNMOOKAH(CommodityType.TEXTILES, "Icelock", new StarSystem("Jotun", -11.03125, -79.21875, -92.3125)),
    KACHIRIGINLEACHES(CommodityType.MEDICINES, "Nowak Orbital", new StarSystem("Kachirigin", -105.375, -73.34375, 27.75)),
    KAMITRACIGARS(CommodityType.LEGAL_DRUGS, "Hammel Terminal", new StarSystem("Kamitra", 5.53125, -183.40625, 63.84375)),
    KAMORINHISTORICWEAPONS(CommodityType.WEAPONS, "Godwin Vision", new StarSystem("Kamorin", -123.875, -81.59375, 45.1875)),
    KARETIICOUTURE(CommodityType.CONSUMER_ITEMS, "Sinclair Platform", new StarSystem("Karetii", -125.59375, 44.03125, 78.40625)),
    KARSUKILOCUSTS(CommodityType.FOODS, "West Market", new StarSystem("Karsuki Ti", 134.03125, -163.59375, 71.0625)),
    KINAGOINSTRUMENTS(CommodityType.CONSUMER_ITEMS, "Fozard Ring", new StarSystem("Kinago", -67.40625, -7.40625, 150.0625)),
    KONGGAALE(CommodityType.LEGAL_DRUGS, "Laplace Ring", new StarSystem("Kongga", -104.15625, 82.71875, -32.375)),
    KORROKUNGPELLETS(CommodityType.CHEMICALS, "Lonchakov Orbital", new StarSystem("Korro Kung", 81.03125, 52.84375, 31.5)),
    LAVIANBRANDY(CommodityType.LEGAL_DRUGS, "Lave Station", new StarSystem("Lave", 75.75, 48.75, 70.75)),
    LEESTIANEVILJUICE(CommodityType.LEGAL_DRUGS, "George Lucas", new StarSystem("Leesti", 72.75, 48.75, 68.25)),
    LFTVOIDEXTRACTCOFFEE(CommodityType.FOODS, "Ehrlich Orbital", new StarSystem("LFT 1421", -45.46875, 18.5625, 12.59375)),
    LIVEHECATESEAWORMS(CommodityType.FOODS, "RJH1972", new StarSystem("Hecate", -56.0, -25.125, -44.28125)),
    LTTHYPERSWEET(CommodityType.FOODS, "Smeaton Orbital", new StarSystem("LTT 9360", -16.59375, -62.6875, 21.40625)),
    LYRAEWEED(CommodityType.LEGAL_DRUGS, "Budrys Ring", new StarSystem("16 Lyrae", -113.53125, 37.03125, 25.53125)),
    MASTERCHEFS(CommodityType.SLAVERY, "Pataarcy Corporate", new StarSystem("Viracocha", 14.0, -46.4375, 9.65625)),
    MECHUCOSHIGHTEA(CommodityType.FOODS, "Brandenstein Port", new StarSystem("Mechucos", 67.03125, 39.59375, -70.09375)),
    MEDBSTARLUBE(CommodityType.CHEMICALS, "Vela Dock", new StarSystem("Medb", 12.78125, 4.8125, 39.34375)),
    MOKOJINGBEASTFEAST(CommodityType.FOODS, "Noli Terminal", new StarSystem("Mokojing", 73.4375, 58.34375, -0.21875)),
    MOMUSBOGSPANIEL(CommodityType.CONSUMER_ITEMS, "Tartarus Point", new StarSystem("Momus Reach", -34.9375, -44.15625, -77.34375)),
    MOTRONAEXPERIENCEJELLY(CommodityType.LEGAL_DRUGS, "Pinzon Dock", new StarSystem("Dea Motrona", -12.15625, 62.625, 29.71875)),
    MUKUSUBIICHITINOS(CommodityType.FOODS, "Ledyard Dock", new StarSystem("Mukusubii", -147.46875, -64.125, 46.09375)),
    MULACHIGIANTFUNGUS(CommodityType.FOODS, "Clark Terminal", new StarSystem("Mulachi", 6.25, -5.78125, 35.9375)),
    NANOMEDICINES(CommodityType.MEDICINES, "Elion Dock", new StarSystem("Kuma", -82.15625, 53.71875, 10.0625)),
    NERITUSBERRIES(CommodityType.FOODS, "Toll Ring", new StarSystem("Neritus", 75.4375, 2.625, -30.875)),
    NGADANDARIFIREOPALS(CommodityType.METALS, "Napier Terminal", new StarSystem("Ngadandari", 62.75, -74.1875, 109.5)),
    NGUNAMODERNANTIQUES(CommodityType.CONSUMER_ITEMS, "Biggle Hub", new StarSystem("Nguna", -122.78125, -102.53125, -22.5625)),
    NJANGARISADDLES(CommodityType.CONSUMER_ITEMS, "Lee Hub", new StarSystem("Njangari", 8.875, -205.4375, 64.375)),
    NONEUCLIDIANEXOTANKS(CommodityType.MACHINERY, "Euclid Terminal", new StarSystem("LTT 8517", 4.0, -55.625, 52.65625)),
    OCHOENGCHILLIES(CommodityType.FOODS, "Roddenberry Gateway", new StarSystem("Ochoeng", -139.0625, -2.3125, -6.65625)),
    ONIONHEAD(CommodityType.LEGAL_DRUGS, "Harvestport", new StarSystem("Kappa Fornacis", 12.46875, -66.71875, -22.90625)),
    ONIONHEADA(CommodityType.LEGAL_DRUGS, "Navigator Market", new StarSystem("Xelabara", -93.40625, 66.0625, -44.96875)),
    ONIONHEADB(CommodityType.LEGAL_DRUGS, "la Cosa City", new StarSystem("HIP 112974", -59.09375, -107.875, 34.4375)),
    OPHIUCHIEXINOARTEFACTS(CommodityType.CONSUMER_ITEMS, "Katzenstein Dock", new StarSystem("36 Ophiuchi", 0.4375, 2.09375, 19.21875)),
    ORRERIANVICIOUSBREW(CommodityType.FOODS, "Sharon Lee Free Market", new StarSystem("Orrere", 68.84375, 48.75, 76.75)),
    PANTAAPRAYERSTICKS(CommodityType.MEDICINES, "Zamka Platform", new StarSystem("George Pantazis", -12.09375, -16.0, -14.21875)),
    PAVONISEARGRUBS(CommodityType.LEGAL_DRUGS, "Hooper Relay", new StarSystem("Delta Pavonis", 8.375, -10.84375, 14.46875)),
    PERSONALGIFTS(CommodityType.SALVAGE, "Frost Dock", new StarSystem("Njambalba", -3.65625, -153.71875, 26.46875)),
    PLATINUMALOY(CommodityType.METALS, "Artzybasheff Terminal", new StarSystem("Nahuatl", 65.25, -85.75, -6.15625)),
    RAJUKRUSTOVES(CommodityType.CONSUMER_ITEMS, "Snyder Terminal", new StarSystem("Rajukru", -96.9375, 143.4375, 4.625)),
    RAPABAOSNAKESKINS(CommodityType.TEXTILES, "Flagg Gateway", new StarSystem("Rapa Bao", 94.15625, -167.375, -23.1875)),
    RUSANIOLDSMOKEY(CommodityType.LEGAL_DRUGS, "Fernandes Market", new StarSystem("Rusani", -64.75, 57.21875, -82.625)),
    SANUMAMEAT(CommodityType.FOODS, "Dunyach Gateway", new StarSystem("Sanuma", 153.4375, 61.4375, 79.03125)),
    SAXONWINE(CommodityType.LEGAL_DRUGS, "Hunt Enterprise", new StarSystem("9 Aurigae", -32.75, 10.5, -78.5625)),
    SHANSCHARISORCHID(CommodityType.CONSUMER_ITEMS, "Baird Gateway", new StarSystem("Arque", 66.5, 38.0625, 61.125)),
    SOONTILLRELICS(CommodityType.CONSUMER_ITEMS, "Cheranovsky City", new StarSystem("Ngurii", 169.5, -42.34375, 87.0625)),
    SOTHISCRYSTALLINEGOLD(CommodityType.METALS, "Newholm Station", new StarSystem("Sothis", -352.78125, 10.5, -346.34375)),
    TANMARKTRANQUILTEA(CommodityType.FOODS, "Cassie-L-Peia", new StarSystem("Tanmark", 1.6875, -77.875, -30.09375)),
    TARACHTORSPICE(CommodityType.LEGAL_DRUGS, "Tranquillity", new StarSystem("Tarach Tor", -13.125, -81.0, -20.28125)),
    TAURICHIMES(CommodityType.CONSUMER_ITEMS, "Porta", new StarSystem("39 Tauri", -7.3125, -20.28125, -50.90625)),
    TERRAMATERBLOODBORES(CommodityType.MEDICINES, "GR8Minds", new StarSystem("Terra Mater", -49.75, -19.03125, -45.0)),
    THEHUTTONMUG(CommodityType.CONSUMER_ITEMS, "Hutton Orbital", new StarSystem("Alpha Centauri", 3.03125, -0.09375, 3.15625)),
    THRUTISCREAM(CommodityType.LEGAL_DRUGS, "Kingsbury Dock", new StarSystem("Thrutis", -90.6875, 8.125, -79.53125)),
    TIEGFRIESSYNTHSILK(CommodityType.TEXTILES, "Larbalestier Dock", new StarSystem("Tiegfries", 21.96875, 43.0, -139.0)),
    TIOLCEWASTE2PASTEUNITS(CommodityType.CONSUMER_ITEMS, "Gordon Terminal", new StarSystem("Tiolce", 10.5, -34.78125, 25.0625)),
    TOXANDJIVIROCIDE(CommodityType.CHEMICALS, "Tsunenaga Orbital", new StarSystem("Toxandji", 155.09375, -12.40625, 61.0625)),
    TRANSGENICONIONHEAD(CommodityType.LEGAL_DRUGS, "Cassie-L-Peia", new StarSystem("Tanmark", 1.6875, -77.875, -30.09375)),
    USZAIANTREEGRUB(CommodityType.FOODS, "Guest Installation", new StarSystem("Uszaa", 68.84375, 48.75, 74.75)),
    UTGAROARMILLENIALEGGS(CommodityType.FOODS, "Fort Klarix", new StarSystem("Utgaroar", -0.15625, -102.75, -5.5625)),
    UZUMOKULOWGWINGS(CommodityType.CONSUMER_ITEMS, "Sverdrup Ring", new StarSystem("Uzumoku", -95.21875, 9.875, -74.125)),
    VANAYEQUIRHINOFUR(CommodityType.TEXTILES, "Clauss Hub", new StarSystem("Vanayequi", -88.5, -12.3125, 98.84375)),
    VEGASLIMWEED(CommodityType.MEDICINES, "Taylor City", new StarSystem("Vega", -21.90625, 8.125, 9.0)),
    VHERCULISBODYRUB(CommodityType.MEDICINES, "Kaku Plant", new StarSystem("V1090 Herculis", -44.9375, 36.9375, 13.46875)),
    VIDAVANTIANLACE(CommodityType.CONSUMER_ITEMS, "Lee Mines", new StarSystem("Vidavanta", -50.9375, 90.21875, 65.8125)),
    VOLKHABBEEDRONES(CommodityType.MACHINERY, "Vernadsky Dock", new StarSystem("Volkhab", 36.53125, 100.0625, -135.71875)),
    WATERSOFSHINTARA(CommodityType.MEDICINES, "Jameson Memorial", new StarSystem("Shinrarta Dezhra", 55.71875, 17.59375, 27.15625)),
    WHEEMETEWHEATCAKES(CommodityType.FOODS, "Eisinga Enterprise", new StarSystem("Wheemete", 145.125, -140.0625, 131.96875)),
    WITCHHAULKOBEBEEF(CommodityType.FOODS, "Hornby Terminal", new StarSystem("Witchhaul", -13.25, -52.0625, -65.875)),
    WOLF1301FESH(CommodityType.LEGAL_DRUGS, "Saunders's Dive", new StarSystem("Wolf 1301", -15.4375, -34.6875, -79.40625)),
    WULPAHYPERBORESYSTEMS(CommodityType.MACHINERY, "Williams Gateway", new StarSystem("Wulpa", -126.09375, -83.09375, -85.5625)),
    WUTHIELOKUFROTH(CommodityType.LEGAL_DRUGS, "Tarter Dock", new StarSystem("Wuthielo Ku", 68.3125, -190.03125, 12.375)),
    XIHECOMPANIONS(CommodityType.TECHNOLOGY, "Zhen Dock", new StarSystem("Xihe", -19.8125, -35.375, 34.0)),
    YASOKONDILEAF(CommodityType.LEGAL_DRUGS, "Wheeler Market", new StarSystem("Yaso Kondi", 4.9375, -175.90625, -0.90625)),
    ZEESSZEANTGLUE(CommodityType.CONSUMER_ITEMS, "Nicollier Hangar", new StarSystem("Zeessze", -19.8125, -3.25, -14.28125)),
    UNKNOWN(CommodityType.UNKNOWN, null, null);

    private final CommodityType commodityType;
    private final String station;
    private final StarSystem starSystem;
    private final GameVersion gameVersion;

    RareCommodity(final CommodityType commodityType, final String station, final StarSystem starSystem) {
        this(commodityType, station, starSystem, GameVersion.LEGACY);
    }

    public static RareCommodity forName(final String name) {
        try {
            return RareCommodity.valueOf(name.toUpperCase());
        } catch (final IllegalArgumentException ex) {
            return RareCommodity.UNKNOWN;
        }
    }

    @Override
    public boolean isPurchasable() {
        return false;
    }

    @Override
    public boolean isUnknown() {
        return this == RareCommodity.UNKNOWN;
    }


}
