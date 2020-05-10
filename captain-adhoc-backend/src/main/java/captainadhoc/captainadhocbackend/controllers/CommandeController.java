package captainadhoc.captainadhocbackend.controllers;

import captainadhoc.captainadhocbackend.domain.Commande;
import captainadhoc.captainadhocbackend.services.CommandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/commandes")
public class CommandeController {

    @Autowired
    private CommandeService commandeService;

    @GetMapping
    public ArrayList<Commande> getCommandes(){
        return commandeService.findAllCommandes();
    }

}
