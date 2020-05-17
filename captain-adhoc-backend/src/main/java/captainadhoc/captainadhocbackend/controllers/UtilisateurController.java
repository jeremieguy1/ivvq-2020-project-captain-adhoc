package captainadhoc.captainadhocbackend.controllers;

import captainadhoc.captainadhocbackend.domain.Utilisateur;
import captainadhoc.captainadhocbackend.dto.UtilisateurDto;
import captainadhoc.captainadhocbackend.exceptions.UtilisateurExisteException;
import captainadhoc.captainadhocbackend.services.implementations.UtilisateurService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class UtilisateurController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    UtilisateurService utilisateurService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> registerUserAccount(@RequestBody UtilisateurDto utilisateurDto) {
        Utilisateur utilisateur = null;
        try {
            utilisateur = modelMapper.map(utilisateurDto, Utilisateur.class);
            utilisateurService.saveUtilisateur(utilisateur);
        } catch (UtilisateurExisteException exception) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        return ResponseEntity.ok("Utilisateur enregistré.");
    }
}
