package captainadhoc.captainadhocbackend.controllers;

import captainadhoc.captainadhocbackend.domain.Member;
import captainadhoc.captainadhocbackend.dto.UserRegistrationDto;
import captainadhoc.captainadhocbackend.dto.UserDto;
import captainadhoc.captainadhocbackend.exceptions.ExistingUserException;
import captainadhoc.captainadhocbackend.services.implementations.MemberService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class MemberController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MemberService memberService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> registerUserAccount(
            @RequestBody UserRegistrationDto memberRegistrationDto) {

        try {
            Member member = modelMapper.map(
                    memberRegistrationDto,
                    Member.class);

            memberService.saveMember(member);

        } catch (ExistingUserException exception) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        return ResponseEntity.ok("Utilisateur enregistré.");
    }

    @GetMapping("/current-member")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserDto> getUser() {

        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        Member member =
                memberService.findByUserName(auth.getName());

        UserDto memberDto =
                modelMapper.map(member, UserDto.class);

        return ResponseEntity.ok(memberDto);
    }
}
