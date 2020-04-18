package captainadhoc.captainadhocbackend.services;

import captainadhoc.captainadhocbackend.domain.Produit;
import captainadhoc.captainadhocbackend.repositories.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;

@Service
public class ProduitService {

    @Autowired
    private ProduitRepository produitRepository;

    public ArrayList<Produit> findAllProduits() {
        ArrayList<Produit> produits = new ArrayList<>();
        produitRepository.findAll().forEach(prod -> {
            produits.add(prod);
        });

        return produits;
    }
}
