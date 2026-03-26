package nl.jixxed.eliteodysseymaterials.parser.messageprocessor.capi;

public interface CapiMessageProcessor<T> {
    void process(final T data);
    Class<T> getMessageClass();
    void clear();

}
