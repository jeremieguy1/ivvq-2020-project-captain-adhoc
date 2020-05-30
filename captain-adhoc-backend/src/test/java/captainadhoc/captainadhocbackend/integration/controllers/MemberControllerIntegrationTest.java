package captainadhoc.captainadhocbackend.integration.controllers;

import captainadhoc.captainadhocbackend.dto.UserRegistrationDto;
import captainadhoc.captainadhocbackend.integration.DataLoader;
import captainadhoc.captainadhocbackend.services.interfaces.IMemberService;
import captainadhoc.captainadhocbackend.services.interfaces.IProductService;
import captainadhoc.captainadhocbackend.services.interfaces.IPurchaseProductService;
import captainadhoc.captainadhocbackend.services.interfaces.IPurchaseService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.nio.charset.Charset;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@Transactional
public class MemberControllerIntegrationTest {

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

    private MediaType contentTypeText = new MediaType(MediaType.TEXT_PLAIN.getType(),
            MediaType.TEXT_PLAIN.getSubtype(),
            Charset.forName("utf8"));

    private MediaType contentTypeJson = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype());

    @BeforeEach
    public void setup() {

        mapper = new ObjectMapper();

        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        DataLoader dataLoader = new DataLoader(productService, memberService, purchaseService, purchaseProductService);
        dataLoader.run();
    }

    @Test
    public void registerMemberAccountTest() throws Exception {

        UserRegistrationDto userRegistrationDto = new UserRegistrationDto("Kev", "User", "Keke", "password31");

        // when: l'utilisateur émet une requête pour s'enregistrer
        mockMvc.perform(post("/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(userRegistrationDto)))
                // then: la réponse a le status 201(OK)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(contentTypeText))
                // then: le résultat obtenu contient le message "Utilisateur enregistré."
                .andExpect(jsonPath("$", Matchers.is("Utilisateur enregistré.")));

    }

    @Test
    public void registerMemberAccountExeptionTest() throws Exception {

        UserRegistrationDto userRegistrationDto =
                new UserRegistrationDto("Kevin", "Marchand", "marchand1", "mdp");

        // when: l'utilisateur émet une requête pour s'enregistrer
        mockMvc.perform(post("/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(userRegistrationDto)))
                // then: la réponse a le status 409(CONFLIT) car l'utilisateur existe déjà
                .andExpect(status().isConflict());

    }

    @WithMockUser("marchand1")
    @Test
    public void getMember() throws Exception {

        // when:  une requête est émise pour récupérer les informations de l'utilisateur
        mockMvc.perform(get("/member"))
                // then: la réponse a le status 200(OK)
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentTypeJson))
                .andExpect(jsonPath("$.userName", Matchers.is("marchand1")))
                .andExpect(jsonPath("$.isAdmin", Matchers.is(true)));

    }
}
