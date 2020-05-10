package captainadhoc.captainadhocbackend;

import captainadhoc.captainadhocbackend.domain.Commande;
import captainadhoc.captainadhocbackend.domain.CommandeProduit;
import captainadhoc.captainadhocbackend.domain.Marchand;
import captainadhoc.captainadhocbackend.domain.Produit;
import captainadhoc.captainadhocbackend.services.CommandeService;
import captainadhoc.captainadhocbackend.services.CommandeProduitService;
import captainadhoc.captainadhocbackend.services.MarchandService;
import captainadhoc.captainadhocbackend.services.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class DataLoader implements ApplicationRunner {

    @Autowired
    private ProduitService produitService;

    @Autowired
    private MarchandService marchandService;

    @Autowired
    private CommandeService commandeService;

    @Autowired
    private CommandeProduitService commandeProduitService;

    public void initProduit(){

        produitService.deleteAllProduit();
        Marchand marchand1 = new Marchand("marchand1");

        List<Produit> produitList = new ArrayList<>();
        Produit produit2 = new Produit(16, "produit2", "description2", "image2", 2);
        Produit produit1 = new Produit(15, "produit1", "description1", "image1", 1);
        produitList.add(produit1);
        produitList.add(produit2);

        marchand1.setProduitList(produitList);

        marchandService.saveMarchand(marchand1);
        produit1.setMarchand(marchand1);
        produit2.setMarchand(marchand1);

        produitService.saveProduit(produit1);
        produitService.saveProduit(produit2);

    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        initProduit();
        initCommandes();
    }

    public void initCommandes () {
        Commande commande1 = new Commande("20/20/2020","code");
        Commande commande2 = new Commande("20/20/2020","code");
        Commande commande3 = new Commande("20/20/2020","code");

        CommandeProduit commandeProduit = new CommandeProduit();
        CommandeProduit commandeProduit2 = new CommandeProduit();
        CommandeProduit commandeProduit3 = new CommandeProduit();
        CommandeProduit commandeProduit4 = new CommandeProduit();

        ArrayList<Produit> produitArrayList = produitService.findAllProduits();

        commandeProduit.setProduit(produitArrayList.get(0));
        commandeProduit2.setProduit(produitArrayList.get(0));
        commandeProduit3.setProduit(produitArrayList.get(1));
        commandeProduit4.setProduit(produitArrayList.get(1));

        commandeProduit.setQuantite_commande_produit(1);
        commandeProduit.setQuantite_commande_produit(2);
        commandeProduit.setQuantite_commande_produit(3);
        commandeProduit.setQuantite_commande_produit(4);

        commandeProduit.setCommande(commande1);
        commandeProduit2.setCommande(commande2);
        commandeProduit3.setCommande(commande2);
        commandeProduit4.setCommande(commande3);

        List<CommandeProduit> commandeProduitList = new ArrayList<>();
        commandeProduitList.add(commandeProduit);

        List<CommandeProduit> commandeProduitList2 = new ArrayList<>();
        commandeProduitList2.add(commandeProduit2);
        commandeProduitList2.add(commandeProduit3);

        List<CommandeProduit> commandeProduitList3 = new ArrayList<>();
        commandeProduitList3.add(commandeProduit4);

        commande1.setCommandeProduitsList(commandeProduitList);
        commande2.setCommandeProduitsList(commandeProduitList2);
        commande3.setCommandeProduitsList(commandeProduitList3);

        commandeService.saveCommande(commande1);
        commandeService.saveCommande(commande2);
        commandeService.saveCommande(commande3);

        commandeProduit.setCommande(commande1);
        commandeProduit2.setCommande(commande2);
        commandeProduit3.setCommande(commande2);
        commandeProduit4.setCommande(commande3);

        commandeProduitService.saveCommandeProduit(commandeProduit);
        commandeProduitService.saveCommandeProduit(commandeProduit2);
        commandeProduitService.saveCommandeProduit(commandeProduit3);
        commandeProduitService.saveCommandeProduit(commandeProduit4);
    }
}
