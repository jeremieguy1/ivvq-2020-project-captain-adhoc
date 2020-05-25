package captainadhoc.captainadhocbackend.repositories;

import captainadhoc.captainadhocbackend.domain.Member;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends CrudRepository<Member, Long> {
    Member findByUserName(String UserName);
}
