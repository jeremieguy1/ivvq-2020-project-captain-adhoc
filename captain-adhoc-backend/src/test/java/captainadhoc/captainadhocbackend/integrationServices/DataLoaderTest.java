package captainadhoc.captainadhocbackend.integrationServices;

import captainadhoc.captainadhocbackend.DataLoader;
import captainadhoc.captainadhocbackend.services.interfaces.ICommandeProduitService;
import captainadhoc.captainadhocbackend.services.interfaces.ICommandeService;
import captainadhoc.captainadhocbackend.services.interfaces.IMarchandService;
import captainadhoc.captainadhocbackend.services.interfaces.IProduitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;

@SpringBootTest
public class DataLoaderTest {

    private DataLoader dataLoader;

    @Mock
    private IProduitService produitService;

    @Mock
    private IMarchandService marchandService;

    @Mock
    private ICommandeService commandeService;

    @Mock
    private ICommandeProduitService commandeProduitService;

    @BeforeEach
    public void setup() {
        dataLoader = new DataLoader(produitService, marchandService, commandeService, commandeProduitService);
    }

    @Test
    public void testInitMethodCalls() throws Exception {
        DataLoader spy = spy(dataLoader);

        doNothing().when(spy).initProduit();
        doNothing().when(spy).initCommandes();

        // when: la méthode run est appelée
        spy.run(new DefaultApplicationArguments());
        // then: la méthode initProduit() est invoquée
        verify(spy).initProduit();
        // then: la méthode initCommandes() est invoquée
        verify(spy).initCommandes();

    }
}