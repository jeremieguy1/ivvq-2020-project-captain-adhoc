package captainadhoc.captainadhocbackend.controllers;

import captainadhoc.captainadhocbackend.beans.Achat;
import captainadhoc.captainadhocbackend.domain.Produit;
import captainadhoc.captainadhocbackend.services.implementations.IProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/produits")
public class ProduitController {

    @Autowired
    private IProduitService produitService;

    @GetMapping
    public ArrayList<Produit> getAllProduit() {
        return produitService.findAllProduits();
    }

    @PostMapping("/ajouter")
    public void ajouterProduit(@RequestBody Produit produit) {}

    @PutMapping("/modifier/quantite")
    public void modifierQuantite(@RequestParam(value="quantite") int quantite_produit,
                               @RequestParam(value="id_produit") Long id_produit) {
        produitService.modifierQuantite(id_produit, quantite_produit);
    }

    @PutMapping("/achat")
    public void commander(@RequestBody Achat achat) {}

}
