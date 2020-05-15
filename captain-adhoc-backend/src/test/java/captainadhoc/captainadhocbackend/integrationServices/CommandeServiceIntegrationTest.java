package captainadhoc.captainadhocbackend.integrationServices;

import captainadhoc.captainadhocbackend.domain.Commande;
import captainadhoc.captainadhocbackend.services.implementations.ICommandeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CommandeServiceIntegrationTest {

    @Autowired
    private ICommandeService commandeService;

    private Commande commande;

    @BeforeEach
    public void setup() {
        commande = new Commande("20/20/2020","code");
    }

    @Test
    void findAllCommandesTest() {
        // given: un DataLoader initialisant la base des commandes

        // when: la liste des commandes est récupérée
        ArrayList<Commande> commandes = commandeService.findAllCommandes();

        // then: on a récupérer l'ensemble des commandes
        assertEquals(4, commandes.size());
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
}
