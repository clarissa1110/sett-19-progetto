package it.epicode.gestioneEventi.repository;

import it.epicode.gestioneEventi.model.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Integer> {
    public List<Prenotazione> findByUtenteId(int id);
}
