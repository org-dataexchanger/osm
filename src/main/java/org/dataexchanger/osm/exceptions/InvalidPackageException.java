package org.dataexchanger.osm.exceptions;

public class InvalidPackageException extends RuntimeException {
    private String message;

    public InvalidPackageException(String message) {
        super(message);
        this.message = message;
    }
}
