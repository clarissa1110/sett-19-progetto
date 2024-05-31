package it.epicode.gestioneEventi.exception;

import it.epicode.gestioneEventi.model.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> BadRequestHandler(BadRequestException e){
        Error error = new Error();
        error.setMessage(e.getMessage());
        error.setErrorStatus(HttpStatus.BAD_REQUEST);
        error.setErrorTime(LocalDateTime.now());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UtenteNotFoundException.class)
    public ResponseEntity<Object> UtenteNotFoundHandler(UtenteNotFoundException e){
        Error error = new Error();
        error.setMessage(e.getMessage());
        error.setErrorStatus(HttpStatus.NOT_FOUND);
        error.setErrorTime(LocalDateTime.now());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EventoNotFoundException.class)
    public ResponseEntity<Object> EventoNotFoundHandler(EventoNotFoundException e){
        Error error = new Error();
        error.setMessage(e.getMessage());
        error.setErrorStatus(HttpStatus.NOT_FOUND);
        error.setErrorTime(LocalDateTime.now());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PrenotazioneNotFoundException.class)
    public ResponseEntity<Object> PrenotazioneNotFoundHandler(PrenotazioneNotFoundException e){
        Error error = new Error();
        error.setMessage(e.getMessage());
        error.setErrorStatus(HttpStatus.NOT_FOUND);
        error.setErrorTime(LocalDateTime.now());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Object> UnauthorizedExceptionHandler (UnauthorizedException e){
        Error error  = new Error();
        error.setMessage(e.getMessage());
        error.setErrorStatus(HttpStatus.UNAUTHORIZED);
        error.setErrorTime(LocalDateTime.now());

        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }
}
