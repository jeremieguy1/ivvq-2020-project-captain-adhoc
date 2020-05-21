package captainadhoc.captainadhocbackend;

import captainadhoc.captainadhocbackend.domain.Commande;
import captainadhoc.captainadhocbackend.domain.CommandeProduit;
import captainadhoc.captainadhocbackend.domain.Produit;
import captainadhoc.captainadhocbackend.domain.Utilisateur;
import captainadhoc.captainadhocbackend.services.interfaces.ICommandeProduitService;
import captainadhoc.captainadhocbackend.services.interfaces.ICommandeService;
import captainadhoc.captainadhocbackend.services.interfaces.IProduitService;
import captainadhoc.captainadhocbackend.services.interfaces.IUtilisateurService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.util.Collections.emptyList;

@Component
@Transactional
@AllArgsConstructor
@Profile({"dev", "test"})
public class DataLoader implements ApplicationRunner {

    @Autowired
    private IProduitService produitService;

    @Autowired
    private IUtilisateurService utilisateurService;

    @Autowired
    private ICommandeService commandeService;

    @Autowired
    private ICommandeProduitService commandeProduitService;

    public void initProduit(){

        produitService.deleteAllProduit();
        Utilisateur admin = new Utilisateur((long) 1, "Kevin", "Marchand", "marchand1", "mdp", true, emptyList());

        Produit produit2 = new Produit(
                16,
                "CyberboX",
                "Non comptant d'avoir les meilleures voitures au MONDE, Tesla propose la meilleure console de jeu grand public !",
                "https://cdn.dribbble.com/users/332589/screenshots/9955348/image.png", 100000);
        Produit produit1 = new Produit(
                15,
                "PS5",
                "Encore une playstation de folie \\o/", "https://static.mensup.fr/photo_article/209383/105703/1200-L-ps5-actualits.jpg", 1);
        Produit produit3 = new Produit(
                2
                , "Mad box",
                "Cette console va révolutionner le du la de esport !",
                "https://www.unsimpleclic.com/wp-content/uploads/2019/01/190124-une-nouvelle-console-mad-box-pourrait-debarquer-sur-le-marche-01.jpg", 666);
        Produit produit4 = new Produit(
                100,
                "New retro +",
                "Elle fera tourner les jeux dernières générations tels que tetris et même Donkey kong 64 ! Et tout àa pour seulement 1399,99€",
                "https://i.pinimg.com/originals/d4/51/bd/d451bd6be0a4bdb720b8e3386c15a855.jpg", 10);
        Produit produit5 = new Produit(
                5,
                "Xbox Serie X",
                "C'est partiiiii pour la console pc !",
                "https://compass-ssl.xbox.com/assets/85/8b/858b94d4-0ca6-4e74-ac9f-38565c49f2df.jpg?n=Xbox-Series-X_Image-0_1083x1400_02.jpg", 200);

        utilisateurService.saveUtilisateur(admin);

        produitService.saveProduit(produit1);
        produitService.saveProduit(produit2);
        produitService.saveProduit(produit3);
        produitService.saveProduit(produit4);
        produitService.saveProduit(produit5);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        initProduit();
        initCommandes();
    }

    public void initCommandes () {
        Commande commande1 = new Commande(new Date(),"code");
        Commande commande2 = new Commande(new Date(),"code");
        Commande commande3 = new Commande(new Date(),"");

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
        commandeProduit2.setQuantite_commande_produit(2);
        commandeProduit3.setQuantite_commande_produit(3);
        commandeProduit4.setQuantite_commande_produit(4);

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
