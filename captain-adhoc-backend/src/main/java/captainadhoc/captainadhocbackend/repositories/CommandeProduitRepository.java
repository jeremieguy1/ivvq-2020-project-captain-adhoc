package captainadhoc.captainadhocbackend.repositories;

import captainadhoc.captainadhocbackend.domain.CommandeProduit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommandeProduitRepository extends CrudRepository<CommandeProduit, Long> {
}
