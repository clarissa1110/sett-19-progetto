package it.epicode.gestioneEventi.service;

import it.epicode.gestioneEventi.dto.EventoDto;
import it.epicode.gestioneEventi.exception.EventoNotFoundException;
import it.epicode.gestioneEventi.exception.UnauthorizedException;
import it.epicode.gestioneEventi.exception.UtenteNotFoundException;
import it.epicode.gestioneEventi.model.Evento;
import it.epicode.gestioneEventi.model.Utente;
import it.epicode.gestioneEventi.repository.EventoRepository;
import it.epicode.gestioneEventi.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private UtenteRepository utenteRepository;

    public String saveEvento(EventoDto eventoDto){
        Evento evento = new Evento();
        evento.setTitolo(eventoDto.getTitolo());
        evento.setDescrizione(eventoDto.getDescrizione());
        evento.setDataEvento(eventoDto.getDataEvento());
        evento.setLuogo(eventoDto.getLuogo());
        evento.setPostiDisponibili(eventoDto.getPostiDisponibili());

        Optional<Utente> utenteOptional = utenteRepository.findById(eventoDto.getOrganizzatoreId());

        if (utenteOptional.isPresent()){
            Utente utente = utenteOptional.get();
            evento.setOrganizzatore(utente);

            eventoRepository.save(evento);
            return "Evento con id = " + evento.getId() + " è stato creato correttamente";
        }
        else {
                throw new UtenteNotFoundException("Utente con id = " + evento.getOrganizzatore().getId() + " non trovato");
        }
    }

    public List<Evento> getEventi(){
        return eventoRepository.findAll();
    }

    public Optional<Evento> getEventoById(int id){
        return eventoRepository.findById(id);
    }

    public Evento updateEvento(int id, EventoDto eventoDto){
        Optional<Evento> eventoOp = getEventoById(id);

        if (eventoOp.isPresent()){
            Evento evento = eventoOp.get();
            evento.setTitolo(eventoDto.getTitolo());
            evento.setDescrizione(eventoDto.getDescrizione());
            evento.setDataEvento(eventoDto.getDataEvento());
            evento.setLuogo(eventoDto.getLuogo());
            evento.setPostiDisponibili(eventoDto.getPostiDisponibili());

            Optional<Utente> utenteOptional = utenteRepository.findById(eventoDto.getOrganizzatoreId());

            if (utenteOptional.isPresent()){
                Utente utente = utenteOptional.get();
                evento.setOrganizzatore(utente);

                eventoRepository.save(evento);

                return evento;
            }
            else {
                throw new UtenteNotFoundException("Utente con id = " + eventoDto.getOrganizzatoreId() + " non trovato");
            }
        }
        else {
            throw new EventoNotFoundException("Evento con id = " + id + " non trovato");
        }
    }

    public String deleteEvento(int id){
        Optional<Evento> eventoOp = getEventoById(id);

        if (eventoOp.isPresent()){
            eventoRepository.delete(eventoOp.get());
            return "Evento evento con id = " + id + " eliminato correttamente";
        }
        else {
            throw new EventoNotFoundException("Evento con id = " + id + " non trovato");
        }
    }
}
