package captainadhoc.captainadhocbackend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "commande")
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_commande;

    @NotEmpty
    private String date_commande;

    @NotEmpty
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
