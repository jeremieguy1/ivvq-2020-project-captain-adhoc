package captainadhoc.captainadhocbackend.repositories;

import captainadhoc.captainadhocbackend.domain.Marchand;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarchandRepository extends CrudRepository<Marchand,Long> {
}
