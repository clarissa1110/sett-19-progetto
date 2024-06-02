package it.epicode.gestioneEventi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PrenotazioneDto {
    @NotBlank(message = "Il campo id utente non può essere vuoto")
    private int utenteId;

    @NotBlank(message = "Il campo id evento non può essere vuoto")
    private int eventoId;
}
