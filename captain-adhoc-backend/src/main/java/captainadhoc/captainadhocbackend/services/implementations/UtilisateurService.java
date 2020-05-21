package captainadhoc.captainadhocbackend.services.implementations;

import captainadhoc.captainadhocbackend.domain.Utilisateur;
import captainadhoc.captainadhocbackend.exceptions.UtilisateurExisteException;
import captainadhoc.captainadhocbackend.repositories.UtilisateurRepository;
import captainadhoc.captainadhocbackend.services.interfaces.IUtilisateurService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

@Service
public class UtilisateurService implements IUtilisateurService, UserDetailsService {

    @Autowired
    @Getter
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Utilisateur saveUtilisateur(Utilisateur utilisateur) {
        if(findByNomUtilisateur(utilisateur.getNomUtilisateur()) != null){
            throw new UtilisateurExisteException(utilisateur.getNomUtilisateur());
        }
        utilisateur.setMotDePasse(bCryptPasswordEncoder.encode(utilisateur.getMotDePasse()));
        return utilisateurRepository.save(utilisateur);
    }

    public Utilisateur findById(Long id){
        return utilisateurRepository.findById(id).orElse(null);
    }

    public Utilisateur findByNomUtilisateur(String nomUtilisateur){
        return utilisateurRepository.findByNomUtilisateur(nomUtilisateur);
    }

    @Override
    public UserDetails loadUserByUsername(String nomUtilisateur) throws UsernameNotFoundException {
        Utilisateur utilisateur = utilisateurRepository.findByNomUtilisateur(nomUtilisateur);
        if (utilisateur == null) {
            throw new UsernameNotFoundException(nomUtilisateur);
        }
        return new User(utilisateur.getNomUtilisateur(), utilisateur.getMotDePasse(), emptyList());
    }
}
