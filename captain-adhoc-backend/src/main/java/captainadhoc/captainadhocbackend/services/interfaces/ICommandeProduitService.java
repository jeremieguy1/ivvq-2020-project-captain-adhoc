package captainadhoc.captainadhocbackend.services.interfaces;

import captainadhoc.captainadhocbackend.dto.ProduitsAchatDto;
import captainadhoc.captainadhocbackend.domain.Commande;
import captainadhoc.captainadhocbackend.domain.CommandeProduit;
import captainadhoc.captainadhocbackend.exceptions.InsufficientQuantityException;

import java.util.List;

public interface ICommandeProduitService {

    void saveCommandeProduit(CommandeProduit commandeProduit);

    List<CommandeProduit> createCommandeProduit(
            List<ProduitsAchatDto> produitsAchats,
            Commande commande)
            throws InsufficientQuantityException;

    void saveAllCommandeProduit(List<CommandeProduit> commandeProduits);
}
