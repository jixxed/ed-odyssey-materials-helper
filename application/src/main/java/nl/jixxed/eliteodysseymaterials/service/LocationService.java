package nl.jixxed.eliteodysseymaterials.service;

import lombok.Getter;
import lombok.Setter;
import nl.jixxed.eliteodysseymaterials.domain.Location;
import nl.jixxed.eliteodysseymaterials.domain.StarSystem;
import nl.jixxed.eliteodysseymaterials.service.event.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class LocationService {
    private static final Double DEFAULT_LATITUDE = 999.9;
    private static final Double DEFAULT_LONGITUDE = 999.9;
    @Getter
    private static StarSystem currentStarSystem = new StarSystem("Sol", 0, 0, 0);
    @Getter
    private static BigInteger currentSystemAddress = new BigInteger("0");
    private static String body = "";
    private static String station = "";
    @Getter
    private static BigInteger marketID = BigInteger.ZERO;//default to 0 when not docked at a station
    private static Double latitude = DEFAULT_LATITUDE;
    private static Double longitude = DEFAULT_LONGITUDE;
    private static Long bodyID;
    @Getter
    @Setter
    private static Optional<String> statusBodyName;
    private static final List<EventListener<?>> EVENT_LISTENERS = new ArrayList<>();


    private LocationService() {
    }

    static {
        EVENT_LISTENERS.add(EventService.addStaticListener(ApproachBodyJournalEvent.class, event -> {//When approaching body
            body = event.getApproachBody().getBody();
            bodyID = event.getApproachBody().getBodyID().longValue();
            station = "";
            marketID = BigInteger.ZERO;
            notifyListeners();
        }));
        EVENT_LISTENERS.add(EventService.addStaticListener(ApproachSettlementJournalEvent.class, event -> {//when approaching settlement, also on startup if at settlement
            body = event.getApproachSettlement().getBodyName();
            bodyID = event.getApproachSettlement().getBodyID().longValue();
            station = "";
            marketID = BigInteger.ZERO;
            notifyListeners();
        }));
        EVENT_LISTENERS.add(EventService.addStaticListener(DockedJournalEvent.class, event -> {//Always player controlled
            station = event.getDocked().getStationName();
            marketID = event.getDocked().getMarketID();
            notifyListeners();
        }));
        EVENT_LISTENERS.add(EventService.addStaticListener(FSDJumpJournalEvent.class, event -> {//After jump to other system
            currentStarSystem = event.getStarSystem();
            currentSystemAddress = event.getEvent().getSystemAddress();
            body = event.getBody();
            bodyID = event.getEvent().getBodyID().longValue();
            notifyListeners();
        }));
        EVENT_LISTENERS.add(EventService.addStaticListener(CarrierJumpJournalEvent.class, event -> {//After jump to other system
            if (event.getEvent().getDocked()) {//should always be true, but let's be safe
                currentStarSystem = event.getStarSystem();
                currentSystemAddress = event.getEvent().getSystemAddress();
                body = event.getBody();
                bodyID = event.getEvent().getBodyID().longValue();
                notifyListeners();
            }
        }));
        EVENT_LISTENERS.add(EventService.addStaticListener(LeaveBodyJournalEvent.class, event -> {//on leaving body
            body = "";
            bodyID = null;
            station = "";
            marketID = BigInteger.ZERO;
            notifyListeners();
        }));
        EVENT_LISTENERS.add(EventService.addStaticListener(LiftOffJournalEvent.class, event -> {//can be either player or AI controlled
            if (event.getLiftoff().getPlayerControlled() || event.getLiftoff().getTaxi().orElse(false)) {// both false means ship sent away
                station = "";
                marketID = BigInteger.ZERO;
                latitude = DEFAULT_LATITUDE;
                longitude = DEFAULT_LONGITUDE;
            }
            notifyListeners();
        }));
        EVENT_LISTENERS.add(EventService.addStaticListener(LocationJournalEvent.class, event -> {//at startup or upon respawn
            currentStarSystem = event.getStarSystem();
            currentSystemAddress = event.getLocation().getSystemAddress();
            body = event.getBody();
            bodyID = event.getLocation().getBodyID().longValue();
            //on relog station is empty for POI's.
            //on respawn after death there is a station?
            //if we already have a station from before the relog, we keep the station
            if (!Objects.equals(event.getStationName(), "")) {
                station = event.getStationName();
                marketID = event.getLocation().getMarketID().orElse(BigInteger.ZERO);
            }
            notifyListeners();
        }));
        EVENT_LISTENERS.add(EventService.addStaticListener(SupercruiseEntryJournalEvent.class, event -> {//on entering SC
            station = "";
            marketID = BigInteger.ZERO;
            latitude = DEFAULT_LATITUDE;
            longitude = DEFAULT_LONGITUDE;
            notifyListeners();
        }));
        EVENT_LISTENERS.add(EventService.addStaticListener(TouchdownJournalEvent.class, event -> {//can be either player or AI controlled
            station = event.getTouchdown().getNearestDestination().orElse("");
            latitude = event.getTouchdown().getLatitude().map(BigDecimal::doubleValue).orElse(DEFAULT_LATITUDE);
            longitude = event.getTouchdown().getLongitude().map(BigDecimal::doubleValue).orElse(DEFAULT_LONGITUDE);

            notifyListeners();
        }));
        EVENT_LISTENERS.add(EventService.addStaticListener(UndockedJournalEvent.class, event -> {//Always player controlled
            station = "";
            marketID = BigInteger.ZERO;
            latitude = DEFAULT_LATITUDE;
            longitude = DEFAULT_LONGITUDE;
            notifyListeners();
        }));
    }

    public static void init() {
        //empty method to trigger static block
    }

    private static void notifyListeners() {
        EventService.publish(new LocationChangedEvent(currentStarSystem, body, station, latitude, longitude));
    }

    public static Location getCurrentLocation() {
        return new Location(currentStarSystem, body, bodyID, station, latitude, longitude);
    }

    public static Double calculateDistance(final StarSystem currentStarSystem, final StarSystem starSystem) {
        return calculateDistance(currentStarSystem.getX(), currentStarSystem.getY(), currentStarSystem.getZ(), starSystem.getX(), starSystem.getY(), starSystem.getZ());
    }

    private static Double calculateDistance(final double x1, final double y1, final double z1, final double x2, final double y2, final double z2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2) + Math.pow(z2 - z1, 2));
    }

    public static String getCurrentStarSystemName() {
        return currentStarSystem.getName();
    }

    public static List<Double> getCurrentStarPos() {
        return List.of(getCurrentStarSystem().getX(),
                getCurrentStarSystem().getY(),
                getCurrentStarSystem().getZ()
        );
    }

    public static List<Double> getCurrentStarPos(final BigInteger address) {
        if (currentSystemAddress.equals(address)) {
            return List.of(getCurrentStarSystem().getX(),
                    getCurrentStarSystem().getY(),
                    getCurrentStarSystem().getZ()
            );
        }
        return null;
    }

    public static String getCurrentStarSystemName(final BigInteger address) {
        if (currentSystemAddress.equals(address)) {
            return currentStarSystem.getName();
        }
        return null;
    }
}
