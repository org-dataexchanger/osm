package org.dataexchanger.osm.exceptions;

public class InvalidSheetException extends RuntimeException{
    private String message;

    public InvalidSheetException(String message) {
        this.message = message;
    }
}
