package nl.jixxed.eliteodysseymaterials.service.exception;

public class LoadoutDeeplinkException extends RuntimeException {
    public LoadoutDeeplinkException() {
    }

    public LoadoutDeeplinkException(final String message) {
        super(message);
    }

    public LoadoutDeeplinkException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public LoadoutDeeplinkException(final Throwable cause) {
        super(cause);
    }

    public LoadoutDeeplinkException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
