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
public class Produit {

    @Id
    @GeneratedValue
    private Long id_produit;

    @NotNull
    private int quantite_produit;

    @NotEmpty
    private String nom_produit;

    @NotEmpty
    private String description_produit;

    @URL
    private String image_produit;

    @NotNull
    private float prix_produit;

    @JsonIgnore
    @OneToMany(mappedBy = "produit")
    private List<CommandeProduit> commandeProduitsList;

}
