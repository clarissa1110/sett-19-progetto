package it.epicode.gestioneEventi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EventoDto {

    @NotBlank(message = "il campo titolo non può essere vuoto")
    private String titolo;

    @NotBlank(message = "il campo descrizione non può essere vuoto")
    private String descrizione;

    @NotBlank(message = "il campo data evento non può essere vuoto")
    private LocalDate dataEvento;

    @NotBlank(message = "il campo luogo non può essere vuoto")
    private String luogo;

    @NotBlank(message = "il campo posti disponibili non può essere vuoto")
    private int postiDisponibili;

    @NotBlank(message = "il campo id organizzatore non può essere vuoto")
    private int organizzatoreId;
}
