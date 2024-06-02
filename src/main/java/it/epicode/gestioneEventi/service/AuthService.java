package it.epicode.gestioneEventi.service;

import it.epicode.gestioneEventi.dto.UtenteLoginDto;
import it.epicode.gestioneEventi.exception.UnauthorizedException;
import it.epicode.gestioneEventi.model.Utente;
import it.epicode.gestioneEventi.security.JwtTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private JwtTool jwtTool;

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String autenticaUtenteECreaToken(UtenteLoginDto utenteLoginDto){
        Optional<Utente> utenteOptional = utenteService.getUtenteByEmail(utenteLoginDto.getEmail());

        if (utenteOptional.isPresent()){
            Utente utente = utenteOptional.get();

            if (passwordEncoder.matches(utenteLoginDto.getPassword(), utente.getPassword())){
                return jwtTool.creaToken(utente);
            }
            else {
                throw new UnauthorizedException("Utente non presente, effettuare nuovamente il login");
            }
        }
        else {
            throw new UnauthorizedException("Utente non presente, effettuare nuovamente il login");
        }
    }
}
