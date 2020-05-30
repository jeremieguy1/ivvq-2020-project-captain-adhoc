package captainadhoc.captainadhocbackend.controllers;

import captainadhoc.captainadhocbackend.domain.Member;
import captainadhoc.captainadhocbackend.domain.Product;
import captainadhoc.captainadhocbackend.services.interfaces.IMemberService;
import captainadhoc.captainadhocbackend.services.interfaces.IProductService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@Setter
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private IProductService productService;

    @Autowired
    private IMemberService memberService;

    @GetMapping
    public ArrayList<Product> getAllProducts() {
        return productService.findAllProducts();
    }


    @PutMapping("/{idProduct}")
    public void modifyQuantity(@RequestParam int quantity,
                               @PathVariable Long idProduct) {

        if (quantity < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        Member member =
                memberService.findByUserName(auth.getName());

        if (member.getIsAdmin()) {

            try {
                productService.modifyQuantity(idProduct, quantity);
            } catch (IllegalArgumentException e) {
                throw new ResponseStatusException(HttpStatus.CONFLICT);
            }

        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

    }

}
