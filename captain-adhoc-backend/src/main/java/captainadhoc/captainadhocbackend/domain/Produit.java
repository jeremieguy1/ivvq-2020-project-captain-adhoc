package captainadhoc.captainadhocbackend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "produit")
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_produit;

    @NotNull
    private int quantite_produit;

    @NotEmpty
    private String nom_produit;

    @NotEmpty
    private String description_produit;

    @NotEmpty
    private String image_produit;

    @NotNull
    private float prix_produit;

    @JsonIgnore
    @OneToMany(mappedBy = "produit")
    private List<CommandeProduit> commandeProduitsList;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Marchand marchand;

    public Produit(int quantite_produit, String nom_produit, String description_produit, String image_produit, float prix_produit) {
        this.quantite_produit = quantite_produit;
        this.nom_produit = nom_produit;
        this.description_produit = description_produit;
        this.image_produit = image_produit;
        this.prix_produit = prix_produit;
    }
}
