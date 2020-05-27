package captainadhoc.captainadhocbackend.integration.controllers;

import captainadhoc.captainadhocbackend.domain.Product;
import captainadhoc.captainadhocbackend.dto.ProductPurchaseDto;
import captainadhoc.captainadhocbackend.dto.PurchaseDto;
import captainadhoc.captainadhocbackend.integration.DataLoader;
import captainadhoc.captainadhocbackend.services.interfaces.IMemberService;
import captainadhoc.captainadhocbackend.services.interfaces.IProductService;
import captainadhoc.captainadhocbackend.services.interfaces.IPurchaseProductService;
import captainadhoc.captainadhocbackend.services.interfaces.IPurchaseService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
public class PurchaseControllerIntegrationTest {

    @Autowired
    private IPurchaseService purchaseService;

    @Autowired
    private IProductService productService;

    @Autowired
    private IMemberService memberService;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private IPurchaseProductService purchaseProductService;

    private ObjectMapper mapper;

    private MockMvc mockMvc;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype());

    private String jsonResult;

    private DataLoader dataLoader;

    @BeforeEach
    public void setup() {

        mapper = new ObjectMapper();

        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        dataLoader = new DataLoader(productService, memberService, purchaseService, purchaseProductService);
        dataLoader.run();
    }

    @WithMockUser("marchand1")
    @Test
    public void getPurchasesTest() throws Exception {

        // when: l'utilisateur émet une requête pour obtenir la liste des produits
        mockMvc.perform(get("/purchases"))
                // then: la réponse a le status 200(OK)
                .andExpect(status().isOk())
                // then: la réponse est au format JSON
                .andExpect(content().contentType(contentType))
                .andDo(mvcResult -> {
                    jsonResult = mvcResult.getResponse().getContentAsString();
                });

        JSONArray jsonArray = new JSONArray(jsonResult);

        // then: le résultat obtenu contient 3 purchases
        assertEquals(3, jsonArray.length());

    }

    @WithMockUser("marchand1")
    @Test
    public void postPurchaseTest() throws Exception {

        PurchaseDto purchaseDto = initPurchaseDto(false);

        // when: l'utilisateur émet une requête pour obtenir la liste des produits
        mockMvc.perform(post("/purchases/order")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(purchaseDto)))
                // then: la réponse a le status 200(OK)
                .andExpect(status().isOk());

    }

    @WithMockUser("marchand1")
    @Test
    public void postPurchaseExceptionTest() throws Exception {

        PurchaseDto purchaseDto = initPurchaseDto(true);

        mockMvc.perform(post("/purchases/order")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(purchaseDto)))
                // then: la réponse a le status 409(CONFLIT)
                .andExpect(status().isConflict());
    }

    private PurchaseDto initPurchaseDto(boolean isExceptionTest) {

        ArrayList<Product> productArrayList = productService.findAllProducts();

        Long idProduct = productArrayList.get(0).getIdProduct();

        int quantity;

        if (isExceptionTest) {
            quantity = productArrayList.get(0).getProductQuantity() + 10;
        } else {
            quantity = productArrayList.get(0).getProductQuantity();
        }

        ProductPurchaseDto productPurchaseDto = ProductPurchaseDto.builder()
                .idProduct(idProduct)
                .quantity(quantity)
                .build();

        List<ProductPurchaseDto> productPurchaseDtoList = new ArrayList<>();
        productPurchaseDtoList.add(productPurchaseDto);

        return PurchaseDto.builder()
                .productPurchaseList(productPurchaseDtoList)
                .build();
    }
}
