package org.dataexchanger.osm.exceptions;

/**
 * @author dipanjal
 * @since 7/16/2020
 */
public class FieldCastException extends RuntimeException {
    private final String message;

    public FieldCastException(String message) {
        super(message);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
