package captainadhoc.captainadhocbackend.services;

import captainadhoc.captainadhocbackend.domain.Produit;
import captainadhoc.captainadhocbackend.repositories.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
<<<<<<< HEAD
=======
import java.util.Comparator;
>>>>>>> 60241ed31d31489b7d145d5a66566dc0992e9cda

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
<<<<<<< HEAD

    public void saveProduit(Produit produit){

        produitRepository.save(produit);

    }

    public void deleteAllProduit() {
        produitRepository.deleteAll();
    }
=======
>>>>>>> 60241ed31d31489b7d145d5a66566dc0992e9cda
}
