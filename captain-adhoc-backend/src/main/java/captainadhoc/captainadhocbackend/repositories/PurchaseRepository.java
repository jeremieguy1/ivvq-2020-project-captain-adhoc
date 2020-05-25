package captainadhoc.captainadhocbackend.repositories;

import captainadhoc.captainadhocbackend.domain.Purchase;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends CrudRepository<Purchase, Long> {
}
