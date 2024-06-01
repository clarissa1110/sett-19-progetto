package it.epicode.gestioneEventi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Evento {

    @Id
    @GeneratedValue
    private int id;

    private String titolo;
    private String descrizione;

    @Column(name = "data_evento")
    private LocalDate dataEvento;

    private String luogo;

    @Column(name = "posti_disponibili")
    private int postiDisponibili;

    @OneToMany
    private List<Utente> partecipanti;

    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente organizzatore;


}
