package captainadhoc.captainadhocbackend.services.implementations;

import captainadhoc.captainadhocbackend.domain.Commande;

import java.util.ArrayList;

public interface ICommandeService {

    ArrayList<Commande> findAllCommandes();

    void saveCommande(Commande commande);
}
