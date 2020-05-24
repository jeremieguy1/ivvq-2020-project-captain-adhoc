package captainadhoc.captainadhocbackend.exceptions;

import captainadhoc.captainadhocbackend.domain.Produit;

public class InsufficientQuantityException extends RuntimeException {

    public InsufficientQuantityException(Produit produit) {
        super("Quantité diponible insuffisante pour le produit " + produit.getNom_produit());
    }
}
