package br.inatel.labs.labrest.server.controllers.exceptions;

public class CursoAlreadyExistException extends RuntimeException {

    public CursoAlreadyExistException(String message) {
        super(message);
    }
}
