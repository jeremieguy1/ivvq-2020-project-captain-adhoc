package captainadhoc.captainadhocbackend.services;

import captainadhoc.captainadhocbackend.dto.AchatDto;
import captainadhoc.captainadhocbackend.dto.ProduitsAchatDto;
import captainadhoc.captainadhocbackend.domain.Commande;
import captainadhoc.captainadhocbackend.domain.CommandeProduit;
import captainadhoc.captainadhocbackend.domain.Produit;
import captainadhoc.captainadhocbackend.services.implementations.CommandeService;
import captainadhoc.captainadhocbackend.services.interfaces.ICommandeProduitService;
import org.junit.jupiter.api.Test;
import captainadhoc.captainadhocbackend.repositories.CommandeRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class CommandeServiceTest {

    @Mock
    private CommandeRepository commandeRepository;

    @Mock
    private ICommandeProduitService commandeProduitService;

    @InjectMocks
    private CommandeService commandeService;

    @Test
    public void findAllCommandesTest() {
        // when: la méthode findAllCommandes est invoquée
        commandeService.findAllCommandes();

        // then: la méthode findAll du Repository associé est invoquée
        verify(commandeService.getCommandeRepository()).findAll();
    }

    @Test
    public void saveCommandeTest() {
        //given une Commande
        Commande commande = Commande.builder()
                .date_commande(new Date())
                .code("code")
                .build();

        when(commandeService.getCommandeRepository().save(commande)).thenReturn(commande);

        // when: la méthode saveCommande est invoquée
        commandeService.saveCommande(commande);

        // then: la méthode save du CommandeRepository associé est invoquée
        verify(commandeService.getCommandeRepository()).save(commande);
    }

    @Test
    public void newCommandeTest() {

        ProduitsAchatDto produitsAchat1 = new ProduitsAchatDto(1L, 2);
        ProduitsAchatDto produitsAchat2 = new ProduitsAchatDto(2L, 3);

        List<ProduitsAchatDto> produitsAchats = new ArrayList<>();
        produitsAchats.add(produitsAchat1);
        produitsAchats.add(produitsAchat2);

        AchatDto achat = new AchatDto("CODE", produitsAchats);

        Date date = new Date();
        Commande commande = Commande.builder()
                .date_commande(date)
                .code(achat.getCode())
                .build();

        //given un produit
        Produit produit1 = Produit.builder()
                .id_produit(1L)
                .quantite_produit(15)
                .nom_produit("produit1")
                .description_produit("description1")
                .image_produit("ps5_large.png")
                .prix_produit(1)
                .build();

        Produit produit2 = Produit.builder()
                .id_produit(2L)
                .quantite_produit(16)
                .nom_produit("produit2")
                .description_produit("description2")
                .image_produit("cyberbox_large.png")
                .prix_produit(2)
                .build();

        List<CommandeProduit> commandeProduits = new ArrayList<>();

        CommandeProduit commandeProduit1 = new CommandeProduit();
        commandeProduit1.setCommande(commande);
        commandeProduit1.setProduit(produit1);

        CommandeProduit commandeProduit2 = new CommandeProduit();
        commandeProduit2.setCommande(commande);
        commandeProduit2.setProduit(produit2);

        commandeProduits.add(commandeProduit1);
        commandeProduits.add(commandeProduit2);

        when(commandeService.getCommandeProduitService().createCommandeProduit(Mockito.any(List.class), Mockito.any(Commande.class))).thenReturn(commandeProduits);

        commandeService.newCommande(achat);

        // then: la méthode saveAllCommandeProduit du CommandeProduitService associé est invoquée
        verify(commandeService.getCommandeProduitService()).createCommandeProduit(Mockito.eq(produitsAchats), Mockito.any(Commande.class));

        // then: la méthode saveAllCommandeProduit du CommandeProduitService associé est invoquée
        verify(commandeService.getCommandeProduitService()).saveAllCommandeProduit(commandeProduits);


    }
}
