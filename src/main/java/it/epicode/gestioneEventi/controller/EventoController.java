package it.epicode.gestioneEventi.controller;

import it.epicode.gestioneEventi.dto.EventoDto;
import it.epicode.gestioneEventi.exception.BadRequestException;
import it.epicode.gestioneEventi.exception.EventoNotFoundException;
import it.epicode.gestioneEventi.model.Evento;
import it.epicode.gestioneEventi.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class EventoController {
    @Autowired
    private EventoService eventoService;

    @PostMapping("/api/events")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    public String saveEvento(@RequestBody @Validated EventoDto eventoDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BadRequestException((bindingResult.getAllErrors().stream().
                    map(objectError -> objectError.getDefaultMessage()).reduce("", (s, s2) -> s+s2)));
        }
        return eventoService.saveEvento(eventoDto);
    }

    @GetMapping("api/events")
    @PreAuthorize("hasAnyAuthority('ORGANIZZATORE', 'UTENTE')")
    public List<Evento> getEventi(){
        return eventoService.getEventi();
    }

    @GetMapping("api/events/{id}")
    @PreAuthorize("hasAnyAuthority('ORGANIZZATORE', 'UTENTE')")
    public Evento getEventoById(@PathVariable int id){
        Optional<Evento> eventoOptional = eventoService.getEventoById(id);

        if (eventoOptional.isPresent()){
            return eventoOptional.get();
        }
        else {
            throw new EventoNotFoundException("Evento con id = " + id + " non trovato");
        }
    }

    @PutMapping("api/events/{id}")
    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    public Evento updateEvento(@PathVariable int id, @RequestBody @Validated EventoDto eventoDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BadRequestException((bindingResult.getAllErrors().stream().
                    map(objectError -> objectError.getDefaultMessage()).reduce("", (s, s2) -> s+s2)));
        }

        return eventoService.updateEvento(id, eventoDto);
    }

    @DeleteMapping("api/events/{id}")
    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    public String deleteEvento(@PathVariable int id){
        return eventoService.deleteEvento(id);
    }
}
