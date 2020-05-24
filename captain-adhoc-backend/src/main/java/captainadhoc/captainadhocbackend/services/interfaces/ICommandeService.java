package captainadhoc.captainadhocbackend.services.interfaces;

import captainadhoc.captainadhocbackend.dto.AchatDto;
import captainadhoc.captainadhocbackend.domain.Commande;
import captainadhoc.captainadhocbackend.exceptions.InsufficientQuantityException;

import java.util.ArrayList;

public interface ICommandeService {

    ArrayList<Commande> findAllCommandes();

    void saveCommande(Commande commande);

    void newCommande(AchatDto achat) throws InsufficientQuantityException;
}
