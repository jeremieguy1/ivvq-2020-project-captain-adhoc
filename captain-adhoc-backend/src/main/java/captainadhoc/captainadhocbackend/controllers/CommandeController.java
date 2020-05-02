package captainadhoc.captainadhocbackend.controllers;

import captainadhoc.captainadhocbackend.domain.Commande;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/commandes")
public class CommandeController {

    @GetMapping
    public List<Commande> getCommandes(){
        return null;
    }

}
