package captainadhoc.captainadhocbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UtilisateurDto {

    private String prenom;
    private String nom;
    private String nomUtilisateur;
    private String motDePasse;
}