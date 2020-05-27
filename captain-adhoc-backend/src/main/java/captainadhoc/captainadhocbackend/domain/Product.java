package captainadhoc.captainadhocbackend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Builder
public class Product {

    @Id
    @GeneratedValue
    private Long idProduct;

    @NotNull
    private int productQuantity;

    @NotEmpty
    private String productName;

    @NotEmpty
    private String productDescription;

    @URL
    private String productPicture;

    @NotNull
    private float productPrice;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<PurchaseProduct> purchaseProductList;

}
