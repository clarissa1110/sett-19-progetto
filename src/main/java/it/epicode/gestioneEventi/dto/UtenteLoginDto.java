package it.epicode.gestioneEventi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UtenteLoginDto {
    @Email(message = "Inserire un indirizzo email valido")
    @NotBlank(message = "Il campo email non può essere vuoto")
    private String email;

    @NotBlank(message = "Il campo password non può essere vuoto")
    private String password;
}
