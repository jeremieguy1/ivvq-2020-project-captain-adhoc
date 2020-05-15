package captainadhoc.captainadhocbackend.services.implementations;

import captainadhoc.captainadhocbackend.domain.CommandeProduit;
import captainadhoc.captainadhocbackend.repositories.CommandeProduitRepository;
import captainadhoc.captainadhocbackend.services.interfaces.ICommandeProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommandeProduitService implements ICommandeProduitService {

    @Autowired
    private CommandeProduitRepository commandeProduitRepository;

    @Override
    public void saveCommandeProduit(CommandeProduit commandeProduit) {
        commandeProduitRepository.save(commandeProduit);
    }
}
