package captainadhoc.captainadhocbackend.services;

import captainadhoc.captainadhocbackend.domain.Member;
import captainadhoc.captainadhocbackend.dto.PurchaseDto;
import captainadhoc.captainadhocbackend.dto.ProductPurchaseDto;
import captainadhoc.captainadhocbackend.domain.Purchase;
import captainadhoc.captainadhocbackend.domain.PurchaseProduct;
import captainadhoc.captainadhocbackend.domain.Product;
import captainadhoc.captainadhocbackend.services.implementations.PurchaseService;
import captainadhoc.captainadhocbackend.services.interfaces.IPurchaseProductService;
import org.junit.jupiter.api.Test;
import captainadhoc.captainadhocbackend.repositories.PurchaseRepository;
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
class PurchaseServiceTest {

    @Mock
    private PurchaseRepository purchaseRepository;

    @Mock
    private IPurchaseProductService purchaseProductService;

    @InjectMocks
    private PurchaseService purchaseService;

    @Test
    public void findAllPurchasesTest() {
        // when: la méthode findAllPurchases est invoquée
        purchaseService.findAllPurchases();

        // then: la méthode findAll du Repository associé est invoquée
        verify(purchaseService.getPurchaseRepository()).findAll();
    }

    @Test
    public void savePurchaseTest() {
        //given une Purchase
        Purchase purchase = Purchase.builder()
                .purchaseDate(new Date())
                .code("code")
                .build();

        when(purchaseService.getPurchaseRepository().save(purchase)).thenReturn(purchase);

        // when: la méthode savePurchase est invoquée
        purchaseService.savePurchase(purchase);

        // then: la méthode save du PurchaseRepository associé est invoquée
        verify(purchaseService.getPurchaseRepository()).save(purchase);
    }

    @Test
    public void newPurchaseTest() {

        Member utilisateur = Member.builder()
                .lastName("Kevin")
                .firstName("Marchand")
                .userName("marchand1")
                .password("mdp")
                .isAdmin(true)
                .build();

        ProductPurchaseDto productPurchaseDto1 = new ProductPurchaseDto(1L, 2);
        ProductPurchaseDto productPurchaseDto2 = new ProductPurchaseDto(2L, 3);

        List<ProductPurchaseDto> productPurchaseDtoList = new ArrayList<>();
        productPurchaseDtoList.add(productPurchaseDto1);
        productPurchaseDtoList.add(productPurchaseDto2);

        PurchaseDto purchaseDto = new PurchaseDto("CODE", productPurchaseDtoList);

        Date date = new Date();
        Purchase purchase = Purchase.builder()
                .purchaseDate(date)
                .code(purchaseDto.getCode())
                .build();

        //given un product
        Product product1 = Product.builder()
                .idProduct(1L)
                .productQuantity(15)
                .productName("product1")
                .productDescription("description1")
                .productPicture("ps5_large.png")
                .productPrice(1)
                .build();

        Product product2 = Product.builder()
                .idProduct(2L)
                .productQuantity(16)
                .productName("product2")
                .productDescription("description2")
                .productPicture("cyberbox_large.png")
                .productPrice(2)
                .build();

        List<PurchaseProduct> purchaseProducts = new ArrayList<>();

        PurchaseProduct purchaseProduct1 = new PurchaseProduct();
        purchaseProduct1.setPurchase(purchase);
        purchaseProduct1.setProduct(product1);

        PurchaseProduct purchaseProduct2 = new PurchaseProduct();
        purchaseProduct2.setPurchase(purchase);
        purchaseProduct2.setProduct(product2);

        purchaseProducts.add(purchaseProduct1);
        purchaseProducts.add(purchaseProduct2);

        when(purchaseService.getPurchaseProductService().createPurchaseProduct(Mockito.any(List.class), Mockito.any(Purchase.class))).thenReturn(purchaseProducts);

        purchaseService.newPurchase(purchaseDto, utilisateur);

        // then: la méthode createPurchaseProduct du PurchaseProductService associé est invoquée
        verify(purchaseService.getPurchaseProductService()).createPurchaseProduct(Mockito.eq(productPurchaseDtoList), Mockito.any(Purchase.class));

        // then: la méthode saveAllPurchaseProducts du PurchaseProductService associé est invoquée
        verify(purchaseService.getPurchaseProductService()).saveAllPurchaseProducts(purchaseProducts);


    }
}
