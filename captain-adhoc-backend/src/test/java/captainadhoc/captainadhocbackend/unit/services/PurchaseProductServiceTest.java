package captainadhoc.captainadhocbackend.unit.services;

import captainadhoc.captainadhocbackend.dto.ProductPurchaseDto;
import captainadhoc.captainadhocbackend.domain.Purchase;
import captainadhoc.captainadhocbackend.domain.PurchaseProduct;
import captainadhoc.captainadhocbackend.domain.Product;
import captainadhoc.captainadhocbackend.services.implementations.PurchaseProductService;
import captainadhoc.captainadhocbackend.services.interfaces.IProductService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import captainadhoc.captainadhocbackend.repositories.PurchaseProductRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@SpringBootTest
public class PurchaseProductServiceTest {

    @Mock
    private PurchaseProductRepository purchaseProductRepository;

    @Mock
    private IProductService productService;

    @InjectMocks
    private PurchaseProductService purchaseProductService;

    private static List<PurchaseProduct> purchaseProductList;

    private static List<ProductPurchaseDto> productPurchaseList;

    private Purchase purchase;

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

        Purchase purchase = Purchase.builder()
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

    @Test
    public void savePurchaseProductTest(){

        when(purchaseProductService.getPurchaseProductRepository().save(purchaseProductList.get(0)))
                .thenReturn(purchaseProductList.get(0));

        // when: la méthode savePurchaseProduct est invoquée
        purchaseProductService.savePurchaseProduct(purchaseProductList.get(0));

        // then: la méthode save du PurchaseProductRepository associé est invoquée
        verify(purchaseProductService.getPurchaseProductRepository()).save(purchaseProductList.get(0));
    }

    @Test
    public void createPurchaseProductTest(){

        purchaseProductService.createPurchaseProduct(productPurchaseList, purchase);

        verify(purchaseProductService.getProductService(), times(3))
                .decrementQuantity(Mockito.any(Long.class), Mockito.any(int.class));
    }

    @Test
    public void saveAllPurchaseProductTest(){

        // when: la méthode saveAllPurchaseProduct est invoquée
        purchaseProductService.saveAllPurchaseProducts(purchaseProductList);

        // then: la méthode save du PurchaseProductRepository associé est invoquée 3 fois
        verify(purchaseProductService.getPurchaseProductRepository(), times(3))
                .save(Mockito.any(PurchaseProduct.class));

    }
}
