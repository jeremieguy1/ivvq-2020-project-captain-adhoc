package captainadhoc.captainadhocbackend.services.interfaces;

import captainadhoc.captainadhocbackend.dto.Achat;
import captainadhoc.captainadhocbackend.domain.Commande;

import java.util.ArrayList;

public interface ICommandeService {

    ArrayList<Commande> findAllCommandes();

    void saveCommande(Commande commande);

    void newCommande(Achat achat);
}
