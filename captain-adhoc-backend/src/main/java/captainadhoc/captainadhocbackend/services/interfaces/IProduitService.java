package captainadhoc.captainadhocbackend.services.interfaces;

import captainadhoc.captainadhocbackend.domain.Produit;

import java.util.ArrayList;
import java.util.List;

public interface IProduitService {

    ArrayList<Produit> findAllProduits();

    void saveProduit(Produit produit);

    void deleteAllProduit();

    void modifierQuantite(Long idProduit, int quantite);

    Produit decrementQuantity(Long idProduit, int quantiteProduit);
}
