package it.epicode.gestioneEventi.controller;

import it.epicode.gestioneEventi.dto.EventoDto;
import it.epicode.gestioneEventi.dto.PrenotazioneDto;
import it.epicode.gestioneEventi.exception.BadRequestException;
import it.epicode.gestioneEventi.exception.EventoNotFoundException;
import it.epicode.gestioneEventi.exception.PrenotazioneNotFoundException;
import it.epicode.gestioneEventi.model.Evento;
import it.epicode.gestioneEventi.model.Prenotazione;
import it.epicode.gestioneEventi.service.PrenotazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PrenotazioneController {

    @Autowired
    private PrenotazioneService prenotazioneService;

    @PostMapping("api/bookings")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ORGANIZZATORE', 'UTENTE')")
    public String savePrenotazione(@RequestBody @Validated PrenotazioneDto prenotazioneDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BadRequestException((bindingResult.getAllErrors().stream().
                    map(objectError -> objectError.getDefaultMessage()).reduce("", (s, s2) -> s+s2)));
        }
        return prenotazioneService.savePrenotazione(prenotazioneDto);
    }

    @GetMapping("api/bookings")
    @PreAuthorize("hasAnyAuthority('ORGANIZZATORE', 'UTENTE')")
    public List<Prenotazione> getPrenotazioni(){
        return prenotazioneService.getPretonazioni();
    }

    @GetMapping("api/bookings/{id}")
    @PreAuthorize("hasAnyAuthority('ORGANIZZATORE', 'UTENTE')")
    public Prenotazione getPrenotazioneById(@PathVariable int id){
        Optional<Prenotazione> prenotazioneOptional = prenotazioneService.getPrenotazioneById(id);

        if (prenotazioneOptional.isPresent()){
            return prenotazioneOptional.get();
        }
        else {
            throw new PrenotazioneNotFoundException("Prenotazione con id = " + id + " non trovata");
        }
    }

    @PutMapping("api/bookings/{id}")
    @PreAuthorize("hasAnyAuthority('ORGANIZZATORE', 'UTENTE')")
    public Prenotazione updatePrenotazione(@PathVariable int id, @RequestBody @Validated PrenotazioneDto prenotazioneDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BadRequestException((bindingResult.getAllErrors().stream().
                    map(objectError -> objectError.getDefaultMessage()).reduce("", (s, s2) -> s+s2)));
        }
        return prenotazioneService.updatePrenotazione(id, prenotazioneDto);
    }

    @DeleteMapping("api/bookings/{id}")
    @PreAuthorize("hasAnyAuthority('ORGANIZZATORE', 'UTENTE')")
    public String deletePrenotazione(@PathVariable int id){
        return prenotazioneService.deletePrenotazione(id);
    }

    @GetMapping("api/bookings")
    @PreAuthorize("hasAnyAuthority('ORGANIZZATORE', 'UTENTE')")
    public List<Prenotazione> getPrenotazioniByIdUtente(@PathVariable int id){
        return prenotazioneService.getPrenotazioniByIdUtente(id);
    }
}
