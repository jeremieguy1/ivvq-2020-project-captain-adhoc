package captainadhoc.captainadhocbackend.services.implementations;

import captainadhoc.captainadhocbackend.dto.AchatDto;
import captainadhoc.captainadhocbackend.domain.Commande;
import captainadhoc.captainadhocbackend.domain.CommandeProduit;
import captainadhoc.captainadhocbackend.exceptions.InsufficientQuantityException;
import captainadhoc.captainadhocbackend.repositories.CommandeRepository;
import captainadhoc.captainadhocbackend.services.interfaces.ICommandeProduitService;
import captainadhoc.captainadhocbackend.services.interfaces.ICommandeService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommandeService implements ICommandeService {

    @Getter
    @Autowired
    private CommandeRepository commandeRepository;

    @Getter
    @Autowired
    private ICommandeProduitService commandeProduitService;

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

    @Override
    public void newCommande(AchatDto achat) throws InsufficientQuantityException {

        Date date = new Date();
        Commande commande = Commande.builder()
                .date_commande(date)
                .code(achat.getCode())
                .build();

        List<CommandeProduit> commandeProduits =
                commandeProduitService.createCommandeProduit(
                        achat.getProduitsAchat(),
                        commande);

        commande.setCommandeProduitsList(commandeProduits);

        saveCommande(commande);
        commandeProduitService.saveAllCommandeProduit(commandeProduits);
    }

}
