package it.epicode.gestioneEventi.service;

import it.epicode.gestioneEventi.dto.EventoDto;
import it.epicode.gestioneEventi.dto.PrenotazioneDto;
import it.epicode.gestioneEventi.dto.UtenteDto;
import it.epicode.gestioneEventi.exception.BadRequestException;
import it.epicode.gestioneEventi.exception.EventoNotFoundException;
import it.epicode.gestioneEventi.exception.PrenotazioneNotFoundException;
import it.epicode.gestioneEventi.exception.UtenteNotFoundException;
import it.epicode.gestioneEventi.model.Evento;
import it.epicode.gestioneEventi.model.Prenotazione;
import it.epicode.gestioneEventi.model.Utente;
import it.epicode.gestioneEventi.repository.EventoRepository;
import it.epicode.gestioneEventi.repository.PrenotazioneRepository;
import it.epicode.gestioneEventi.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrenotazioneService {

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private EventoRepository eventoRepository;

    public String savePrenotazione(PrenotazioneDto prenotazioneDto){
        Optional<Evento> eventoOptional = eventoRepository.findById(prenotazioneDto.getEventoId());
        if (!eventoOptional.isPresent()){
            throw new EventoNotFoundException("Evento con id = " + prenotazioneDto.getEventoId() + " non trovato");
        }

        Optional<Utente> utenteOptional = utenteRepository.findById(prenotazioneDto.getUtenteId());
        if (!utenteOptional.isPresent()){
            throw new UtenteNotFoundException("Utente con id = " + prenotazioneDto.getUtenteId() + " non trovato");
        }

        Evento evento = eventoOptional.get();
        Utente utente = utenteOptional.get();

        if (evento.getPostiDisponibili() <= 0) {
            throw new BadRequestException("L'evento selezionato risulta al completo, impossibile effettuare una prenotazione");
        }
        else {
            Prenotazione prenotazione = new Prenotazione();
            prenotazione.setUtente(utente);
            prenotazione.setEvento(evento);

            evento.setPostiDisponibili(evento.getPostiDisponibili() - 1);
            eventoRepository.save(evento);

            prenotazioneRepository.save(prenotazione);
            return "Prenotazione con id = " + prenotazione.getId() + ", creata dall'utente " + prenotazione.getUtente() + ", per l'evento " + prenotazione.getEvento() + " salvata correttamente";
        }
    }

    public List<Prenotazione> getPretonationi(){
        return prenotazioneRepository.findAll();
    }

    public Optional<Prenotazione> getPrenotazioneById(int id){
        return prenotazioneRepository.findById(id);
    }

    public Prenotazione updatePrenotazione(int id, PrenotazioneDto prenotazioneDto){
        Optional<Prenotazione> prenotazioneOptional = prenotazioneRepository.findById(id);

        if (prenotazioneOptional.isPresent()){
            Prenotazione prenotazione = prenotazioneOptional.get();

            Optional<Evento> eventoOptional = eventoRepository.findById(prenotazioneDto.getEventoId());
            if (!eventoOptional.isPresent()){
                throw new EventoNotFoundException("Evento con id = " + prenotazioneDto.getEventoId() + " non trovato");
            }

            Optional<Utente> utenteOptional = utenteRepository.findById(prenotazioneDto.getUtenteId());
            if (!utenteOptional.isPresent()){
                throw new UtenteNotFoundException("Utente con id = " + prenotazioneDto.getUtenteId() + " non trovato");
            }

            Evento evento = eventoOptional.get();
            Utente utente = utenteOptional.get();

            if (evento.getPostiDisponibili() <= 0) {
                throw new BadRequestException("L'evento selezionato risulta al completo, impossibile effettuare una prenotazione");
            }
            else {
                prenotazione.setUtente(utente);
                prenotazione.setEvento(evento);

                evento.setPostiDisponibili(evento.getPostiDisponibili() - 1);
                eventoRepository.save(evento);

                prenotazioneRepository.save(prenotazione);
                return prenotazione;
            }
        }
        else {
            throw new PrenotazioneNotFoundException("Prenotazione con id = " + id + " non trovata");
        }
    }

    public String deletePrenotazione(int id){
        Optional<Prenotazione> prenotazioneOptional = prenotazioneRepository.findById(id);

        if (prenotazioneOptional.isPresent()){
            Prenotazione prenotazione = prenotazioneOptional.get();
            Evento evento = prenotazione.getEvento();
            prenotazioneRepository.delete(prenotazione);
            evento.setPostiDisponibili(evento.getPostiDisponibili() + 1);

            return "Prenotazione con id = " + id + " eliminata correttamente";
        }
        else {
            throw new PrenotazioneNotFoundException("Prenotazione con id = " + id + " non trovata");
        }
    }

    public List<Prenotazione> getPrenotazioniByIdUtente(int id){
        Optional<Utente> utenteOptional = utenteRepository.findById(id);

        if (utenteOptional.isPresent()){
            Utente utente = utenteOptional.get();

            return prenotazioneRepository.findByUtenteId(utente.getId());
        }
        else {
            throw new UtenteNotFoundException("Utente con id = " + id + " non trovato");
        }
    }
}
