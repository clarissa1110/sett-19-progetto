package it.epicode.gestioneEventi.repository;

import it.epicode.gestioneEventi.model.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Integer> {
}
