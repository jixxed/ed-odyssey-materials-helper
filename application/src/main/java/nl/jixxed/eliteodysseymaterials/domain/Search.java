package nl.jixxed.eliteodysseymaterials.domain;

import nl.jixxed.eliteodysseymaterials.enums.Show;
import nl.jixxed.eliteodysseymaterials.enums.Sort;

public class Search {
    private String query;
    private Sort sort;
    private Show show;

    public Search(final String query, final Sort sort, final Show show) {
        this.query = query;
        this.sort = sort;
        this.show = show;
    }

    public String getQuery() {
        return this.query;
    }

    public void setQuery(final String query) {
        this.query = query;
    }

    public Sort getSort() {
        return this.sort;
    }

    public void setSort(final Sort sort) {
        this.sort = sort;
    }

    public Show getShow() {
        return this.show;
    }

    public void setShow(final Show show) {
        this.show = show;
    }
}
