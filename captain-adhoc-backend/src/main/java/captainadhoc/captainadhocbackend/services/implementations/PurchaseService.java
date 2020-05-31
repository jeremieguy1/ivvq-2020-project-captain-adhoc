package captainadhoc.captainadhocbackend.services.implementations;

import captainadhoc.captainadhocbackend.domain.Member;
import captainadhoc.captainadhocbackend.dto.PurchaseDto;
import captainadhoc.captainadhocbackend.domain.Purchase;
import captainadhoc.captainadhocbackend.domain.PurchaseProduct;
import captainadhoc.captainadhocbackend.exceptions.InsufficientQuantityException;
import captainadhoc.captainadhocbackend.repositories.PurchaseRepository;
import captainadhoc.captainadhocbackend.services.interfaces.IPurchaseProductService;
import captainadhoc.captainadhocbackend.services.interfaces.IPurchaseService;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@Service
public class PurchaseService implements IPurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private IPurchaseProductService purchaseProductService;

    @Override
    public ArrayList<Purchase> findAllPurchases() {
        ArrayList<Purchase> purchaseArrayList = new ArrayList<>();
        Iterable<Purchase> purchaseIterable = purchaseRepository.findAll();

        if (purchaseIterable != null) {
            purchaseIterable.forEach(comm ->
                purchaseArrayList.add(comm)
            );
        }

        return purchaseArrayList;
    }

    @Override
    public void savePurchase(Purchase purchase) {
        purchaseRepository.save(purchase);
    }

    @Override
    public void newPurchase(PurchaseDto purchaseDto, Member member)
            throws InsufficientQuantityException {

        Purchase purchase = Purchase.builder()
                .purchaseDate(new Date())
                .code(purchaseDto.getCode())
                .member(member)
                .build();

        List<PurchaseProduct> purchaseProductList =
                purchaseProductService.createPurchaseProduct(
                        purchaseDto.getProductPurchaseList(),
                        purchase);

        purchase.setPurchaseProductList(purchaseProductList);

        savePurchase(purchase);
        purchaseProductService.saveAllPurchaseProducts(purchaseProductList);
    }

}
