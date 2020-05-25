package captainadhoc.captainadhocbackend.repositories;

import captainadhoc.captainadhocbackend.domain.PurchaseProduct;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseProductRepository extends CrudRepository<PurchaseProduct, Long> {
}
