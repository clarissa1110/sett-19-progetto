package it.epicode.gestioneEventi.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Prenotazione {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;

    @ManyToOne
    @JoinColumn(name = "evento_id")
    private Evento evento;
}
