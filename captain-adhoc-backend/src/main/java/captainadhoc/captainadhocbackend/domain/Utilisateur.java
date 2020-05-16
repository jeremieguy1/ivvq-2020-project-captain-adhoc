package captainadhoc.captainadhocbackend.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@ToString(of = {"prenom", "nom", "nomUtilisateur"})
public class Utilisateur {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    @Pattern(regexp = "^[A-Za-z]*$")
    private String prenom;

    @NotEmpty
    @Pattern(regexp = "^[A-Za-z]*$")
    private String nom;

    @NotEmpty
    @Column(unique = true)
    @Pattern(regexp = "^[A-Za-z0-9]*$")
    private String nomUtilisateur;

    @NotEmpty
    private String motDePasse;

    @NotNull
    private Boolean isAdmin = false;

    @OneToMany(mappedBy = "utilisateur")
    private List<Commande> commandeList;
}
