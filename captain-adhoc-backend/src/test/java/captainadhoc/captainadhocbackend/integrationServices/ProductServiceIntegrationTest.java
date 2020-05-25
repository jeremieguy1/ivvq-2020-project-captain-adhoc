package captainadhoc.captainadhocbackend.integrationServices;

import captainadhoc.captainadhocbackend.domain.Product;
import captainadhoc.captainadhocbackend.exceptions.InsufficientQuantityException;
import captainadhoc.captainadhocbackend.services.implementations.ProductService;
import captainadhoc.captainadhocbackend.services.interfaces.IPurchaseProductService;
import captainadhoc.captainadhocbackend.services.interfaces.IPurchaseService;
import captainadhoc.captainadhocbackend.services.interfaces.IMemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@SpringBootTest
public class ProductServiceIntegrationTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private IMemberService memberService;

    @Autowired
    private IPurchaseProductService purchaseProductService;

    @Autowired
    private IPurchaseService purchaseService;

    private DataLoader dataLoader;

    private Product product;

    @BeforeEach
    public void setup() {

        product = Product.builder()
                .productQuantity(15)
                .productName("produit1")
                .productDescription("description1")
                .productPicture("https://aaaa")
                .productPrice(1)
                .build();

        dataLoader = new DataLoader(productService, memberService, purchaseService, purchaseProductService);

        dataLoader.run();
    }

    @Test
    public void saveProductTest() {

        // le produit n'a pas d'ID
        assertNull(product.getIdProduct());

        // when: le produit est persistée
        productService.saveProduct(product);

        // then: le produit a un id
        assertNotNull(product.getIdProduct());

        // then: le produit a bien été ajouté en base
        assertEquals(6, productService.findAllProducts().size());

    }

    @Test
    public void findAllProductsTest() {

        // given: un DataLoader initialisant la base des produits

        // when: la liste des produits est récupérée
        ArrayList<Product> productList = productService.findAllProducts();

        // then: on a récupérer l'ensemble des produits
        assertEquals(5, productList.size());
    }

    @Test
    public void modifyQuantityTest() {

        //given un produit
        Product product = productService.findAllProducts().get(0);

        assertEquals(15, product.getProductQuantity());

        // when: la méthode modifyQuantity est invoquée
        productService.modifyQuantity(product.getIdProduct(),20);

        //then: la quantite du produit a été modifiée en base
        assertEquals(20, productService.findAllProducts().get(0).getProductQuantity());
    }

    @Test
    public void decrementQuantityTest() {

        Long idProductToDecrement = productService.findAllProducts().get(0).getIdProduct();

        // when: la méthode decrementQuantity est invoquée
        Product product = productService.decrementQuantity(idProductToDecrement, 5);

        //then: la quantite du produit a été modifiée en base
        assertEquals(10, productService.getProductRepository().findById(idProductToDecrement).get().getProductQuantity());

        //then: le produit modifié a été retourné
        assertEquals(idProductToDecrement, product.getIdProduct());
        assertEquals(10, product.getProductQuantity());
    }

    @Test
    public void DecrementQuantityTestException() {

        //given un produit
        Long idProductToDecrement = productService.findAllProducts().get(0).getIdProduct();

        // when: la méthode decrementQuantity est invoquée
        // then: une exception est levé
        assertThrows(InsufficientQuantityException.class, () ->
                productService.decrementQuantity(idProductToDecrement, 100)
        );

    }
}
