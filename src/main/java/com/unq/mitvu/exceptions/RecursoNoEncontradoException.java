package com.unq.mitvu.exceptions;

public class RecursoNoEncontradoException extends RuntimeException {
    public RecursoNoEncontradoException(String id, String message) {
        super(message);
    }
}
