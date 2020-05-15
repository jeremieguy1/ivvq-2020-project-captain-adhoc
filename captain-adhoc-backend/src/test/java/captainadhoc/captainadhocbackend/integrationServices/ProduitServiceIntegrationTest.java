package captainadhoc.captainadhocbackend.integrationServices;

import captainadhoc.captainadhocbackend.domain.Produit;
import captainadhoc.captainadhocbackend.services.implementations.IProduitService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ProduitServiceIntegrationTest {

    @Autowired
    private IProduitService produitService;

    private Produit produit;

    @BeforeEach
    public void setup() {
        produit = new Produit(15, "produit1", "description1", "image1", 1);
    }

    @Test
    public void testSaveProduit() {

        // le produit n'a pas d'ID
        assertNull(produit.getId_produit());

        // when: produit est persistée
        produitService.saveProduit(produit);

        // then: produit a un id
        assertNotNull(produit.getId_produit());

        // then: le produit a bien été ajouté en base
        assertEquals(3, produitService.findAllProduits().size());
    }

    @Test
    public void testFindAllProduits() {

        // given: un DataLoader initialisant la base des produits

        // when: la liste des produits est récupérée
        ArrayList<Produit> produits = produitService.findAllProduits();

        // then: on a récupérer l'ensemble des produits
        Assert.assertEquals(3, produits.size());
    }

    @Test
    public void testModifierQuantite() {
        //given un produit
        Produit produit = produitService.findAllProduits().get(0);

        assertEquals(15, produit.getQuantite_produit());

        // when: la méthode modifierQuantite est invoquée
        produitService.modifierQuantite(produit.getId_produit(),20);

        //then: la quantite du produit a été modifiée en base
        assertEquals(20, produitService.findAllProduits().get(0).getQuantite_produit());
    }
}
