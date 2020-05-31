package captainadhoc.captainadhocbackend.unit.services;

import captainadhoc.captainadhocbackend.domain.Product;
import captainadhoc.captainadhocbackend.exceptions.InsufficientQuantityException;
import captainadhoc.captainadhocbackend.services.implementations.ProductService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import captainadhoc.captainadhocbackend.repositories.ProductRepository;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class ProductServiceTest {

    @MockBean
    private ProductRepository productRepository;

    private ProductService productService;

    private static Product product;

    @BeforeAll
    public static void setup() {
        product = Product.builder()
                .idProduct(1L)
                .productQuantity(15)
                .productName("product")
                .productDescription("description")
                .productDescription("image")
                .productPrice(1)
                .build();
    }

    @BeforeEach
    public void setupEach() {
        productService = new ProductService();
        productService.setProductRepository(productRepository);
    }

    @Test
    public void saveProductTest() {

        // when: la méthode saveProduct est invoquée
        productService.saveProduct(product);

        // then: la méthode save du ProductRepository associé est invoquée
        verify(productRepository).save(product);
    }

    @Test
    public void findAllProductsTest() {

        // when: la méthode findAllProducts est invoquée
        productService.findAllProducts();

        // then: la méthode findAll du Repository associé est invoquée
        verify(productService.getProductRepository()).findAll();
    }

    @Test
    public void modifyQuantityTest() {

        // when: la méthode findById du repository associé renvoie un produit
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // when: la méthode modifyQuantity est invoquée
        productService.modifyQuantity(product.getIdProduct(),20);

        // then: la méthode save du ProductRepository associé est invoquée
        verify(productRepository).save(product);

        // then: la méthode findById du ProductRepository associé est invoquée
        verify(productRepository).findById(product.getIdProduct());

        // then: la quantite du product a été mis à jour
        assertEquals(20, product.getProductQuantity());
    }

    @Test
    public void modifyQuantityProductNotFoundTest() {

        // given: un objet Optional vide
        Optional<Product> optionalProduct = Optional.empty();

        // when: la méthode findById du repository associé renvoie un objet Optional vide
        when(productRepository.findById(1L)).thenReturn(optionalProduct);

        // when: la méthode modifyQuantity est invoquée avec un idProduct null
        // then : la méthode renvoie une exception IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () ->
                productService.modifyQuantity(1L, 20)
        );

        // then: la méthode findById du ProductRepository associé est invoquée
        verify(productRepository).findById(1L);

        // then: la méthode save du ProductRepository associé n'est pas invoquée
        verify(productRepository, times(0)).save(Mockito.any(Product.class));

    }

    @Test
    public void decrementQuantityTest() {

        // given : un Product
        Product product = Product.builder()
                .idProduct(1L)
                .productQuantity(15)
                .productName("product")
                .productDescription("description")
                .productDescription("image")
                .productPrice(1)
                .build();

        //given: la quantité du product acheté
        int quantiteProduct = 5;

        //when: la méthode findById du ProductRepository associé renvoie un produit
        when(productRepository.findById(product.getIdProduct())).thenReturn(Optional.of(product));

        //when: la méthode decrementQuantity est invoquée
        productService.decrementQuantity(product.getIdProduct(), quantiteProduct);

        // then: la méthode findById du ProductRepository associé est invoquée
        verify(productRepository).findById(product.getIdProduct());

        // then: la méthode save du ProductRepository associé est invoquée
        verify(productRepository).save(product);

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

        //given: la quantité du product acheté supérieur à la quantité disponible
        int productQuantity = 5;

        //when: la méthode findById du ProductRepository associé renvoie un produit
        when(productRepository.findById(product.getIdProduct())).thenReturn(Optional.of(product));

        //then: decrementQuantity renvoie une exception InsufficientQuantityException
        assertThrows(InsufficientQuantityException.class, () ->
                productService.decrementQuantity(product.getIdProduct(), productQuantity)
        );
    }
}
