package nl.jixxed.eliteodysseymaterials.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum JournalEventType {
    FILEHEADER("Fileheader"),
    CARRIERJUMP("CarrierJump"),
    COMMANDER("Commander"),
    ENGINEERPROGRESS("EngineerProgress"),
    EMBARK("Embark"),
    SHIPLOCKER("ShipLocker"),
    BACKPACK("Backpack"),
    CAPIFLEETCARRIER("CapiFleetCarrier"),//CAPI Resource
    BACKPACKCHANGE("BackpackChange"),
    RESUPPLY("Resupply"),
    FSDJUMP("FSDJump"),
    LOADOUT("Loadout"),
    LOCATION("Location"),
    DOCKED("Docked"),
    DOCKINGDENIED("DockingDenied"),
    DOCKINGGRANTED("DockingGranted"),
    TOUCHDOWN("Touchdown"),
    UNDOCKED("Undocked"),
    LIFTOFF("Liftoff"),
    APPROACHBODY("ApproachBody"),
    APPROACHSETTLEMENT("ApproachSettlement"),
    LEAVEBODY("LeaveBody"),
    SUPERCRUISEENTRY("SupercruiseEntry"),
    LOADGAME("LoadGame"),
    UNKNOWN("Unknown"),
    MATERIALS("Materials"),
    MATERIALCOLLECTED("MaterialCollected"),
    MATERIALTRADE("MaterialTrade"),
    MATERIALDISCARDED("MaterialDiscarded"),
    ENGINEERCONTRIBUTION("EngineerContribution"),
    ENGINEERCRAFT("EngineerCraft"),
    MISSIONCOMPLETED("MissionCompleted"),
    SYNTHESIS("Synthesis"),
    UPGRADESUIT("UpgradeSuit"),
    UPGRADEWEAPON("UpgradeWeapon"),
    RECEIVETEXT("ReceiveText"),
    SENDTEXT("SendText"),
    BUYMICRORESOURCES("BuyMicroResources"),
    MARKETBUY("MarketBuy"),
    MARKETSELL("MarketSell"),
    CARGO("Cargo"),
    CARGOTRANSFER("CargoTransfer"),
    TECHNOLOGYBROKER("TechnologyBroker"),
    SUITLOADOUT("SuitLoadout"),
    SWITCHSUITLOADOUT("SuitLoadout"),
    CODEXENTRY("CodexEntry"),
    SHIPYARD("Shipyard"),
    OUTFITTING("Outfitting"),
    MARKET("Market"),
    NAVROUTE("NavRoute"),
    NAVROUTECLEAR("NavRouteClear"),
    MODULEINFO("ModuleInfo"),
    FCMATERIALS("FCMaterials"),
    SCAN("Scan"),
    SCANBARYCENTRE("ScanBaryCentre"),
    SAASIGNALSFOUND("SAASignalsFound"),
    FSSSIGNALDISCOVERED("FSSSignalDiscovered"),
    FSSALLBODIESFOUND("FSSAllBodiesFound"),
    FSSBODYSIGNALS("FSSBodySignals"),
    FSSDISCOVERYSCAN("FSSDiscoveryScan"),
    NAVBEACONSCAN("NavBeaconScan"),
    STATUS("Status"),
    USSDROP("USSDrop"),
    SHUTDOWN("Shutdown"),
    MUSIC("Music"),
    DELIVERPOWERMICRORESOURCES("DeliverPowerMicroResources"),
    STARTJUMP("StartJump"),
    POWERPLAY("Powerplay"),
    POWERPLAYLEAVE("PowerplayLeave"),
    POWERPLAYRANK("PowerplayRank"),
    POWERPLAYMERITS("PowerplayMerits"),
    SCIENTIFICRESEARCH("ScientificResearch"),
    COLONISATIONCONSTRUCTIONDEPOT("ColonisationConstructionDepot");
    private final String name;

    public static JournalEventType forName(final String name) {
        try {
            return JournalEventType.valueOf(name.toUpperCase());
        } catch (final IllegalArgumentException ex) {
            return JournalEventType.UNKNOWN;
        }
    }

    public String friendlyName() {
        return this.name;
    }
}
