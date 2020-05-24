package captainadhoc.captainadhocbackend.controllers;

import captainadhoc.captainadhocbackend.domain.Utilisateur;
import captainadhoc.captainadhocbackend.dto.AchatDto;
import captainadhoc.captainadhocbackend.domain.Commande;
import captainadhoc.captainadhocbackend.exceptions.InsufficientQuantityException;
import captainadhoc.captainadhocbackend.services.interfaces.ICommandeService;
import captainadhoc.captainadhocbackend.services.interfaces.IUtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@RestController
@RequestMapping("/commandes")
public class CommandeController {

    @Autowired
    private ICommandeService commandeService;

    @Autowired
    private IUtilisateurService utilisateurService;

    @GetMapping
    public ArrayList<Commande> getCommandes() {
        return commandeService.findAllCommandes();
    }

    @PostMapping("/achat")
    public void commander(@RequestBody AchatDto achat) {

        try {

            Authentication auth =
                    SecurityContextHolder.getContext().getAuthentication();

            Utilisateur utilisateur =
                    utilisateurService.findByNomUtilisateur(auth.getName());

            commandeService.newCommande(achat, utilisateur);

        } catch(InsufficientQuantityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }
}
