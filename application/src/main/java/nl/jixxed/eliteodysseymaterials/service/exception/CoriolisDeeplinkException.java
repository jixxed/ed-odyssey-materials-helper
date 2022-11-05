package nl.jixxed.eliteodysseymaterials.service.exception;

public class CoriolisDeeplinkException extends RuntimeException {
    public CoriolisDeeplinkException() {
    }

    public CoriolisDeeplinkException(final String message) {
        super(message);
    }

    public CoriolisDeeplinkException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public CoriolisDeeplinkException(final Throwable cause) {
        super(cause);
    }

    public CoriolisDeeplinkException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
