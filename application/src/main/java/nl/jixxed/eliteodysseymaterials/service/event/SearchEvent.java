package nl.jixxed.eliteodysseymaterials.service.event;

import nl.jixxed.eliteodysseymaterials.domain.Search;

public class SearchEvent extends Event {
    private final Search search;

    public SearchEvent(final Search search) {
        this.search = search;
    }

    public Search getSearch() {
        return this.search;
    }
}
