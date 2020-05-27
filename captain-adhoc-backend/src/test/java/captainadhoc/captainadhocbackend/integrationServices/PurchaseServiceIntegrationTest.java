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
    private IMemberService memberService;

    @Autowired
    private IPurchaseProductService purchaseProductService;

    @BeforeEach
    public void setup() {

        purchase = Purchase.builder()
                .purchaseDate(new Date())
                .code("code")
                .build();

        dataLoader = new DataLoader(productService, memberService, purchaseService, purchaseProductService);

        dataLoader.run();
    }

    @Test
    void findAllPurchasesTest() {
        // given: un DataLoader initialisant la base des purchases

        // when: la liste des purchases est récupérée
        ArrayList<Purchase> purchaseList = purchaseService.findAllPurchases();

        // then: on a récupérer l'ensemble des purchases
        assertEquals(3, purchaseList.size());
    }

    @Test
    void savePurchaseTest() {

        // purchase n'a pas d'ID
        assertNull(purchase.getIdPurchase());

        // when: purchase est persistée
        purchaseService.savePurchase(purchase);

        // then: purchase a id
        assertNotNull(purchase.getIdPurchase());

        // then: on a récupérer l'ensemble des purchases
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
                memberService.findByUserName("Kevin"));

        ArrayList<Purchase> purchaseList = purchaseService.findAllPurchases();

        //then : une nouvelle entité Purchase a été ajoutée en base
        assertEquals(4, purchaseList.size());

        Purchase purchase = purchaseList.get(3);
        //then : le bon code est associé à la commande
        assertEquals("CODETEST", purchase.getCode());

        //then : le bon nombre de PurchaseProduct est associé à la commande
        assertEquals(2, purchase.getPurchaseProductList().size());

        PurchaseProduct purchaseProduct1 = purchase.getPurchaseProductList().get(0);
        PurchaseProduct purchaseProduct2 = purchase.getPurchaseProductList().get(1);

        //then : la bon produit avec la bonne quantité sont associés
        assertEquals(idPurchasedProduct1, purchaseProduct1.getProduct().getIdProduct());
        assertEquals(2, purchaseProduct1.getPurchaseProductQuantity());

        assertEquals(idPurchasedProduct2, purchaseProduct2.getProduct().getIdProduct());
        assertEquals(3, purchaseProduct2.getPurchaseProductQuantity());

    }

    @Test
    public void newPurchaseExceptionTest() {

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
        // then: une exception est levée
        assertThrows(InsufficientQuantityException.class, () ->
                purchaseService.newPurchase(
                        purchaseDto,
                        memberService.findByUserName("Kevin"))
        );
    }
}
