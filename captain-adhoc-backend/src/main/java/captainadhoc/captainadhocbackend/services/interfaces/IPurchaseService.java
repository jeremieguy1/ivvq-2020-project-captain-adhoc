package captainadhoc.captainadhocbackend.services.interfaces;

import captainadhoc.captainadhocbackend.domain.Member;
import captainadhoc.captainadhocbackend.dto.PurchaseDto;
import captainadhoc.captainadhocbackend.domain.Purchase;
import captainadhoc.captainadhocbackend.exceptions.InsufficientQuantityException;

import java.util.ArrayList;

public interface IPurchaseService {

    ArrayList<Purchase> findAllPurchases();

    void savePurchase(Purchase purchase);

    void newPurchase(PurchaseDto purchaseDto, Member user)
            throws InsufficientQuantityException;
}
