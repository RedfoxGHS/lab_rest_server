package br.inatel.labs.labrest.server.controllers.exceptions;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class GlobalControllerException {

    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<StandardError> numberFormatHandler(NumberFormatException e, HttpServletRequest request) {
        StandardError error = new StandardError();
        error.setTimestamp(Instant.now());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        error.setMessage("O valor digitado [" + getParamOfError(e.getLocalizedMessage()) + "] deve ser um valor numerico.");
        error.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(CursoAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<StandardError> cursoAlreadyExistHandler(CursoAlreadyExistException e, HttpServletRequest request) {
        StandardError error = new StandardError();
        error.setTimestamp(Instant.now());
        error.setStatus(HttpStatus.CONFLICT.value());
        error.setError(HttpStatus.CONFLICT.getReasonPhrase());
        error.setMessage(e.getMessage());
        error.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(InvalidFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<StandardError> invalidFormatHandler(InvalidFormatException e, HttpServletRequest request) {
        StandardError error = new StandardError();
        error.setTimestamp(Instant.now());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());

        error.setMessage("O valor digitado [" + e.getValue().toString() + "] deve ser um valor numerico.");
        error.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    private String getParamOfError(String msg) {
        int start = msg.indexOf(": ") + 3;
        msg = msg.substring(start);
        int end = msg.indexOf("\"");
        return msg.substring(0, end);
    }
}
