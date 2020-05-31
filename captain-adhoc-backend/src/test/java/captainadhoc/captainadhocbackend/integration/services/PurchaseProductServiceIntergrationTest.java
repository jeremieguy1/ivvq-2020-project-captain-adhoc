package captainadhoc.captainadhocbackend.integration.services;

import captainadhoc.captainadhocbackend.dto.ProductPurchaseDto;
import captainadhoc.captainadhocbackend.domain.Purchase;
import captainadhoc.captainadhocbackend.domain.PurchaseProduct;
import captainadhoc.captainadhocbackend.domain.Product;
import captainadhoc.captainadhocbackend.exceptions.InsufficientQuantityException;
import captainadhoc.captainadhocbackend.integration.DataLoader;
import captainadhoc.captainadhocbackend.repositories.PurchaseRepository;
import captainadhoc.captainadhocbackend.services.implementations.PurchaseProductService;
import captainadhoc.captainadhocbackend.services.interfaces.IPurchaseService;
import captainadhoc.captainadhocbackend.services.interfaces.IProductService;
import captainadhoc.captainadhocbackend.services.interfaces.IMemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
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
@ActiveProfiles("test")
public class PurchaseProductServiceIntergrationTest {

    @Autowired
    private PurchaseProductService purchaseProductService;

    @Autowired
    private PurchaseRepository purchaseRepository;

    private PurchaseProduct purchaseProduct;

    private Purchase purchase;

    private List<ProductPurchaseDto> productPurchaseList;

    private List<PurchaseProduct> purchaseProductList;

    private Long idProduct1;

    private Long idProduct2;

    @Autowired
    private IProductService productService;

    @Autowired
    private IMemberService memberService;

    @Autowired
    private IPurchaseService purchaseService;

    @BeforeEach
    public void setup() {

        // given: un DataLoader initialisant les données
        DataLoader dataLoader = new DataLoader(productService, memberService, purchaseService, purchaseProductService);
        dataLoader.run();

        // given : un Purchase
        purchase = Purchase.builder()
                .purchaseDate(new Date())
                .code("code")
                .build();

        Product product = Product.builder()
                .productQuantity(15)
                .productName("produit1")
                .productDescription("description1")
                .productPicture("https://urlz.fr/cHLz")
                .productPrice(300)
                .build();

        // given : un PurchaseProduct
        purchaseProduct = new PurchaseProduct();
        purchaseProduct.setProduct(product);
        purchaseProduct.setPurchaseProductQuantity(5);
        purchaseProduct.setPurchase(purchase);

        List<Product> productList = productService.findAllProducts();

        // given : deux identifiant de Product
        idProduct1 = productList.get(0).getIdProduct();
        idProduct2 = productList.get(1).getIdProduct();

        ProductPurchaseDto productPurchase1 = new ProductPurchaseDto(idProduct1, 2);
        ProductPurchaseDto productPurchase2 = new ProductPurchaseDto(idProduct2, 5);

        // given : une liste de ProductPurchaseDto
        productPurchaseList = new ArrayList<>();
        productPurchaseList.add(productPurchase1);
        productPurchaseList.add(productPurchase2);

    }

    private void initPurchaseProducts() {

        ArrayList<Product> productList = purchaseProductService.getProductService().findAllProducts();

        Product product1 = productList.get(0);

        Product product2 = productList.get(1);

        PurchaseProduct purchaseProduct1 = new PurchaseProduct();
        purchaseProduct1.setProduct(product1);
        purchaseProduct1.setPurchaseProductQuantity(1);

        PurchaseProduct purchaseProduct2 = new PurchaseProduct();
        purchaseProduct2.setProduct(product2);
        purchaseProduct2.setPurchaseProductQuantity(1);

        purchaseProductList = new ArrayList<>();
        purchaseProductList.add(purchaseProduct1);
        purchaseProductList.add(purchaseProduct2);

        purchase.setPurchaseProductList(purchaseProductList);
        purchase = purchaseRepository.save(purchase);

        purchaseProduct1.setPurchase(purchase);
        purchaseProduct2.setPurchase(purchase);
    }

    @Test
    public void savePurchaseProductTest(){

        // l'objet PurchaseProduct n'a pas d'ID
        assertNull(purchaseProduct.getIdPurchaseProduct());

        // when: purchaseProduct est persisté
        purchaseProductService.savePurchaseProduct(purchaseProduct);

        // then: purchaseProduct a un id
        assertNotNull(purchaseProduct.getIdPurchaseProduct());

        // then: le nouveau purchaseProduct a été ajouté en base
        assertEquals(5L, purchaseProductService.getPurchaseProductRepository().count());

    }

    @Test
    public void createPurchaseProductTest(){

        // when : la méthode createPurchaseProduct du service PurchaseProductService est invoquée
        List<PurchaseProduct> purchaseProductList = purchaseProductService.createPurchaseProduct(productPurchaseList, purchase);

        // then : On a bien deux objets PurchaseProduct qui ont été créé
        assertEquals(2, purchaseProductList.size());

        // then : Les bons produits sont présents dans la liste
        assertEquals(idProduct1, purchaseProductList.get(0).getProduct().getIdProduct());
        assertEquals(idProduct2, purchaseProductList.get(1).getProduct().getIdProduct());

        // then : les quantités des PurchaseProduct sont corrects
        assertEquals(2, purchaseProductList.get(0).getPurchaseProductQuantity());
        assertEquals(5, purchaseProductList.get(1).getPurchaseProductQuantity());

        // then : les bons Purchase sont présents dans la liste
        assertEquals(purchase, purchaseProductList.get(0).getPurchase());
        assertEquals(purchase, purchaseProductList.get(1).getPurchase());
    }

    @Test
    public void createPurchaseProductExceptionTest() {

        // given : une liste de ProductPurchaseDto
        ProductPurchaseDto productPurchase = new ProductPurchaseDto(idProduct1, 200);
        List<ProductPurchaseDto> productPurchaseList = new ArrayList<>();
        productPurchaseList.add(productPurchase);

        // given : un objet Purchase
        Purchase newPurchase = Purchase.builder()
                .purchaseDate(new Date())
                .code("code")
                .build();

        // when : la méthode createPurchaseProduct du service PurchaseProductService est invoquée
        // then : une erreur InsufficientQuantityException est retournée
        assertThrows(InsufficientQuantityException.class, () ->
                purchaseProductService.createPurchaseProduct(productPurchaseList, newPurchase)
        );
    }

    @Test
    public void saveAllPurchaseProductsTest(){

        // given : une liste de PurchaseProducts
        initPurchaseProducts();

        // les purchaseProducts n'ont pas d'ID
        assertNull(purchaseProductList.get(0).getIdPurchaseProduct());
        assertNull(purchaseProductList.get(1).getIdPurchaseProduct());

        // when: la méthode saveAllPurchaseProduct est invoquée
        purchaseProductService.saveAllPurchaseProducts(purchaseProductList);

        // then: les purchaseProducts ont un id
        assertNotNull(purchaseProductList.get(0).getIdPurchaseProduct());
        assertNotNull(purchaseProductList.get(1).getIdPurchaseProduct());

        // then: les nouveaux purchaseProducts ont été ajouté en base
        assertEquals(6L, purchaseProductService.getPurchaseProductRepository().count());

    }
}
