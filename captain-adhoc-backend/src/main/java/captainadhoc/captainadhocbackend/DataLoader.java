package captainadhoc.captainadhocbackend;

import captainadhoc.captainadhocbackend.domain.Marchand;
import captainadhoc.captainadhocbackend.domain.Produit;
import captainadhoc.captainadhocbackend.services.MarchandService;
import captainadhoc.captainadhocbackend.services.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class DataLoader implements ApplicationRunner {

    @Autowired
    private ProduitService produitService;

    @Autowired
    private MarchandService marchandService;

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
    }
}
