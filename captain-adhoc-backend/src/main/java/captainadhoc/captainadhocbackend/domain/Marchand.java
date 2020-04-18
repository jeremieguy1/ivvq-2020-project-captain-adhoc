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
@Table(name = "marchands")
public class Marchand {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_marchand;
    private String identifiant_marchand;
}
