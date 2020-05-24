package captainadhoc.captainadhocbackend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Builder
public class CommandeProduit {

    @Id
    @GeneratedValue
    private Long id_commandeProduit;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Produit produit;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Commande commande;

    @NotNull
    private int quantite_commande_produit;

}
