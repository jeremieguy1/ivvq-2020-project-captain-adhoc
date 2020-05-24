package captainadhoc.captainadhocbackend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UtilisateurDto {
    private String nomUtilisateur;
    private Boolean isAdmin;
}

