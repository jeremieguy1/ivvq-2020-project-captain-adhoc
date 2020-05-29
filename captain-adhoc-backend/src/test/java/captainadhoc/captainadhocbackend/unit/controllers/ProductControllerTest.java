package captainadhoc.captainadhocbackend.unit.controllers;

import captainadhoc.captainadhocbackend.controllers.ProductController;
import captainadhoc.captainadhocbackend.services.interfaces.IProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class ProductControllerTest {

    @MockBean
    private IProductService productService;

    private ProductController productController;

    @BeforeEach
    public void setupEach() {
        productController = new ProductController();
        productController.setProductService(productService);
    }

    @Test
    public void getAllProducts() {

        // when: on récupère dans le contrôleur la liste des produits
        productController.getAllProducts();

        // then: la requête est traitée par le service correspondant
        verify(productService).findAllProducts();
    }

    @Test
    public void modifyQuantity() {

        // when : on modifie la quantité d'un produit
        productController.modifyQuantity(10, 1L);

        //then : la requête est traitée par le service correspondant
        verify(productService).modifyQuantity(1L, 10);
    }

    @Test
    public void modifyQuantityTestNegativeQuantity() throws Exception {

        // when : on modifie la quantité d'un produit avec une valeur négative
        //then: modifyQuantity renvoie une exception ResponseStatusException
        assertThrows(ResponseStatusException.class, () ->
                productController.modifyQuantity(-10, 1L)
        );

        //then : la méthode modifyQuantity n'est jamais appelé par le service correspondant
        verify(productService,times(0)).modifyQuantity(1L, 10);
    }
}
