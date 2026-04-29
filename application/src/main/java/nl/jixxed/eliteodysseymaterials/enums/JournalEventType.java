/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
    CAPISQUADRON("CapiSquadron"),//CAPI Resource
    CAPIARX("CapiArx"),//CAPI Resource
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
    EJECTCARGO("Ejectcargo"),
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
    COLONISATIONCONTRIBUTION("ColonisationContribution"),
    COLONISATIONCONSTRUCTIONDEPOT("ColonisationConstructionDepot"),
    SUPERCRUISEDESTINATIONDROP("SupercruiseDestinationDrop"),
    SUPERCRUISEEXIT("SupercruiseExit"),
    PROSPECTEDASTEROID("ProspectedAsteroid"),
    CARRIERLOCATION("CarrierLocation"),
    CARRIERSTATS("CarrierStats"),
    CARRIERTRADEORDER("CarrierTradeOrder"),
    MODULEBUY("ModuleBuy"),
    MODULERETRIEVE("ModuleRetrieve"),
    MODULESELL("ModuleSell"),
    MODULESTORE("ModuleStore"),
    MODULESWAP("ModuleSwap"),
    REPUTATION("Reputation"),
    RANK("Rank"),
    PROGRESS("Progress"),
    COMMUNITYGOAL("CommunityGoal"),
    ;
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
