package captainadhoc.captainadhocbackend.integration.controllers;

import captainadhoc.captainadhocbackend.domain.Product;
import captainadhoc.captainadhocbackend.integration.DataLoader;
import captainadhoc.captainadhocbackend.services.implementations.ProductService;
import captainadhoc.captainadhocbackend.services.interfaces.IMemberService;
import captainadhoc.captainadhocbackend.services.interfaces.IPurchaseProductService;
import captainadhoc.captainadhocbackend.services.interfaces.IPurchaseService;
import org.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
public class ProductControllerIntegrationTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private IMemberService memberService;

    @Autowired
    private IPurchaseProductService purchaseProductService;

    @Autowired
    private IPurchaseService purchaseService;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype());

    private String jsonResult;

    private DataLoader dataLoader;

    @BeforeEach
    public void setup() {

        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        dataLoader = new DataLoader(productService, memberService, purchaseService, purchaseProductService);
        dataLoader.run();
    }

    @WithMockUser("marchand1")
    @Test
    public void getAllProductsTest() throws Exception {

        // when: l'utilisateur émet une requête pour obtenir la liste des produits
        mockMvc.perform(get("/products"))
                // then: la réponse a le status 200(OK)
                .andExpect(status().isOk())
                // then: la réponse est au format JSON
                .andExpect(content().contentType(contentType))
                .andDo(mvcResult -> {
                    jsonResult = mvcResult.getResponse().getContentAsString();
                });

        JSONArray jsonArray = new JSONArray(jsonResult);

        // then: le résultat obtenu contient 5 produits
        assertEquals(5, jsonArray.length());

        // then: le résultat obtenu contient le produit Mad box
        assertThat(jsonResult, containsString("Mad box"));
    }

    @WithMockUser("marchand1")
    @Test
    public void modifyQuantityTest() throws Exception {

        Product product = Product.builder()
                .productQuantity(15)
                .productName("product")
                .productDescription("description")
                .productPicture("image")
                .productPrice(1)
                .build();

        productService.saveProduct(product);

        Long idProduct = product.getIdProduct();

        // when: l'utilisateur émet une requête pour obtenir la liste des produits
        mockMvc.perform(put("/products/modify/quantity?quantity=10&idProduct="+idProduct))
                // then: la réponse a le status 200(OK)
                .andExpect(status().isOk());

        //then la quantité du produit a été modifiée
        assertEquals(10, product.getProductQuantity());
    }
}
