package captainadhoc.captainadhocbackend.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
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

    @NotNull
    private Date date_commande;

    private String code;

    @OneToMany(mappedBy = "commande")
    private List<CommandeProduit> commandeProduitsList;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Utilisateur utilisateur;

    public Commande(Date date_commande, String code) {
        this.date_commande = date_commande;
        this.code = code;
    }
}
