package captainadhoc.captainadhocbackend.unit.services;

import captainadhoc.captainadhocbackend.dto.ProductPurchaseDto;
import captainadhoc.captainadhocbackend.domain.Purchase;
import captainadhoc.captainadhocbackend.domain.PurchaseProduct;
import captainadhoc.captainadhocbackend.domain.Product;
import captainadhoc.captainadhocbackend.exceptions.InsufficientQuantityException;
import captainadhoc.captainadhocbackend.services.implementations.PurchaseProductService;
import captainadhoc.captainadhocbackend.services.interfaces.IProductService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import captainadhoc.captainadhocbackend.repositories.PurchaseProductRepository;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;

@SpringBootTest
public class PurchaseProductServiceTest {

    @MockBean
    private PurchaseProductRepository purchaseProductRepository;

    @MockBean
    private IProductService productService;

    private PurchaseProductService purchaseProductService;

    private static List<PurchaseProduct> purchaseProductList;

    private static List<ProductPurchaseDto> productPurchaseList;

    private static Purchase purchase;

    @BeforeAll
    public static void setup() {

        Product product = Product.builder()
                .idProduct(1L)
                .productQuantity(15)
                .productName("product1")
                .productDescription("description1")
                .productPicture("ps5.png")
                .productPrice(300)
                .build();

        product.setIdProduct(3L);

        ProductPurchaseDto productPurchase1 = new ProductPurchaseDto(1L, 2);
        ProductPurchaseDto productPurchase2 = new ProductPurchaseDto(2L, 5);
        ProductPurchaseDto productPurchase3 = new ProductPurchaseDto(3L, 1);

        productPurchaseList = new ArrayList<>();
        productPurchaseList.add(productPurchase1);
        productPurchaseList.add(productPurchase2);
        productPurchaseList.add(productPurchase3);

        purchase = Purchase.builder()
                .purchaseDate(new Date())
                .code("code")
                .build();

        PurchaseProduct purchaseProduct1 = new PurchaseProduct();
        purchaseProduct1.setProduct(product);
        purchaseProduct1.setPurchaseProductQuantity(5);
        purchaseProduct1.setPurchase(purchase);

        PurchaseProduct purchaseProduct2 = new PurchaseProduct();
        PurchaseProduct purchaseProduct3 = new PurchaseProduct();

        purchaseProductList = new ArrayList<>();
        purchaseProductList.add(purchaseProduct1);
        purchaseProductList.add(purchaseProduct2);
        purchaseProductList.add(purchaseProduct3);
    }

    @BeforeEach
    public void setupEach() {
        purchaseProductService = new PurchaseProductService();
        purchaseProductService.setProductService(productService);
        purchaseProductService.setPurchaseProductRepository(purchaseProductRepository);
    }

    @Test
    public void savePurchaseProductTest() {

        // when: la méthode savePurchaseProduct est invoquée
        purchaseProductService.savePurchaseProduct(purchaseProductList.get(0));

        // then: la méthode save du PurchaseProductRepository associé est invoquée
        verify(purchaseProductRepository).save(purchaseProductList.get(0));
    }

    @Test
    public void createPurchaseProductTest() {

        // when: la méthode createPurchaseProduct est invoquée
        purchaseProductService.createPurchaseProduct(productPurchaseList, purchase);

        // then : la méthode decrementQuantity est appelé 3 fois
        // (1 fois pour chaque objet ProductPurchaseDto)
        verify(productService, times(3))
                .decrementQuantity(Mockito.any(Long.class), Mockito.any(int.class));
    }

    @Test
    public void createPurchaseProductExceptionTest() {

        // when : la méthode decrementQuantity du productService renvoie une exception
        doThrow(new InsufficientQuantityException(new Product()))
                .when(productService).decrementQuantity(Mockito.any(Long.class), Mockito.any(int.class));

        // when: la méthode createPurchaseProduct est invoquée
        // then : la méthode createPurchaseProduct renvoie une exception InsufficientQuantityException
        assertThrows(InsufficientQuantityException.class, () ->
                purchaseProductService.createPurchaseProduct(productPurchaseList, purchase)
        );
    }

    @Test
    public void saveAllPurchaseProductTest() {

        // when: la méthode saveAllPurchaseProduct est invoquée
        purchaseProductService.saveAllPurchaseProducts(purchaseProductList);

        // then: la méthode save du PurchaseProductRepository associé est invoquée 3 fois
        // (1 fois pour chaque objet PurchaseProduct)
        verify(purchaseProductRepository, times(3))
                .save(Mockito.any(PurchaseProduct.class));

    }
}
