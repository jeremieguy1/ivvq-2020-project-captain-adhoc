package captainadhoc.captainadhocbackend.controllers;

import captainadhoc.captainadhocbackend.domain.Member;
import captainadhoc.captainadhocbackend.dto.PurchaseDto;
import captainadhoc.captainadhocbackend.domain.Purchase;
import captainadhoc.captainadhocbackend.exceptions.InsufficientQuantityException;
import captainadhoc.captainadhocbackend.services.interfaces.IPurchaseService;
import captainadhoc.captainadhocbackend.services.interfaces.IMemberService;
import lombok.Setter;
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

@Setter
@RestController
@RequestMapping("/purchases")
public class PurchaseController {

    @Autowired
    private IPurchaseService purchaseService;

    @Autowired
    private IMemberService memberService;

    @GetMapping
    public ArrayList<Purchase> getPurchases() {

        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        Member member =
                memberService.findByUserName(auth.getName());

        if (member.getIsAdmin()) {
            return purchaseService.findAllPurchases();
        } else {
            return new ArrayList<>(member.getPurchaseList());
        }
    }

    @PostMapping
    public void postPurchase(@RequestBody PurchaseDto purchaseDto) {

        try {
            Authentication auth =
                    SecurityContextHolder.getContext().getAuthentication();

            Member member =
                    memberService.findByUserName(auth.getName());

            purchaseService.newPurchase(purchaseDto, member);

        } catch (InsufficientQuantityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }
}
