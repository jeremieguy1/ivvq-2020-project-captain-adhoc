package captainadhoc.captainadhocbackend.services;

import captainadhoc.captainadhocbackend.domain.Marchand;
import captainadhoc.captainadhocbackend.repositories.MarchandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MarchandService {

    @Autowired
    private MarchandRepository marchandRepository;

    public Marchand saveMarchand(Marchand marchand) {
        return marchandRepository.save(marchand);
    }
}
