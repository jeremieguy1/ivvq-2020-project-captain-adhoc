package captainadhoc.captainadhocbackend.integrationServices;

import captainadhoc.captainadhocbackend.dto.Achat;
import captainadhoc.captainadhocbackend.dto.ProduitsAchat;
import captainadhoc.captainadhocbackend.domain.Commande;
import captainadhoc.captainadhocbackend.domain.CommandeProduit;
import captainadhoc.captainadhocbackend.services.interfaces.ICommandeService;
import org.junit.jupiter.api.BeforeEach;
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
public class CommandeServiceIntegrationTest {

    @Autowired
    private ICommandeService commandeService;

    private Commande commande;

    @BeforeEach
    public void setup() {
        commande = new Commande(new Date(),"code");
    }

    @Test
    void findAllCommandesTest() {
        // given: un DataLoader initialisant la base des commandes

        // when: la liste des commandes est récupérée
        ArrayList<Commande> commandes = commandeService.findAllCommandes();

        // then: on a récupérer l'ensemble des commandes
        assertEquals(3, commandes.size());
    }

    @Test
    void saveCommandeTest() {

        // la commande n'a pas d'ID
        assertNull(commande.getId_commande());

        // when: util est persistée
        commandeService.saveCommande(commande);

        // then: util a id
        assertNotNull(commande.getId_commande());

        // then: on a récupérer l'ensemble des produits
        assertEquals(4, commandeService.findAllCommandes().size());
    }

    @Test
    public void newCommandeTest() {
        ProduitsAchat produitsAchat1 = new ProduitsAchat(2L, 2);
        ProduitsAchat produitsAchat2 = new ProduitsAchat(3L, 3);

        List<ProduitsAchat> produitsAchats = new ArrayList<>();
        produitsAchats.add(produitsAchat1);
        produitsAchats.add(produitsAchat2);

        Achat achat = new Achat("CODETEST", produitsAchats);

        commandeService.newCommande(achat);

        ArrayList<Commande> commandes = commandeService.findAllCommandes();

        //then : une nouvelle commande a été ajoutée en base
        assertEquals(4, commandes.size());

        Commande commande = commandes.get(3);
        //then : le bon code est associé à la commande
        assertEquals("CODETEST", commande.getCode());

        //then : le bon nombre de ProduitCommande est associé à la commande
        assertEquals(2, commande.getCommandeProduitsList().size());

        CommandeProduit commandeProduit1 = commande.getCommandeProduitsList().get(0);
        CommandeProduit commandeProduit2 = commande.getCommandeProduitsList().get(1);

        //then : la bon produit avec la bonne quantite sont associés
        assertEquals(2L, commandeProduit1.getProduit().getId_produit());
        assertEquals(2, commandeProduit1.getQuantite_commande_produit());

        assertEquals(3L, commandeProduit2.getProduit().getId_produit());
        assertEquals(3, commandeProduit2.getQuantite_commande_produit());

    }
}
