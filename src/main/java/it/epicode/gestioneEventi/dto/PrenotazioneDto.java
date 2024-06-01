package it.epicode.gestioneEventi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PrenotazioneDto {

    @NotBlank(message = "il campo id utente non può essere vuoto")
    private int utenteId;

    @NotBlank(message = "il campo id evento non può essere vuoto")
    private int eventoId;
}
