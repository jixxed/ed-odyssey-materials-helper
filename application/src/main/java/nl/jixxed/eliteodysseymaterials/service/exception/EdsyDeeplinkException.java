package nl.jixxed.eliteodysseymaterials.service.exception;

public class EdsyDeeplinkException extends RuntimeException {
    public EdsyDeeplinkException() {
    }

    public EdsyDeeplinkException(final String message) {
        super(message);
    }

    public EdsyDeeplinkException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public EdsyDeeplinkException(final Throwable cause) {
        super(cause);
    }

    public EdsyDeeplinkException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
