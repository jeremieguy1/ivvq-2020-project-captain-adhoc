package captainadhoc.captainadhocbackend.unit.controllers;

import captainadhoc.captainadhocbackend.controllers.ProductController;
import captainadhoc.captainadhocbackend.domain.Member;
import captainadhoc.captainadhocbackend.services.implementations.MemberService;
import captainadhoc.captainadhocbackend.services.interfaces.IProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;

@SpringBootTest
public class ProductControllerTest {

    @MockBean
    private IProductService productService;

    @MockBean
    private MemberService memberService;

    private ProductController productController;

    @BeforeEach
    public void setupEach() {
        productController = new ProductController();
        productController.setProductService(productService);
        productController.setMemberService(memberService);
    }

    @Test
    public void getAllProducts() {

        // when: on récupère dans le contrôleur la liste des produits
        productController.getAllProducts();

        // then: la requête est traitée par le service correspondant
        verify(productService).findAllProducts();
    }

    @Test
    @WithMockUser("marchand1")
    public void modifyQuantityTest() {

        // given: un member qui a les droits d'admin
        Member member = Member.builder()
                .lastName("Kevin")
                .firstName("Marchand")
                .userName("marchand1")
                .password("mdp")
                .isAdmin(true)
                .build();

        // when: findByUserName renvoie bien un objet Member
        when(memberService.findByUserName(member.getUserName())).thenReturn(member);

        // when : on modifie la quantité d'un produit
        productController.modifyQuantity(10, 1L);

        //then : la requête est traitée par le service correspondant
        verify(productService).modifyQuantity(1L, 10);
    }

    @Test
    public void modifyQuantityTestNegativeQuantity() {

        // when : on modifie la quantité d'un produit avec une valeur négative
        //then: modifyQuantity renvoie une exception ResponseStatusException
        assertThrows(ResponseStatusException.class, () ->
                productController.modifyQuantity(-10, 1L)
        );

        //then : la méthode modifyQuantity n'est jamais appelé par le service correspondant
        verify(productService,times(0)).modifyQuantity(1L, 10);
    }

    @Test
    @WithMockUser("marchand1")
    public void modifyQuantityTestProductNotFound() {

        // given: un member qui a les droits d'admin
        Member member = Member.builder()
                .lastName("Kevin")
                .firstName("Marchand")
                .userName("marchand1")
                .password("mdp")
                .isAdmin(true)
                .build();

        // when: findByUserName renvoie bien un objet Member
        when(memberService.findByUserName(member.getUserName())).thenReturn(member);

        // when: la méthode modifyQuantity du productService renvoie une exception IllegalArgumentException
        doThrow(new IllegalArgumentException())
                .when(productService).modifyQuantity(Mockito.any(Long.class), Mockito.any(int.class));

        // when: la méthode modifyQuantity est invoquée
        // then: modifyQuantity renvoie une exception ResponseStatusException
        assertThrows(ResponseStatusException.class, () ->
                productController.modifyQuantity(10, 1L)
        );
    }

    @Test
    @WithMockUser("Keke")
    public void modifyQuantityTestNotAdmin() {

        // given : un member qui n'a pas les droits d'admin
        Member member = Member.builder()
                .lastName("User")
                .firstName("Kev")
                .userName("Keke")
                .password("password31")
                .isAdmin(false)
                .build();

        // when: findByUserName renvoie bien un objet Member
        when(memberService.findByUserName(member.getUserName())).thenReturn(member);

        //then : la méthode postPurchase renvoie une exception ResponseStatusException
        assertThrows(ResponseStatusException.class, () ->
                productController.modifyQuantity(10, 1L)
        );
    }
}
