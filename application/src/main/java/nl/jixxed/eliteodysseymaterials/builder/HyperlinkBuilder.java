package nl.jixxed.eliteodysseymaterials.builder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableHyperlink;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HyperlinkBuilder extends AbstractButtonBaseBuilder<HyperlinkBuilder> {

    public static HyperlinkBuilder builder() {
        return new HyperlinkBuilder();
    }

    @Override
    @SuppressWarnings("unchecked")
    public DestroyableHyperlink build() {
        final DestroyableHyperlink hyperlink = new DestroyableHyperlink();
        super.build(hyperlink);
        return hyperlink;
    }

}
