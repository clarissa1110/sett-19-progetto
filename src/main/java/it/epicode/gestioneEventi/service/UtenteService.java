package it.epicode.gestioneEventi.service;

import it.epicode.gestioneEventi.dto.UtenteDto;
import it.epicode.gestioneEventi.enums.Ruolo;
import it.epicode.gestioneEventi.exception.BadRequestException;
import it.epicode.gestioneEventi.exception.UtenteNotFoundException;
import it.epicode.gestioneEventi.model.Utente;
import it.epicode.gestioneEventi.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<Utente> getUtenteByEmail(String email){
        return utenteRepository.findByEmail(email);
    }

    public String saveUtente(UtenteDto utenteDto){
        if (getUtenteByEmail(utenteDto.getEmail()).isEmpty()){
            Utente utente = new Utente();
            utente.setNomeCognome(utenteDto.getNomeCognome());
            utente.setEmail(utenteDto.getEmail());
            utente.setPassword(utenteDto.getPassword());
            utente.setRuolo(Ruolo.UTENTE);

            utenteRepository.save(utente);
            return "Utente con id = " + utente.getId() + " salvato correttamente";
        }
        else {
            throw new BadRequestException("L'email inserita è già presente");
        }
    }

    public String saveOrganizzatore(UtenteDto utenteDto){
        if (getUtenteByEmail(utenteDto.getEmail()).isEmpty()){
            Utente utente = new Utente();
            utente.setNomeCognome(utenteDto.getNomeCognome());
            utente.setEmail(utenteDto.getEmail());
            utente.setPassword(utenteDto.getPassword());
            utente.setRuolo(Ruolo.ORGANIZZATORE);

            utenteRepository.save(utente);
            return "Utente con id = " + utente.getId() + " salvato correttamente";
        }
        else {
            throw new BadRequestException("L'email inserita è già presente");
        }
    }

    public List<Utente> getUtenti(){
        return utenteRepository.findAll();
    }

    public Optional<Utente> getUtenteById(int id){
        return utenteRepository.findById(id);
    }

    public Utente updateUtente(int id, UtenteDto utenteDto){
        Optional<Utente> utenteOp = getUtenteById(id);

        if (utenteOp.isPresent()){
            Utente utente = utenteOp.get();
            utente.setNomeCognome(utenteDto.getNomeCognome());
            utente.setEmail(utenteDto.getEmail());
            utente.setPassword(passwordEncoder.encode(utenteDto.getPassword()));
            utente.setRuolo(utenteDto.getRuolo());

            utenteRepository.save(utente);

            return utente;
        }
        else {
            throw new UtenteNotFoundException("Utente con id = " + id + " non trovato");
        }
    }

    public String deleteUtente(int id){
        Optional<Utente> utenteOp = getUtenteById(id);

        if (utenteOp.isPresent()){
            utenteRepository.delete(utenteOp.get());
            return "Utente con id = " + id + " eliminato correttamente";
        }
        else {
            throw new UtenteNotFoundException("Utente con id = " + id + " non trovato");
        }
    }
}
