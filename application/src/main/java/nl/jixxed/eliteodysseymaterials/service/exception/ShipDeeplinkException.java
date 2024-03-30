package nl.jixxed.eliteodysseymaterials.service.exception;

public class ShipDeeplinkException extends RuntimeException {
    public ShipDeeplinkException() {
    }

    public ShipDeeplinkException(final String message) {
        super(message);
    }

    public ShipDeeplinkException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ShipDeeplinkException(final Throwable cause) {
        super(cause);
    }

    public ShipDeeplinkException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
