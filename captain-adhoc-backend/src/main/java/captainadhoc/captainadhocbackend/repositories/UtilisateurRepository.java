package captainadhoc.captainadhocbackend.repositories;

import captainadhoc.captainadhocbackend.domain.Utilisateur;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilisateurRepository extends CrudRepository<Utilisateur,Long> {
    public Utilisateur findByNomUtilisateur(String nomUtilisateur);
}
