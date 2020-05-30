package captainadhoc.captainadhocbackend.controllers;

import captainadhoc.captainadhocbackend.domain.Member;
import captainadhoc.captainadhocbackend.dto.UserRegistrationDto;
import captainadhoc.captainadhocbackend.dto.UserDto;
import captainadhoc.captainadhocbackend.exceptions.ExistingUserException;
import captainadhoc.captainadhocbackend.services.implementations.MemberService;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;

@Setter
@RestController
public class MemberController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MemberService memberService;

    @PostMapping("/members")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> registerMemberAccount(
            @RequestBody UserRegistrationDto memberRegistrationDto) {
        Member member = null;
        try {
            member = modelMapper.map(
                    memberRegistrationDto,
                    Member.class);

            memberService.saveMember(member);

        } catch (ExistingUserException exception) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        URI location = URI.create(String.format("/members/%s", member.getIdMember()));

        return ResponseEntity.created(location).body("User created.");
    }

    @GetMapping("/member")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserDto> getMember() {

        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        Member member =
                memberService.findByUserName(auth.getName());

        UserDto memberDto =
                modelMapper.map(member, UserDto.class);

        return ResponseEntity.ok(memberDto);
    }
}
