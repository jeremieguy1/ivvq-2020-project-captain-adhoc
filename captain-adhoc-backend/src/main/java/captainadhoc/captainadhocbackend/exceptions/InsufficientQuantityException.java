package captainadhoc.captainadhocbackend.exceptions;

import captainadhoc.captainadhocbackend.domain.Product;

public class InsufficientQuantityException extends RuntimeException {

    public InsufficientQuantityException(Product product) {
        super("Quantité diponible insuffisante pour le produit "
                + product.getProductName());
    }
}
