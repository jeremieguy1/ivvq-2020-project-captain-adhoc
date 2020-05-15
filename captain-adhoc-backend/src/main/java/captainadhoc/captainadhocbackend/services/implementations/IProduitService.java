package captainadhoc.captainadhocbackend.services.implementations;

import captainadhoc.captainadhocbackend.domain.Produit;

import java.util.ArrayList;

public interface IProduitService {

    ArrayList<Produit> findAllProduits();

    void saveProduit(Produit produit);

    void deleteAllProduit();

    void modifierQuantite(Long idProduit, int quantite);
}
