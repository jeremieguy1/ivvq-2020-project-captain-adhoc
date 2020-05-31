package captainadhoc.captainadhocbackend.services.implementations;

import captainadhoc.captainadhocbackend.dto.ProductPurchaseDto;
import captainadhoc.captainadhocbackend.domain.Purchase;
import captainadhoc.captainadhocbackend.domain.PurchaseProduct;
import captainadhoc.captainadhocbackend.domain.Product;
import captainadhoc.captainadhocbackend.exceptions.InsufficientQuantityException;
import captainadhoc.captainadhocbackend.repositories.PurchaseProductRepository;
import captainadhoc.captainadhocbackend.services.interfaces.IPurchaseProductService;
import captainadhoc.captainadhocbackend.services.interfaces.IProductService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@Service
public class PurchaseProductService implements IPurchaseProductService {


    @Autowired
    private PurchaseProductRepository purchaseProductRepository;

    @Autowired
    private IProductService productService;

    @Override
    public void savePurchaseProduct(PurchaseProduct purchaseProduct) {
        purchaseProductRepository.save(purchaseProduct);
    }

    @Override
    public List<PurchaseProduct> createPurchaseProduct(
            List<ProductPurchaseDto> productPurchaseList,
            Purchase purchase) throws InsufficientQuantityException {

        return productPurchaseList.stream().map(productPurchase -> {

            Product product = productService.decrementQuantity(
                    productPurchase.getIdProduct(),
                    productPurchase.getQuantity());

            PurchaseProduct purchaseProduct = PurchaseProduct.builder()
                    .product(product)
                    .purchaseProductQuantity(productPurchase.getQuantity())
                    .purchase(purchase)
                    .build();

            return purchaseProduct;

        }).collect(Collectors.toList());

    }

    @Override
    public void saveAllPurchaseProducts(
            List<PurchaseProduct> purchaseProductList) {

        purchaseProductList.forEach(purchaseProduct ->
            savePurchaseProduct(purchaseProduct)
        );
    }
}
