package captainadhoc.captainadhocbackend.services.interfaces;

import captainadhoc.captainadhocbackend.domain.Product;

import java.util.ArrayList;

public interface IProductService {

    ArrayList<Product> findAllProducts();

    void saveProduct(Product produit);

    void modifyQuantity(Long idProduct, int quantity);

    Product decrementQuantity(Long idProduct, int produitQuantity);
}
