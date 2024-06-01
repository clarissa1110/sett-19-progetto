package it.epicode.gestioneEventi.dto;

import it.epicode.gestioneEventi.enums.Ruolo;
import it.epicode.gestioneEventi.model.Evento;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UtenteDto {

    @NotBlank(message = "Il campo nome e cognome non può essere vuoto")
    private String nomeCognome;

    @Email(message = "Inserire un indirizzo email valido")
    @NotBlank(message = "Il campo email non può essere vuoto")
    private String email;

    @NotBlank(message = "Il campo password non può essere vuoto")
    private String password;

    @NotNull(message = "Il campo ruolo non può essere vuoto")
    private Ruolo ruolo;

    @NotBlank(message = "Il campo id evento non può essere vuoto")
    private int eventoId;
}
