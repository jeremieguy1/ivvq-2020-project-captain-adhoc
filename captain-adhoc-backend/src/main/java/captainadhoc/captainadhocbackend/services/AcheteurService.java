package captainadhoc.captainadhocbackend.services;

import captainadhoc.captainadhocbackend.domain.Acheteur;
import captainadhoc.captainadhocbackend.repositories.AcheteurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AcheteurService {

    @Autowired
    private AcheteurRepository acheteurRepository;

    private void saveAcheteur(Acheteur acheteur) {
        acheteurRepository.save(acheteur);
    }
}
