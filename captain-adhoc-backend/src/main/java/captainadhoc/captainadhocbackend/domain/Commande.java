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

}
