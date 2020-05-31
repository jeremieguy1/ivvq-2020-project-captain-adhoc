package captainadhoc.captainadhocbackend.integration.services;

import captainadhoc.captainadhocbackend.domain.Member;
import captainadhoc.captainadhocbackend.services.implementations.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class MemberServiceIntegrationTest {

    @Autowired
    private MemberService memberService;

    private Member member;

    @BeforeEach
    public void setupEach() {

        // given un Member
        member = Member.builder()
                .lastName("Kevin")
                .firstName("Marchand")
                .userName("marchand1")
                .password("mdp")
                .isAdmin(true)
                .build();

        memberService.saveMember(member);
    }

    @Test
    public void saveMemberTest() {

        // given: un Member non persisté
        Member member = Member.builder()
                .lastName("Test")
                .firstName("Utilisateur")
                .userName("UtilisateurTest")
                .password("mdp")
                .isAdmin(true)
                .build();

        assertNull(member.getIdMember());

        // when: le member est persisté
        memberService.saveMember(member);

        // then: le member a un id
        assertNotNull(member.getIdMember());
    }

    @Test
    public void findByIdTest() {

        // when : findById est invoquée
        Member memberFound = memberService.findById(member.getIdMember());

        // then : le bon Member a été récupéré
        assertEquals(member, memberFound);
    }

    @Test
    public void findByUserNameTest() {
        // when : findById est invoquée
        Member memberFound = memberService.findByUserName(member.getUserName());

        // then : le bon Member a été récupéré
        assertEquals(member, memberFound);
    }

    @Test
    public void loadUserByUsername() {

        // when : loadUserByUsername est invoquée
        UserDetails userDetails = memberService.loadUserByUsername("marchand1");

        // then : on obtient un UserDetail correspondant au User en base
        assertEquals(member.getUserName(), userDetails.getUsername());
    }

    @Test
    public void loadUserByUsernameExceptionTest() {

        // when : loadUserByUsername est invoquée avec un nom d'utilisateur qui n'existe pas
        // then: une exception InsufficientQuantityException est levé
        assertThrows(UsernameNotFoundException.class, () ->
                memberService.loadUserByUsername("NotExist")
        );
    }
}
