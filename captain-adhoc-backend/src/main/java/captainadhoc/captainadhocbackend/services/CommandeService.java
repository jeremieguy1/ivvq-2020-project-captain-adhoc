package captainadhoc.captainadhocbackend.services;

import captainadhoc.captainadhocbackend.domain.Commande;
import captainadhoc.captainadhocbackend.repositories.CommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CommandeService {

    @Autowired
    private CommandeRepository commandeRepository;

    public ArrayList<Commande> findAllCommandes() {
        ArrayList<Commande> commandes = new ArrayList<>();
        commandeRepository.findAll().forEach(comm -> {
            commandes.add(comm);
        });

        return commandes;
    }

    public void saveCommande(Commande commande) {
        commandeRepository.save(commande);
    }
}
