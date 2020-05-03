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
@Table(name = "acheteur")
public class Acheteur {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_acheteur;

    @NotEmpty
    private String nom_acheteur;

    @NotEmpty
    private String identifiant_acheteur;

    @JsonIgnore
    @OneToMany(mappedBy = "acheteur")
    private List<Commande> commandeList;
}
