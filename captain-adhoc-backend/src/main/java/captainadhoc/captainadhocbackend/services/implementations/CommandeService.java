package captainadhoc.captainadhocbackend.services.implementations;

import captainadhoc.captainadhocbackend.domain.Utilisateur;
import captainadhoc.captainadhocbackend.dto.AchatDto;
import captainadhoc.captainadhocbackend.domain.Commande;
import captainadhoc.captainadhocbackend.domain.CommandeProduit;
import captainadhoc.captainadhocbackend.exceptions.InsufficientQuantityException;
import captainadhoc.captainadhocbackend.repositories.CommandeRepository;
import captainadhoc.captainadhocbackend.services.interfaces.ICommandeProduitService;
import captainadhoc.captainadhocbackend.services.interfaces.ICommandeService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Getter
    @Autowired
    private UtilisateurService utilisateurService;

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
    public void newCommande(AchatDto achat, Utilisateur utilisateur) throws InsufficientQuantityException {

        Commande commande = Commande.builder()
                .date_commande(new Date())
                .code(achat.getCode())
                .utilisateur(utilisateur)
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
