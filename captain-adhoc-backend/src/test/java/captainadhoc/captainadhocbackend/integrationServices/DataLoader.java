package captainadhoc.captainadhocbackend.integrationServices;

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
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Component
@Transactional
@AllArgsConstructor
public class DataLoader {

    @Autowired
    private IProduitService produitService;

    @Autowired
    private IUtilisateurService utilisateurService;

    @Autowired
    private ICommandeService commandeService;

    @Autowired
    private ICommandeProduitService commandeProduitService;

    public void initProduit() {

        Utilisateur admin = Utilisateur.builder()
                .nom("Kevin")
                .prenom("Marchand")
                .nomUtilisateur("marchand1")
                .motDePasse("mdp")
                .isAdmin(true)
                .build();

        Produit produit1 = Produit.builder()
                .quantite_produit(15)
                .nom_produit("PS5")
                .description_produit("Encore une playstation de folie \\o/")
                .image_produit("https://urlz.fr/cHLz")
                .prix_produit(1)
                .build();

        Produit produit2 = Produit.builder()
                .quantite_produit(16)
                .nom_produit("CyberboX")
                .description_produit("Non comptant d'avoir " +
                        "les meilleures voitures au MONDE, " +
                        "Tesla propose la meilleure console " +
                        "de jeu grand public !")
                .image_produit("https://urlz.fr/cHLH")
                .prix_produit(100000)
                .build();

        Produit produit3 = Produit.builder()
                .quantite_produit(2)
                .nom_produit("Mad box")
                .description_produit("Cette console " +
                        "va révolutionner le du la de esport !")
                .image_produit("https://urlz.fr/cHJp")
                .prix_produit(666)
                .build();

        Produit produit4 = Produit.builder()
                .quantite_produit(100)
                .nom_produit("New retro +")
                .description_produit("Elle fera tourner " +
                        "les jeux dernières générations " +
                        "tels que tetris et même Donkey kong 64 ! " +
                        "Et tout àa pour seulement 1399,99€")
                .image_produit("https://urlz.fr/cHJz")
                .prix_produit(10)
                .build();

        Produit produit5 = Produit.builder()
                .quantite_produit(5)
                .nom_produit("Xbox Serie X")
                .description_produit("C'est partiiiii pour la console pc !")
                .image_produit("https://urlz.fr/cHLM")
                .prix_produit(200)
                .build();

        List<Produit> produitList = new ArrayList<>();

        produitList.add(produit1);
        produitList.add(produit2);
        produitList.add(produit3);
        produitList.add(produit4);
        produitList.add(produit5);

        utilisateurService.saveUtilisateur(admin);

        produitService.saveProduit(produit1);
        produitService.saveProduit(produit2);
        produitService.saveProduit(produit3);
        produitService.saveProduit(produit4);
        produitService.saveProduit(produit5);
    }

    public void run() {
        initProduit();
        initCommandes();
    }

    public void initCommandes() {
        Commande commande1 = Commande.builder()
                .date_commande(new Date())
                .code("code")
                .build();

        Commande commande2 = Commande.builder()
                .date_commande(new Date())
                .code("code")
                .build();

        Commande commande3 = Commande.builder()
                .date_commande(new Date())
                .code("")
                .build();

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
