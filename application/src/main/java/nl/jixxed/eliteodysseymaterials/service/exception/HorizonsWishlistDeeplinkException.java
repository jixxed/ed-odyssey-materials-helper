package nl.jixxed.eliteodysseymaterials.service.exception;

public class HorizonsWishlistDeeplinkException extends RuntimeException {
    public HorizonsWishlistDeeplinkException() {
    }

    public HorizonsWishlistDeeplinkException(final String message) {
        super(message);
    }

    public HorizonsWishlistDeeplinkException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public HorizonsWishlistDeeplinkException(final Throwable cause) {
        super(cause);
    }

    public HorizonsWishlistDeeplinkException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
