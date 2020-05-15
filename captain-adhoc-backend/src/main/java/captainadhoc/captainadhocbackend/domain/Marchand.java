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
public class Marchand {

    @Id
    @GeneratedValue
    private Long id_marchand;

    @NotEmpty
    private String identifiant_marchand;

    @JsonIgnore
    @OneToMany(mappedBy = "marchand")
    private List<Produit> produitList;

    public Marchand(String identifiant_marchand) {
        this.identifiant_marchand = identifiant_marchand;
    }
}
