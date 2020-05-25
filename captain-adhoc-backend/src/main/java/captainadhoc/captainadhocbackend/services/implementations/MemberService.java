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
    public Member saveMember(Member user) {

        if (findByUserName(user.getUserName()) != null) {

            throw new ExistingUserException(
                    user.getUserName());
        }

        user.setPassword(
                bCryptPasswordEncoder.encode(user.getPassword()));

        return memberRepository.save(user);
    }

    @Override
    public Member findById(Long idUser) {
        return memberRepository.findById(idUser).orElse(null);
    }

    @Override
    public Member findByUserName(String userName) {
        return memberRepository.findByUserName(userName);
    }

    @Override
    public UserDetails loadUserByUsername(String userName)
            throws UsernameNotFoundException {

        Member user =
                memberRepository.findByUserName(userName);

        if (user == null) {
            throw new UsernameNotFoundException(userName);
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassword(),
                emptyList());
    }
}
