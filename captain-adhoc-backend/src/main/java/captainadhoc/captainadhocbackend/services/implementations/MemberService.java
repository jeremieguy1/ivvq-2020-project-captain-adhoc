package captainadhoc.captainadhocbackend.services.implementations;

import captainadhoc.captainadhocbackend.domain.Member;
import captainadhoc.captainadhocbackend.exceptions.ExistingUserException;
import captainadhoc.captainadhocbackend.repositories.MemberRepository;
import captainadhoc.captainadhocbackend.services.interfaces.IMemberService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

@Service
public class MemberService implements IMemberService, UserDetailsService {

    @Autowired
    @Getter
    @Setter
    private MemberRepository memberRepository;

    @Autowired
    @Getter
    @Setter
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Member saveMember(Member member) {

        if (findByUserName(member.getUserName()) != null) {

            throw new ExistingUserException(
                    member.getUserName());
        }

        member.setPassword(
                bCryptPasswordEncoder.encode(member.getPassword()));

        return memberRepository.save(member);
    }

    @Override
    public Member findById(Long idMember) {
        return memberRepository.findById(idMember).orElse(null);
    }

    @Override
    public Member findByUserName(String userName) {
        return memberRepository.findByUserName(userName);
    }

    @Override
    public UserDetails loadUserByUsername(String userName)
            throws UsernameNotFoundException {

        Member member =
                memberRepository.findByUserName(userName);

        if (member == null) {
            throw new UsernameNotFoundException(userName);
        }

        return new org.springframework.security.core.userdetails.User(
                member.getUserName(),
                member.getPassword(),
                emptyList());
    }
}
