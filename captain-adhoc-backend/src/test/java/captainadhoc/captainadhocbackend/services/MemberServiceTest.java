package captainadhoc.captainadhocbackend.services;

import captainadhoc.captainadhocbackend.domain.Member;
import captainadhoc.captainadhocbackend.repositories.MemberRepository;
import captainadhoc.captainadhocbackend.services.implementations.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class MemberServiceTest {

    @MockBean
    private MemberRepository memberRepository;

    @MockBean
    private BCryptPasswordEncoder passwordEncoder;

    private MemberService memberService;
    private Member member;

    @BeforeEach
    public void setupEach() {
        memberService = new MemberService();
        memberService.setMemberRepository(memberRepository);
        memberService.setBCryptPasswordEncoder(passwordEncoder);

        member = Member.builder()
                .lastName("Kevin")
                .firstName("Marchand")
                .userName("marchand1")
                .password("mdp")
                .isAdmin(true)
                .build();
    }


    @Test
    public void testSaveMember() {

        // Given: un MemberService et un member
        // When: la méthode saveMember est invoquée
        when(memberService.getMemberRepository().save(member)).thenReturn(member);
        memberService.saveMember(member);

        // then: la méthode findAll du Repository associé est invoquée
        verify(memberService.getMemberRepository()).save(member);
    }

    @Test
    public void testFindById() {

        // given: un MemberService
        // when: la méthode findById est invoquée
        memberService.findById(0L);

        // then: la méthode findById du Repository associé est invoquée
        verify(memberService.getMemberRepository()).findById(0L);
    }

    @Test
    public void testFindByMemberName() {

        // given: un MemberService
        // when: la méthode findByUserName est invoquée
        memberService.findByUserName("nomUtilisateur");

        // then: la méthode findById du Repository associé est invoquée
        verify(memberService.getMemberRepository()).findByUserName("nomUtilisateur");
    }

    @Test
    public void testLoadUserByUsername() {

        // given: un UserService
        when(memberService.getMemberRepository().findByUserName("nomUtilisateur")).thenReturn(member);

        // when: la méthode loadUserByUsername est invoquée
        memberService.loadUserByUsername("nomUtilisateur");

        // then: la méthode findById du Repository associé est invoquée
        verify(memberService.getMemberRepository()).findByUserName("nomUtilisateur");
    }
}
