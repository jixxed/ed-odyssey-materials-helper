package nl.jixxed.eliteodysseymaterials.service.exception;

public class WishlistDeeplinkException extends RuntimeException {
    public WishlistDeeplinkException() {
    }

    public WishlistDeeplinkException(final String message) {
        super(message);
    }

    public WishlistDeeplinkException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public WishlistDeeplinkException(final Throwable cause) {
        super(cause);
    }

    public WishlistDeeplinkException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
