package captainadhoc.captainadhocbackend.controllers;

import captainadhoc.captainadhocbackend.dto.Achat;
import captainadhoc.captainadhocbackend.domain.Commande;
import captainadhoc.captainadhocbackend.services.interfaces.ICommandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/commandes")
public class CommandeController {

    @Autowired
    private ICommandeService commandeService;

    @GetMapping
    public ArrayList<Commande> getCommandes() {
        return commandeService.findAllCommandes();
    }

    @PostMapping("/achat")
    public void commander(@RequestBody Achat achat) {
        commandeService.newCommande(achat);
    }
}
