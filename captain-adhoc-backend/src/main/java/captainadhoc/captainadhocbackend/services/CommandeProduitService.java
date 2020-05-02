package captainadhoc.captainadhocbackend.services;

import captainadhoc.captainadhocbackend.domain.CommandeProduit;
import captainadhoc.captainadhocbackend.repositories.CommandeProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommandeProduitService {

    @Autowired
    private CommandeProduitRepository commandeProduitRepository;

    private void saveCommandeProduit(CommandeProduit commandeProduit) {
        commandeProduitRepository.save(commandeProduit);
    }
}
