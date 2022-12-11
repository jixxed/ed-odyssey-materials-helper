package nl.jixxed.eliteodysseymaterials.service.eddn;

import nl.jixxed.eliteodysseymaterials.enums.Expansion;
import nl.jixxed.eliteodysseymaterials.schemas.eddn.navroute.Message;
import nl.jixxed.eliteodysseymaterials.schemas.eddn.navroute.Route;
import nl.jixxed.eliteodysseymaterials.schemas.journal.NavRoute.NavRoute;

public class EDDNNavRouteMapper extends EDDNMapper {
    public static Message mapToEDDN(final NavRoute navRoute, final Expansion expansion) {
        return new Message.MessageBuilder()
                .withTimestamp(navRoute.getTimestamp())
                .withEvent(navRoute.getEvent())
                .withHorizons(expansion.equals(Expansion.HORIZONS) || expansion.equals(Expansion.ODYSSEY))
                .withOdyssey(expansion.equals(Expansion.ODYSSEY))
                .withRoute(mapToNullIfEmptyList(navRoute.getRoute())
                        .map(routes -> routes.stream()
                                .map(route -> new Route.RouteBuilder()
                                        .withStarPos(route.getStarPos())
                                        .withStarSystem(route.getStarSystem())
                                        .withStarClass(route.getStarClass())
                                        .withSystemAddress(route.getSystemAddress())
                                        .build())
                                .toList())
                        .orElse(null))
                .build();
    }
}
