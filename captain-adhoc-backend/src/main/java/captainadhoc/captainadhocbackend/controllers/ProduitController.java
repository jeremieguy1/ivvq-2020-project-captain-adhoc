package captainadhoc.captainadhocbackend.controllers;

import captainadhoc.captainadhocbackend.domain.Produit;
import captainadhoc.captainadhocbackend.services.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class ProduitController {

    @Autowired
    private ProduitService produitService;

    @GetMapping("/produits")
    public ArrayList<Produit> index() {
        return produitService.findAllProduits();
    }
}
