package it.epicode.gestioneEventi.service;

import it.epicode.gestioneEventi.dto.PrenotazioneDto;
import it.epicode.gestioneEventi.exception.EventoNotFoundException;
import it.epicode.gestioneEventi.exception.PrenotazioneNotFoundException;
import it.epicode.gestioneEventi.exception.UtenteNotFoundException;
import it.epicode.gestioneEventi.model.Evento;
import it.epicode.gestioneEventi.model.Prenotazione;
import it.epicode.gestioneEventi.model.Utente;
import it.epicode.gestioneEventi.repository.EventoRepository;
import it.epicode.gestioneEventi.repository.PrenotazioneRepository;
import it.epicode.gestioneEventi.repository.PrenotazioneRepository;
import it.epicode.gestioneEventi.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrenotazioneService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    public String savePrenotazione(PrenotazioneDto prenotazioneDto) throws Exception {
        Prenotazione prenotazione = new Prenotazione();

        Optional<Utente> utenteOptional = utenteRepository.findById(prenotazioneDto.getUtenteId());

        Optional<Evento> eventoOptional = eventoRepository.findById(prenotazioneDto.getEventoId());
        if (utenteOptional.isPresent() && eventoOptional.isPresent()){
            Utente utente = utenteOptional.get();
            prenotazione.setUtente(utente);

            Evento evento = eventoOptional.get();
            prenotazione.setEvento(evento);

            prenotazioneRepository.save(prenotazione);
            return "Prenotazione con id = " + prenotazione.getId() + " è stata creata correttamente";
        }
        else {
            throw new Exception("Utente con id = " + prenotazione.getUtente().getId() + " oppure evento con id = " + prenotazione.getEvento().getId() + " non trovato");
        }
    }

    public List<Prenotazione> getEventi(){
        return prenotazioneRepository.findAll();
    }

    public Optional<Prenotazione> getPrenotazioneById(int id){
        return prenotazioneRepository.findById(id);
    }

    public Prenotazione updatePrenotazione(int id, PrenotazioneDto prenotazioneDto) throws Exception {
        Optional<Prenotazione> prenotazioneOp = getPrenotazioneById(id);

        if (prenotazioneOp.isPresent()){
            Prenotazione prenotazione = prenotazioneOp.get();

            Optional<Utente> utenteOptional = utenteRepository.findById(prenotazioneDto.getUtenteId());

            Optional<Evento> eventoOptional = eventoRepository.findById(prenotazioneDto.getEventoId());

            if (utenteOptional.isPresent() && eventoOptional.isPresent()){
                Utente utente = utenteOptional.get();
                prenotazione.setUtente(utente);

                Evento evento = eventoOptional.get();
                prenotazione.setEvento(evento);

                prenotazioneRepository.save(prenotazione);

                prenotazioneRepository.save(prenotazione);

                return prenotazione;
            }
            else {
                throw new Exception("Utente con id = " + prenotazione.getUtente().getId() + " oppure evento con id = " + prenotazione.getEvento().getId() + " non trovato");
            }
        }
        else {
            throw new PrenotazioneNotFoundException("Prenotazione con id = " + id + " non trovata");
        }
    }

    public String deletePrenotazione(int id){
        Optional<Prenotazione> prenotazioneOp = getPrenotazioneById(id);

        if (prenotazioneOp.isPresent()){
            prenotazioneRepository.delete(prenotazioneOp.get());
            return "Prenotazione con id = " + id + " eliminata correttamente";
        }
        else {
            throw new PrenotazioneNotFoundException("Prenotazione con id = " + id + " non trovata");
        }
    }
}
