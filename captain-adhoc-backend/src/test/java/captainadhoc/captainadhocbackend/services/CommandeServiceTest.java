package captainadhoc.captainadhocbackend.services;

import captainadhoc.captainadhocbackend.domain.Commande;
import captainadhoc.captainadhocbackend.services.Interfaces.CommandeService;
import org.junit.jupiter.api.Test;
import captainadhoc.captainadhocbackend.repositories.CommandeRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class CommandeServiceTest {

    @Mock
    private CommandeRepository commandeRepository;

    @InjectMocks
    private CommandeService commandeService;

    @Test
    public void findAllCommandesTest() {
        // when: la méthode findAllCommandes est invoquée
        commandeService.findAllCommandes();

        // then: la méthode findAll du Repository associé est invoquée
        verify(commandeService.getCommandeRepository()).findAll();
    }

    @Test
    public void saveCommandeTest() {
        //given une Commande
        Commande commande = new Commande("20/20/2020","code");
        when(commandeService.getCommandeRepository().save(commande)).thenReturn(commande);

        // when: la méthode saveCommande est invoquée
        commandeService.saveCommande(commande);

        // then: la méthode save du CommandeRepository associé est invoquée
        verify(commandeService.getCommandeRepository()).save(commande);
    }
}