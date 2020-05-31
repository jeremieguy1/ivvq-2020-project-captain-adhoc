package captainadhoc.captainadhocbackend.integration;

import captainadhoc.captainadhocbackend.domain.Purchase;
import captainadhoc.captainadhocbackend.domain.PurchaseProduct;
import captainadhoc.captainadhocbackend.domain.Product;
import captainadhoc.captainadhocbackend.domain.Member;
import captainadhoc.captainadhocbackend.services.interfaces.IPurchaseProductService;
import captainadhoc.captainadhocbackend.services.interfaces.IPurchaseService;
import captainadhoc.captainadhocbackend.services.interfaces.IProductService;
import captainadhoc.captainadhocbackend.services.interfaces.IMemberService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Transactional
@AllArgsConstructor
public class DataLoader {

    @Autowired
    private IProductService productService;

    @Autowired
    private IMemberService memberService;

    @Autowired
    private IPurchaseService purchaseService;

    @Autowired
    private IPurchaseProductService purchaseProductService;

    public void initProducts() {

        Member admin = Member.builder()
                .lastName("Kevin")
                .firstName("Marchand")
                .userName("marchand1")
                .password("mdp")
                .isAdmin(true)
                .build();

        Product product1 = Product.builder()
                .productQuantity(15)
                .productName("PS5")
                .productDescription("Encore une playstation de folie \\o/")
                .productPicture("https://urlz.fr/cHLz")
                .productPrice(1)
                .build();

        Product product2 = Product.builder()
                .productQuantity(16)
                .productName("CyberboX")
                .productDescription("Non comptant d'avoir " +
                        "les meilleures voitures au MONDE, " +
                        "Tesla propose la meilleure console " +
                        "de jeu grand public !")
                .productPicture("https://urlz.fr/cHLH")
                .productPrice(100000)
                .build();

        Product product3 = Product.builder()
                .productQuantity(2)
                .productName("Mad box")
                .productDescription("Cette console " +
                        "va révolutionner le du la de esport !")
                .productPicture("https://urlz.fr/cHJp")
                .productPrice(666)
                .build();

        Product product4 = Product.builder()
                .productQuantity(100)
                .productName("New retro +")
                .productDescription("Elle fera tourner " +
                        "les jeux dernières générations " +
                        "tels que tetris et même Donkey kong 64 ! " +
                        "Et tout àa pour seulement 1399,99€")
                .productPicture("https://urlz.fr/cHJz")
                .productPrice(10)
                .build();

        Product product5 = Product.builder()
                .productQuantity(5)
                .productName("Xbox Serie X")
                .productDescription("C'est partiiiii pour la console pc !")
                .productPicture("https://urlz.fr/cHLM")
                .productPrice(200)
                .build();

        List<Product> productList = new ArrayList<>();

        productList.add(product1);
        productList.add(product2);
        productList.add(product3);
        productList.add(product4);
        productList.add(product5);

        memberService.saveMember(admin);

        productService.saveProduct(product1);
        productService.saveProduct(product2);
        productService.saveProduct(product3);
        productService.saveProduct(product4);
        productService.saveProduct(product5);
    }

    public void run() {
        initProducts();
        initPurchases();
    }

    public void initPurchases() {

        Purchase purchase1 = Purchase.builder()
                .purchaseDate(new Date())
                .code("code")
                .build();

        Purchase purchase2 = Purchase.builder()
                .purchaseDate(new Date())
                .code("code")
                .build();

        Purchase purchase3 = Purchase.builder()
                .purchaseDate(new Date())
                .code("")
                .build();

        PurchaseProduct purchaseProduct = new PurchaseProduct();
        PurchaseProduct purchaseProduct2 = new PurchaseProduct();
        PurchaseProduct purchaseProduct3 = new PurchaseProduct();
        PurchaseProduct purchaseProduct4 = new PurchaseProduct();

        ArrayList<Product> productArrayList = productService.findAllProducts();

        purchaseProduct.setProduct(productArrayList.get(0));
        purchaseProduct2.setProduct(productArrayList.get(0));
        purchaseProduct3.setProduct(productArrayList.get(1));
        purchaseProduct4.setProduct(productArrayList.get(1));

        purchaseProduct.setPurchaseProductQuantity(1);
        purchaseProduct2.setPurchaseProductQuantity(2);
        purchaseProduct3.setPurchaseProductQuantity(3);
        purchaseProduct4.setPurchaseProductQuantity(4);

        purchaseProduct.setPurchase(purchase1);
        purchaseProduct2.setPurchase(purchase2);
        purchaseProduct3.setPurchase(purchase2);
        purchaseProduct4.setPurchase(purchase3);

        List<PurchaseProduct> purchaseProductList = new ArrayList<>();
        purchaseProductList.add(purchaseProduct);

        List<PurchaseProduct> purchaseProductList2 = new ArrayList<>();
        purchaseProductList2.add(purchaseProduct2);
        purchaseProductList2.add(purchaseProduct3);

        List<PurchaseProduct> purchaseProductList3 = new ArrayList<>();
        purchaseProductList3.add(purchaseProduct4);

        purchase1.setPurchaseProductList(purchaseProductList);
        purchase2.setPurchaseProductList(purchaseProductList3);
        purchase3.setPurchaseProductList(purchaseProductList3);

        purchaseService.savePurchase(purchase1);
        purchaseService.savePurchase(purchase2);
        purchaseService.savePurchase(purchase3);

        purchaseProduct.setPurchase(purchase1);
        purchaseProduct2.setPurchase(purchase2);
        purchaseProduct3.setPurchase(purchase2);
        purchaseProduct4.setPurchase(purchase3);

        purchaseProductService.savePurchaseProduct(purchaseProduct);
        purchaseProductService.savePurchaseProduct(purchaseProduct2);
        purchaseProductService.savePurchaseProduct(purchaseProduct3);
        purchaseProductService.savePurchaseProduct(purchaseProduct4);
    }
}
