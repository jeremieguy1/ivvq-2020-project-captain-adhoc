package captainadhoc.captainadhocbackend.services;

import captainadhoc.captainadhocbackend.domain.Commande;
import captainadhoc.captainadhocbackend.repositories.CommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommandeService {

    @Autowired
    private CommandeRepository commandeRepository;

    public void saveCommande(Commande commande) {
        commandeRepository.save(commande);
    }
}
