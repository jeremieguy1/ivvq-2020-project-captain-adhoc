package captainadhoc.captainadhocbackend.integrationServices;

import captainadhoc.captainadhocbackend.domain.Product;
import captainadhoc.captainadhocbackend.dto.PurchaseDto;
import captainadhoc.captainadhocbackend.dto.ProductPurchaseDto;
import captainadhoc.captainadhocbackend.domain.Purchase;
import captainadhoc.captainadhocbackend.domain.PurchaseProduct;
import captainadhoc.captainadhocbackend.exceptions.InsufficientQuantityException;
import captainadhoc.captainadhocbackend.services.interfaces.IPurchaseProductService;
import captainadhoc.captainadhocbackend.services.interfaces.IPurchaseService;
import captainadhoc.captainadhocbackend.services.interfaces.IProductService;
import captainadhoc.captainadhocbackend.services.interfaces.IMemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@SpringBootTest
public class PurchaseServiceIntegrationTest {

    @Autowired
    private IPurchaseService purchaseService;

    private Purchase purchase;

    private DataLoader dataLoader;

    @Autowired
    private IProductService productService;

    @Autowired
    private IMemberService userService;

    @Autowired
    private IPurchaseProductService purchaseProductService;

    @BeforeEach
    public void setup() {

        purchase = Purchase.builder()
                .purchaseDate(new Date())
                .code("code")
                .build();

        dataLoader = new DataLoader(productService, userService, purchaseService, purchaseProductService);
        dataLoader.run();
    }

    @Test
    void findAllPurchasesTest() {
        // given: un DataLoader initialisant la base des commandes

        // when: la liste des commandes est récupérée
        ArrayList<Purchase> commandes = purchaseService.findAllPurchases();

        // then: on a récupérer l'ensemble des commandes
        assertEquals(3, commandes.size());
    }

    @Test
    void savePurchaseTest() {

        // la commande n'a pas d'ID
        assertNull(purchase.getIdPurchase());

        // when: util est persistée
        purchaseService.savePurchase(purchase);

        // then: util a id
        assertNotNull(purchase.getIdPurchase());

        // then: on a récupérer l'ensemble des produits
        assertEquals(4, purchaseService.findAllPurchases().size());
    }

    @Test
    public void newPurchaseTest() {

        List<Product> productList = productService.findAllProducts();
        Long idPurchasedProduct1 = productList.get(0).getIdProduct();
        Long idPurchasedProduct2 = productList.get(1).getIdProduct();

        ProductPurchaseDto productPurchase1 = new ProductPurchaseDto(idPurchasedProduct1, 2);
        ProductPurchaseDto productPurchase2 = new ProductPurchaseDto(idPurchasedProduct2, 3);

        List<ProductPurchaseDto> productPurchaseList = new ArrayList<>();
        productPurchaseList.add(productPurchase1);
        productPurchaseList.add(productPurchase2);

        PurchaseDto purchaseDto = new PurchaseDto("CODETEST", productPurchaseList);

        purchaseService.newPurchase(
                purchaseDto,
                userService.findByUserName("Kevin"));

        ArrayList<Purchase> purchaseList = purchaseService.findAllPurchases();

        //then : une nouvelle commande a été ajoutée en base
        assertEquals(4, purchaseList.size());

        Purchase commande = purchaseList.get(3);
        //then : le bon code est associé à la commande
        assertEquals("CODETEST", commande.getCode());

        //then : le bon nombre de ProduitCommande est associé à la commande
        assertEquals(2, commande.getPurchaseProductList().size());

        PurchaseProduct purchaseProduct1 = commande.getPurchaseProductList().get(0);
        PurchaseProduct purchaseProduct2 = commande.getPurchaseProductList().get(1);

        //then : la bon produit avec la bonne quantite sont associés
        assertEquals(idPurchasedProduct1, purchaseProduct1.getProduct().getIdProduct());
        assertEquals(2, purchaseProduct1.getPurchaseProductQuantity());

        assertEquals(idPurchasedProduct2, purchaseProduct2.getProduct().getIdProduct());
        assertEquals(3, purchaseProduct2.getPurchaseProductQuantity());

    }

    @Test
    public void newPurchaseTestException() {

        List<Product> productList = productService.findAllProducts();
        Long idPurchasedProduct1 = productList.get(0).getIdProduct();
        Long idPurchasedProduct2 = productList.get(1).getIdProduct();

        ProductPurchaseDto productPurchase1 = new ProductPurchaseDto(idPurchasedProduct1, 200);
        ProductPurchaseDto productPurchase2 = new ProductPurchaseDto(idPurchasedProduct2, 300);

        List<ProductPurchaseDto> productPurchaseList = new ArrayList<>();
        productPurchaseList.add(productPurchase1);
        productPurchaseList.add(productPurchase2);

        PurchaseDto purchaseDto = new PurchaseDto("CODETEST", productPurchaseList);


        // when: la méthode newPurchase est invoquée
        // then: une exception est levé
        assertThrows(InsufficientQuantityException.class, () ->
                purchaseService.newPurchase(
                        purchaseDto,
                        userService.findByUserName("Kevin"))
        );
    }
}
