package nl.jixxed.eliteodysseymaterials.service.event;

public class FontSizeEvent extends Event {
    private final Integer fontSize;

    public FontSizeEvent(final Integer fontSize) {
        this.fontSize = fontSize;
    }

    public Integer getFontSize() {
        return this.fontSize;
    }
}
