package captainadhoc.captainadhocbackend.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "acheteurs")
public class Acheteur {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_acheteur;
    private String nom_acheteur;
    private String identifiant_acheteur;
}
