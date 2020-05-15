package captainadhoc.captainadhocbackend.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class Commande {

    @Id
    @GeneratedValue
    private Long id_commande;

    @NotEmpty
    private String date_commande;

    private String code;

    @OneToMany(mappedBy = "commande")
    private List<CommandeProduit> commandeProduitsList;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Acheteur acheteur;

    public Commande(String date_commande, String code) {
        this.date_commande = date_commande;
        this.code = code;
    }
}
