package captainadhoc.captainadhocbackend.unit.controllers;

import captainadhoc.captainadhocbackend.controllers.MemberController;
import captainadhoc.captainadhocbackend.domain.Member;
import captainadhoc.captainadhocbackend.dto.UserDto;
import captainadhoc.captainadhocbackend.dto.UserRegistrationDto;
import captainadhoc.captainadhocbackend.exceptions.ExistingUserException;
import captainadhoc.captainadhocbackend.services.implementations.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doThrow;

@SpringBootTest
public class MemberControllerTest {

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private MemberService memberService;

    private MemberController memberController;

    @BeforeEach
    public void setupEach() {
        memberController = new MemberController();
        memberController.setMemberService(memberService);
        memberController.setModelMapper(modelMapper);
    }

    @Test
    public void registerMemberAccountTest() {

        UserRegistrationDto userRegistrationDto =
                new UserRegistrationDto("Kev", "User", "Keke", "password31");

        Member member = Member.builder()
                .lastName("User")
                .firstName("Kev")
                .userName("Keke")
                .password("password31")
                .isAdmin(false)
                .build();

        when(modelMapper.map(Mockito.any(), Mockito.any())).thenReturn(member);

        ResponseEntity<String> responseEntity = memberController.registerMemberAccount(userRegistrationDto);

        verify(modelMapper).map(userRegistrationDto, Member.class);

        // then: la méthode saveMember du Service memberService est invoquée
        verify(memberService).saveMember(member);

        assertEquals("User created.", responseEntity.getBody());

    }

    @Test
    public void registerMemberAccountExceptionTest() {

        UserRegistrationDto userRegistrationDto =
                new UserRegistrationDto("Kevin", "Marchand", "marchand1", "mdp");

        Member member = Member.builder()
                .lastName("Kevin")
                .firstName("Marchand")
                .userName("marchand1")
                .password("mdp")
                .isAdmin(true)
                .build();

        when(modelMapper.map(Mockito.any(), Mockito.any())).thenReturn(member);

        doThrow(new ExistingUserException("marchand1")).when(memberService).saveMember(Mockito.any());

        assertThrows(ResponseStatusException.class, () ->
                memberController.registerMemberAccount(userRegistrationDto)
        );

    }

    @Test
    @WithMockUser("marchand1")
    public void getMemberTest() {

        Member member = Member.builder()
                .lastName("Kevin")
                .firstName("Marchand")
                .userName("marchand1")
                .password("mdp")
                .isAdmin(true)
                .build();

        UserDto userDto = new UserDto();

        when(memberService.findByUserName(member.getUserName())).thenReturn(member);

        when(modelMapper.map(Member.class,UserDto.class)).thenReturn(userDto);

        memberController.getMember();

        verify(memberService).findByUserName("marchand1");

        verify(modelMapper).map(member, UserDto.class);

    }
}
