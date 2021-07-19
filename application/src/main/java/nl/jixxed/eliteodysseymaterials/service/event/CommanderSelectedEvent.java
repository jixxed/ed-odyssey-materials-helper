package nl.jixxed.eliteodysseymaterials.service.event;

public class CommanderSelectedEvent extends Event {
    private final String name;

    public CommanderSelectedEvent(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
