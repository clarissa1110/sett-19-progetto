package it.epicode.gestioneEventi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

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

}
