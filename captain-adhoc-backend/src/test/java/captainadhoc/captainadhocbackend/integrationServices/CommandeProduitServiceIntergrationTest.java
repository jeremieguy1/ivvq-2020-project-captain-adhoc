package captainadhoc.captainadhocbackend.integrationServices;

import captainadhoc.captainadhocbackend.beans.ProduitsAchat;
import captainadhoc.captainadhocbackend.domain.Commande;
import captainadhoc.captainadhocbackend.domain.CommandeProduit;
import captainadhoc.captainadhocbackend.domain.Produit;
import captainadhoc.captainadhocbackend.repositories.CommandeRepository;
import captainadhoc.captainadhocbackend.services.implementations.CommandeProduitService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
public class CommandeProduitServiceIntergrationTest {

    @Autowired
    private CommandeProduitService commandeProduitService;

    @Autowired
    private CommandeRepository commandeRepository;

    private static CommandeProduit commandeProduit;

    private static Commande commande;

    private static List<ProduitsAchat> produitsAchats;

    private static List<CommandeProduit> commandeProduits;

    @BeforeAll
    public static void setup() {

        Produit produit = new Produit(
                15,
                "produit1",
                "description1",
                "ps5.png",
                300);

        commande = new Commande(new Date(),"code");

        commandeProduit = new CommandeProduit();
        commandeProduit.setProduit(produit);
        commandeProduit.setQuantite_commande_produit(5);
        commandeProduit.setCommande(commande);


        ProduitsAchat produitsAchat1 = new ProduitsAchat(2L, 2);
        ProduitsAchat produitsAchat2 = new ProduitsAchat(3L, 5);
        produitsAchats = new ArrayList<>();
        produitsAchats.add(produitsAchat1);
        produitsAchats.add(produitsAchat2);

    }

    private void initCommandeProduits() {

        ArrayList<Produit> produits = commandeProduitService.getProduitService().findAllProduits();

        Produit produit1 = produits.get(0);

        Produit produit2 = produits.get(1);

        CommandeProduit commandeProduit1 = new CommandeProduit();
        commandeProduit1.setProduit(produit1);
        commandeProduit1.setQuantite_commande_produit(1);

        CommandeProduit commandeProduit2 = new CommandeProduit();
        commandeProduit2.setProduit(produit2);
        commandeProduit2.setQuantite_commande_produit(1);

        commandeProduits = new ArrayList<>();
        commandeProduits.add(commandeProduit1);
        commandeProduits.add(commandeProduit2);

        commande.setCommandeProduitsList(commandeProduits);
        commande = commandeRepository.save(commande);

        commandeProduit1.setCommande(commande);
        commandeProduit2.setCommande(commande);
    }

    @Test
    public void saveCommandeProduitTest(){

        // l'objet CommandeProduit n'a pas d'ID
        assertNull(commandeProduit.getId_commandeProduit());

        // when: commandeProduit est persistée
        commandeProduitService.saveCommandeProduit(commandeProduit);

        // then: commandeProduit a un id
        assertNotNull(commandeProduit.getId_commandeProduit());

        // then: le nouveau commandeProduit a été ajouté en base
        assertEquals(5L, commandeProduitService.getCommandeProduitRepository().count());

    }

    @Test
    public void createCommandeProduitTest(){

        List<CommandeProduit> commandeProduits = commandeProduitService.createCommandeProduit(produitsAchats, commande);

        assertEquals(2, commandeProduits.size());

        assertEquals(2L, commandeProduits.get(0).getProduit().getId_produit());
        assertEquals(3L, commandeProduits.get(1).getProduit().getId_produit());

        assertEquals(2, commandeProduits.get(0).getQuantite_commande_produit());
        assertEquals(5, commandeProduits.get(1).getQuantite_commande_produit());

        assertEquals(commande, commandeProduits.get(0).getCommande());
        assertEquals(commande, commandeProduits.get(1).getCommande());
    }

    @Test
    public void saveAllCommandeProduitTest(){

        initCommandeProduits();

        // les commandeProduit n'ont pas d'ID
        assertNull(commandeProduits.get(0).getId_commandeProduit());
        assertNull(commandeProduits.get(1).getId_commandeProduit());

        // when: la méthode saveAllCommandeProduit est invoquée
        commandeProduitService.saveAllCommandeProduit(commandeProduits);

        // then: les commandeProduits ont un id
        assertNotNull(commandeProduits.get(0).getId_commandeProduit());
        assertNotNull(commandeProduits.get(1).getId_commandeProduit());

        // then: les nouveaux commandeProduits ont été ajouté en base
        assertEquals(6L, commandeProduitService.getCommandeProduitRepository().count());

    }
}
