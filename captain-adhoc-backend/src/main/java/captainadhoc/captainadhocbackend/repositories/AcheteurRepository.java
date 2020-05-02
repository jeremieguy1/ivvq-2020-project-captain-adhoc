package captainadhoc.captainadhocbackend.repositories;

import captainadhoc.captainadhocbackend.domain.Acheteur;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcheteurRepository extends CrudRepository<Acheteur,Long> {
}
