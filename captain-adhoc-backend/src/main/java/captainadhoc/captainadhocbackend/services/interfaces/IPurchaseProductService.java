package captainadhoc.captainadhocbackend.services.interfaces;

import captainadhoc.captainadhocbackend.dto.ProductPurchaseDto;
import captainadhoc.captainadhocbackend.domain.Purchase;
import captainadhoc.captainadhocbackend.domain.PurchaseProduct;
import captainadhoc.captainadhocbackend.exceptions.InsufficientQuantityException;

import java.util.List;

public interface IPurchaseProductService {

    void savePurchaseProduct(PurchaseProduct purchaseProduct);

    List<PurchaseProduct> createPurchaseProduct(
            List<ProductPurchaseDto> productPurchaseList,
            Purchase purchase)
            throws InsufficientQuantityException;

    void saveAllPurchaseProducts(List<PurchaseProduct> purchaseProductList);
}
