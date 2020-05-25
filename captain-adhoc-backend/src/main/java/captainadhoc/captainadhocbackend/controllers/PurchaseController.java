package captainadhoc.captainadhocbackend.controllers;

import captainadhoc.captainadhocbackend.domain.Member;
import captainadhoc.captainadhocbackend.dto.PurchaseDto;
import captainadhoc.captainadhocbackend.domain.Purchase;
import captainadhoc.captainadhocbackend.exceptions.InsufficientQuantityException;
import captainadhoc.captainadhocbackend.services.interfaces.IPurchaseService;
import captainadhoc.captainadhocbackend.services.interfaces.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {

    @Autowired
    private IPurchaseService purchaseService;

    @Autowired
    private IMemberService memberService;

    @GetMapping
    public ArrayList<Purchase> getPurchases() {
        return purchaseService.findAllPurchases();
    }

    @PostMapping("/order")
    public void postPurchase(@RequestBody PurchaseDto purchaseDto) {

        try {

            Authentication auth =
                    SecurityContextHolder.getContext().getAuthentication();

            Member user =
                    memberService.findByUserName(auth.getName());

            purchaseService.newPurchase(purchaseDto, user);

        } catch(InsufficientQuantityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }
}
