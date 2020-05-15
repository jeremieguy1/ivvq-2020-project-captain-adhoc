package captainadhoc.captainadhocbackend.services.Interfaces;

import captainadhoc.captainadhocbackend.domain.Acheteur;
import captainadhoc.captainadhocbackend.repositories.AcheteurRepository;
import captainadhoc.captainadhocbackend.services.implementations.IAcheteurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AcheteurService implements IAcheteurService {

    @Autowired
    private AcheteurRepository acheteurRepository;

    @Override
    public void saveAcheteur(Acheteur acheteur) {
        acheteurRepository.save(acheteur);
    }
}
