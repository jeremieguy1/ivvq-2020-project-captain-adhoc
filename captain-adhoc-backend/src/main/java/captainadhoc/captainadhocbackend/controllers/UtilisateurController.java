package captainadhoc.captainadhocbackend.controllers;

import captainadhoc.captainadhocbackend.domain.Utilisateur;
import captainadhoc.captainadhocbackend.dto.EnregistrementUtilisateurDto;
import captainadhoc.captainadhocbackend.dto.UtilisateurDto;
import captainadhoc.captainadhocbackend.exceptions.UtilisateurExisteException;
import captainadhoc.captainadhocbackend.services.implementations.UtilisateurService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class UtilisateurController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    UtilisateurService utilisateurService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> registerUserAccount(@RequestBody EnregistrementUtilisateurDto enregistrementUtilisateurDto) {
        Utilisateur utilisateur = null;
        try {
            utilisateur = modelMapper.map(enregistrementUtilisateurDto, Utilisateur.class);
            utilisateurService.saveUtilisateur(utilisateur);
        } catch (UtilisateurExisteException exception) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        return ResponseEntity.ok("Utilisateur enregistré.");
    }

    @GetMapping("/current-user")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UtilisateurDto> getUtilisateur() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Utilisateur utilisateur = utilisateurService.findByNomUtilisateur(auth.getName());
        UtilisateurDto utilisateurDto = modelMapper.map(utilisateur, UtilisateurDto.class);
        return ResponseEntity.ok(utilisateurDto);
    }
}
