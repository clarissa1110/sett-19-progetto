package it.epicode.gestioneEventi.controller;


import it.epicode.gestioneEventi.dto.UtenteDto;
import it.epicode.gestioneEventi.exception.BadRequestException;
import it.epicode.gestioneEventi.exception.UtenteNotFoundException;
import it.epicode.gestioneEventi.model.Utente;
import it.epicode.gestioneEventi.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UtenteController {
    @Autowired
    private UtenteService utenteService;

    @GetMapping("/api/users")
    @PreAuthorize("hasAnyAuthority('ORGANIZZATORE', 'UTENTE')")
    public List<Utente> getUtenti(){
        return utenteService.getUtenti();
    }

    @GetMapping("/api/users/{id}")
    @PreAuthorize("hasAnyAuthority('ORGANIZZATORE', 'UTENTE')")
    public Utente getUtenteByUsername(@PathVariable int id){
        Optional<Utente> utenteOptional = utenteService.getUtenteById(id);

        if (utenteOptional.isPresent()){
            return utenteOptional.get();
        }
        else{
            throw new UtenteNotFoundException("Utente con id =" + id + " non trovato");
        }
    }


    @PutMapping("/api/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    public Utente updateUtente(@PathVariable int id, @RequestBody @Validated UtenteDto utenteDto, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            throw new BadRequestException((bindingResult.getAllErrors().stream().
                    map(objectError -> objectError.getDefaultMessage()).reduce("", (s, s2) -> s+s2)));
        }
        return utenteService.updateUtente(id, utenteDto);

    }


    @DeleteMapping("/api/users/{id}")
    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    public String deleteUtente(@PathVariable int id){

        if (utenteService.getUtenteById(id).isPresent()){
            return utenteService.deleteUtente(id);
        }
        else{
            throw new UtenteNotFoundException("Utente con id =" + id + " non trovato");
        }
    }
}
