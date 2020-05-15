package captainadhoc.captainadhocbackend.services.implementations;

import captainadhoc.captainadhocbackend.domain.Marchand;
import captainadhoc.captainadhocbackend.repositories.MarchandRepository;
import captainadhoc.captainadhocbackend.services.interfaces.IMarchandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MarchandService implements IMarchandService {

    @Autowired
    private MarchandRepository marchandRepository;

    @Override
    public Marchand saveMarchand(Marchand marchand) {
        return marchandRepository.save(marchand);
    }
}
