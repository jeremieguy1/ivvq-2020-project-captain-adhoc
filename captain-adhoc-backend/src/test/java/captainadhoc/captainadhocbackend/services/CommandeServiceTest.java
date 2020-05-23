package captainadhoc.captainadhocbackend.services;

import captainadhoc.captainadhocbackend.dto.Achat;
import captainadhoc.captainadhocbackend.dto.ProduitsAchat;
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
        Commande commande = new Commande(new Date(),"code");
        when(commandeService.getCommandeRepository().save(commande)).thenReturn(commande);

        // when: la méthode saveCommande est invoquée
        commandeService.saveCommande(commande);

        // then: la méthode save du CommandeRepository associé est invoquée
        verify(commandeService.getCommandeRepository()).save(commande);
    }

    @Test
    public void newCommandeTest() {

        ProduitsAchat produitsAchat1 = new ProduitsAchat(1L, 2);
        ProduitsAchat produitsAchat2 = new ProduitsAchat(2L, 3);

        List<ProduitsAchat> produitsAchats = new ArrayList<>();
        produitsAchats.add(produitsAchat1);
        produitsAchats.add(produitsAchat2);

        Achat achat = new Achat("CODE", produitsAchats);

        Date date = new Date();
        Commande commande = new Commande(date, achat.getCode());

        Produit produit1 = new Produit(15, "produit1", "description1", "ps5_large.png", 1);
        produit1.setId_produit(1L);
        Produit produit2 = new Produit(16, "produit2", "description2", "cyberbox_large.png", 2);
        produit2.setId_produit(2L);

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
