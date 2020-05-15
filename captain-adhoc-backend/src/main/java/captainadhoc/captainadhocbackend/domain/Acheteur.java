package captainadhoc.captainadhocbackend.domain;

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
public class Acheteur {

    @Id
    @GeneratedValue
    private Long id_acheteur;

    @NotEmpty
    private String nom_acheteur;

    @NotEmpty
    private String identifiant_acheteur;

    @OneToMany(mappedBy = "acheteur")
    private List<Commande> commandeList;
}
