package captainadhoc.captainadhocbackend.services;

import captainadhoc.captainadhocbackend.dto.ProduitsAchatDto;
import captainadhoc.captainadhocbackend.domain.Commande;
import captainadhoc.captainadhocbackend.domain.CommandeProduit;
import captainadhoc.captainadhocbackend.domain.Produit;
import captainadhoc.captainadhocbackend.services.implementations.CommandeProduitService;
import captainadhoc.captainadhocbackend.services.interfaces.IProduitService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import captainadhoc.captainadhocbackend.repositories.CommandeProduitRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@SpringBootTest
public class CommandeProduitServiceTest {

    @Mock
    private CommandeProduitRepository commandeProduitRepository;

    @Mock
    private IProduitService produitService;

    @InjectMocks
    private CommandeProduitService commandeProduitService;

    private static List<CommandeProduit> commandeProduits;

    private static List<ProduitsAchatDto> produitsAchats;

    private Commande commande;

    @BeforeAll
    public static void setup() {

        Produit produit = Produit.builder()
                .id_produit(1L)
                .quantite_produit(15)
                .nom_produit("produit1")
                .description_produit("description1")
                .image_produit("ps5.png")
                .prix_produit(300)
                .build();

        produit.setId_produit(3L);

        ProduitsAchatDto produitsAchat1 = new ProduitsAchatDto(1L, 2);
        ProduitsAchatDto produitsAchat2 = new ProduitsAchatDto(2L, 5);
        ProduitsAchatDto produitsAchat3 = new ProduitsAchatDto(3L, 1);

        produitsAchats = new ArrayList<>();
        produitsAchats.add(produitsAchat1);
        produitsAchats.add(produitsAchat2);
        produitsAchats.add(produitsAchat3);

        Commande commande = Commande.builder()
                .date_commande(new Date())
                .code("code")
                .build();

        CommandeProduit commandeProduit1 = new CommandeProduit();
        commandeProduit1.setProduit(produit);
        commandeProduit1.setQuantite_commande_produit(5);
        commandeProduit1.setCommande(commande);

        CommandeProduit commandeProduit2 = new CommandeProduit();
        CommandeProduit commandeProduit3 = new CommandeProduit();

        commandeProduits = new ArrayList<>();
        commandeProduits.add(commandeProduit1);
        commandeProduits.add(commandeProduit2);
        commandeProduits.add(commandeProduit3);
    }

    @Test
    public void saveCommandeProduitTest(){

        when(commandeProduitService.getCommandeProduitRepository().save(commandeProduits.get(0)))
                .thenReturn(commandeProduits.get(0));

        // when: la méthode saveCommandeProduit est invoquée
        commandeProduitService.saveCommandeProduit(commandeProduits.get(0));

        // then: la méthode save du CommandeProduitRepository associé est invoquée
        verify(commandeProduitService.getCommandeProduitRepository()).save(commandeProduits.get(0));
    }

    @Test
    public void createCommandeProduitTest(){

        commandeProduitService.createCommandeProduit(produitsAchats, commande);

        verify(commandeProduitService.getProduitService(), times(3))
                .decrementQuantity(Mockito.any(Long.class), Mockito.any(int.class));
    }

    @Test
    public void saveAllCommandeProduitTest(){

        // when: la méthode saveAllCommandeProduit est invoquée
        commandeProduitService.saveAllCommandeProduit(commandeProduits);

        // then: la méthode save du CommandeProduitRepository associé est invoquée 3 fois
        verify(commandeProduitService.getCommandeProduitRepository(), times(3))
                .save(Mockito.any(CommandeProduit.class));

    }
}
