package ru.otus.jdbc.exceptions;

public class MetaDataException extends RuntimeException {

    public MetaDataException(Exception e) {
        super(e);
    }

    public MetaDataException(String message) {
        super(message);
    }
}
