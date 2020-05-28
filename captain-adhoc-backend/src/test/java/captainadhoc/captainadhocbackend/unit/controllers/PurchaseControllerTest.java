package captainadhoc.captainadhocbackend.unit.controllers;

import captainadhoc.captainadhocbackend.controllers.PurchaseController;
import captainadhoc.captainadhocbackend.domain.Member;
import captainadhoc.captainadhocbackend.domain.Product;
import captainadhoc.captainadhocbackend.domain.Purchase;
import captainadhoc.captainadhocbackend.dto.ProductPurchaseDto;
import captainadhoc.captainadhocbackend.dto.PurchaseDto;
import captainadhoc.captainadhocbackend.exceptions.InsufficientQuantityException;
import captainadhoc.captainadhocbackend.services.implementations.MemberService;
import captainadhoc.captainadhocbackend.services.interfaces.IPurchaseService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;

@SpringBootTest
public class PurchaseControllerTest {

    @MockBean
    private IPurchaseService purchaseService;

    @MockBean
    private MemberService memberService;

    private PurchaseController purchaseController;

    private static PurchaseDto purchaseDto;

    private static Member member;

    @BeforeAll
    public static void setup() {
        ProductPurchaseDto productPurchaseDto1 = new ProductPurchaseDto(1L, 2);
        ProductPurchaseDto productPurchaseDto2 = new ProductPurchaseDto(2L, 3);

        List<ProductPurchaseDto> productPurchaseDtoList = new ArrayList<>();
        productPurchaseDtoList.add(productPurchaseDto1);
        productPurchaseDtoList.add(productPurchaseDto2);

        member = Member.builder()
                .lastName("Kevin")
                .firstName("Marchand")
                .userName("marchand1")
                .password("mdp")
                .isAdmin(true)
                .build();

        purchaseDto = new PurchaseDto("CODE", productPurchaseDtoList);
    }

    @BeforeEach
    public void setupEach() {
        purchaseController = new PurchaseController();
        purchaseController.setMemberService(memberService);
        purchaseController.setPurchaseService(purchaseService);
    }

    @Test
    @WithMockUser("marchand1")
    public void getPurchasesIsAdminTest() {

        when(memberService.findByUserName(member.getUserName())).thenReturn(member);

        // when : getPurchases est appelé
        purchaseController.getPurchases();

        // then : findByUserName du service MemberService est appelé
        verify(memberService).findByUserName("marchand1");

        // then : l'ensemble des produits sont récupérer
        verify(purchaseService).findAllPurchases();
    }

    @Test
    @WithMockUser("Keke")
    public void getPurchasesIsNotAdminTest() {

        List<Purchase> purchaseList = new ArrayList<>();

        // given : un member qui n'a pas les droits d'admin
        Member member = Member.builder()
                .lastName("User")
                .firstName("Kev")
                .userName("Keke")
                .password("password31")
                .isAdmin(false)
                .purchaseList(purchaseList)
                .build();

        memberService.saveMember(member);

        when(memberService.findByUserName(member.getUserName())).thenReturn(member);

        // when : getPurchases est appelé
        purchaseController.getPurchases();

        // then : findByUserName du service MemberService est appelé
        verify(memberService).findByUserName("Keke");

        // then : on ne récupère pas l'ensemble des produits car ce n'est pas un utilisateur admin
        verify(purchaseService, times(0)).findAllPurchases();
    }

    @Test
    @WithMockUser("marchand1")
    public void postPurchaseTest() {

        when(memberService.findByUserName(member.getUserName())).thenReturn(member);

        purchaseController.postPurchase(purchaseDto);

        // then : findByUserName du service MemberService est appelé
        verify(memberService).findByUserName("marchand1");

        //then : la méthode newPurchase est invoquée
        verify(purchaseService).newPurchase(purchaseDto, member);
    }

    @Test
    @WithMockUser("marchand1")
    public void postPurchaseExceptionTest() {

        when(memberService.findByUserName(member.getUserName())).thenReturn(member);

        doThrow(new InsufficientQuantityException(new Product())).when(purchaseService).newPurchase(purchaseDto, member);

        //then : la méthode postPurchase renvoie une exception
        assertThrows(ResponseStatusException.class, () ->
                purchaseController.postPurchase(purchaseDto)
        );
    }
}
