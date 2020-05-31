package captainadhoc.captainadhocbackend.services.interfaces;

import captainadhoc.captainadhocbackend.domain.Member;

public interface IMemberService {

    Member saveMember(Member member);

    Member findById(Long idMember);

    Member findByUserName(String memberName);
}
