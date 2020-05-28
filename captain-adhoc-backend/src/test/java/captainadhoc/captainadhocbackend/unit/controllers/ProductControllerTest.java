package captainadhoc.captainadhocbackend.unit.controllers;

import captainadhoc.captainadhocbackend.controllers.ProductController;
import captainadhoc.captainadhocbackend.services.interfaces.IProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

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
}
