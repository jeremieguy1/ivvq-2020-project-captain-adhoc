package captainadhoc.captainadhocbackend.services.implementations;

import captainadhoc.captainadhocbackend.dto.ProduitsAchatDto;
import captainadhoc.captainadhocbackend.domain.Commande;
import captainadhoc.captainadhocbackend.domain.CommandeProduit;
import captainadhoc.captainadhocbackend.domain.Produit;
import captainadhoc.captainadhocbackend.repositories.CommandeProduitRepository;
import captainadhoc.captainadhocbackend.services.interfaces.ICommandeProduitService;
import captainadhoc.captainadhocbackend.services.interfaces.IProduitService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommandeProduitService implements ICommandeProduitService {

    @Getter
    @Autowired
    private CommandeProduitRepository commandeProduitRepository;

    @Getter
    @Autowired
    private IProduitService produitService;

    @Override
    public void saveCommandeProduit(CommandeProduit commandeProduit) {
        commandeProduitRepository.save(commandeProduit);
    }

    @Override
    public List<CommandeProduit> createCommandeProduit(
            List<ProduitsAchatDto> produitsAchats,
            Commande commande) {

        return produitsAchats.stream().map(produitsAchat -> {

            Produit produit = produitService.decrementQuantity(
                    produitsAchat.getId_produit(),
                    produitsAchat.getQuantite());

            CommandeProduit commandeProduit = CommandeProduit.builder()
                    .produit(produit)
                    .quantite_commande_produit(produitsAchat.getQuantite())
                    .commande(commande)
                    .build();

            return commandeProduit;

        }).collect(Collectors.toList());

    }

    @Override
    public void saveAllCommandeProduit(
            List<CommandeProduit> commandeProduits) {

        commandeProduits.forEach(commandeProduit ->
            saveCommandeProduit(commandeProduit)
        );
    }
}
