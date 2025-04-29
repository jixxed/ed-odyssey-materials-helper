package nl.jixxed.eliteodysseymaterials.domain;

public record ConstructionProgress(Integer provided, Integer required) {
    public ConstructionProgress sum(ConstructionProgress constructionProgress) {
        return new ConstructionProgress(provided + constructionProgress.provided, required + constructionProgress.required);
    }
}
