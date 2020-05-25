package captainadhoc.captainadhocbackend.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Builder
public class Purchase {

    @Id
    @GeneratedValue
    private Long idPurchase;

    @NotNull
    private Date purchaseDate;

    private String code;

    @OneToMany(mappedBy = "purchase")
    private List<PurchaseProduct> purchaseProductList;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Member member;

}
