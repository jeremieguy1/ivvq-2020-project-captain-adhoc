package captainadhoc.captainadhocbackend.services.implementations;

import captainadhoc.captainadhocbackend.domain.Product;
import captainadhoc.captainadhocbackend.exceptions.InsufficientQuantityException;
import captainadhoc.captainadhocbackend.repositories.ProductRepository;
import captainadhoc.captainadhocbackend.services.interfaces.IProductService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Setter
@Service
public class ProductService implements IProductService {

    @Getter
    @Autowired
    private ProductRepository productRepository;

    @Override
    public ArrayList<Product> findAllProducts() {
        ArrayList<Product> productList = new ArrayList<>();
        Iterable<Product> productIterable = productRepository.findAll();

        if (productIterable != null) {
            productIterable.forEach(prod ->
                productList.add(prod)
            );
        }

        return productList;
    }

    @Override
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public void deleteAllProducts() {
        productRepository.deleteAll();
    }

    @Override
    public void modifyQuantity(Long idProduct, int quantity) {
        Optional<Product> product = productRepository.findById(idProduct);

        if (product.isPresent()) {
            product.get().setProductQuantity(quantity);
            productRepository.save(product.get());
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Product decrementQuantity(Long idProduct, int productQuantity) {

        Optional<Product> optionalProduct =
                productRepository.findById(idProduct);

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();

            if (product.getProductQuantity() - productQuantity < 0) {
                throw new InsufficientQuantityException(product);
            }

            product.setProductQuantity(
                    product.getProductQuantity() - productQuantity);

            return productRepository.save(product);
        }

        return null;
    }
}
