package it.epicode.gestioneEventi.controller;

import it.epicode.gestioneEventi.dto.UtenteDto;
import it.epicode.gestioneEventi.dto.UtenteLoginDto;
import it.epicode.gestioneEventi.exception.BadRequestException;
import it.epicode.gestioneEventi.service.AuthService;
import it.epicode.gestioneEventi.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private UtenteService utenteService;

    @Autowired
    private AuthService authService;

    @PostMapping("/auth/register")
    public String registrazione(@RequestBody @Validated UtenteDto utenteDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).
                    reduce("", (s, s2) -> s+s2));
        }
        return utenteService.saveUtente(utenteDto);
    }

    @PostMapping("/auth/login")
    public String login(@RequestBody @Validated UtenteLoginDto utenteLoginDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).
                    reduce("", (s, s2) -> s+s2));
        }
        return authService.autenticaUtenteECreaToken(utenteLoginDto);
    }
}
