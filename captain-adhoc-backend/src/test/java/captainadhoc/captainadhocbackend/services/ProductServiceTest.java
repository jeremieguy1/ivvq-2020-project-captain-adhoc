package captainadhoc.captainadhocbackend.services;

import captainadhoc.captainadhocbackend.domain.Product;
import captainadhoc.captainadhocbackend.exceptions.InsufficientQuantityException;
import captainadhoc.captainadhocbackend.services.implementations.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import captainadhoc.captainadhocbackend.repositories.ProductRepository;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    public void saveProductTest() {

        //given un product
        Product product = Product.builder()
                .productQuantity(15)
                .productName("product")
                .productDescription("description")
                .productPicture("image")
                .productPrice(1)
                .build();

        when(productService.getProductRepository().save(product)).thenReturn(product);

        // when: la méthode saveProduct est invoquée
        productService.saveProduct(product);

        // then: la méthode save du ProductRepository associé est invoquée
        verify(productService.getProductRepository()).save(product);
    }

    @Test
    public void findAllProductsTest() {
        // given: un ProductService
        // when: la méthode findAllProducts est invoquée
        productService.findAllProducts();

        // then: la méthode findAll du Repository associé est invoquée
        verify(productService.getProductRepository()).findAll();
    }

    @Test
    public void modifyQuantityTest() {

        //given un product
        Product product = Product.builder()
                .idProduct(1L)
                .productQuantity(15)
                .productName("product")
                .productDescription("description")
                .productDescription("image")
                .productPrice(1)
                .build();

        when(productService.getProductRepository().save(product)).thenReturn(product);
        when(productService.getProductRepository().findById(1L)).thenReturn(Optional.of(product));

        // when: la méthode modifyQuantity est invoquée
        productService.modifyQuantity(product.getIdProduct(),20);

        // then: la méthode save du ProductRepository associé est invoquée
        verify(productService.getProductRepository()).save(product);

        // then: la méthode findById du ProductRepository associé est invoquée
        verify(productService.getProductRepository()).findById(product.getIdProduct());

        // then: la quantite du product a été mis à jour
        assertEquals(20, product.getProductQuantity());
    }

    @Test
    public void decrementQuantityTest() {

        //given un product
        Product product = Product.builder()
                .idProduct(1L)
                .productQuantity(15)
                .productName("product")
                .productDescription("description")
                .productPicture("image")
                .productPrice(1)
                .build();

        //given: la quantité du product acheté
        int quantiteProduct = 5;

        when(productService.getProductRepository().findById(product.getIdProduct())).thenReturn(Optional.of(product));
        when(productService.getProductRepository().save(product)).thenReturn(product);

        //when: la méthode decrementQuantity est invoquée
        productService.decrementQuantity(product.getIdProduct(), quantiteProduct);

        // then: la méthode findById du ProductRepository associé est invoquée
        verify(productService.getProductRepository()).findById(product.getIdProduct());

        // then: la méthode save du ProductRepository associé est invoquée
        verify(productService.getProductRepository()).save(product);

        // then: la quantite du product a été mis à jour
        assertEquals(10, product.getProductQuantity());
    }

    @Test
    public void decrementQuantityExceptionTest() {

        //given un product
        Product product = Product.builder()
                .idProduct(1L)
                .productQuantity(1)
                .productName("product")
                .productDescription("description")
                .productPicture("image")
                .productPrice(1)
                .build();

        //given: la quantité du product acheté
        int productQuantity = 5;

        when(productService.getProductRepository().findById(product.getIdProduct())).thenReturn(Optional.of(product));

        assertThrows(InsufficientQuantityException.class, () ->
                productService.decrementQuantity(product.getIdProduct(), productQuantity)
        );
    }
}
