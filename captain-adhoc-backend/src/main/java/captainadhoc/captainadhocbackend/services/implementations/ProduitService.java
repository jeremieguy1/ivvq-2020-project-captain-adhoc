package captainadhoc.captainadhocbackend.services.implementations;

import captainadhoc.captainadhocbackend.domain.Produit;
import captainadhoc.captainadhocbackend.repositories.ProduitRepository;
import captainadhoc.captainadhocbackend.services.interfaces.IProduitService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class ProduitService implements IProduitService {

    @Getter
    @Autowired
    private ProduitRepository produitRepository;

    @Override
    public ArrayList<Produit> findAllProduits() {
        ArrayList<Produit> produits = new ArrayList<>();
        Iterable<Produit> produitIterable = produitRepository.findAll();

        if (produitIterable != null) {
            produitIterable.forEach(prod -> {
                produits.add(prod);
            });
        }

        return produits;
    }

    @Override
    public void saveProduit(Produit produit){

        produitRepository.save(produit);

    }

    @Override
    public void deleteAllProduit() {
        produitRepository.deleteAll();
    }

    @Override
    public void modifierQuantite(Long idProduit, int quantite) {
        Optional<Produit> produit = produitRepository.findById(idProduit);
        if (produit.isPresent()){
            produit.get().setQuantite_produit(quantite);
            produitRepository.save(produit.get());
        }

    }
}
