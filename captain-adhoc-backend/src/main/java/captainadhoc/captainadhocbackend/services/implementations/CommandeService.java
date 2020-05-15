package captainadhoc.captainadhocbackend.services.implementations;

import captainadhoc.captainadhocbackend.domain.Commande;
import captainadhoc.captainadhocbackend.repositories.CommandeRepository;
import captainadhoc.captainadhocbackend.services.interfaces.ICommandeService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CommandeService implements ICommandeService {

    @Getter
    @Autowired
    private CommandeRepository commandeRepository;

    @Override
    public ArrayList<Commande> findAllCommandes() {
        ArrayList<Commande> commandes = new ArrayList<>();
        Iterable<Commande> commandeIterable = commandeRepository.findAll();

        if (commandeIterable != null) {
            commandeIterable.forEach(comm -> {
                commandes.add(comm);
            });
        }

        return commandes;
    }

    @Override
    public void saveCommande(Commande commande) {
        commandeRepository.save(commande);
    }
}
