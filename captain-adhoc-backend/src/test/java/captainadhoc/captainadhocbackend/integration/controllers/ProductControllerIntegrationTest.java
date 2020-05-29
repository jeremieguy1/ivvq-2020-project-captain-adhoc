package captainadhoc.captainadhocbackend.integration.controllers;

import captainadhoc.captainadhocbackend.domain.Member;
import captainadhoc.captainadhocbackend.domain.Product;
import captainadhoc.captainadhocbackend.integration.DataLoader;
import captainadhoc.captainadhocbackend.services.implementations.ProductService;
import captainadhoc.captainadhocbackend.services.interfaces.IMemberService;
import captainadhoc.captainadhocbackend.services.interfaces.IPurchaseProductService;
import captainadhoc.captainadhocbackend.services.interfaces.IPurchaseService;
import org.hamcrest.Matchers;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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

    @BeforeEach
    public void setup() {

        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        DataLoader dataLoader = new DataLoader(productService, memberService, purchaseService, purchaseProductService);
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
                // then: le résultat obtenu contient 5 produits
                .andExpect(jsonPath("$.length()", Matchers.is(5)))
                // then: le résultat obtenu contient le produit Mad box avec ses caractéristiques
                .andExpect(jsonPath("$.[2].productName", Matchers.is("Mad box")))
                .andExpect(jsonPath("$.[2].productQuantity", Matchers.is(2)))
                .andExpect(jsonPath("$.[2].productPrice", Matchers.is(666.)));

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

    @WithMockUser("marchand1")
    @Test
    public void modifyQuantityTestNegativeQuantity() throws Exception {

        // given : un entier négatif
        int negativeQuantity = -10;

        // when: l'utilisateur émet une requête pour obtenir la liste des produits avec une quantité négative
        mockMvc.perform(put("/products/modify/quantity?quantity="+negativeQuantity+"&idProduct=1"))
                // then: la réponse a le status 400(BAD REQUEST)
                .andExpect(status().isBadRequest());
    }

    @WithMockUser("Keke")
    @Test
    public void modifyQuantityTestNotAdmin() throws Exception {

        // given : un Member non admin
        Member member = Member.builder()
                .lastName("User")
                .firstName("Kev")
                .userName("Keke")
                .password("password31")
                .isAdmin(false)
                .build();

        memberService.saveMember(member);

        // when: l'utilisateur non admin émet une requête pour obtenir la liste des produits
        mockMvc.perform(put("/products/modify/quantity?quantity=10&idProduct=1"))
                // then: la réponse a le status 403(FORBIDDEN)
                .andExpect(status().isForbidden());
    }
}
