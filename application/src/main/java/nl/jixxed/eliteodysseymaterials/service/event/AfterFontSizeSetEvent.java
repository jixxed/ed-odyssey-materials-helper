package nl.jixxed.eliteodysseymaterials.service.event;

public class AfterFontSizeSetEvent extends Event {
    private final Integer fontSize;

    public AfterFontSizeSetEvent(final Integer fontSize) {
        this.fontSize = fontSize;
    }

    public Integer getFontSize() {
        return this.fontSize;
    }
}
