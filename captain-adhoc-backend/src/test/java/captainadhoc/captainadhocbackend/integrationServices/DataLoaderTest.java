package captainadhoc.captainadhocbackend.integrationServices;

import captainadhoc.captainadhocbackend.DataLoader;
import captainadhoc.captainadhocbackend.services.implementations.ICommandeProduitService;
import captainadhoc.captainadhocbackend.services.implementations.ICommandeService;
import captainadhoc.captainadhocbackend.services.implementations.IMarchandService;
import captainadhoc.captainadhocbackend.services.implementations.IProduitService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

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

    @Before
    public void setup() {
        dataLoader = new DataLoader(produitService, marchandService, commandeService, commandeProduitService);
    }

    @Test
    public void testInitMethodCalls() throws Exception {
        DataLoader spy = spy(dataLoader);
        // when: la méthode run est appelée
        spy.run(null);
        // then: la méthode initProduit() est invoquée
        verify(spy).initProduit();
        // then: la méthode initCommandes() est invoquée
        verify(spy).initCommandes();

    }
}