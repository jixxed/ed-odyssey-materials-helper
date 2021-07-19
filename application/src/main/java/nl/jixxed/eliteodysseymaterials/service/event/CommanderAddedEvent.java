package nl.jixxed.eliteodysseymaterials.service.event;

public class CommanderAddedEvent extends Event {
    private final String name;

    public CommanderAddedEvent(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
