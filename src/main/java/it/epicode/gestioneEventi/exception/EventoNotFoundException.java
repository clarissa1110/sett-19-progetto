package it.epicode.gestioneEventi.exception;

public class EventoNotFoundException extends RuntimeException{

    public EventoNotFoundException(String message){
        super(message);
    }
}
