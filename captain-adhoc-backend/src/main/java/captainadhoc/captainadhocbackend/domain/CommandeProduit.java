package captainadhoc.captainadhocbackend.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "commande_produit_assoc")
public class CommandeProduit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_commandeProduit;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Produit produit;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Commande commande;

    @NotEmpty
    private Long quantite_commade_produit;
}